package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class GestionProductos extends AppCompatActivity {


    TextView nombre, imagen, marca, descripcion, precio, cantidad;
    Button eliminar, modificar;
    int idProducto, idCategoria;
    ImageButton producto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_productos);

        ListElementProducto element = (ListElementProducto) getIntent().getSerializableExtra("ListElementProducto");
        idProducto = element.getIdProducto();
        idCategoria = element.getIdCategoria();
        new GestionProductos.Consultar(GestionProductos.this).execute();
        nombre = findViewById(R.id.EditTextNombreGestionProducto);
        imagen = findViewById(R.id.EditTextImagenGestionProducto);
        marca = findViewById(R.id.EditTextMarcaGestionProducto);
        descripcion = findViewById(R.id.EditTextDescripcionGestionProducto);
        precio = findViewById(R.id.EditTextPrecioGestionProducto);
        cantidad = findViewById(R.id.EditTextCantidadGestionProducto);
        producto = findViewById(R.id.imageButtonProducto);


        eliminar = (Button) findViewById(R.id.botonEliminar);
        modificar = (Button) findViewById(R.id.botonModificar);

        eliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new GestionProductos.Eliminar(GestionProductos.this).execute();
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GestionProductos.Modificar(GestionProductos.this).execute();
            }
        });

        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(GestionProductos.this, ApartadoCategorias2.class);
                vDomicilio.putExtra("idCategoria", idCategoria);
                startActivity(vDomicilio);
            }
        });
    }

    private boolean eliminar() {

        String url = Constants.URL + "producto/delete.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idProducto)));
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
                        Toast.makeText(context, "Producto eliminado correctamente", Toast.LENGTH_LONG).show();
                        Intent vApartado = new Intent(GestionProductos.this, ApartadoProductos.class);
                        vApartado.putExtra("idCategoria", String.valueOf(idCategoria));
                        startActivity(vApartado);
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "No se ha podido eliminar el producto", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    private Producto consultar() throws JSONException, IOException {

        String url = Constants.URL + "producto/get-by-id.php"; // Ruta

        //DATOS
        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(1);//definimos array
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idProducto))); // pasamos el id al servicio php

        String json = APIHandler.POSTRESPONSE(url, nameValuePairs); // creamos var json que se le asocia la respuesta del webservice
        if (json != null) { // si la respuesta no es vacia
            JSONObject object = new JSONObject(json); // creamos el objeto json que recorrera el servicio
            JSONArray json_array = object.optJSONArray("producto");// accedemos al objeto json llamado multas
            if (json_array.length() > 0) { // si lo encontrado tiene al menos un registro
                Producto producto = new Producto(json_array.getJSONObject(0));// instanciamos la clase multa para obtener los datos json

                return producto;// retornamos la multa
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
                final Producto producto = consultar();
                if (producto != null)
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nombre.setText(producto.getNombre());
                            imagen.setText(producto.getImagen());
                            marca.setText(producto.getMarca());
                            cantidad.setText(producto.getCantidad() + "");
                            descripcion.setText(producto.getDescripcion());
                            precio.setText(producto.getPrecio());
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

        String url = Constants.URL + "producto/update.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(9);
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idProducto)));
        nameValuePairs.add(new BasicNameValuePair("nombre", nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("imagen", imagen.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("marca", marca.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("descripcion", descripcion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("precio", precio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("cantidad", cantidad.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("idCategoria", String.valueOf(idCategoria)));

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
                        Toast.makeText(context, "Producto modificado correctamente", Toast.LENGTH_LONG).show();
                        Intent vApartado = new Intent(GestionProductos.this, ApartadoProductos.class);
                        vApartado.putExtra("idCategoria", String.valueOf(idCategoria));
                        startActivity(vApartado);
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Producto no encontrado", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}