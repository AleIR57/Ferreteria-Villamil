package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class Administrador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
    }

    public void activityApartadoUsuarios(View v){
        Intent vApartadoUsuarios = new Intent(this, ApartadoUsuarios.class);
        startActivity(vApartadoUsuarios);
    }

    public void activityApartadoAlmacen(View v){
        Intent vApartadoAlmacen = new Intent(this, ApartadoAlmacen.class);
        startActivity(vApartadoAlmacen);
    }

    public void activityInformes(View v){
        Intent vInforme = new Intent(this, Informes.class);
        startActivity(vInforme);
    }

    @Override public void onBackPressed() { }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if(keyCode== KeyEvent.KEYCODE_BACK) { return false; } return super.onKeyDown(keyCode, event); }
}