package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class CrearCategorias extends AppCompatActivity {
    TextView nombre, imagen;
    Button insertar;
    ImageButton categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categorias);
        nombre = (TextView) findViewById(R.id.EditTextNombreCrearCategoria);
        imagen = (TextView) findViewById(R.id.EditTextUrlCrearCategoria);
        categoria = findViewById(R.id.imageButtonCategoria);

        insertar = (Button) findViewById(R.id.botonRegistro);


        insertar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if ((!nombre.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!imagen.getText().toString().trim().equalsIgnoreCase(""))){

                    new CrearCategorias.Registrar(CrearCategorias.this).execute();
                } else {

                    Toast.makeText(CrearCategorias.this, "Hay información por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(CrearCategorias.this, ApartadoCategorias.class);
                startActivity(vDomicilio);
            }
        });
    }

    private boolean registrar() {

        String url = Constants.URL + "categoria/addCategoria.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(3); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("nombre", nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("imagen", imagen.getText().toString().trim()));

        boolean response = APIHandler.POST(url, nameValuePairs);
        return response;
    }

    class Registrar extends AsyncTask<String, String, String> {
        private Activity context;

        Registrar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (registrar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Categoría creada correctamente", Toast.LENGTH_LONG).show();
                        nombre.setText("");
                        imagen.setText("");
                        Intent vApartado = new Intent(CrearCategorias.this, ApartadoCategorias.class);
                        startActivity(vApartado);
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "No se pudo crear la categoría", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}