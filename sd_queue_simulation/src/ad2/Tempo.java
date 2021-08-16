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
public class Tempo implements Comparable<Tempo>{
	public double tempo;
	public TipoEvento tipo;

	public Tempo(double tempo, TipoEvento tipo) {
		this.tempo = tempo;
		this.tipo = tipo;
	}

	@Override
	public int compareTo(Tempo t) {
		if(this.tempo > t.tempo)
			return 1;
		else if(this.tempo < t.tempo)
			return -1;
		else
			return 0;
	}
}
