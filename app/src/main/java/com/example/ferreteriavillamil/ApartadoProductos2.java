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

public class ApartadoProductos2 extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView txtBuscar;
    List<ListElementProducto> elements;
    FloatingActionButton fab;
    ListAdapterProductoCliente listAdapter, objeto1;
    RecyclerView recyclerView;
    int idCategoriaAux;
    String idUsuario;
    String nombreCategoria;
    ImageButton categoria;
    private static final String PRODUCTO_URL = "http://192.168.1.15/api/producto/productos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.background4);
        idUsuario = getIntent().getStringExtra("idUsuario");
        setContentView(R.layout.activity_apartado_productos2);
        categoria = findViewById(R.id.imageButtonCategoria);
        txtBuscar = findViewById(R.id.txtBuscar);
        try{
            ListElementCategoria element = (ListElementCategoria) getIntent().getSerializableExtra("ListElementCategoria");
            idCategoriaAux = element.getIdCategoria();
        }
        catch(NullPointerException e ){
            idCategoriaAux = Integer.parseInt(getIntent().getStringExtra("idCategoria"));
        }

        loadProductos();

        elements = new ArrayList<>();

        categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(ApartadoProductos2.this, Clientes.class);
                vDomicilio.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vDomicilio);
            }
        });


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

                            listAdapter = new ListAdapterProductoCliente(elements, ApartadoProductos2.this, new ListAdapterProductoCliente.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListElementProducto item) {
                                    moveToDescription(item);
                                }
                            });
                            recyclerView = findViewById(R.id.recyclerViewProductos2);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ApartadoProductos2.this));
                            recyclerView.setAdapter(listAdapter);
                            txtBuscar.setOnQueryTextListener(ApartadoProductos2.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApartadoProductos2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }


    public void moveToDescription(ListElementProducto item){
        Intent intent = new Intent(this, DetalleProducto.class);
        intent.putExtra("ListElementProducto", item);
        intent.putExtra("idUsuario", idUsuario);
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