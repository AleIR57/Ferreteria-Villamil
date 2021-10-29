package com.example.ferreteriavillamil;

import android.content.ContentValues;
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

import java.util.List;

public class ListAdapterProductoCarrito extends RecyclerView.Adapter<ListAdapterProductoCarrito.ViewHolder>{
    private List<ListElementProducto> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapterProductoCarrito.OnItemClickListener listener;
    String urlImagen;
    int idCategoria, idProducto;
    public interface OnItemClickListener{
        void onItemClick(ListElementProducto item);
    }


    public ListAdapterProductoCarrito(List<ListElementProducto> itemList, Context context, ListAdapterProductoCarrito.OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;

    }

    @Override
    public int getItemCount(){ return mData.size(); }

    @Override
    public ListAdapterProductoCarrito.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element6, null);
        return new ListAdapterProductoCarrito.ViewHolder(view);

    }

    public void onBindViewHolder(final ListAdapterProductoCarrito.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));

        Glide.with(context).load(urlImagen).into(holder.iconImage);
    }

    public void setItems(List<ListElementProducto> items) {mData =  items;}




    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, precio, cantidad, nombreCategoria, imagen;
        ImageButton imageButton2, imageButton3;

        ViewHolder(View itemView){

            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewProducto);
            name = itemView.findViewById(R.id.nameTextView);
            precio = itemView.findViewById(R.id.PrecioTextView);
            cantidad = itemView.findViewById(R.id.CantidadTextView);
            imageButton2 = itemView.findViewById(R.id.iconImageButton2Producto);
            imageButton3 = itemView.findViewById(R.id.iconImageButton3Producto);
        }

        void bindData(final ListElementProducto item){
            urlImagen = item.getImagen();
            idProducto = item.getIdProducto();
            name.setText(item.getName());
            idCategoria = item.getIdCategoria();
            precio.setText("Precio: " + item.getPrecio());
            cantidad.setText("Cantidad: "+ item.getCantidad());
            imageButton2.setColorFilter(Color.parseColor(item.getColor2()), PorterDuff.Mode.SRC_IN);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
            imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   eliminarDelCarrito(item);
                }
            });



        }

    }


    public void eliminarDelCarrito(final ListElementProducto item){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "carrito", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int cant = bd.delete("productos","idProducto="+item.getIdProducto(),null);
        if(cant==1){
            Toast.makeText(context,"Se eliminó con éxito el producto del carrito", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,item.getIdProducto() + "", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
}
