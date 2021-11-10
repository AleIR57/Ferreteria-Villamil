package com.example.ferreteriavillamil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PedidosCliente extends AppCompatActivity {
    List<ListElementDomicilio> elements;
    FloatingActionButton fab;
    String nombreAux, imagenAux;
    int  idVariableAux;
    ListAdapterDomicilio listAdapter, objeto1;
    RecyclerView recyclerView;
    String idUsuario;
    ImageButton volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_cliente);
        idUsuario = getIntent().getStringExtra("idUsuario");
        volver = findViewById(R.id.imageButtonVolver);
        init();
        elements = new ArrayList<>();

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vProveedor = new Intent(PedidosCliente.this, Clientes.class);
                vProveedor.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vProveedor);
            }
        });
    }

    public void init(){

        loadDomicilios();

    }

    public void loadDomicilios(){
        String DOMICILIO_URL = "http://192.168.1.15/api/domicilio/domicilios2.php?idUsuario="+idUsuario;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, DOMICILIO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray domicilios = new JSONArray(response);

                            for(int i = 0; i<domicilios.length(); i++){
                                JSONObject domicilioObject = domicilios.getJSONObject(i);

                                int idDomicilio = domicilioObject.getInt("idDomicilio");
                                int idUsuario = domicilioObject.getInt("idUsuario");
                                int idFactura = domicilioObject.getInt("idFactura");
                                String ciudad = domicilioObject.getString("ciudad");
                                String barrio = domicilioObject.getString("barrio");
                                String direccion = domicilioObject.getString("direccion");
                                String latitud = domicilioObject.getString("latitud");
                                String longitud = domicilioObject.getString("longitud");
                                String estado = domicilioObject.getString("estado");
                                elements.add(new ListElementDomicilio(idDomicilio, idUsuario, idFactura, ciudad, barrio, direccion, latitud, longitud, estado, "#010101"));


                            }

                            listAdapter = new ListAdapterDomicilio(elements, PedidosCliente.this, new ListAdapterDomicilio.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListElementDomicilio item) {
                                    moveToDescription(item);
                                }
                            });
                            recyclerView = findViewById(R.id.recyclerViewDomicilios);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(PedidosCliente.this));
                            recyclerView.setAdapter(listAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PedidosCliente.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void moveToDescription(ListElementDomicilio item){
        Intent intent = new Intent(this, DetalleDomicilio.class);
        intent.putExtra("ListElementDomicilio", item);
        startActivity(intent);
    }
}