/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: flavio
 *
 * Created on 2 de Abril de 2020, 14:58
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

typedef struct Info_ {
	unsigned long int numeroEventos; //numero de elementos
	long double somaAreas; //soma das areas
	long double tempoAnterior; //tempo do evento anterior
} Info;

void iniciaInfo(Info *info) {
	info->numeroEventos = 0;
	info->somaAreas = 0.0;
	info->tempoAnterior = 0.0;
}

/**
 * Retorna um valor contido no intervalo entre (0,1]
 * @return 
 */
double aleatorio() {
	//resultado sera algo entre [0,0.999999...] proximo a 1.0
	double u = rand() / ((double) RAND_MAX + 1);
	//limitando entre (0,1]
	u = 1.0 - u;
	return u;
}

double minimo(double n1, double n2) {
	if (n1 < n2)
		return n1;
	return n2;
}

/**
 * Simulador de um caixa onde clientes chegam e sao atendidos no modelo FIFO.
 * O tempo entre a chegada de clientes, bem como o tempo de atendimento devem 
 * ser gerados de maneira pseudoaleatoria atraves da v.a. exponencial.
 *  
 * Utilizacao ou Ocupacao = fracao de tempo que o caixa permanecera ocupado.
 * Little: E[N] = \lambda * E[w]
 */
int main() {
	srand(time(NULL));

	double tempo_medio_clientes;
	printf("Informe o tempo medio entre a chegada de clientes (segundos): ");
	scanf("%lF", &tempo_medio_clientes);
	tempo_medio_clientes = 1.0 / tempo_medio_clientes;

	double tempo_medio_atendimento;
	printf("Informe o tempo medio gasto para atender cada cliente (segundos): ");
	scanf("%lF", &tempo_medio_atendimento);
	tempo_medio_atendimento = 1.0 / tempo_medio_atendimento;

	//tempo decorrido tempo da simulacao
	double tempo;

	double tempo_simulacao;
	printf("Informe o tempo total de simulacao (segundos): ");
	scanf("%lF", &tempo_simulacao);

	//armazena o tempo de chegada do proximo cliente
	double chegada_cliente = (-1.0 / tempo_medio_clientes) * log(aleatorio());

	//armazena o tempo em que o cliente que estiver em atendimento saira do comercio
	//saida_atendimento == 0.0 indica caixa ocioso
	double saida_atendimento = 0.0;

	double fila = 0.0;

	//somar os tempos de atendimento, para no final calcularmos a ocupacao.
	double soma_atendimentos = 0.0;

        //variaveis para o calculo de E[N] e E[W]
	Info en;
	Info ewEntrada;
	Info ewSaida;

	iniciaInfo(&en);
	iniciaInfo(&ewEntrada);
	iniciaInfo(&ewSaida);

	//logica da simulacao
	while (tempo <= tempo_simulacao) {
		//nao existe cliente sendo atendido no momento atual,
		//de modo que a simulacao pode avancar no tempo para
		//a chegada do proximo cliente
		if (!saida_atendimento)
			tempo = chegada_cliente;
		else {
			tempo = minimo(chegada_cliente, saida_atendimento);
		}


		if (tempo == chegada_cliente) {
			//printf("Chegada de cliente: %lF\n", chegada_cliente);
			//evento de chegada de cliente
			fila++;
			//printf("fila: %lF\n", fila);
			//indica que o caixa esta ocioso
			//logo, pode-se comecar a atender
			//o cliente que acaba de chegar
			if (!saida_atendimento) {
				saida_atendimento = tempo;
			}

			//gerar o tempo de chegada do proximo cliente
			chegada_cliente = tempo + (-1.0 / tempo_medio_clientes) * log(aleatorio());

			//calculo do E[N]
			en.somaAreas += en.numeroEventos * (tempo - en.tempoAnterior);
			en.tempoAnterior = tempo;
			en.numeroEventos++;

			//calculo do E[W]
			ewEntrada.somaAreas += ewEntrada.numeroEventos * (tempo - ewEntrada.tempoAnterior);
			ewEntrada.tempoAnterior = tempo;
			ewEntrada.numeroEventos++;
		} else {
			//evento executado se houver saida de cliente
			//ou ainda se houver chegada de cliente, mas
			//o caixa estiver ocioso.
			//a cabeca da fila nao consiste no cliente em atendimento.
			//o cliente que comeca a ser atendido portanto, sai da fila,
			//e passa a estar ainda no comercio, mas em atendimento no caixa.

			//verifica se ha cliente na fila
			if (fila) {
				fila--;

				double tempo_atendimento = (-1.0 / tempo_medio_atendimento) * log(aleatorio());
				saida_atendimento = tempo + tempo_atendimento;
				soma_atendimentos += tempo_atendimento;
			} else {
				saida_atendimento = 0.0;
			}


			if (en.tempoAnterior < tempo) {
				//calculo do E[N]
				en.somaAreas += en.numeroEventos * (tempo - en.tempoAnterior);
				en.tempoAnterior = tempo;
				en.numeroEventos--;

				//calculo do E[W]
				ewSaida.somaAreas += ewSaida.numeroEventos * (tempo - ewSaida.tempoAnterior);
				ewSaida.tempoAnterior = tempo;
				ewSaida.numeroEventos++;
			}
		}
	}

	if (saida_atendimento > tempo)
		soma_atendimentos -= (saida_atendimento - tempo);

        //fazendo o calculo da ultima area dos graficos antes do termino da simulacao
	ewSaida.somaAreas += ewSaida.numeroEventos * (tempo - ewSaida.tempoAnterior);
	ewEntrada.somaAreas += ewEntrada.numeroEventos * (tempo - ewEntrada.tempoAnterior);

	double enF = en.somaAreas / tempo;
	double ew = (ewEntrada.somaAreas - ewSaida.somaAreas)/ (double) ewEntrada.numeroEventos;
	double lambda = ewEntrada.numeroEventos / tempo;

	printf("Ocupacao: %.5lF\n", soma_atendimentos / tempo);
	printf("E[N]: %lF\n", enF);
	printf("E[W]: %lF\n", ew);
	//Little --> en = lambda * ew
	//Little --> en - lambda * ew ~ 0.0
        printf("Validação Little: %2.20lF\n", (enF - lambda * ew));

	getchar();
	getchar();

	return (EXIT_SUCCESS);
}

