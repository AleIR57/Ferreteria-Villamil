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

import java.util.ArrayList;
import java.util.List;

public class CrearProductos2 extends AppCompatActivity {
    TextView nombre, marca, descripcion, precio, imagen, cantidad;
    Button insertar;
    String idCategoria;
    String codigoBarra, marcaCodigo, precioCodigo;
    ImageButton producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_productos2);


        idCategoria = getIntent().getStringExtra("idCategoria");
        codigoBarra= getIntent().getStringExtra("codigoBarra");
        nombre = (TextView) findViewById(R.id.EditTextNombreCrearProducto);
        marca = (TextView) findViewById(R.id.EditTextMarcaCrearProducto);
        descripcion = (TextView) findViewById(R.id.EditTextDescripcionCrearProducto);
        precio = (TextView) findViewById(R.id.EditTextPrecioCrearProducto);
        imagen = (TextView) findViewById(R.id.EditTextImagenCrearProducto);
        cantidad = (TextView) findViewById(R.id.EditTextCantidadCrearProducto);
        producto = findViewById(R.id.imageButtonProducto);

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

                    new CrearProductos2.Registrar(CrearProductos2.this).execute();
                } else {

                    Toast.makeText(CrearProductos2.this, "Hay información por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(CrearProductos2.this, ApartadoCategorias2.class);
                vDomicilio.putExtra("idCategoria", idCategoria);
                startActivity(vDomicilio);
            }
        });
    }

    private boolean registrar() {
        String url = Constants.URL + "producto/addProducto2.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(8); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("idProducto", codigoBarra));
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
                        Intent vApartado = new Intent(CrearProductos2.this, ApartadoProductos.class);
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