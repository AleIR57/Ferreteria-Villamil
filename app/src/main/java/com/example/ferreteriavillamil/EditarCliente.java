package com.example.ferreteriavillamil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditarCliente extends AppCompatActivity {

    TextView nombre, correo, contrasena, telefono, direccion, identificacion;
    Button modificar;
    int idUsuario;
    ImageButton usuario;
    int idRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        idUsuario = Integer.parseInt(getIntent().getStringExtra("idUsuario"));

        new Consultar(EditarCliente.this).execute();
        nombre = findViewById(R.id.EditTextNombreGestion);
        correo = findViewById(R.id.EditTextCorreoGestion);
        contrasena = findViewById(R.id.EditTextContraseñaGestion);
        telefono = findViewById(R.id.EditTextTelefonoGestion);
        direccion = findViewById(R.id.EditTextDireccionGestion);
        identificacion = findViewById(R.id.EditTextIdentificacionGestion);
        usuario = findViewById(R.id.imageButtonUsuario);

        String [] opciones = {"Usuario", "Administrador", "Proveedor"};



        modificar = (Button) findViewById(R.id.botonModificar);


        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Modificar(EditarCliente.this).execute();
            }
        });

        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(EditarCliente.this, Clientes.class);
                vDomicilio.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vDomicilio);
            }
        });

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
                            nombre.setText(usuario.getNombre());
                            correo.setText(usuario.getCorreo());
                            contrasena.setText(usuario.getContrasena());
                            telefono.setText(usuario.getTelefono() + "");
                            direccion.setText(usuario.getDireccion());
                            identificacion.setText(usuario.getIdentificacion() + "");
                            idRol = usuario.getIdRol();
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

    private boolean modificar() {


        String url = Constants.URL + "usuario/update.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(9);
        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idUsuario)));
        nameValuePairs.add(new BasicNameValuePair("nombre", nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("correo", correo.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("contrasena", contrasena.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono", telefono.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("direccion", direccion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("identificacion", identificacion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("idRol", String.valueOf(idRol).trim()));


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
                        Toast.makeText(context, "Usuario modificado correctamente", Toast.LENGTH_LONG).show();

                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }



}