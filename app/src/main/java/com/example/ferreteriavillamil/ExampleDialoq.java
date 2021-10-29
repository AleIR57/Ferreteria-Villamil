package com.example.ferreteriavillamil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialoq extends AppCompatDialogFragment {
    private EditText editTextCantidad;
    private ExampleDialogListener listener;
    private int idUsuario;



    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view).setTitle("Cantidad del producto").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cantidad = editTextCantidad.getText().toString();
                listener.applyTexts(cantidad);
            }
        });

                editTextCantidad = view.findViewById(R.id.etcantidad);

        return builder.create();
    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener{
        void applyTexts(String cantidad);
    }
}
