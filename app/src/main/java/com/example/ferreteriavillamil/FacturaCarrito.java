package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import java.util.Iterator;
import java.util.List;

public class FacturaCarrito extends AppCompatActivity implements ExampleDialoq2.ExampleDialogListener2 {
    String idUsuario;
    String nombreUsuario;
    int contadorVentas;
    String fecha;
    Button boton;
    Spinner idPago;
    int idPagoSpinner;
    String selecteditem;
    int cantidadAux;
    float cantidad3 = 0;
    String resultado3 = "";
    int idProducto, idFactura;
    ImageButton carrito;
    ArrayList<Integer> idProductos = new ArrayList<Integer>();
    ArrayList<Integer> cantidades = new ArrayList<Integer>();
    ArrayList<String> cantidades2 = new ArrayList<String>();
    private static final String FACTURA_URL = "http://192.168.1.15/api/factura/ultimoIdFactura.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura_carrito);
        carrito = findViewById(R.id.imageButtonCarrito);

        idPago = findViewById(R.id.spinnerPagos);

        String[] opciones = {"Efectivo", "Tarjeta"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_primero, opciones);
        idPago.setAdapter(adapter);
        idUsuario = getIntent().getStringExtra("idUsuario");

        new FacturaCarrito.Consultar(FacturaCarrito.this).execute();

        boton = findViewById(R.id.boton);

        idPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecteditem = adapter.getItem(position).toString();

                if (selecteditem == "Tarjeta") {
                    openDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new FacturaCarrito.Registrar(FacturaCarrito.this).execute();
                new FacturaCarrito.Modificar(FacturaCarrito.this).execute();
                loadFacturas();
                new FacturaCarrito.Registrar3(FacturaCarrito.this).execute();

            }
        });

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(FacturaCarrito.this, CarritoProductos.class);
                vDomicilio.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vDomicilio);
            }
        });

    }



    public void openDialog() {
        ExampleDialoq2 exampleDialog = new ExampleDialoq2();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String nombreTitular, String numeroTarjeta, String codigoCVC, String fechaExpiracion, String nombreEntidadBancaria) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tarjetacompra", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int numRows = (int) DatabaseUtils.longForQuery(bd, "SELECT COUNT(*) FROM tarjetas", null);

        ContentValues registro = new ContentValues();
        registro.put("idTarjeta", numRows + 1);
        registro.put("nombreTitular", nombreTitular);
        registro.put("numeroTarjeta", numeroTarjeta);
        registro.put("codigoCVC", codigoCVC);
        registro.put("fechaExpiracion", fechaExpiracion);
        registro.put("nombreEntidadBancaria", nombreEntidadBancaria);
        bd.insert("tarjetas", null, registro);
        bd.close();
        Toast.makeText(this, "La tarjeta fue añadida exitosamente como método de pago", Toast.LENGTH_SHORT).show();

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
                            nombreUsuario = usuario.getNombre();
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
        idPagoSpinner = 1;

        if(idPago.getSelectedItem() == "Efectivo"){
            idPagoSpinner = 1;
        }
        if(idPago.getSelectedItem() == "Tarjeta"){
            idPagoSpinner = 2;
        }
        int idProducto;
        String nombre;
        String resultado4 = "";
        String marca;
        String cantidad;

        String precio;
        String imagen;
        int idCategoria;
        float precio2;
        float cantidad2;
        float resultado;
        float resultado2 = 0;

        boolean response = true;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "carrito", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM productos", null);
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String url = Constants.URL + "factura/addFactura.php";
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(10);

        while (fila.moveToNext() != false) {
            idProducto = fila.getInt(0);
            idProductos.add(idProducto);
            nombre = fila.getString(1);
            marca = fila.getString(2);
            resultado4 += nombre + " " + marca + "-";
            precio = fila.getString(3);
            imagen = fila.getString(4);
            cantidad = fila.getString(5);
            cantidades2.add(cantidad);
            idCategoria = fila.getInt(6);
            precio2 = Float.parseFloat(precio);
            cantidad2 = Float.parseFloat(cantidad);
            cantidad3 += cantidad2;
            resultado = (float) ((precio2 * cantidad2));
            resultado2 += resultado;


        }

        float resultado5 = (float) (resultado2 + (resultado2 * 0.19));
        resultado3 = String.valueOf(resultado5);
        nameValuePairs.add(new BasicNameValuePair("fecha", fecha));
        nameValuePairs.add(new BasicNameValuePair("precioTotal", resultado3));
        nameValuePairs.add(new BasicNameValuePair("cantidadTotal", String.valueOf(cantidad3)));
        nameValuePairs.add(new BasicNameValuePair("descripcion", resultado4));
        nameValuePairs.add(new BasicNameValuePair("idUsuario", String.valueOf(idUsuario)));
        nameValuePairs.add(new BasicNameValuePair("idTipoPago", String.valueOf(idPagoSpinner)));
        response = APIHandler.POST(url, nameValuePairs);
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
                        Toast.makeText(context, "Se han creado las facturas exitosamente", Toast.LENGTH_LONG).show();
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

    private boolean modificar() {

        String cantidad;
        boolean response = true;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "carrito", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM productos", null);
        String url = Constants.URL + "producto/updateCantidad2.php";

        while (fila.moveToNext() != false) {
            idProducto = fila.getInt(0);
            cantidad = fila.getString(5);
            List<NameValuePair> nameValuePairs;
            nameValuePairs = new ArrayList<NameValuePair>(9);
            nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idProducto)));
            nameValuePairs.add(new BasicNameValuePair("cantidad", cantidad));
            response = APIHandler.POST(url, nameValuePairs);
        }

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
                        Intent intent = new Intent(FacturaCarrito.this, FacturaCarrito2.class);
                        intent.putExtra("idFactura", String.valueOf(idFactura));
                        intent.putExtra("idUsuario", String.valueOf(idUsuario));
                        startActivity(intent);
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
                Toast.makeText(FacturaCarrito.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        Volley.newRequestQueue(this).add(stringRequest);

    }


    private boolean registrar3() {
        System.out.println("El id de la factura es: " + idFactura);

        boolean response = false;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "carrito", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM productos", null);
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String url = Constants.URL + "productoxfactura/addProductoxFactura2.php";
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(10);

        while (fila.moveToNext() != false) {
            int idProducto = fila.getInt(0);
            String precio = fila.getString(3);
            String cantidad = fila.getString(5);
            nameValuePairs = new ArrayList<NameValuePair>(5); // tamaño del array
            nameValuePairs.add(new BasicNameValuePair("idProducto", String.valueOf(idProducto)));
            nameValuePairs.add(new BasicNameValuePair("idFactura", String.valueOf(idFactura)));
            nameValuePairs.add(new BasicNameValuePair("cantidad", String.valueOf(cantidad)));
            nameValuePairs.add(new BasicNameValuePair("precio", String.valueOf(precio)));
            response = APIHandler.POST(url, nameValuePairs);
        }

        //DATOS// definimos la lista de datos

        bd.execSQL("delete from productos");
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


}