package com.example.ferreteriavillamil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapterTarjeta extends RecyclerView.Adapter<ListAdapterTarjeta.ViewHolder>{

    private List<ListElementTarjeta> mData;
    private LayoutInflater mInflater;
    private Context context;
    TextView nombreEntidad;
    String urlImagen;
    final ListAdapterTarjeta.OnItemClickListener listener;
    int idCategoria, idProducto;
    public interface OnItemClickListener{
        void onItemClick(ListElementTarjeta item);
    }

    public ListAdapterTarjeta(List<ListElementTarjeta> itemList, Context context, ListAdapterTarjeta.OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;

    }

    @Override
    public int getItemCount(){ return mData.size(); }

    @Override
    public ListAdapterTarjeta.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element7, null);
        return new ListAdapterTarjeta.ViewHolder(view);

    }

    public void onBindViewHolder(final ListAdapterTarjeta.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
        Glide.with(context).load(urlImagen).into(holder.iconImage);
    }

    public void setItems(List<ListElementTarjeta> items) {mData =  items;}




    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, idTarjeta;
        ImageButton imageButton2;

        ViewHolder(View itemView){

            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewTarjeta);
            name = itemView.findViewById(R.id.nameTextView);
            idTarjeta = itemView.findViewById(R.id.idTarjetaTextView);
            nombreEntidad = itemView.findViewById(R.id.NombreEntidadTextView);
            imageButton2 = itemView.findViewById(R.id.iconImageButton2);

        }

        void bindData(final ListElementTarjeta item){
            name.setText(item.getNombreTitular());
            idTarjeta.setText(item.getIdTarjeta() + "");
            nombreEntidad.setText(item.getNombreEntidadBancaria());
            urlImagen = item.getImagen();
            imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eliminarTarjeta(item);
                }
            });

        }

    }

    public void eliminarTarjeta(final ListElementTarjeta item){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "tarjetacompra", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int cant = bd.delete("tarjetas","idTarjeta="+ item.getIdTarjeta(),null);
        if(cant==1){
            Toast.makeText(context,"Se eliminó con éxito la tarjeta", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,item.getIdTarjeta() + "", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
}
