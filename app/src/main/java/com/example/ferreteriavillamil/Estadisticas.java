package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity {

    PieChart pieChart;
    BarChart barChart;
    String PRODUCTOXFACTURA_URL, PRODUCTOXFACTURA_URL2;
    ArrayList<Integer> idProductos = new ArrayList<>();
    ArrayList<Integer> idProductos2 = new ArrayList<>();
    ArrayList<Integer> cantidades = new ArrayList<>();
    ArrayList<String> nombres = new ArrayList<>();
    int cantidadesAux, idsAux;
    String nombresAux;
    JSONObject productoObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        //Aux.setVisibility(View.INVISIBLE);
        loadProductosxFactura();
        pieChart = findViewById(R.id.idGraficoPastel);
        barChart = findViewById(R.id.idGraficoBarras);
        crearGraficoBarras();
        crearGraficoPastel();

    }

    public void loadProductosxFactura() {


        PRODUCTOXFACTURA_URL2 = "http://192.168.1.15/api/productoxfactura/nombreproductos.php";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, PRODUCTOXFACTURA_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray producto = new JSONArray(response);

                            for (int i = 0; i < producto.length(); i++) {
                                productoObject = producto.getJSONObject(i);
                                nombresAux += productoObject.getString("nombre");
                                nombres.add(nombresAux);
                                nombresAux = "";
                                idProductos2.add(productoObject.getInt("idProducto"));

                            }

                            PRODUCTOXFACTURA_URL = "http://192.168.1.15/api/productoxfactura/productosxfactura2.php";
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTOXFACTURA_URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONArray productoxfactura = new JSONArray(response);

                                                for (int i = 0; i < productoxfactura.length(); i++) {
                                                    JSONObject productoxfacturaObject = productoxfactura.getJSONObject(i);
                                                    if(idProductos.contains(productoxfacturaObject.getInt("idProducto"))){
                                                        int indice = idProductos.indexOf(productoxfacturaObject.getInt("idProducto"));
                                                        cantidadesAux += Integer.parseInt(productoxfacturaObject.getString("cantidad"));
                                                        int element = cantidades.get(indice);
                                                        int resultado = element + cantidadesAux;
                                                        cantidades.set(indice, resultado);
                                                        cantidadesAux = 0;
                                                    }
                                                    else{
                                                        idProductos.add(productoxfacturaObject.getInt("idProducto"));
                                                        cantidades.add(Integer.parseInt(productoxfacturaObject.getString("cantidad")));


                                                    }

                                                }

                                                Description description = new Description();
                                                description.setText("Cantidad vendidad por cada producto");

                                                ArrayList<PieEntry> pieEntries = new ArrayList<>();;
                                                pieChart.setDescription(description);
                                                for(int j = 0; j < idProductos.size(); j++){
                                                    if(idProductos.get(j) == idProductos2.get(j)){
                                                        int indice = idProductos.indexOf(idProductos.get(j));
                                                        nombresAux += nombres.get(indice);
                                                        nombres.set(indice, nombresAux);
                                                        nombresAux = "";
                                                    }
                                                    pieEntries.add(new PieEntry(cantidades.get(j),nombres.get(j)));

                                                }
                                                PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                                                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                                PieData pieData = new PieData(pieDataSet);

                                                pieChart.setData(pieData);


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Estadisticas.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                            Volley.newRequestQueue(Estadisticas.this).add(stringRequest);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Estadisticas.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest2);



    }


    public void crearGraficoPastel(){


    }

    public void crearGraficoBarras(){
        ArrayList<BarEntry> ventasDiarias = new ArrayList<BarEntry>();
        ventasDiarias.add(new BarEntry(2, 2));
        ventasDiarias.add(new BarEntry(1, 5));
        ventasDiarias.add(new BarEntry(4, 6));
        ventasDiarias.add(new BarEntry(3, 10));
        ventasDiarias.add(new BarEntry(1, 9));


        BarDataSet barDataSet = new BarDataSet(ventasDiarias, "Ventas Diarias");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.animateY(2000);

    }
}