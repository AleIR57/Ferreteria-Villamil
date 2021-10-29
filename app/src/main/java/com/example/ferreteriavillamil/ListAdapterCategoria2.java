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

public class ListAdapterCategoria2 extends RecyclerView.Adapter<ListAdapterCategoria2.ViewHolder>{
    private List<ListElementCategoria> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapterCategoria2.OnItemClickListener listener;
    String urlImagen;

    public interface OnItemClickListener{
        void onItemClick(ListElementCategoria item);
    }



    public ListAdapterCategoria2(List<ListElementCategoria> itemList, Context context, ListAdapterCategoria2.OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;

    }

    @Override
    public int getItemCount(){ return mData.size(); }

    @Override
    public ListAdapterCategoria2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element4, null);
        return new ListAdapterCategoria2.ViewHolder(view);

    }

    public void onBindViewHolder(final ListAdapterCategoria2.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));

        Glide.with(context).load(urlImagen).into(holder.iconImage);
    }

    public void setItems(List<ListElementCategoria> items) {mData =  items;}




    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name;

        ViewHolder(View itemView){

            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewCategoria);
            name = itemView.findViewById(R.id.nameTextView);

        }

        void bindData(final ListElementCategoria item){
            urlImagen = item.getImagen();
            name.setText(item.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }


    }
}
