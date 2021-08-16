/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: Ariela
 *
 * Created on 10 de Outubro de 2020, 14:58
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.log;

import java.util.Random;

public class Simulador {
	
	public static Tempo proximoEvento(Tempo t1, Tempo t2, Tempo t3){
		if(t1.compareTo(t2) < 0)
			if(t1.compareTo(t3) < 0)
				return t1;
			else
				return t3;
		else if(t2.compareTo(t3) < 0)
			return t2;
		
		return t3;
	}
	
	public void simulador(double qtd_caixas, double relacao_chegada_capacidade, double tempo_coleta) throws IOException{

		//variaveis
		double tempo_simulacao = 1200000;
		int fila = 0;
		double tempo = 0.0;

		//estrutura de dados
		ControleCaixas c = new ControleCaixas();

		//v.a. Exponencial
		double tempo_medio_atendimento = 1 / 60.0;
		double tempo_medio_chegada =  qtd_caixas * relacao_chegada_capacidade / 60.0;

		Random rand = new Random(2);

		double chegada_cliente = (-1.0 / tempo_medio_chegada) * Math.log(1.0 - rand.nextDouble());

		double saida_cliente;
		
		//para armazenar o tempo atual
		Tempo chegada = new Tempo(chegada_cliente, TipoEvento.ENTRADA);
		Tempo coleta = new Tempo(tempo_coleta, TipoEvento.COLETA);
		Tempo t;

		//armazenar E[N] e E[W]
		MedidaDesempenho en = new MedidaDesempenho();
		MedidaDesempenho ewEntrada = new MedidaDesempenho();
		MedidaDesempenho ewSaida = new MedidaDesempenho();
		
		//e[n] e e[w] para gerar o relatório
		double enMonitoramento;
		double ewMonitoramento;

		File monitor = new File("monitoramento" + qtd_caixas + "_" + relacao_chegada_capacidade + ".txt");
		BufferedWriter moni = new BufferedWriter(new FileWriter(monitor));
		
		//lógica de simulação
		while(tempo < tempo_simulacao){

			// nao existe cliente sendo atendido no momento atual,
			// de modo que a simulacao pode avancar no tempo para
			// a chegada do proximo cliente
			/* Essa função deve retornar verdade se existe algum
			 * caixa com saida_atendimento == 0.0
			 * Complexidade: O[1]
			 */
			if(c.caixas.isEmpty()){
				t = proximoEvento(chegada, chegada, coleta);
			}
			else{
				// checa se chegada cliente é menor do que saida_atendimento
				// de qualquer caixa da loja
				t = proximoEvento(chegada, c.get(), coleta);
			}

			tempo = t.tempo;

			switch(t.tipo){
				case ENTRADA:

					// evento de chegada de cliente
					fila++;

					// indica que o caixa esta ocioso
					// logo, pode-se comecar a atender
					// o cliente que acaba de chegar
					if(c.size() < qtd_caixas)
						c.update(tempo, TipoEvento.SAIDA);

					chegada_cliente = tempo + ((-1.0/ tempo_medio_chegada) * log(rand.nextDouble()));
					chegada.tempo = chegada_cliente;
					
					//calculo do E[N]
					en.somaAreas += en.numeroEventos * (tempo - en.tempoAnterior);
					en.tempoAnterior = tempo;
					en.numeroEventos++;

					//calculo do E[W]
					ewEntrada.somaAreas += ewEntrada.numeroEventos * (tempo - ewEntrada.tempoAnterior);
					ewEntrada.tempoAnterior = tempo;
					ewEntrada.numeroEventos++;
					
					break;
				
				case SAIDA:
					// evento executado se houver saida de clien
					// ou ainda se houver chegada de cliente, maste
					// ou ainda se houver chegada de cliente, mas
					// o caixa estiver ocioso.
					// a cabeca da fila nao consiste no cliente em atendimento.
					// o cliente que comeca a ser atendido portanto, sai da fila,
					// e passa a estar ainda no comercio, mas em atendimento no caixa.
					c.remove();

					// verifica se ha cliente na fila
					if(fila > 0){

						fila--;
						saida_cliente = tempo + ((-1.0/tempo_medio_atendimento) * log(rand.nextDouble()));
						c.update(saida_cliente, TipoEvento.SAIDA);
					}
					
					if(en.tempoAnterior != tempo){
						//calculo do E[N]
						en.somaAreas += en.numeroEventos * (tempo - en.tempoAnterior);
						en.tempoAnterior = tempo;
						en.numeroEventos--;

						//calculo do E[W]
						ewSaida.somaAreas += ewSaida.numeroEventos * (tempo - ewSaida.tempoAnterior);
						ewSaida.tempoAnterior = tempo;
						ewSaida.numeroEventos++;
					}
					
					break;
				
				case COLETA:
					//Calculando as medidads de interesse no momento de coleta
					enMonitoramento = (en.somaAreas + (en.numeroEventos * (coleta.tempo - en.tempoAnterior))) / coleta.tempo;
					ewMonitoramento = ((ewEntrada.somaAreas + (ewEntrada.numeroEventos * (coleta.tempo - ewEntrada.tempoAnterior))) - (ewSaida.somaAreas + (ewSaida.numeroEventos * (coleta.tempo - ewSaida.tempoAnterior)))) / (double) ewEntrada.numeroEventos;

					//Escrevendo valores no relatório
					moni.append(enMonitoramento + "\t" + ewMonitoramento +  "\n");
					
					//atualizando o objeto com t de coleta para o próximo tempo de coleta
					coleta.tempo += tempo_coleta;
					break;
			}
		}

		//fazendo o calculo da ultima area dos graficos antes do termino da simulacao
		ewSaida.somaAreas += ewSaida.numeroEventos * (tempo - ewSaida.tempoAnterior);
		ewEntrada.somaAreas += ewEntrada.numeroEventos * (tempo - ewEntrada.tempoAnterior);

		double enF = en.somaAreas / tempo;
		double ew = (ewEntrada.somaAreas - ewSaida.somaAreas) / ewEntrada.numeroEventos;
		double lambda = ewEntrada.numeroEventos / tempo;

		System.out.println("E[N]: " + enF);
		System.out.println("E[W]: " + ew);
		System.out.println("Lambda: " + lambda);
		//Little --> en = lambda * ew
		//Little --> en - lambda * ew ~ 0.0
		System.out.printf("Little: %.13f\n",  (Math.abs(enF - lambda * ew)));
		System.out.println("--------------------------------------------------------------");
		System.out.println("");
//        System.out.printf("Validação de Little: %.20f\n", (Math.abs(enF - lambda * ew)));

		moni.close();

	}
	
}
