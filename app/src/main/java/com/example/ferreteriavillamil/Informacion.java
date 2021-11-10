package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Informacion extends AppCompatActivity {

    ImageButton volver;
    String idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        idUsuario = getIntent().getStringExtra("idUsuario");
        volver = findViewById(R.id.imageButtonVolver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vVolver = new Intent(Informacion.this, Clientes.class);
                vVolver.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vVolver);
            }
        });
    }
}