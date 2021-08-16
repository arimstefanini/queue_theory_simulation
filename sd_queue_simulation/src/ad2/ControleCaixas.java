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
import java.util.PriorityQueue;
import java.util.Queue;

public class ControleCaixas {

    Queue<Tempo> caixas = new PriorityQueue<>();

    /* Essa função deve retornar o primeiro da fila
     * ou seja, qualquer caixa que esteja ocioso
     * Complexidade: O[1]
     */
    Tempo get() {
        return this.caixas.peek();
    }

    /* com a atualização constante do contador 
     *do tamanho da lista
     * Complexidade: O[1]
     */
    int size(){
        return this.caixas.size();
    }

     /* Essa função deve retornar algum o menor valor o qual
     * será removido como atendimento bem sucedido
     * Complexidade: O[1]
     */
    Tempo remove(){
        return this.caixas.poll();
    }

    /* Essa função deve atualizar a saida_atendimento
     * do caixa e ordena da mesma forma que uma arvore
     * Complexidade: O[2*log(n)]
     */
    void update(double tempo, TipoEvento t) {
        this.caixas.add(new Tempo(tempo, t));
    }
}
