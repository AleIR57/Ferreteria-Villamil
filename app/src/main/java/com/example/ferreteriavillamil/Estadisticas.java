package com.example.ferreteriavillamil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.apache.http.message.HeaderValueFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Estadisticas extends AppCompatActivity {

    PieChart pieChart;
    BarChart barChart;
    String PRODUCTOXFACTURA_URL, PRODUCTOXFACTURA_URL2, VENTASDIARIAS_URL;
    ArrayList<Integer> idProductos = new ArrayList<>();
    ArrayList<Integer> idProductos2 = new ArrayList<>();
    ArrayList<Integer> cantidades = new ArrayList<>();
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<String> dias = new ArrayList<>();
    ArrayList<Integer> ventasDiarias = new ArrayList<>();
    int cantidadesAux, idsAux, ventasAux;
    String nombresAux, diasAux;
    JSONObject productoObject;
    ImageButton volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        //Aux.setVisibility(View.INVISIBLE);
        pieChart = findViewById(R.id.idGraficoPastel);
        loadProductosxFactura();
        barChart = findViewById(R.id.idGraficoBarras);
        loadVentasDiarias();
        volver = findViewById(R.id.imageButtonVolver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vVolver = new Intent(Estadisticas.this, Informes.class);
                startActivity(vVolver);
            }
        });


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
                            String primerNombre = nombres.get(0);
                            String primerNombreAux = primerNombre.replace("null", "");
                            System.out.println("El nuevo nombre es:" + primerNombreAux);
                            nombres.set(0, primerNombreAux);

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
                                                description.setText("Cantidad vendida por cada producto");

                                                ArrayList<PieEntry> pieEntries = new ArrayList<>();;
                                                pieChart.setDescription(description);
                                                pieChart.setEntryLabelColor(Color.BLACK);

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
                                                pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                                                pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                                                pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                                                pieDataSet.setValueTextSize(10f);
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


    public void loadVentasDiarias(){
        VENTASDIARIAS_URL = "http://192.168.1.15/api/factura/ventasdiarias.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, VENTASDIARIAS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray facturav = new JSONArray(response);
                            for (int i = 0; i < facturav.length(); i++) {
                                JSONObject facturaventasObject = facturav.getJSONObject(i);
                                diasAux += facturaventasObject.getString("dia");
                                dias.add(facturaventasObject.getString("dia"));
                                diasAux = "";
                                ventasAux += Integer.parseInt(facturaventasObject.getString("total"));
                                ventasDiarias.add(ventasAux);
                                ventasAux = 0;
                            }
                            ArrayList<BarEntry> graficoBarras = new ArrayList<BarEntry>();


                            int contador = 0;
                            for(int j = 0; j < dias.size(); j++){
                                if(dias.get(j).equals("lunes")){
                                    contador = 1;
                                    graficoBarras.add(new BarEntry(contador, ventasDiarias.get(j)));
                                }
                                if(dias.get(j).equals("martes")){
                                    contador = 2;
                                    graficoBarras.add(new BarEntry(contador, ventasDiarias.get(j)));
                                }
                                if(dias.get(j).equals("miércoles")){
                                    contador = 3;
                                    graficoBarras.add(new BarEntry(contador, ventasDiarias.get(j)));
                                }
                                if(dias.get(j).equals("jueves")){
                                    contador = 4;
                                    graficoBarras.add(new BarEntry(contador, ventasDiarias.get(j)));
                                }
                                if(dias.get(j).equals("viernes")){
                                    contador = 5;
                                    graficoBarras.add(new BarEntry(contador, ventasDiarias.get(j)));
                                }
                                if(dias.get(j).equals("sábado")){
                                    contador = 6;
                                    graficoBarras.add(new BarEntry(contador, ventasDiarias.get(j)));
                                }
                                if(dias.get(j).equals("domingo")){
                                    contador = 7;
                                    graficoBarras.add(new BarEntry(contador, ventasDiarias.get(j)));
                                }

                            }
                            BarDataSet barDataSet = new BarDataSet(graficoBarras, "Ventas diarias");
                            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                            barDataSet.setValueTextColor(Color.BLACK);
                            barDataSet.setValueTextSize(12f);

                            BarData barData = new BarData(barDataSet);
                            barChart.setFitBars(true);
                            barChart.setData(barData);
                            barData.setBarWidth(0.3f);

                            barChart.getDescription().setText("");
                            barChart.getAxisLeft().setDrawGridLines(false);
                            barChart.getXAxis().setDrawGridLines(false);
                            barChart.getAxisRight().setDrawGridLines(false);
                            barChart.animateY(2000);

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



        Volley.newRequestQueue(this).add(stringRequest);
    }


}