package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class ApartadoCategorias2 extends AppCompatActivity {
    List<ListElementCategoria> elements;
    FloatingActionButton fab, fab5;

    String nombreAux, imagenAux;
    int  idVariableAux;
    ListAdapterCategoria2 listAdapter, objeto1;
    RecyclerView recyclerView;
    ImageButton almacen;
    private static final String CATEGORIA_URL = "http://192.168.1.15/api/categoria/categorias.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartado_categorias2);
        fab5 = findViewById(R.id.fabCliente5);
        init();
        elements = new ArrayList<>();
        almacen = findViewById(R.id.imageButtonAlmacen);

        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()== R.id.fabCliente5){
                    IntentIntegrator integrador = new IntentIntegrator(ApartadoCategorias2.this);
                    integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrador.setPrompt("Lector de productos");
                    integrador.setCameraId(0);
                    integrador.setBeepEnabled(true);
                    integrador.setBarcodeImageEnabled(true);
                    integrador.initiateScan();
                }
            }
        });

        almacen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(ApartadoCategorias2.this, ApartadoAlmacen.class);
                startActivity(vDomicilio);
            }
        });

    }

    public void init(){

        loadCategorias();

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
                Intent vCrear = new Intent(this, GestionProductos2.class);
                vCrear.putExtra("codigoBarra", String.valueOf(codigoBarra));
                startActivity(vCrear);
                Toast.makeText(getApplicationContext(), "Captura de datos", Toast.LENGTH_SHORT).show();

            }
        }

        super.onActivityResult(requestCode, resultCode, intent);
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

                            listAdapter = new ListAdapterCategoria2(elements, ApartadoCategorias2.this, new ListAdapterCategoria2.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListElementCategoria item) {
                                    moveToDescription(item);
                                }
                            });
                            recyclerView = findViewById(R.id.recyclerViewCategorias2);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ApartadoCategorias2.this));
                            recyclerView.setAdapter(listAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApartadoCategorias2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void moveToDescription(ListElementCategoria item){
        Intent intent = new Intent(this, ApartadoProductos.class);
        intent.putExtra("ListElementCategoria", item);
        startActivity(intent);
    }


}