package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApartadoUsuarios extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SearchView txtBuscar;
    List<ListElement> elements;
    FloatingActionButton fab;
    String nombreAux, correoAux, contrasenaAux, direccionAux;
    int telefonoAux, identificacionAux, idRolAux, idVariableAux, idVariableAux2;
    ListAdapter listAdapter, objeto1;
    RecyclerView recyclerView;
    ImageButton administrador;
    Bitmap imageBitmap;
    private static final String USUARIO_URL = "http://192.168.1.15/api/usuario/usuarios.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.background3);
        setContentView(R.layout.activity_apartado_usuarios);
        administrador = findViewById(R.id.imageButtonAdministrador);
        txtBuscar = findViewById(R.id.txtBuscar);
        init();
        elements = new ArrayList<>();
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuarios();
            }
        });



        administrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(ApartadoUsuarios.this, Administrador.class);
                startActivity(vDomicilio);
            }
        });
    }

    public void init(){

        loadUsuarios();



    }


    public void loadUsuarios(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, USUARIO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray usuarios = new JSONArray(response);

                            for(int i = 0; i<usuarios.length(); i++){
                                JSONObject usuarioObject = usuarios.getJSONObject(i);

                                int idUsuario = usuarioObject.getInt("idUsuario");
                                String nombre = usuarioObject.getString("nombre");
                                String correo = usuarioObject.getString("correo");
                                String contrasena = usuarioObject.getString("contrasena");
                                int telefono = usuarioObject.getInt("telefono");
                                String direccion = usuarioObject.getString("direccion");
                                int identificacion = usuarioObject.getInt("identificacion");
                                int idRol = usuarioObject.getInt("idRol");
                                String foto = usuarioObject.getString("imagen");

                                elements.add(new ListElement("#3381CF", nombre, correo, idUsuario, identificacion, foto,"#3381CF"));


                            }

                            listAdapter = new ListAdapter(elements, ApartadoUsuarios.this, new ListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListElement item) {
                                    moveToDescription(item);
                                }
                            });
                            recyclerView = findViewById(R.id.recyclerView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ApartadoUsuarios.this));
                            recyclerView.setAdapter(listAdapter);
                            txtBuscar.setOnQueryTextListener(ApartadoUsuarios.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApartadoUsuarios.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void moveToDescription(ListElement item){
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }

    public void crearUsuarios(){
        Intent vCrear = new Intent(this, CrearUsuarios.class);
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