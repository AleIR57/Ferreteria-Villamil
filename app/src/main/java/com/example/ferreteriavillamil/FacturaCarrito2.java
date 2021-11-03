package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FacturaCarrito2 extends AppCompatActivity {
    String contadorFacturas, idUsuario;
    int contadorFacturas2;
    TextView nombreProductos, cantidadProductos, precioProductos, cantidadTotalProductos, precioTotalProductos;
    String nombreProductosAux, cantidadProductosAux, precioProductosAux;
    String PRODUCTO_URL, PRODUCTOXFACTURA_URL;
    String cantidadTotal;
    String precioTotal;
    ImageButton domicilio, volver;
    float precioPorCantidad;
    float precio;
    String resultado = "";
    String cantidades = "";
    String cantidades2 = "";
    String precios = "";
    String precios2 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura_carrito2);
        contadorFacturas = getIntent().getStringExtra("idFactura");
        idUsuario = getIntent().getStringExtra("idUsuario");
        contadorFacturas2 = Integer.parseInt(contadorFacturas);
        domicilio = findViewById(R.id.imageButtonDomicilio);
        volver = findViewById(R.id.imageButtonVolver);

        System.out.println("El contador es: " + contadorFacturas2);

        nombreProductos = findViewById(R.id.textViewNombreProductoTexto2);
        cantidadProductos = findViewById(R.id.textViewCantidadTexto2);
        precioProductos = findViewById(R.id.textViewPrecioTexto2);
        cantidadTotalProductos = findViewById(R.id.textViewCantidadTotalProducto);
        precioTotalProductos = findViewById(R.id.textViewPrecioTotalproducto);

        loadFacturas();


        domicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(FacturaCarrito2.this, Domicilio.class);
                vDomicilio.putExtra("idUsuario", idUsuario);
                vDomicilio.putExtra("idFactura", String.valueOf(contadorFacturas));
                startActivity(vDomicilio);
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vVolver = new Intent(FacturaCarrito2.this, Clientes.class);
                startActivity(vVolver);
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadProductosxFactura();
            }
        }, 800);



    }

    public void loadFacturas() {

        PRODUCTO_URL = "http://192.168.1.15/api/factura/facturas.php?idFactura="+contadorFacturas2;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray facturas = new JSONArray(response);

                            for (int i = 0; i < facturas.length(); i++) {
                                JSONObject facturaObject = facturas.getJSONObject(i);
                                String descripcion = facturaObject.getString("descripcion");
                                String[] partes = descripcion.split("-");
                                for(int j = 0; j < partes.length; j++){
                                    resultado +=  partes[j] + "\n";
                                }
                                precioTotal = facturaObject.getString("precioTotal");
                                cantidadTotal = facturaObject.getString("cantidadTotal");
                                cantidadTotalProductos.setText(cantidadTotal + "");
                                precioTotalProductos.setText(precioTotal + "");
                                nombreProductos.setText(resultado);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FacturaCarrito2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        Volley.newRequestQueue(this).add(stringRequest);


    }

    public void loadProductosxFactura() {

        PRODUCTOXFACTURA_URL = "http://192.168.1.15/api/productoxfactura/productosxfactura.php?idFactura="+contadorFacturas2;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTOXFACTURA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray productoxfactura = new JSONArray(response);
                            for (int i = 0; i < productoxfactura.length(); i++) {
                                JSONObject productoxfacturaObject = productoxfactura.getJSONObject(i);
                                cantidades += productoxfacturaObject.getString("cantidad") + "\n";
                                precios += productoxfacturaObject.getString("precio") + "\n";
                                cantidadProductos.setText(cantidades);
                                precioProductos.setText(precios);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FacturaCarrito2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        Volley.newRequestQueue(this).add(stringRequest);


    }



}