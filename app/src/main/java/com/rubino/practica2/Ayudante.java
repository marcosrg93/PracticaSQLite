package com.rubino.practica2;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Ayudante extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "jugadores.sqlite";
    public static final int DATABASE_VERSION = 2;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;


        sql = "create table " + Contrato.TablaPartido.TABLA + " (" +
                Contrato.TablaPartido._ID + " integer primary key autoincrement, " +
                Contrato.TablaPartido.VALORACION + " integer, " +
                Contrato.TablaPartido.IDJUGADOR + " integer, " +
                Contrato.TablaPartido.CONTRINCANTE + " text" +
                ")";
        db.execSQL(sql);

        sql = "create table " + Contrato.TablaJugador.TABLA + "( " +
                Contrato.TablaJugador._ID + " integer primary key autoincrement, " +
                Contrato.TablaJugador.NOMBRE + " text, " +
                Contrato.TablaJugador.TELEFONO + " text, " +
                Contrato.TablaJugador.FNAC + " text" +
                ")";
        Log.v("sql", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            //Creamos una tabla auxiliar para los datos ya almacenados
            String sql = "CREATE TABLE respaldo (id INTEGER, nombre TEXT, telefono TEXT, valoracion INTEGER, fnac TEXT)";

            db.execSQL(sql);

            //Insertamos los datos de la tabla a modificar en la tabla anteriormente creada
            sql = "INSERT INTO respaldo SELECT * FROM jugador";

            db.execSQL(sql);

            //Una vez tenemos los datos en la tabla auxiliar,borramos la tabla que vamos a modificar
            sql = "drop table if exists " + Contrato.TablaJugador.TABLA;

            db.execSQL(sql);

            //Llamamos al onCreate para que vuelva a crear la tabla jugadores
            onCreate(db);

            //Insertamos los datos en la tabla jugador una vez se ha modficado
            sql = "INSERT INTO " + Contrato.TablaJugador.TABLA +
                    " (" +
                    Contrato.TablaJugador.NOMBRE + " , " +
                    Contrato.TablaJugador.TELEFONO + " , " +
                    Contrato.TablaJugador.FNAC +
                    ") SELECT nombre, telefono, fnac FROM respaldo";

            db.execSQL(sql);

            //Insertamos los datos en la tabla Partido
            sql = "INSERT INTO " + Contrato.TablaPartido.TABLA + " (" +
                    Contrato.TablaPartido.VALORACION + " , "
                    + Contrato.TablaPartido.IDJUGADOR +
                    " ) SELECT valoracion, " + Contrato.TablaJugador._ID + " FROM respaldo res INNER JOIN " +
                    Contrato.TablaJugador.TABLA + " juga WHERE res.nombre=juga." + Contrato.TablaJugador.NOMBRE +
                    " AND res.telefono=juga." + Contrato.TablaJugador.TELEFONO +
                    " AND res.fnac=juga." + Contrato.TablaJugador.FNAC;

            db.execSQL(sql);

            //Actualizamos la tabla Partidos
            sql = "UPDATE " + Contrato.TablaPartido.TABLA + " SET "
                    + Contrato.TablaPartido.CONTRINCANTE + "= s"
                    +Contrato.TablaPartido.CONTRINCANTE;

            db.execSQL(sql);

            //Borramos la tabla auxiliar
            sql = "drop table respaldo";

            db.execSQL(sql);


    }//onUpgrade
}//Ayudante