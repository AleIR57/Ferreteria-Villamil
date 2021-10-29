package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ApartadoAlmacen extends AppCompatActivity {
    ImageButton administrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartado_almacen);
        administrador = findViewById(R.id.imageButtonAdministrador);

        administrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(ApartadoAlmacen.this, Administrador.class);
                startActivity(vDomicilio);
            }
        });
    }

    public void activityApartadoCategorias2(View v){
        Intent vApartadoCategorias2 = new Intent(this, ApartadoCategorias2.class);
        startActivity(vApartadoCategorias2);
    }

    public void activityApartadoCategorias(View v){
        Intent vApartadoCategorias = new Intent(this, ApartadoCategorias.class);
        startActivity(vApartadoCategorias);
    }
}