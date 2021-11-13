package com.example.ferreteriavillamil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class Domicilio extends AppCompatActivity implements OnMapReadyCallback {
    MapFragment mapFragment;
    TextView prueba;
    String latitud = "", longitud = "", idUsuario, idFactura;
    Button insertar;
    EditText ciudad, barrio, direccion;
    ImageButton volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilio);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        prueba = findViewById(R.id.textView2);

        idUsuario = getIntent().getStringExtra("idUsuario");
        idFactura = getIntent().getStringExtra("idFactura");
        System.out.println("El id del usuario en el domicilio es: " + idUsuario);
        System.out.println("El id de la factura en el domicilio es: " + idFactura);
        ciudad = findViewById(R.id.EditTextCiudad);
        barrio = findViewById(R.id.EditTextBarrio);
        direccion = findViewById(R.id.EditTextDireccion);

        insertar = (Button) findViewById(R.id.botonRegistro);

        insertar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String prueba2 = prueba.getText().toString();
                if ((!ciudad.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!barrio.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!direccion.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!prueba2.equals("Seleccione la ubicación de destino"))){

                    new Domicilio.Registrar(Domicilio.this).execute();
                } else {

                    Toast.makeText(Domicilio.this, "Hay información por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng cali = new LatLng(3.42158, -76.5225);
        MarkerOptions marc = new MarkerOptions();
        marc.title("Domicilio");
        marc.draggable(true);
        marc.position(cali);
        googleMap.addMarker(marc);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cali));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cali, 12f));
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                latitud = marker.getPosition().latitude+"";
                longitud = marker.getPosition().longitude+"";
                prueba.setText("Su posición es: " + latitud + ", " + longitud);
            }
        });


    }

    private boolean registrar() {


        String url = Constants.URL + "domicilio/addDomicilio.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(9); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
        nameValuePairs.add(new BasicNameValuePair("idFactura", idFactura));
        nameValuePairs.add(new BasicNameValuePair("ciudad", ciudad.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("barrio", barrio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("direccion", direccion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("latitud", String.valueOf(latitud)));
        nameValuePairs.add(new BasicNameValuePair("longitud", String.valueOf(longitud)));
        nameValuePairs.add(new BasicNameValuePair("estado", String.valueOf("Pendiente")));
        boolean response = APIHandler.POST(url, nameValuePairs);
        return response;
    }

    class Registrar extends AsyncTask<String, String, String> {
        private Activity context;

        Registrar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (registrar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Su domicilio llegará pronto", Toast.LENGTH_LONG).show();
                        ciudad.setText("");
                        barrio.setText("");
                        direccion.setText("");
                        Intent vApartado = new Intent(Domicilio.this, Clientes.class);
                        vApartado.putExtra("idUsuario", String.valueOf(idUsuario));
                        startActivity(vApartado);
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "No se pudo realizar el domicilio", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    @Override public void onBackPressed() { }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if(keyCode== KeyEvent.KEYCODE_BACK) { return false; } return super.onKeyDown(keyCode, event); }



}