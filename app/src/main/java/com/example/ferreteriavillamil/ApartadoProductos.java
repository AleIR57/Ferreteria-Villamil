package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ApartadoProductos extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SearchView txtBuscar;
    List<ListElementProducto> elements;
    FloatingActionButton fab, fab5, fab4;
    ListAdapterProducto listAdapter, objeto1;
    RecyclerView recyclerView;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
    int idCategoriaAux;
    String nombreCategoria;
    String nombreCodigo, marcaCodigo, precioCodigo;
    ImageButton categoria;
    private static final String PRODUCTO_URL = "http://192.168.1.15/api/producto/productos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.background3);
        setContentView(R.layout.activity_apartado_productos);

        try{
            ListElementCategoria element = (ListElementCategoria) getIntent().getSerializableExtra("ListElementCategoria");
            idCategoriaAux = element.getIdCategoria();
        }
        catch(NullPointerException e ){
            idCategoriaAux = Integer.parseInt(getIntent().getStringExtra("idCategoria"));
        }

        System.out.println("El id de la categoría es: " + idCategoriaAux);
        categoria = findViewById(R.id.imageButtonCategoria);
        txtBuscar = findViewById(R.id.txtBuscar);

        loadProductos();

        elements = new ArrayList<>();
        fab = findViewById(R.id.fab3);
        fab5 = findViewById(R.id.fabCliente5);
        fab4 = findViewById(R.id.fabCliente4);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backword);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    animateFab();
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()== R.id.fabCliente4){
                    animateFab();
                    Intent vCrear = new Intent(ApartadoProductos.this, CrearProductos.class);
                    vCrear.putExtra("idCategoria", String.valueOf(idCategoriaAux));
                    startActivity(vCrear);
                }
            }
        });

        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()== R.id.fabCliente5){
                    animateFab();
                    IntentIntegrator integrador = new IntentIntegrator(ApartadoProductos.this);
                    integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrador.setPrompt("Lector de productos");
                    integrador.setCameraId(0);
                    integrador.setBeepEnabled(true);
                    integrador.setBarcodeImageEnabled(true);
                    integrador.initiateScan();
                }
            }
        });

        categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(ApartadoProductos.this, ApartadoCategorias2.class);
                startActivity(vDomicilio);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if(result != null){
            if(result.getContents()== null){
                Toast.makeText(getApplicationContext(), "Error de lectura", Toast.LENGTH_LONG).show();
            }
            else{
                String scanContent=result.getContents();
                StringTokenizer t = new StringTokenizer(scanContent, "*");
                String codigoBarra = t.nextToken();
                Intent vCrear = new Intent(this, CrearProductos2.class);
                vCrear.putExtra("idCategoria", String.valueOf(idCategoriaAux));
                vCrear.putExtra("codigoBarra", String.valueOf(codigoBarra));
                startActivity(vCrear);
                Toast.makeText(getApplicationContext(), "Captura de datos", Toast.LENGTH_SHORT).show();

            }
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }


    private void animateFab(){
        if(isOpen){
            fab.startAnimation(rotateBackward);
            fab4.startAnimation(fabClose);
            fab5.startAnimation(fabClose);
            fab4.setClickable(false);
            fab5.setClickable(false);
            isOpen=false;
        }
        else {
            fab.startAnimation(rotateForward);
            fab4.startAnimation(fabOpen);
            fab5.startAnimation(fabOpen);
            fab4.setClickable(true);
            fab5.setClickable(true);
            isOpen=true;
        }
    }


    public void loadProductos(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray productos = new JSONArray(response);

                            for(int i = 0; i<productos.length(); i++){
                                JSONObject productoObject = productos.getJSONObject(i);
                                int idProducto = productoObject.getInt("idProducto");
                                String nombre = productoObject.getString("nombre");
                                String precio = productoObject.getString("precio");
                                int cantidad = productoObject.getInt("cantidad");
                                String descripcion = productoObject.getString("descripcion");
                                int idCategoria= productoObject.getInt("idCategoria");
                                String imagen = productoObject.getString("imagen");

                                if(idCategoriaAux == idCategoria){
                                    elements.add(new ListElementProducto(nombre, precio, idProducto, idCategoria, cantidad, imagen, "#3381CF"));
                                }
                            }

                            listAdapter = new ListAdapterProducto(elements, ApartadoProductos.this, new ListAdapterProducto.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListElementProducto item) {
                                    moveToDescription(item);
                                }
                            });
                            recyclerView = findViewById(R.id.recyclerViewProductos);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ApartadoProductos.this));
                            recyclerView.setAdapter(listAdapter);
                            txtBuscar.setOnQueryTextListener(ApartadoProductos.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApartadoProductos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        Volley.newRequestQueue(this).add(stringRequest);

    }


    public void moveToDescription(ListElementProducto item){
        Intent intent = new Intent(this, GestionProductos.class);
        intent.putExtra("ListElementProducto", item);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filtrado(newText);
        return false;
    }



}