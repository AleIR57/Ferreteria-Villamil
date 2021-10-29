package com.example.ferreteriavillamil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(ListElement item);
    }



    public ListAdapter(List<ListElement> itemList, Context context, ListAdapter.OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;

    }

    @Override
    public int getItemCount(){ return mData.size(); }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);

    }

    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElement> items) {mData =  items;}




    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, correo, idUsuario, identificacion;
        ImageButton  imageButton2;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            correo = itemView.findViewById(R.id.correoTextView);
            idUsuario = itemView.findViewById(R.id.idTextView);
            identificacion = itemView.findViewById(R.id.identificacionTextView);

            imageButton2 = itemView.findViewById(R.id.iconImageButton2);
        }

        void bindData(final ListElement item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            name.setText(item.getName());
            correo.setText(item.getCorreo());
            idUsuario.setText("Código: " + item.getIdUsuario() + "");
            identificacion.setText("Identificación: " + item.getIdentificacion() + "");
            imageButton2.setColorFilter(Color.parseColor(item.getColor2()), PorterDuff.Mode.SRC_IN);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }


    }





}
