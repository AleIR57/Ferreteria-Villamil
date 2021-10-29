package com.example.ferreteriavillamil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase bd){
        bd.execSQL("CREATE table productos(idProducto integer primary key, nombre text, marca text, precio text, imagen text, cantidad text, idCategoria integer)");
        bd.execSQL("CREATE table tarjetas(idTarjeta integer primary key, nombreTitular text, numeroTarjeta text, codigoCVC text, fechaExpiracion text, nombreEntidadBancaria text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion){
        bd.execSQL("drop table if exists productos");
        bd.execSQL("CREATE table productos(idProducto integer primary key, nombre text, marca text, precio text, imagen text, cantidad integer, idCategoria integer)");
        bd.execSQL("drop table if exists tarjetas");
        bd.execSQL("CREATE table tarjetas(idTarjeta integer primary key, nombreTitular text, numeroTarjeta text, codigoCVC text, fechaExpiracion text, nombreEntidadBancaria text)");
    }
}
