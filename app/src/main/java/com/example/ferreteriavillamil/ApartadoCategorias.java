package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApartadoCategorias extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView txtBuscar;
    List<ListElementCategoria> elements;
    FloatingActionButton fab;
    String nombreAux, imagenAux;
    int  idVariableAux;
    ListAdapterCategoria listAdapter, objeto1;
    RecyclerView recyclerView;
    ImageButton almacen;
    private static final String CATEGORIA_URL = "http://192.168.1.15/api/categoria/categorias.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.background3);
        setContentView(R.layout.activity_apartado_categorias);
        almacen = findViewById(R.id.imageButtonAlmacen);
        txtBuscar = findViewById(R.id.txtBuscar);


        init();
        elements = new ArrayList<>();
        fab = findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCategorias();
            }
        });

        almacen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(ApartadoCategorias.this, ApartadoAlmacen.class);
                startActivity(vDomicilio);
            }
        });
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

                            listAdapter = new ListAdapterCategoria(elements, ApartadoCategorias.this, new ListAdapterCategoria.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListElementCategoria item) {
                                    moveToDescription(item);
                                }
                            });
                            recyclerView = findViewById(R.id.recyclerViewCategorias);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ApartadoCategorias.this));
                            recyclerView.setAdapter(listAdapter);
                            txtBuscar.setOnQueryTextListener(ApartadoCategorias.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApartadoCategorias.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void moveToDescription(ListElementCategoria item){
        Intent intent = new Intent(this, GestionCategoria.class);
        intent.putExtra("ListElementCategoria", item);
        startActivity(intent);
    }

    public void crearCategorias(){
        Intent vCrear = new Intent(this, CrearCategorias.class);
        startActivity(vCrear);
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