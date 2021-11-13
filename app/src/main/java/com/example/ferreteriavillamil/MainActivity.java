package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView correo, contrasena, irARegistro;
    Button consultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correo = (TextView) findViewById(R.id.EditTextCorreo);
        contrasena = (TextView) findViewById(R.id.EditTextContraseña);
        consultar = (Button) findViewById(R.id.botonLogin);
        irARegistro = (TextView) findViewById(R.id.textView2);

        SpannableString mensaje = new SpannableString("¿Eres nuevo? ¡Regístrate!");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#fd1251"));// Puedes usar tambien .. new ForegroundColorSpan(Color.RED);
        mensaje.setSpan(colorSpan, 13, 25, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        irARegistro.setText(mensaje);

        consultar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Consultar(MainActivity.this).execute();
            }
        });
    }


    private Usuario consultar() throws JSONException, IOException {

        String url = Constants.URL + "usuario/login.php"; // Ruta

        //DATOS
        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(2);//definimos array
        nameValuePairs.add(new BasicNameValuePair("correo", correo.getText().toString().trim())); // pasamos el id al servicio php
        nameValuePairs.add(new BasicNameValuePair("contrasena", contrasena.getText().toString().trim()));

        String json = APIHandler.POSTRESPONSE(url, nameValuePairs); // creamos var json que se le asocia la respuesta del webservice
        if (json != null) { // si la respuesta no es vacia
            JSONObject object = new JSONObject(json); // creamos el objeto json que recorrera el servicio
            JSONArray json_array = object.optJSONArray("usuario");// accedemos al objeto json llamado multas
            if (json_array.length() > 0) { // si lo encontrado tiene al menos un registro
                Usuario usuario = new Usuario(json_array.getJSONObject(0));// instanciamos la clase multa para obtener los datos json
                int rol = usuario.getIdRol();
                int idUsuario = usuario.getIdUsuario();

                if(rol == 1){
                    Intent vClientes = new Intent(this, Clientes.class);
                    vClientes.putExtra("idUsuario", String.valueOf(idUsuario));
                    startActivity(vClientes);

                }

                if(rol == 2){
                    Intent vAdministrador = new Intent(this, Administrador.class);

                    startActivity(vAdministrador);
                }
                if(rol == 3){
                    Intent vProveedor = new Intent(this, Proveedor.class);

                    startActivity(vProveedor);
                }

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
                            Toast.makeText(context, "Has ingresado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                else
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(context, "Usuario no registrado o contraseña incorrecta", Toast.LENGTH_SHORT).show();
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




    public void activityRegistro(View v){
        Intent vRegistro = new Intent(this, RegistroUsuario.class);
        startActivity(vRegistro);
    }

}