package com.example.ev2appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Map;

public class DosActivity extends AppCompatActivity {

    private ArrayAdapter<String> adaptador;
    private ArrayList<String> lasTareas;
    private ListView lstTareas;
    private Button btnFiltrar;
    private TextView tilBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dos);
        referencias();
        eventos();
        leer();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lasTareas);
        lstTareas.setAdapter(adaptador);

    }

    public void referencias() {
        lstTareas = findViewById(R.id.lvItems);
        lasTareas = new ArrayList();
        tilBusqueda = findViewById(R.id.tietBuscar);
        btnFiltrar = findViewById(R.id.btnFiltrar);
    }

    public void eventos() {

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lasTareas.clear();
                SharedPreferences p = getSharedPreferences("Tareas", Context.MODE_PRIVATE);
                Map<String, ?> claves = p.getAll();

                if (tilBusqueda.getText().toString().isEmpty()) {
                    leer();
                } else {
                    int val = 0;
                    for (Map.Entry<String, ?> entrada : claves.entrySet()) {
                        if (entrada.getKey().contains(tilBusqueda.getText().toString()) || entrada.getValue().toString().contains(tilBusqueda.getText().toString())) {
                            lasTareas.add(entrada.getKey() + " : " + entrada.getValue().toString());
                            val = 1;
                        }
                    }
                    if (val == 0) {
                        lasTareas.add("no hay registro");
                    }
                }
                lstTareas.setAdapter(adaptador);
            }
        });

        lstTareas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,int i, long l) {

                SharedPreferences p = getSharedPreferences("Tareas", Context.MODE_PRIVATE);


                Snackbar sb = Snackbar.make(view, "este es un mensaje" + i + l , Snackbar.LENGTH_LONG);
                sb.setAction("aceptar", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                sb.show();



                lasTareas.remove(i);
                lstTareas.setAdapter(adaptador);
              //  Toast.makeText(DosActivity.this,"click" + lasTareas.remove(i), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    public void leer() {
        SharedPreferences p = getSharedPreferences("Tareas", Context.MODE_PRIVATE);

        Map<String, ?> claves = p.getAll();
        for (Map.Entry<String, ?> entrada : claves.entrySet()) {
            lasTareas.add(entrada.getKey() + " : " + entrada.getValue().toString());
        }
    }
}
