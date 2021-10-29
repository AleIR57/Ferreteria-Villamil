package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditarTarjeta extends AppCompatActivity {

    TextView nombreTitular, numeroTarjeta, codigoCVC, fechaExpiracion, nombreEntidadBancaria;
    Button modificar;
    int idTarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarjeta);


        ListElementTarjeta element = (ListElementTarjeta) getIntent().getSerializableExtra("ListElementTarjeta");
        idTarjeta= element.getIdTarjeta();

        nombreTitular = findViewById(R.id.EditTextNombreTitular);
        numeroTarjeta = findViewById(R.id.EditTextNumeroTarjeta);
        codigoCVC = findViewById(R.id.EditTextCodigoCVC);
        fechaExpiracion = findViewById(R.id.EditTextFechaExpiracion);
        nombreEntidadBancaria = findViewById(R.id.EditTextNombreEntidad);


        modificar = (Button) findViewById(R.id.botonEditar);



        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarTarjeta();
            }
        });
    }

    public void editarTarjeta(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tarjetacompra", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nombre = nombreTitular.getText().toString();
        String ntarjeta = numeroTarjeta.getText().toString();
        String codigo = codigoCVC.getText().toString();
        String fecha = fechaExpiracion.getText().toString();
        String nombreEntidad = nombreEntidadBancaria.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("nombreTitular", nombre);
        registro.put("numeroTarjeta", ntarjeta);
        registro.put("codigoCVC", codigo);
        registro.put("fechaExpiracion", fecha);
        registro.put("nombreEntidadBancaria", nombreEntidad);


        int cant = bd.update("pacientes",registro,"cedula="+idTarjeta,null);
        if(cant==1){
            Toast.makeText(this,"El paciente se modificó con éxito", Toast.LENGTH_SHORT).show();
            Intent vApartado = new Intent(EditarTarjeta.this, ApartadoTarjeta.class);
            startActivity(vApartado);
        }
        else{
            Toast.makeText(this,"No se pudo modificar el paciente", Toast.LENGTH_SHORT).show();
        }
    }
}