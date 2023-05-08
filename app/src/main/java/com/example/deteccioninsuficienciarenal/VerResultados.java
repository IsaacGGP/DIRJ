package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class VerResultados extends AppCompatActivity {
    String nombrecompleto, riesgos;
    int porcentaje;
    Usuario usuario;
    Riesgo riesgo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_resultados);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        preferences.getInt("idrisk", 0);

        TextView fecha, nombre, porcentaje, riesgos;

        fecha = findViewById(R.id.fecha);
        nombre = findViewById(R.id.nombre);
        porcentaje = findViewById(R.id.porcentaje);
        riesgos = findViewById(R.id.riesgos);

        DataBaseCRUD database = new DataBaseCRUD(VerResultados.this);
        usuario = database.buscarUser(preferences.getInt("iduser", 0));
        riesgo = database.buscarRisk(preferences.getInt("idrisk", 0));

        if (preferences.getInt("iduser", 0) != 0){
            nombre.setText(usuario.getUsername() + " " + usuario.getLastname());
        }else{
            nombre.setText("Usuario no registrado");
        }

        fecha.setText("Resultados insuficiencia renal "+riesgo.getCreatedat());
        porcentaje.setText(riesgo.getPorcentrisk() + "% DE CONTRAER INSUFICIENCIA RENAL");

    }

    public void verResultados (){

    }
}