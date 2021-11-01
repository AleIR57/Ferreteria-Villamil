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

public class ListAdapterCategoria extends RecyclerView.Adapter<ListAdapterCategoria.ViewHolder>{
    private List<ListElementCategoria> mData;
    List<ListElementCategoria> listaOriginal;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapterCategoria.OnItemClickListener listener;
    String urlImagen;

    public interface OnItemClickListener{
        void onItemClick(ListElementCategoria item);
    }



    public ListAdapterCategoria(List<ListElementCategoria> itemList, Context context, ListAdapterCategoria.OnItemClickListener listener){
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
    public ListAdapterCategoria.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element2, null);
        return new ListAdapterCategoria.ViewHolder(view);

    }

    public void onBindViewHolder(final ListAdapterCategoria.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));

        Glide.with(context).load(urlImagen).into(holder.iconImage);
    }

    public void setItems(List<ListElementCategoria> items) {mData =  items;}

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            mData.clear();
            mData.addAll(listaOriginal);
        }else{
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                List<ListElementCategoria> collecion = mData.stream().filter(i -> i.getName().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                mData.clear();
                mData.addAll(collecion);
            }
            else{
                for (ListElementCategoria c: listaOriginal){
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
        TextView name, idCategoria, imagen;
        ImageButton imageButton2;

        ViewHolder(View itemView){

            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewCategoria);
            name = itemView.findViewById(R.id.nameTextView);
            idCategoria = itemView.findViewById(R.id.idCategoriaTextView);
            imageButton2 = itemView.findViewById(R.id.iconImageButton2Categoria);
        }

        void bindData(final ListElementCategoria item){
             urlImagen = item.getImagen();
             name.setText(item.getName());
            idCategoria.setText("Código:" + item.getIdCategoria() + "");
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
