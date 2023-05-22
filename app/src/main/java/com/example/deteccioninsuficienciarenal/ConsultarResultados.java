package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.deteccioninsuficienciarenal.Adaptadores.ListaRiesgosAdapter;

import java.util.ArrayList;

public class ConsultarResultados extends AppCompatActivity {
    RecyclerView listaResultados;
    ArrayList<Riesgo> listaArrayRiesgos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarresultados);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        listaResultados = findViewById(R.id.resultados);
        listaResultados.setLayoutManager(new LinearLayoutManager((this)));
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);

        DataBaseCRUD dbriesgos = new DataBaseCRUD(ConsultarResultados.this);

        listaArrayRiesgos = new ArrayList<>();
        if (preferences.getInt("iduser", 0) != 0){
            ListaRiesgosAdapter adapter = new ListaRiesgosAdapter(dbriesgos.leerRiesgos(preferences.getInt("iduser", 0)));
            listaResultados.setAdapter(adapter);
        }

    }
    public void menu(View view){
        Intent openMenu = new Intent(ConsultarResultados.this, Menu.class);
        startActivity(openMenu);
    }
}