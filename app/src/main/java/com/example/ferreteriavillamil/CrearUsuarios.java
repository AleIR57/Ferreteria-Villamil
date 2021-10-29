package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CrearUsuarios extends AppCompatActivity {
    TextView nombre, correo, contrasena, telefono, direccion, identificacion;
    Spinner idRol;
    Button insertar;
    int idRolSpinner;
    ImageButton usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuarios);
        nombre = (TextView) findViewById(R.id.EditTextNombreCrear);
        correo = (TextView) findViewById(R.id.EditTextCorreoCrear);
        contrasena = (TextView) findViewById(R.id.EditTextContraseñaCrear);
        telefono = (TextView) findViewById(R.id.EditTextTelefonoCrear);
        direccion = (TextView) findViewById(R.id.EditTextDireccionCrear);
        identificacion = (TextView) findViewById(R.id.EditTextIdentificacionCrear);
        idRol = findViewById(R.id.spinnerRoles);
        usuario = findViewById(R.id.imageButtonUsuario);

        String [] opciones = {"Usuario", "Administrador", "Proveedor"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_primero, opciones);
        idRol.setAdapter(adapter);

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

                    new CrearUsuarios.Registrar(CrearUsuarios.this).execute();
                } else {

                    Toast.makeText(CrearUsuarios.this, "Hay información por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vDomicilio = new Intent(CrearUsuarios.this, ApartadoUsuarios.class);
                startActivity(vDomicilio);
            }
        });


    }


    private boolean registrar() {
        idRolSpinner = 1;

        if(idRol.getSelectedItem() == "Usuario"){
            idRolSpinner = 1;
        }
        if(idRol.getSelectedItem() == "Administrador"){
            idRolSpinner = 2;
        }
        if(idRol.getSelectedItem() == "Proveedor"){
            idRolSpinner = 3;
        }

        String url = Constants.URL + "usuario/addUsuario.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(8); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("nombre", nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("correo", correo.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("contrasena", contrasena.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono", telefono.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("direccion", direccion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("identificacion", identificacion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("idRol", String.valueOf(idRolSpinner).trim()));

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
                        Intent vApartado = new Intent(CrearUsuarios.this, ApartadoUsuarios.class);
                        startActivity(vApartado);
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


}