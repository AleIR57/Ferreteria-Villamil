package com.example.ferreteriavillamil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapterDomicilio extends RecyclerView.Adapter<ListAdapterDomicilio.ViewHolder>{

    private List<ListElementDomicilio> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapterDomicilio.OnItemClickListener listener;
    String urlImagen, estado2;

    public interface OnItemClickListener{
        void onItemClick(ListElementDomicilio item);
    }



    public ListAdapterDomicilio(List<ListElementDomicilio> itemList, Context context, ListAdapterDomicilio.OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;

    }

    @Override
    public int getItemCount(){ return mData.size(); }

    @Override
    public ListAdapterDomicilio.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element8, null);
        return new ListAdapterDomicilio.ViewHolder(view);

    }

    public void onBindViewHolder(final ListAdapterDomicilio.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));

        Glide.with(context).load("domicilio.png").into(holder.iconImage);

        System.out.println("Este es el estado: " + estado2);
        if(holder.estado.getText().toString().equals("En camino")){
            holder.estado.setTextColor(Color.parseColor("#FF4CAF50"));
        }
        if(holder.estado.getText().toString().equals("Entregado")){
            holder.estado.setTextColor(Color.parseColor("#7E7E7E"));
        }
    }

    public void setItems(List<ListElementDomicilio> items) {mData =  items;}




    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView barrio, direccion, estado;
        ImageButton imageButton2;

        ViewHolder(View itemView){

            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewDomicilio);
            barrio = itemView.findViewById(R.id.barrioTextView);
            direccion = itemView.findViewById(R.id.direccionTextView);
            estado = itemView.findViewById(R.id.estadoTextView);
            imageButton2 = itemView.findViewById(R.id.iconImageButton2Categoria);
        }

        void bindData(final ListElementDomicilio item){
            estado2 = item.getEstado();
            barrio.setText(item.getBarrio());
            direccion.setText(item.getDireccion());
            estado.setText(item.getEstado());
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
