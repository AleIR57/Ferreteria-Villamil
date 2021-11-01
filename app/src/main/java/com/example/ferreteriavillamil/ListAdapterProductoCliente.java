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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListAdapterProductoCliente extends RecyclerView.Adapter<ListAdapterProductoCliente.ViewHolder>{
    private List<ListElementProducto> mData;
    List<ListElementProducto> listaOriginal;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapterProductoCliente.OnItemClickListener listener;
    String urlImagen;
    int idCategoria;

    public interface OnItemClickListener{
        void onItemClick(ListElementProducto item);
    }


    public ListAdapterProductoCliente(List<ListElementProducto> itemList, Context context, ListAdapterProductoCliente.OnItemClickListener listener){
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
    public ListAdapterProductoCliente.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element5, null);
        return new ListAdapterProductoCliente.ViewHolder(view);

    }

    public void onBindViewHolder(final ListAdapterProductoCliente.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));

        Glide.with(context).load(urlImagen).into(holder.iconImage);
        if(holder.cantidadView.getText().toString().equals("Disponible")){
            holder.cantidadView.setTextColor(Color.parseColor("#FF4CAF50"));
        }
        if(holder.cantidadView.getText().toString().equals("Agotado")){
            holder.cantidadView.setTextColor(Color.parseColor("#ef5350"));
        }
    }

    public void setItems(List<ListElementProducto> items) {mData =  items;}

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            mData.clear();
            mData.addAll(listaOriginal);
        }else{
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                List<ListElementProducto> collecion = mData.stream().filter(i -> i.getName().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                mData.clear();
                mData.addAll(collecion);
            }
            else{
                for (ListElementProducto c: listaOriginal){
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
        TextView name, idProducto, precio, cantidadView, nombreCategoria, imagen;
        ImageButton imageButton2;

        ViewHolder(View itemView){

            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewProductoCliente);
            name = itemView.findViewById(R.id.nameTextView);
            precio = itemView.findViewById(R.id.precioTextView);
            cantidadView = itemView.findViewById(R.id.disponibleTextView);
            imageButton2 = itemView.findViewById(R.id.iconImageButton2Producto);
        }

        void bindData(final ListElementProducto item){
            urlImagen = item.getImagen();
            name.setText(item.getName());
            int cantidad = item.getCantidad();
            precio.setText("Precio: $" + item.getPrecio());
            if(cantidad > 0 ){
                cantidadView.setText("Disponible");
            }
            if (cantidad == 0){
                cantidadView.setText("Agotado");
            }
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
