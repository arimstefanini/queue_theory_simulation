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
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Simulador s = new Simulador();
        Scanner scan = new Scanner(System.in);

        System.out.println("Escolha uma opcao para execução do programa");
        System.out.println("1-para executar todos os cenários propostos");
        System.out.println("2-para executar proprio cenários");
        int opcao = scan.nextInt();

        switch (opcao) {

            case 1:
                
                System.out.println("Relação entre a chegada e capacidade total de atendimento == 0,4 com 5 caixas");
                s.simulador(5, 0.4,600.0);
                System.out.println("Relação entre a chegada e capacidade total de atendimento == 0,8 com 5 caixas");
                s.simulador(5, 0.8, 600.0);
                System.out.println("Relação entre a chegada e capacidade total de atendimento == 0,9 com 5 caixas");
                s.simulador(5, 0.9, 600.0);
                System.out.println("Relação entre a chegada e capacidade total de atendimento == 0,99 com 5 caixas ");
                s.simulador(5, 0.99, 600.0);

                System.out.println("Relação entre a chegada e capacidade total de atendimento == 0,4 com 10 caixas ");
                s.simulador(10, 0.4, 600.0);
                System.out.println("Relação entre a chegada e capacidade total de atendimento == 0,8 com 10 caixas ");
                s.simulador(10, 0.8, 600.0);
                System.out.println("Relação entre a chegada e capacidade total de atendimento == 0,9 com 10 caixas ");
                s.simulador(10, 0.9, 600.0);
                System.out.println("Relação entre a chegada e capacidade total de atendimento == 0,99 com 10 caixas ");
                s.simulador(10, 0.99, 600.0);

                break;

            case 2:
                //variaveis para leitura de parâmetros de execução do simulador
                int numero_caixas;
                double relacao_chegada;
                double tempo_coleta;
                //coletando os parâmetros de execução
                System.out.print("Informe a quantidade de caixas disponíveis para atendimento (int): ");
                numero_caixas = scan.nextInt();
                System.out.print("Informe relação entre chegada e capacidade total de atendimento (double com virgura): ");
                relacao_chegada = scan.nextDouble();
                System.out.print("Informe intervalo de coleta (double): ");
                tempo_coleta = scan.nextDouble();
                s.simulador(numero_caixas, relacao_chegada, tempo_coleta);

                break;
        }

    }
}
