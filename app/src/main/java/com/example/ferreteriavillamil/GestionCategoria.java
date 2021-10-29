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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionCategoria extends AppCompatActivity {

    TextView nombre, imagen;
    Button eliminar, modificar;
    int idCategoria;
    ImageButton categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_categoria);

        ListElementCategoria element = (ListElementCategoria) getIntent().getSerializableExtra("ListElementCategoria");
        idCategoria = element.getIdCategoria();
        new GestionCategoria.Consultar(GestionCategoria.this).execute();
        nombre = findViewById(R.id.EditTextNombreGestionCategoria);
        imagen = findViewById(R.id.EditTextUrlGestionCategoria);
        categoria = findViewById(R.id.imageButtonCategoria);


        eliminar = (Button) findViewById(R.id.botonEliminar);
        modificar = (Button) findViewById(R.id.botonModificar);

        eliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new GestionCategoria.Eliminar(GestionCategoria.this).execute();
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GestionCategoria.Modificar(GestionCategoria.this).execute();
            }
        });

        categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(GestionCategoria.this, ApartadoCategorias.class);
                startActivity(vDomicilio);
            }
        });


    }

    private boolean eliminar() {

        String url = Constants.URL + "categoria/delete.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idCategoria)));
        boolean response = APIHandler.POST(url, nameValuePairs); // Enviamos el id al webservices
        return response;
    }

    class Eliminar extends AsyncTask<String, String, String> {
        private Activity context;

        Eliminar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (eliminar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Categoría eliminada correctamente", Toast.LENGTH_LONG).show();
                        Intent vApartado = new Intent(GestionCategoria.this, ApartadoCategorias.class);
                        startActivity(vApartado);
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "No se ha podido eliminar la categoría", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    private Categoria consultar() throws JSONException, IOException {

        String url = Constants.URL + "categoria/get-by-id.php"; // Ruta

        //DATOS
        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(1);//definimos array
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idCategoria))); // pasamos el id al servicio php

        String json = APIHandler.POSTRESPONSE(url, nameValuePairs); // creamos var json que se le asocia la respuesta del webservice
        if (json != null) { // si la respuesta no es vacia
            JSONObject object = new JSONObject(json); // creamos el objeto json que recorrera el servicio
            JSONArray json_array = object.optJSONArray("categoria");// accedemos al objeto json llamado multas
            if (json_array.length() > 0) { // si lo encontrado tiene al menos un registro
                Categoria categoria = new Categoria(json_array.getJSONObject(0));// instanciamos la clase multa para obtener los datos json

                return categoria;// retornamos la multa
            }
            return null;
        }
        return null;
    }

    class Consultar extends AsyncTask<String, String, String> {
        private Activity context;

        Consultar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            try {
                final Categoria categoria = consultar();
                if (categoria != null)
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nombre.setText(categoria.getNombre());
                            imagen.setText(categoria.getImagen());
                        }
                    });
                else
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Categoría no encontrada", Toast.LENGTH_LONG).show();
                        }
                    });
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private boolean modificar() {

        String url = Constants.URL + "categoria/update.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(9);
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idCategoria)));
        nameValuePairs.add(new BasicNameValuePair("nombre", nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("imagen", imagen.getText().toString().trim()));


        boolean response = APIHandler.POST(url, nameValuePairs); // enviamos los datos por POST al Webservice PHP
        return response;
    }

    class Modificar extends AsyncTask<String, String, String> {
        private Activity context;

        Modificar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (modificar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Categoría modificada correctamente", Toast.LENGTH_LONG).show();
                        Intent vApartado = new Intent(GestionCategoria.this, ApartadoCategorias.class);
                        startActivity(vApartado);
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Categoría no encontrada", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}