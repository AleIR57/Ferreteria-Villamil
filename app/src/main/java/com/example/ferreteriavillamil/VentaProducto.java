package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

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

public class VentaProducto extends AppCompatActivity implements ExampleDialoq2.ExampleDialogListener2{

    TextView nombreTV, marcaTV, precioTV, cantidadTV;
    EditText cantidadET;
    String idUsuario;
    String marca;
    String nombreProducto;
    String descripcion;
    String precio;
    String idProducto, idCategoria;
    String cantidad;
    String cantidad2;
    int cantidadAux;
    Spinner idPago;
    String imagen;
    int idPagoSpinner;
    Button insertar, carrito;
    String fecha;
    String selecteditem;
    int contadorVentas;
    ImageButton producto;
    private static final String VENTA_URL = "http://192.168.1.15/api/productoxfactura/productosxfactura.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_producto);

        idUsuario= getIntent().getStringExtra("idUsuario");
        System.out.print("El id del usuario :" + idUsuario);
        idProducto = getIntent().getStringExtra("DetallesProductoID");
        nombreProducto= getIntent().getStringExtra("DetallesProductoNombre");
        marca = getIntent().getStringExtra("DetallesProductoMarca");
        descripcion =  getIntent().getStringExtra("DetallesProductoDescripcion");
        precio = getIntent().getStringExtra("DetallesProductoPrecio");
        cantidad = getIntent().getStringExtra("DetallesProductoCantidad");

        try{
            imagen = getIntent().getStringExtra("DetallesProductoImagen");
        }
        catch(NullPointerException e ){
            imagen = "";
        }

        idCategoria = getIntent().getStringExtra("DetallesProductoIdCategoria");
        producto = findViewById(R.id.imageButtonProducto);

        idPago = findViewById(R.id.spinnerPagos);

        String [] opciones = {"Efectivo", "Tarjeta"};



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_primero, opciones);
        idPago.setAdapter(adapter);

        insertar = (Button) findViewById(R.id.botonComprar);
        carrito = (Button) findViewById(R.id.botonAñadirCarrito);

        nombreTV = findViewById(R.id.textViewNombreProducto);
        marcaTV = findViewById(R.id.textViewMarcaProducto);
        precioTV = findViewById(R.id.textViewPrecioProducto);
        cantidadTV = findViewById(R.id.textViewCantidadProducto);
        cantidadET = findViewById(R.id.EditTextCantidadProducto);

        nombreTV.setText(nombreProducto + "");
        marcaTV.setText(marca + "");
        precioTV.setText(precio + "");
        cantidadTV.setText(cantidad + "");
        cantidad2 = cantidadTV.getText().toString();
        new VentaProducto.Consultar(VentaProducto.this).execute();

        idPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecteditem = adapter.getItem(position).toString();

                if(selecteditem == "Tarjeta"){
                    openDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cantidad1 = cantidadET.getText().toString();
                int cantidad2 = Integer.parseInt(cantidad1);
                if(cantidad2 > cantidadAux || cantidad1.equals("") || cantidad2 <= 0) {
                    Toast.makeText(VentaProducto.this, "Digite una cantidad de compra correcta según la cantidad disponible del producto", Toast.LENGTH_SHORT).show();
                }
                else if (cantidad2 <= cantidadAux) {
                    new VentaProducto.Modificar(VentaProducto.this).execute();

                }

            }
        });

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cantidad1 = cantidadET.getText().toString();

                int cantidad2 = Integer.parseInt(cantidad1);
                if(cantidad2 > cantidadAux || cantidad1.equals("") || cantidad2 <= 0) {
                    Toast.makeText(VentaProducto.this, "Digite una cantidad de compra correcta según la cantidad disponible del producto", Toast.LENGTH_SHORT).show();
                }
                else if (cantidad2 <= cantidadAux) {
                    registrarEnCarrito();

                }


            }
        });

        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(VentaProducto.this, DetalleProducto.class);
                vDomicilio.putExtra("idCategoria", idCategoria);
                vDomicilio.putExtra("idProducto", idProducto);
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
                           cantidadAux = producto.getCantidad();
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



    public void registrarEnCarrito(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "carrito", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String nombre1 = nombreTV.getText().toString();
        String marca1 = marcaTV.getText().toString();
        String cantidad1 = cantidadET.getText().toString();
        String precio1 = precioTV.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("idProducto", idProducto);
        registro.put("nombre", nombre1);
        registro.put("marca", marca1);
        registro.put("precio", precio1);
        registro.put("imagen", imagen);
        registro.put("cantidad", cantidad1);
        registro.put("idCategoria", idCategoria);
        bd.insert("productos", null, registro);
        bd.close();
        cantidadET.setText("");
        Toast.makeText(this, "El producto fue añadido al carrito exitosamente", Toast.LENGTH_SHORT).show();


    }

    public void openDialog(){
        ExampleDialoq2 exampleDialog = new ExampleDialoq2();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String nombreTitular, String numeroTarjeta, String codigoCVC, String fechaExpiracion, String nombreEntidadBancaria) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tarjetacompra", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int numRows = (int) DatabaseUtils.longForQuery(bd, "SELECT COUNT(*) FROM tarjetas", null);

        ContentValues registro = new ContentValues();
        registro.put("idTarjeta", numRows+1);
        registro.put("nombreTitular", nombreTitular);
        registro.put("numeroTarjeta", numeroTarjeta);
        registro.put("codigoCVC", codigoCVC);
        registro.put("fechaExpiracion", fechaExpiracion);
        registro.put("nombreEntidadBancaria", nombreEntidadBancaria);
        bd.insert("tarjetas", null, registro);
        bd.close();
        Toast.makeText(this, "La tarjeta fue añadida exitosamente como método de pago", Toast.LENGTH_SHORT).show();

    }



    private boolean modificar() {
        String cantidad1 = cantidadET.getText().toString();
        int cantidad2 = Integer.parseInt(cantidad1);
        int resultadoAux = cantidadAux-cantidad2;
        String resultado = String.valueOf(resultadoAux);
        String url = Constants.URL + "producto/updateCantidad.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(9);
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idProducto)));
        nameValuePairs.add(new BasicNameValuePair("cantidad", resultado));

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
                        enviarDatos();
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

    public void enviarDatos(){

        String contadorVentas2 = String.valueOf(contadorVentas);
        fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        idPagoSpinner = 1;

        if(idPago.getSelectedItem() == "Efectivo"){
            idPagoSpinner = 1;
        }
        if(idPago.getSelectedItem() == "Tarjeta"){
            idPagoSpinner = 2;
        }
        Intent intent = new Intent(this, Factura.class);
        intent.putExtra("idUsuario", String.valueOf(idUsuario));
        intent.putExtra("VentaProductoID", idProducto);
        intent.putExtra("VentaProductoNombre", nombreProducto);
        intent.putExtra("VentaProductoMarca", marca);
        intent.putExtra("VentaProductoCantidad", cantidadET.getText().toString());
        intent.putExtra("VentaProductoCantidad2", cantidad2);
        intent.putExtra("VentaProductoPrecio", precio);
        intent.putExtra("VentaProductoFecha", fecha);
        intent.putExtra("VentaProductoIdPago", String.valueOf(idPagoSpinner));
        intent.putExtra("VentaProductoidVenta", contadorVentas2);
        intent.putExtra("VentaProductoIdCategoria", idCategoria);
        startActivity(intent);
    }




}