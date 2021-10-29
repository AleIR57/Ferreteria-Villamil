package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class RegistroUsuario extends AppCompatActivity {
    TextView nombre, correo, contrasena, telefono, direccion, identificacion, irARegistro;
    Button insertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        nombre = (TextView) findViewById(R.id.EditTextNombreRegistro);
        correo = (TextView) findViewById(R.id.EditTextCorreoRegistro);
        contrasena = (TextView) findViewById(R.id.EditTextContraseñaRegistro);
        telefono = (TextView) findViewById(R.id.EditTextTelefonoRegistro);
        direccion = (TextView) findViewById(R.id.EditTextDireccionRegistro);
        identificacion = (TextView) findViewById(R.id.EditTextIdentificacionRegistro);
        irARegistro = (TextView) findViewById(R.id.textView2);

        SpannableString mensaje = new SpannableString("¿Ya tienes una cuenta? ¡Inicia Sesión!");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#ef5350"));// Puedes usar tambien .. new ForegroundColorSpan(Color.RED);
        mensaje.setSpan(colorSpan, 23, 38, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        irARegistro.setText(mensaje);

        insertar = (Button) findViewById(R.id.botonRegistro);

        insertar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if ((!nombre.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!correo.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!contrasena.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!telefono.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!direccion.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!identificacion.getText().toString().trim().equalsIgnoreCase(""))){

                    new Registrar(RegistroUsuario.this).execute();
                } else {

                    Toast.makeText(RegistroUsuario.this, "Hay información por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean registrar() {

        String url = Constants.URL + "usuario/add.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(7); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("nombre", nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("correo", correo.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("contrasena", contrasena.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono", telefono.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("direccion", direccion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("identificacion", identificacion.getText().toString().trim()));

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
                        Toast.makeText(context, "Usuario creado correctamente", Toast.LENGTH_LONG).show();
                        nombre.setText("");
                        correo.setText("");
                        contrasena.setText("");
                        telefono.setText("");
                        direccion.setText("");
                        identificacion.setText("");
                        Intent vLogin = new Intent(RegistroUsuario.this, MainActivity.class);
                        startActivity(vLogin);
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "No se pudo crear el usuario", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    public void activityLogin(View v){
        Intent vLogin = new Intent(this, MainActivity.class);
        startActivity(vLogin);
    }




}