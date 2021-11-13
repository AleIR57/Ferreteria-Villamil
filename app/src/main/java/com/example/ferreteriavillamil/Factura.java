package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura extends AppCompatActivity {

    TextView nombreTV, nombreUsuarioTV, marcaTV, precioTV, cantidadTV, fechaTV, precioTotalTV, cantidadTotalTV;
    String idUsuario;
    String idCategoria;
    String idVenta;
    String marca;
    String nombreProducto;
    String descripcion;
    String precio;
    String idProducto;
    String cantidad;
    String cantidad2;
    String fecha;
    String idPagoSpinner;
    ImageButton domicilio, regresar;

    int idFactura;
    private static final String FACTURA_URL = "http://192.168.1.15/api/factura/ultimoIdFactura.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        idUsuario= getIntent().getStringExtra("idUsuario");
        System.out.println("El id del usuario es: " + idUsuario);
        idProducto = getIntent().getStringExtra("VentaProductoID");
        System.out.println("El id del producto es: " + idProducto);
        idCategoria = getIntent().getStringExtra("VentaProductoIdCategoria");
        nombreProducto= getIntent().getStringExtra("VentaProductoNombre");
        marca = getIntent().getStringExtra("VentaProductoMarca");
        descripcion =  getIntent().getStringExtra("VentaProductoDescripcion");
        precio = getIntent().getStringExtra("VentaProductoPrecio");
        int precio2 = Integer.parseInt(precio);
        cantidad = getIntent().getStringExtra("VentaProductoCantidad");
        cantidad2 = getIntent().getStringExtra("VentaProductoCantidad2");
        fecha = getIntent().getStringExtra("VentaProductoFecha");
        idVenta = getIntent().getStringExtra("VentaProductoidVenta");
        idPagoSpinner = getIntent().getStringExtra("VentaProductoIdPago");
        System.out.println("El id del pago es: " + idPagoSpinner);
        domicilio = findViewById(R.id.imageButtonDomicilio);
        nombreTV = findViewById(R.id.textViewNombreProducto);
        marcaTV = findViewById(R.id.textViewMarcaProducto);
        precioTV = findViewById(R.id.textViewPrecioUnitarioProducto);
        cantidadTV = findViewById(R.id.textViewCantidadProducto);
        nombreUsuarioTV = findViewById(R.id.textViewNombreUsuario);
        fechaTV = findViewById(R.id.textViewFecha);
        int cantidad2 = Integer.parseInt(cantidad);
        float cantidad3 = Float.parseFloat(String.valueOf(cantidad2));
        float resultado = (float) ((precio2*cantidad3));
        float resultado2 = resultado + (float) ((precio2*cantidad3)*0.19);
        String resultado3 = String.valueOf(resultado2);
        cantidadTotalTV = findViewById(R.id.textViewCantidadTotalProducto);
        precioTotalTV = findViewById(R.id.textViewPrecioTotalproducto);
        nombreTV.setText(nombreProducto + "");
        marcaTV.setText(marca + "");
        precioTV.setText(precio + "");
        cantidadTV.setText(cantidad + "");
        cantidadTotalTV.setText(cantidad + "");
        precioTotalTV.setText(resultado3 + "");
        fechaTV.setText(fecha + "");


        new Factura.Consultar(Factura.this).execute();
        new Factura.Registrar(Factura.this).execute();
        loadFacturas();

        domicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(Factura.this, Domicilio.class);
                vDomicilio.putExtra("idUsuario", idUsuario);
                vDomicilio.putExtra("idFactura", String.valueOf(idFactura));
                startActivity(vDomicilio);
            }
        });




    }

    private Usuario consultar() throws JSONException, IOException {

        String url = Constants.URL + "usuario/get-by-id.php"; // Ruta

        //DATOS
        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(1);//definimos array
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idUsuario))); // pasamos el id al servicio php

        String json = APIHandler.POSTRESPONSE(url, nameValuePairs); // creamos var json que se le asocia la respuesta del webservice
        if (json != null) { // si la respuesta no es vacia
            JSONObject object = new JSONObject(json); // creamos el objeto json que recorrera el servicio
            JSONArray json_array = object.optJSONArray("usuario");// accedemos al objeto json llamado multas
            if (json_array.length() > 0) { // si lo encontrado tiene al menos un registro
                Usuario usuario = new Usuario(json_array.getJSONObject(0));// instanciamos la clase multa para obtener los datos json

                return usuario;// retornamos la multa
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
                final Usuario usuario = consultar();
                if (usuario != null)
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nombreUsuarioTV.setText(usuario.getNombre());

                        }
                    });
                else
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_LONG).show();
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


    private boolean registrar() {


        String url = Constants.URL + "factura/addFactura.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(10); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("fecha", fechaTV.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("precioTotal", precioTotalTV.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("cantidadTotal", cantidadTotalTV.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("descripcion", nombreTV.getText().toString().trim() + " " + marcaTV.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
        nameValuePairs.add(new BasicNameValuePair("idTipoPago", idPagoSpinner));


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
                        Toast.makeText(context, "Se ha creado la factura exitosamente", Toast.LENGTH_LONG).show();

                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "No se pudo crear la factura", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    public void loadFacturas(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, FACTURA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ventas = new JSONArray(response);
                            for(int i = 0; i<ventas.length(); i++){
                                JSONObject ventasObject = ventas.getJSONObject(i);
                                idFactura = ventasObject.getInt("idFactura");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Factura.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        Volley.newRequestQueue(this).add(stringRequest);
        new Factura.Registrar3(Factura.this).execute();

    }

    private boolean registrar3() {
        idFactura += 1;
        System.out.println("El id de la factura es: " + idFactura);


        String url = Constants.URL + "productoxfactura/addProductoxFactura2.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(5); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("idProducto", idProducto));
        nameValuePairs.add(new BasicNameValuePair("idFactura", String.valueOf(idFactura)));
        nameValuePairs.add(new BasicNameValuePair("cantidad", cantidadTotalTV.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("precio", precioTotalTV.getText().toString().trim()));
        boolean response = APIHandler.POST(url, nameValuePairs);

        return response;

    }

    class Registrar3 extends AsyncTask<String, String, String> {
        private Activity context;

        Registrar3(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (registrar3())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Se ha realizado la venta exitosamente", Toast.LENGTH_LONG).show();

                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "No se pudo crear la venta", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }


    @Override public void onBackPressed() { }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if(keyCode== KeyEvent.KEYCODE_BACK) { return false; } return super.onKeyDown(keyCode, event); }



}