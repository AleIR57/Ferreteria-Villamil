package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarritoProductos extends AppCompatActivity implements ExampleDialoq.ExampleDialogListener{

    List<ListElementProducto> elements;
    FloatingActionButton fab;
    ListAdapterProductoCarrito listAdapter, objeto1;
    RecyclerView recyclerView;
    int idCategoriaAux;
    String nombreCategoria;
    TextView t1;
    String resultado = "";
    ImageButton facturacarrito;
    ImageButton borrar;
    int idProducto;
    String nombre;
    String idUsuario;
    String marca;
    String cantidad;
    String precio, precio2;
    String imagen;
    int idCategoria, idProductoAux;
    String cantidad2;
    int cantidadAux;
    ImageButton categoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_productos);
        idUsuario = getIntent().getStringExtra("idUsuario");

        t1 = findViewById(R.id.textView);

        facturacarrito = findViewById(R.id.botonFacturaCarrito);
        categoria = findViewById(R.id.imageButtonCategoria);

        facturacarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(CarritoProductos.this, "carrito", null, 1);
                SQLiteDatabase bd = admin.getWritableDatabase();
                int numRows = (int) DatabaseUtils.longForQuery(bd, "SELECT COUNT(*) FROM productos", null);
                if (numRows != 0) {
                    System.out.print("Cantidad de datos: " + numRows);
                    Intent vFacturaCarrito = new Intent(CarritoProductos.this, FacturaCarrito.class);
                    vFacturaCarrito.putExtra("idUsuario", idUsuario);
                    startActivity(vFacturaCarrito);
                }
                else if(numRows == 0){
                    Toast.makeText(CarritoProductos.this, "Primero debe seleccionar un producto antes de proceder a pagarlo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(CarritoProductos.this, Clientes.class);
                vDomicilio.putExtra("idCategoria", idCategoria);
                vDomicilio.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vDomicilio);
            }
        });


        elements = new ArrayList<>();

        loadProductos();



    }

    public void loadProductos(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "carrito", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM productos",null);

        while(fila.moveToNext() != false){
            idProducto = fila.getInt(0);
            nombre = fila.getString(1);
            marca = fila.getString(2);
            precio = fila.getString(3);
            imagen = fila.getString(4);
            cantidad = fila.getString(5);
            int cantidad2 = Integer.parseInt(cantidad);
            idCategoria = fila.getInt(6);

            System.out.println("Id de producto: " + idProducto);

            elements.add(new ListElementProducto(nombre, precio, idProducto, idCategoria, cantidad2, imagen, "#ECF31A"));
        }

        listAdapter = new ListAdapterProductoCarrito(elements, CarritoProductos.this, new ListAdapterProductoCarrito.OnItemClickListener() {
            @Override
            public void onItemClick(ListElementProducto item) {
                idProductoAux = item.getIdProducto();
                new CarritoProductos.Consultar(CarritoProductos.this).execute();
                openDialog();


            }
        });
        recyclerView = findViewById(R.id.recyclerViewProductos3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CarritoProductos.this));
        recyclerView.setAdapter(listAdapter);

    }

    private Producto consultar() throws JSONException, IOException {

        String url = Constants.URL + "producto/get-by-id.php"; // Ruta

        //DATOS
        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(1);//definimos array
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idProductoAux))); // pasamos el id al servicio php

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



    public void openDialog(){
        ExampleDialoq exampleDialog = new ExampleDialoq();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }


    public void moveToDescription(ListElementProducto item){
        Intent intent = new Intent(this, DetalleProducto.class);
        intent.putExtra("ListElementProducto", item);
        startActivity(intent);
    }


    @Override
    public void applyTexts(String cantidad) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "carrito", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        cantidad2 = cantidad;
        int cantidad3 = Integer.parseInt(cantidad2);
        if(cantidadAux >= cantidad3) {
            ContentValues registro2 = new ContentValues();
            registro2.put("cantidad", cantidad2);
            int cant = bd.update("productos", registro2, "idProducto=" + idProductoAux, null);
            if (cant == 1) {
                Toast.makeText(this, "La cantidad se modificó con éxito", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            } else {
                Toast.makeText(this, "No se pudo modificar la cantidad", Toast.LENGTH_SHORT).show();
            }
            bd.close();
            new CarritoProductos.Modificar(CarritoProductos.this).execute();
        }
        else{
            Toast.makeText(CarritoProductos.this, "La cantidad que quiere debe ser menor o igual a la disponible", Toast.LENGTH_LONG).show();
        }


    }

    private boolean modificar() {

        int cantidad3 = Integer.parseInt(cantidad2);
        int cantidad4 = Integer.parseInt(cantidad);
        boolean response = false;
        if(cantidadAux >= cantidad3) {

        int resultadoCantidades = 0;
        resultadoCantidades = cantidad4 - cantidad3;

        int resultadoAux = cantidadAux+resultadoCantidades;

        String resultado = String.valueOf(resultadoAux);
        String url = Constants.URL + "producto/updateCantidad.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(9);
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idProducto)));
        nameValuePairs.add(new BasicNameValuePair("cantidad", resultado));

        response = APIHandler.POST(url, nameValuePairs); // enviamos los datos por POST al Webservice PHP


        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CarritoProductos.this, "La cantidad que quiere debe ser menor o igual a la disponible", Toast.LENGTH_SHORT).show();
                }
            });
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