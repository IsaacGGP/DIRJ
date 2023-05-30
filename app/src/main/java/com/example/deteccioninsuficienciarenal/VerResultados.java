package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VerResultados extends AppCompatActivity {
    String risk = "";
    Usuario usuario;
    Riesgo riesgo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_resultados);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        preferences.getInt("idrisk", 0);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
            }else{
            }
        }else{
            int idriesgo;
            idriesgo = (int) savedInstanceState.getSerializable("idrisk");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("idrisk", idriesgo);
            editor.commit();

        }

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
        if(riesgo.getPorcentrisk() < 50){
            porcentaje.setTextColor(getResources().getColor(R.color.green));
        } else if (riesgo.getPorcentrisk() > 50) {
            porcentaje.setTextColor(getResources().getColor(R.color.red));
        }
        porcentaje.setText(riesgo.getPorcentrisk() + "% DE CONTRAER INSUFICIENCIA RENAL");

        if(riesgo.getDiabetes() == 1){
            risk = risk+"* Diabetes\n";
        }if(riesgo.getBloodpreasure() == 1){
            risk = risk+"* Presion arterial alta\n";
        }if(riesgo.getHeartfailure() == 1){
            risk = risk+"* Problemas cardiacos\n";
        }if(riesgo.getLiverdiseasease() == 1){
            risk = risk + "* Enfermedades del hígado\n";
        }if(riesgo.getKidneydisease() == 1){
            risk = risk + "* Enfermedades renales\n";
        }if(riesgo.getCancer() == 1){
            risk = risk + "* Tratamiento de cancer \n";
        }if(riesgo.getOverweight() == 1){
            risk = risk + "* Sobre peso\n";
        }if(riesgo.getCreatinine() == 1){
            risk = risk + "* Nivel alto de creatinina\n";
        }if(riesgo.getObstruccionbloodv() == 1){
            risk = risk + "* Obstruccion en vasos sanguineos\n";
        }if(riesgo.getUrinarysediment() == 1){
            risk = risk + "* Anormalidad en sedimiento urinario\n";
        }

        if(risk == ""){
            risk = "Sin riesgos registrados";
        }
        riesgos.setText(risk);

    }

    public void menu(View view){
        Intent openMenu = new Intent(VerResultados.this, Menu.class);
        startActivity(openMenu);
    }

    public void inicio(View view){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);

        if (preferences.getInt("iduser", 0) != 0){
            Intent openConsultarResultados = new Intent(VerResultados.this, ConsultarResultados.class);
            startActivity(openConsultarResultados);
        } else if (preferences.getInt("iduser", 0) == 0) {
            Intent openInicio = new Intent(VerResultados.this, Bienvenida.class);
            startActivity(openInicio);
        }

    }

    }