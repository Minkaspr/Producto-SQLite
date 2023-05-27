package com.example.sem09_ejerc1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {


    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // El nombre de la base de datos es 'productos'
    @Override
    public void onCreate(SQLiteDatabase productos) {
        // Creamos la tabla producto
        productos.execSQL("CREATE TABLE producto (codigo int primary key, nombre text, precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase productos, int i, int i1) {

    }
}
