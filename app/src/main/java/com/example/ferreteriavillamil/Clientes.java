package com.example.ferreteriavillamil;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Clientes extends AppCompatActivity {
    List<ListElementCategoria> elements;
    FloatingActionButton fab, fab2, fab3, fab4, fab5, fab6, fab7;
    String nombreAux, imagenAux;
    int  idVariableAux;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
    String idUsuario;
    ListAdapterCategoria2 listAdapter, objeto1;
    RecyclerView recyclerView;
    String nombreCodigo, marcaCodigo, precioCodigo;

    private static final String CATEGORIA_URL = "http://192.168.1.15/api/categoria/categorias.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        idUsuario = getIntent().getStringExtra("idUsuario");
        init();
        elements = new ArrayList<>();
        fab = findViewById(R.id.fabCliente);
        fab2 = findViewById(R.id.fabCliente2);
        fab3 = findViewById(R.id.fabCliente3);
        fab4 = findViewById(R.id.fabCliente4);
        fab5 = findViewById(R.id.fabCliente5);
        fab6 = findViewById(R.id.fabCliente6);
        fab7 = findViewById(R.id.fabCliente7);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backword);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Intent vProductos = new Intent(Clientes.this, CarritoProductos.class);
                vProductos.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vProductos);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Intent vTarjetas = new Intent(Clientes.this, ApartadoTarjeta.class);
                vTarjetas.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vTarjetas);
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Intent vInfo = new Intent(Clientes.this, Informacion.class);
                vInfo.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vInfo);
            }
        });




        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()== R.id.fabCliente5){
                    IntentIntegrator integrador = new IntentIntegrator(Clientes.this);

                    integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrador.setPrompt("Lector de productos");
                    integrador.setCameraId(0);
                    integrador.setBeepEnabled(true);
                    integrador.setBarcodeImageEnabled(true);
                    integrador.initiateScan();
                }
            }
        });

        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Intent vTarjetas = new Intent(Clientes.this, EditarCliente.class);
                vTarjetas.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vTarjetas);
            }
        });

        fab7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Intent vTarjetas = new Intent(Clientes.this, PedidosCliente.class);
                vTarjetas.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vTarjetas);
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
                Intent vCodigo = new Intent(Clientes.this, VentaProductos2.class);
                vCodigo.putExtra("idUsuario", idUsuario);
                vCodigo.putExtra("codigoBarra", codigoBarra);
                startActivity(vCodigo);
                Toast.makeText(getApplicationContext(), "Captura de datos", Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }


    private void animateFab(){
        if(isOpen){
            fab.startAnimation(rotateBackward);
            fab2.startAnimation(fabClose);
            fab3.startAnimation(fabClose);
            fab4.startAnimation(fabClose);
            fab5.startAnimation(fabClose);
            fab6.startAnimation(fabClose);
            fab7.startAnimation(fabClose);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            fab5.setClickable(false);
            fab6.setClickable(false);
            fab7.setClickable(false);
            isOpen=false;
        }
        else {
            fab.startAnimation(rotateForward);
            fab2.startAnimation(fabOpen);
            fab3.startAnimation(fabOpen);
            fab4.startAnimation(fabOpen);
            fab5.startAnimation(fabOpen);
            fab6.startAnimation(fabOpen);
            fab7.startAnimation(fabOpen);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            fab5.setClickable(true);
            fab6.setClickable(true);
            fab7.setClickable(true);
            isOpen=true;
        }
    }

    public void init(){

        loadCategorias();

    }


    public void loadCategorias(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CATEGORIA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray categorias = new JSONArray(response);

                            for(int i = 0; i<categorias.length(); i++){
                                JSONObject categoriaObject = categorias.getJSONObject(i);

                                int idCategoria = categoriaObject.getInt("idCategoria");
                                String nombre = categoriaObject.getString("nombre");
                                String imagen = categoriaObject.getString("imagen");

                                elements.add(new ListElementCategoria( nombre, idCategoria, imagen,"#3381CF"));


                            }

                            listAdapter = new ListAdapterCategoria2(elements, Clientes.this, new ListAdapterCategoria2.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListElementCategoria item) {
                                    moveToDescription(item);
                                }
                            });
                            recyclerView = findViewById(R.id.recyclerViewCategoriasClientes);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Clientes.this));
                            recyclerView.setAdapter(listAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Clientes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void moveToDescription(ListElementCategoria item){
        Intent intent = new Intent(this, ApartadoProductos2.class);
        intent.putExtra("ListElementCategoria", item);
        intent.putExtra("idUsuario", idUsuario);
        startActivity(intent);
    }

    @Override public void onBackPressed() { }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if(keyCode== KeyEvent.KEYCODE_BACK) { return false; } return super.onKeyDown(keyCode, event); }
}