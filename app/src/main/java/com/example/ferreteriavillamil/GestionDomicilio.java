package com.example.ferreteriavillamil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionDomicilio extends AppCompatActivity implements OnMapReadyCallback {
    MapFragment mapFragment;
    TextView nombretv, ciudadtv, barriotv, direcciontv, cantidadProductos, preciotv, nombreproductotv;
    String ciudad, barrio, direccion, latitud, longitud, estado, PRODUCTO_URL, PRODUCTOXFACTURA_URL, resultado, latitudOrigen, longitudOrigen;
    Button tomarPedido, finalizarPedido;
    int idDomicilio, idUsuario, idFactura;
    ImageButton volver;
    boolean actualPosition = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_domicilio);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        nombretv = findViewById(R.id.textViewNombre);
        ciudadtv = findViewById(R.id.textViewCiudad);
        barriotv = findViewById(R.id.textViewBarrio);
        direcciontv = findViewById(R.id.textViewDireccion);
        cantidadProductos = findViewById(R.id.textViewCantidad);
        preciotv = findViewById(R.id.textViewPrecio);
        nombreproductotv = findViewById(R.id.textViewNombreProducto);
        tomarPedido = findViewById(R.id.botonTomarPedido);
        finalizarPedido = findViewById(R.id.botonFinalizarPedido);
        volver = findViewById(R.id.imageButtonVolver);
        nombreproductotv.setMovementMethod(new ScrollingMovementMethod());
        cantidadProductos.setMovementMethod(new ScrollingMovementMethod());

        ListElementDomicilio element = (ListElementDomicilio) getIntent().getSerializableExtra("ListElementDomicilio");
        idDomicilio = element.getIdDomicilio();
        System.out.println("El id del domicilio es: " + idDomicilio);
        idUsuario = element.getIdUsuario();
        new Consultar(GestionDomicilio.this).execute();
        idFactura = element.getIdFactura();
        loadFacturas();
        loadProductosxFactura();
        ciudad = element.getCiudad();
        barrio = element.getBarrio();
        direccion = element.getDireccion();
        ciudadtv.setText("Ciudad: " + ciudad);
        barriotv.setText("Barrio: " + barrio);
        direcciontv.setText("Dirección: " + direccion);
        latitud = element.getLatitud();
        longitud = element.getLongitud();
        estado = element.getEstado();



        tomarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estado = "En camino";
                new GestionDomicilio.Modificar(GestionDomicilio.this).execute();

            }
        });

        finalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estado = "Entregado";
                new GestionDomicilio.Modificar(GestionDomicilio.this).execute();

            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vProveedor = new Intent(GestionDomicilio.this, Proveedor.class);
                startActivity(vProveedor);
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
            }
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            return;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(@NonNull Location location) {
                if(actualPosition){
                    latitudOrigen = String.valueOf(location.getLatitude());
                    longitudOrigen = String.valueOf(location.getLongitude());
                    actualPosition = false;
                    LatLng proveedor = new LatLng(Double.parseDouble(latitudOrigen), Double.parseDouble(longitudOrigen));
                    MarkerOptions marc2 = new MarkerOptions();
                    marc2.title("Proveedor");
                    marc2.position(proveedor);
                    googleMap.addMarker(marc2);
                }
            }
        });
        LatLng colombia = new LatLng(Double.parseDouble(latitud), Double.parseDouble(longitud));
        MarkerOptions marc = new MarkerOptions();
        marc.title("Domicilio");
        marc.position(colombia);
        googleMap.addMarker(marc);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colombia, 14f));
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {

            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    finish();
                    startActivity(getIntent());
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }




    private Usuario consultar() throws JSONException, IOException {

        String url = Constants.URL + "usuario/get-by-id.php"; // Ruta

        //DATOS
        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(1);//definimos array
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idUsuario))); // pasamos el id al servicio php

        String json = APIHandler.POSTRESPONSE(url, nameValuePairs); // creamos var json que se le asocia la respuesta del webservice
        if (json != null) { // si la respuesta no es vacia
            JSONObject object = new JSONObject(json); // creamos el objeto json que recorrera el servicio
            JSONArray json_array = object.optJSONArray("usuario");// accedemos al objeto json llamado multas
            if (json_array.length() > 0) { // si lo encontrado tiene al menos un registro
                Usuario usuario = new Usuario(json_array.getJSONObject(0));// instanciamos la clase multa para obtener los datos json

                return usuario;// retornamos la multa
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
                final Usuario usuario = consultar();
                if (usuario != null)
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nombretv.setText("Cliente: " + usuario.getNombre());
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

    public void loadFacturas() {

        PRODUCTO_URL = "http://192.168.1.15/api/factura/facturas.php?idFactura="+idFactura;
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
                                resultado = resultado.replace("null", "");
                                String precioTotal = facturaObject.getString("precioTotal");
                                nombreproductotv.setText(resultado);
                                preciotv.setText("Precio total: $" + precioTotal);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GestionDomicilio.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);


    }

    public void loadProductosxFactura() {

        PRODUCTOXFACTURA_URL = "http://192.168.1.15/api/productoxfactura/productosxfactura.php?idFactura="+idFactura;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTOXFACTURA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String cantidades = "";
                            String cantidades2 = "";
                            JSONArray productoxfactura = new JSONArray(response);

                            for (int i = 0; i < productoxfactura.length(); i++) {
                                JSONObject productoxfacturaObject = productoxfactura.getJSONObject(i);
                                cantidades = productoxfacturaObject.getString("cantidad") + "\n";
                                cantidades2 += cantidades;

                            }
                            cantidadProductos.setText(cantidades2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GestionDomicilio.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

    }

    private boolean modificar() {
        String url = Constants.URL + "domicilio/updateDomicilio.php";
        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(9);
        nameValuePairs.add(new BasicNameValuePair("idDomicilio", String.valueOf(idDomicilio)));
        nameValuePairs.add(new BasicNameValuePair("estado", estado));

        boolean response = APIHandler.POST(url, nameValuePairs); // enviamos los datos por POST al Webservice PHP
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
                        if(estado == "En camino"){
                            Toast.makeText(context, "Estás en camino a entregar el domicilio", Toast.LENGTH_LONG).show();
                        }
                        if(estado == "Entregado"){
                            Toast.makeText(context, "Entregaste el domicilio", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "No se pudo tomar o finalizar el domicilio", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }







}