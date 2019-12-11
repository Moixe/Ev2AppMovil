package com.example.ev2appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btnBuscar, btnIngresar, btnModificar;
    private TextView tilTitulo, tilDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        referencias();
        eventos();
    }

    private void eventos() {

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DosActivity.class);
                startActivity(i);
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validacion()) {
                    SharedPreferences p = getSharedPreferences(getString(R.string.SP_Tareas), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editarArchivo = p.edit();
                    editarArchivo.putString(tilTitulo.getText().toString(), tilDesc.getText().toString());
                    editarArchivo.commit();
                    tilTitulo.setText("");
                    tilDesc.setText("");
                    Toast.makeText(getApplication(),R.string.registro_exitoso, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validacion()) {
                    SharedPreferences p = getSharedPreferences(getString(R.string.SP_Tareas), Context.MODE_PRIVATE);
                    Map<String, ?> claves = p.getAll();
                    int toast = 0;
                    for (Map.Entry<String, ?> entrada : claves.entrySet()) {
                        if (entrada.getKey().contentEquals(tilTitulo.getText())) {
                            SharedPreferences.Editor editarArchivo = p.edit();
                            editarArchivo.putString(tilTitulo.getText().toString(), tilDesc.getText().toString());
                            editarArchivo.apply();
                            tilTitulo.setText("");
                            tilDesc.setText("");
                            toast = 1;
                            Toast.makeText(getApplication(), R.string.msg_modificacion, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if (1 != toast)
                        Toast.makeText(getApplication(),R.string.error_NoExiste, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void referencias() {
        btnBuscar = findViewById(R.id.btnSearch);
        btnModificar = findViewById(R.id.btnModificar);
        btnIngresar = findViewById(R.id.btnIngresar);
        tilTitulo = findViewById(R.id.tietTitulo);
        tilDesc = findViewById(R.id.tietDesc);
    }

    private boolean validacion() {
        boolean validacion = true;

        if (tilTitulo.getText().toString().trim().isEmpty()) {
            tilTitulo.setError(MainActivity.this.getString(R.string.error_Titulo));
            validacion = false;
        }
        if (tilDesc.getText().toString().trim().isEmpty()) {
            tilDesc.setError(MainActivity.this.getString(R.string.error_Desc));
            validacion = false;
        }
        if (validacion) {
        } else {
            Toast.makeText(getApplication(), R.string.error_Validacion, Toast.LENGTH_SHORT).show();
        }
        return validacion;
    }

}
