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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CrearProductos extends AppCompatActivity {
    TextView nombre, marca, descripcion, precio, imagen, cantidad;
    Button insertar;
    String idCategoria;
    int contadorProductos;
    ImageButton producto;
    private static final String PRODUCTO_URL = "http://192.168.1.15/api/producto/productos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_productos);

        idCategoria = getIntent().getStringExtra("idCategoria");
        System.out.println("El id de la categoría de los productos es: " + idCategoria);
        nombre = (TextView) findViewById(R.id.EditTextNombreCrearProducto);
        marca = (TextView) findViewById(R.id.EditTextMarcaCrearProducto);
        descripcion = (TextView) findViewById(R.id.EditTextDescripcionCrearProducto);
        precio = (TextView) findViewById(R.id.EditTextPrecioCrearProducto);
        imagen = (TextView) findViewById(R.id.EditTextImagenCrearProducto);
        cantidad = (TextView) findViewById(R.id.EditTextCantidadCrearProducto);
        producto = findViewById(R.id.imageButtonProducto);

        loadProducto();

        insertar = (Button) findViewById(R.id.botonRegistro);
        insertar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if ((!nombre.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!marca.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!descripcion.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!precio.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!imagen.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!cantidad.getText().toString().trim().equalsIgnoreCase(""))){

                    new CrearProductos.Registrar(CrearProductos.this).execute();
                } else {

                    Toast.makeText(CrearProductos.this, "Hay información por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(CrearProductos.this, ApartadoCategorias2.class);
                vDomicilio.putExtra("idCategoria", idCategoria);
                startActivity(vDomicilio);
            }
        });
    }

    public void loadProducto() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray productos = new JSONArray(response);
                            contadorProductos = productos.length();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearProductos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }




    private boolean registrar() {
        contadorProductos += 2;
        String url = Constants.URL + "producto/addProducto.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(8); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("idProducto", String.valueOf(contadorProductos)));
        nameValuePairs.add(new BasicNameValuePair("nombre", nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("marca", marca.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("descripcion", descripcion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("precio", precio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("imagen", imagen.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("idCategoria", String.valueOf(idCategoria)));
        nameValuePairs.add(new BasicNameValuePair("cantidad", cantidad.getText().toString().trim()));

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
                        Toast.makeText(context, "Producto creado correctamente", Toast.LENGTH_LONG).show();
                        nombre.setText("");
                        marca.setText("");
                        descripcion.setText("");
                        precio.setText("");
                        imagen.setText("");
                        cantidad.setText("");
                        Intent vApartado = new Intent(CrearProductos.this, ApartadoProductos.class);
                        vApartado.putExtra("idCategoria", idCategoria);
                        startActivity(vApartado);
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "No se pudo crear el producto", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}