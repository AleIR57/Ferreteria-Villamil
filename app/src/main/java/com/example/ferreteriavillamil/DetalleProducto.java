package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetalleProducto extends AppCompatActivity {


    TextView nombre, marca, descripcion, precio, cantidad;
    ImageView imagen;
    Button comprar;
    int idProducto, idCategoria;
    String idUsuario, urlImagen;
    ImageButton producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        idUsuario = getIntent().getStringExtra("idUsuario");

        try{
            ListElementProducto element = (ListElementProducto) getIntent().getSerializableExtra("ListElementProducto");
            idProducto = element.getIdProducto();
            idCategoria = element.getIdCategoria();
        }
        catch(NullPointerException e ){
            idCategoria = Integer.parseInt(getIntent().getStringExtra("idCategoria"));
            idProducto = Integer.parseInt(getIntent().getStringExtra("idProducto"));
        }


        new DetalleProducto.Consultar(DetalleProducto.this).execute();
        nombre = findViewById(R.id.textViewNombre);
        marca = findViewById(R.id.textViewMarca);
        imagen = findViewById(R.id.ImageViewDetalleProducto);
        descripcion = findViewById(R.id.textViewDescripcion);
        precio = findViewById(R.id.textViewPrecio);
        cantidad= findViewById(R.id.textViewCantidad);
        comprar = (Button) findViewById(R.id.botonComprar);
        producto = findViewById(R.id.imageButtonProducto);

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatos();
            }
        });

        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(DetalleProducto.this, ApartadoProductos2.class);
                vDomicilio.putExtra("idCategoria", String.valueOf(idCategoria));
                vDomicilio.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vDomicilio);
            }
        });

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
                            marca.setText(producto.getMarca());
                            cantidad.setText("Cantidad disponible: " + producto.getCantidad() + "");
                            descripcion.setText(producto.getDescripcion());
                            precio.setText("Precio: $" + producto.getPrecio());
                            urlImagen = producto.getImagen();
                            Picasso.get().load(urlImagen).into(imagen);
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


    public void enviarDatos(){


        String nombre1 = nombre.getText().toString();
        String marca1 = marca.getText().toString();
        String cantidad1 = cantidad.getText().toString();
        String dividirValores[] = cantidad1.split(":");
        String cantidad2 = dividirValores[1];
        cantidad2 = cantidad2.replace(" ", "");
        int cantidad3 = Integer.parseInt(cantidad2);
        String descripcion1 = descripcion.getText().toString();
        String precio1 = precio.getText().toString();
        String dividirValores2[] = precio1.split("\\$");
        String precio2 = dividirValores2[1];
        precio2 = precio2.replace(" ", "");
        String imagen1 = urlImagen;
        String idCategoria1 = String.valueOf(idCategoria);
        if(cantidad3 > 0 ){
            Intent vVentaProducto = new Intent(DetalleProducto.this, VentaProducto.class);
            vVentaProducto.putExtra("DetallesProductoID", String.valueOf(idProducto));
            vVentaProducto.putExtra("DetallesProductoNombre", nombre1);
            vVentaProducto.putExtra("DetallesProductoMarca", marca1);
            vVentaProducto.putExtra("DetallesProductoCantidad", cantidad2);
            vVentaProducto.putExtra("DetallesProductoDescripcion", descripcion1);
            vVentaProducto.putExtra("DetallesProductoPrecio", precio2);
            vVentaProducto.putExtra("DetallesProductoImagen" , imagen1);
            vVentaProducto.putExtra("DetallesProductoIdCategoria", idCategoria1);
            vVentaProducto.putExtra("idUsuario", String.valueOf(idUsuario));
            startActivity(vVentaProducto);
        }
        if(cantidad3 == 0){
            Toast.makeText(this, "El producto está agotado", Toast.LENGTH_SHORT).show();
        }

    }


}