package com.example.ferreteriavillamil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    List<ListElement> listaOriginal;
    private LayoutInflater mInflater;
    private Context context;
    String urlImagen;
    final ListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(ListElement item);
    }



    public ListAdapter(List<ListElement> itemList, Context context, ListAdapter.OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(itemList);
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

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            mData.clear();
            mData.addAll(listaOriginal);
        }else{
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                List<ListElement> collecion = mData.stream().filter(i -> i.getName().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                mData.clear();
                mData.addAll(collecion);
            }
            else{
                for (ListElement c: listaOriginal){
                    if(c.getName().toLowerCase().contains(txtBuscar.toLowerCase())){
                        mData.add(c);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }


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
            urlImagen = item.getImagen();
            iconImage.setImageBitmap(convert(urlImagen));
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


    public static Bitmap convert(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }




}
