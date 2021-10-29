package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Informes extends AppCompatActivity {
    private static final String FACTURA_URL = "http://192.168.1.15/api/factura/facturas2.php";
    private static final String PRODUCTO_URL = "http://192.168.1.15/api/producto/productos.php";
    private static final String PRODUCTO2_URL = "http://192.168.1.15/api/producto/productos2.php?cantidad=0";
    private static final String USUARIO_URL = "http://192.168.1.15/api/usuario/usuarios.php";
    int contadorVentas, contadorProductos, contadorProductosAgotados, contadorUsuarios;
    TextView cantidadVentas, ventasTotales, productosTotales, productosAgotados, usuariosRegistrados;
    float precioTotal;
    ImageButton administrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);

        cantidadVentas = findViewById(R.id.CantidadVentas);
        ventasTotales = findViewById(R.id.dineroTotal);
        productosTotales = findViewById(R.id.productosTotales);
        productosAgotados = findViewById(R.id.ProductosAgotados);
        usuariosRegistrados = findViewById(R.id.usuariosRegistrados);
        administrador = findViewById(R.id.imageButtonAdministrador);
        loadFacturas();
        loadDinero();
        loadProducto();
        loadProducto2();
        loadUsuarios();

        administrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(Informes.this, Administrador.class);
                startActivity(vDomicilio);
            }
        });
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
                                contadorVentas = ventas.length();
                                cantidadVentas.setText("Ventas totales: " + contadorVentas);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Informes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        Volley.newRequestQueue(this).add(stringRequest);

    }



    public void loadDinero(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, FACTURA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray facturas = new JSONArray(response);

                            for(int i = 0; i<facturas.length(); i++){
                                JSONObject facturaObject = facturas.getJSONObject(i);
                                String precio = facturaObject.getString("precioTotal");
                                precioTotal += Float.parseFloat(precio);
                            }
                            ventasTotales.setText("Recaudado: " + "\n" + "$"+precioTotal);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Informes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void loadProducto() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray productos = new JSONArray(response);
                            contadorProductos = productos.length();
                            productosTotales.setText("Productos registrados: " + contadorProductos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Informes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void loadProducto2() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTO2_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray productos = new JSONArray(response);
                            contadorProductosAgotados = productos.length();
                            productosAgotados.setText("Productos agotados: " + contadorProductosAgotados);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Informes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void loadUsuarios() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, USUARIO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray usuarios = new JSONArray(response);
                            contadorUsuarios = usuarios.length();
                            usuariosRegistrados.setText("Usuarios registrados: " + contadorUsuarios);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Informes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void irAEstadisticas(View v){
        Intent vEstadisticas = new Intent(this, Estadisticas.class);
        startActivity(vEstadisticas);
    }
}