package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Informacion extends AppCompatActivity {

    ImageButton volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        volver = findViewById(R.id.imageButtonVolver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vVolver = new Intent(Informacion.this, Clientes.class);
                startActivity(vVolver);
            }
        });
    }
}