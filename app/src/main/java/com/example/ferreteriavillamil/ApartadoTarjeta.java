package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ApartadoTarjeta extends AppCompatActivity {

    List<ListElementTarjeta> elements;
    ListAdapterTarjeta listAdapter, objeto1;
    RecyclerView recyclerView;
    int idTarjeta;
    String idUsuario;
    String nombreTitular, numeroTarjeta, codigoCVC, fechaExpiracion, nombreEntidadBancaria, imagen;
    ImageButton volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartado_tarjeta);
        volver = findViewById(R.id.imageButtonVolver);
        idUsuario = getIntent().getStringExtra("idUsuario");

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vVolver = new Intent(ApartadoTarjeta.this, Clientes.class);
                vVolver.putExtra("idUsuario", String.valueOf(idUsuario));
                startActivity(vVolver);
            }
        });

        elements = new ArrayList<>();

        loadTarjetas();
    }

    public void loadTarjetas(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tarjetacompra", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM tarjetas",null);


        while(fila.moveToNext() != false){
            idTarjeta = fila.getInt(0);
            nombreTitular = fila.getString(1);
            numeroTarjeta = fila.getString(2);
            codigoCVC = fila.getString(3);
            fechaExpiracion = fila.getString(4);
            nombreEntidadBancaria = fila.getString(5);
            imagen = "https://www.mastercard.es/content/dam/public/mastercardcom/eu/es/images/Consumidores/escoge-tu-tarjeta/tarjetas-de-debito/World-mastecard-debit/world-debit-card-1280x720.png";

            elements.add(new ListElementTarjeta(nombreTitular,imagen, idTarjeta, numeroTarjeta, codigoCVC, fechaExpiracion, nombreEntidadBancaria));
        }
        bd.close();

        listAdapter = new ListAdapterTarjeta(elements, ApartadoTarjeta.this, new ListAdapterTarjeta.OnItemClickListener() {
            @Override
            public void onItemClick(ListElementTarjeta item) {
                moveToDescription(item);
            }
        });
        recyclerView = findViewById(R.id.recyclerViewTarjetas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApartadoTarjeta.this));
        recyclerView.setAdapter(listAdapter);

    }

    public void moveToDescription(ListElementTarjeta item){
        Toast.makeText(this,"Hola", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, EditarTarjeta.class);
        intent.putExtra("ListElementTarjeta", item);
        startActivity(intent);
    }

}