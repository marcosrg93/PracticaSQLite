package com.rubino.practica2sqlite;

import java.io.Serializable;


public class Partido implements Serializable, Comparable <Partido> {

    private  long id;
    private  String contrincante;
    private int valoracion, idjugador;


    public Partido() {
        this(0, "", 0, 0);
    }

    public Partido(long id, String contrincante, int valoracion, int idjugador) {
        this.id = id;
        this.contrincante = contrincante;
        this.valoracion = valoracion;
        this.idjugador = idjugador;
    }


    //Getter y Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContrincante() {
        return contrincante;
    }

    public void setContrincante(String contrincante) {
        this.contrincante = contrincante;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public int getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(int idjugador) {
        this.idjugador = idjugador;
    }

    @Override
    public int compareTo(Partido another) {
        return 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partido partido = (Partido) o;

        if (id != partido.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Partido{" +
                "id=" + id +
                ", contrincante='" + contrincante + '\'' +
                ", valoracion=" + valoracion +
                ", idjugador=" + idjugador +
                '}';
    }


}
