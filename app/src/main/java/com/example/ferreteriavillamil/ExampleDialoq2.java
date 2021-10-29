package com.example.ferreteriavillamil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialoq2 extends AppCompatDialogFragment {
    private EditText editTextNombreTitular, editTextNumeroTarjeta, editTextCodigoCVC, editTextFechaExpiracion, editTextNombreEntidadBancaria;
    private ExampleDialogListener2 listener;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog2, null);

        builder.setView(view).setTitle("Datos de su tarjeta").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombreTitular = editTextNombreTitular.getText().toString();
                String numeroTarjeta = editTextNumeroTarjeta.getText().toString();
                String codigoCVC = editTextCodigoCVC.getText().toString();
                String fechaExpiracion = editTextFechaExpiracion.getText().toString();
                String nombreEntidadBancaria = editTextNombreEntidadBancaria.getText().toString();
                listener.applyTexts(nombreTitular, numeroTarjeta, codigoCVC, fechaExpiracion, nombreEntidadBancaria);
            }
        });

        editTextNombreTitular = view.findViewById(R.id.etnombretitular);
        editTextNumeroTarjeta = view.findViewById(R.id.etnumerotarjeta);
        editTextCodigoCVC = view.findViewById(R.id.etcodigocvc);
        editTextFechaExpiracion = view.findViewById(R.id.etfechaexpiracion);
        editTextNombreEntidadBancaria = view.findViewById(R.id.etentidadbancaria);

        return builder.create();
    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener2) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener2{
        void applyTexts(String nombreTitular, String numeroTarjeta, String codigoCVC, String fechaExpiracion, String nombreEntidadBancaria);
    }
}
