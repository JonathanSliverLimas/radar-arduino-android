package com.jonathanlimas.radar.Modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    final String crearTablaUsuarios = "create table usuarios(" +
            "run INTEGER PRIMARY KEY, " +
            "nombre TEXT, " +
            "apellido TEXT, " +
            "edad INTEGER,  " +
            "password TEXT, " +
            "rol TEXT )";

    final String crearTablaRegistros = "create table registros(" +
            "codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "fecha TEXT, " +
            "hora TEXT," +
            "posicion INTEGER," +
            "distancia INTEGER)";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(crearTablaUsuarios);
        sqLiteDatabase.execSQL(crearTablaRegistros);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
