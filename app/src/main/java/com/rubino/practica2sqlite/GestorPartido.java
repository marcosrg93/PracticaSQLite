package com.rubino.practica2sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GestorPartido  {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorPartido(Context c) {
        abd = new Ayudante(c);
    }
    public void open() {
        bd = abd.getWritableDatabase();
    }
    public void openRead() {
        bd = abd.getReadableDatabase();
    }
    public void close() {
        abd.close();
    }

    public long insert(Partido objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPartido.VALORACION, objeto.getValoracion());
        valores.put(Contrato.TablaPartido.IDJUGADOR, objeto.getId());
        valores.put(Contrato.TablaPartido.CONTRINCANTE, objeto.getValoracion());
        long id = bd.insert(Contrato.TablaPartido.TABLA, null, valores);
        return id;
    }

    public int delete(Partido objeto) {
        String condicion = Contrato.TablaPartido._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.delete(Contrato.TablaPartido.TABLA, condicion,argumentos);
        return cuenta;
    }

    public int delete(int id){
        return delete(new Partido(id, null, 0, 0));
    }


    public static Partido getRow(Cursor c) {
        Partido objeto = new Partido();
        objeto.setId(c.getLong(0));
        objeto.setValoracion(c.getInt(1));
        objeto.setIdjugador(c.getInt(2));
        objeto.setContrincante(c.getString(3));
        return objeto;
    }

    public Cursor getCursor() {
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null, null, null, null, null, null);
        return cursor;
    }
}
