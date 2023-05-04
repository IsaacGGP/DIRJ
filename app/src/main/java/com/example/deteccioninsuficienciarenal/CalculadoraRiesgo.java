package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CalculadoraRiesgo extends AppCompatActivity {
    EditText peso, creatinina;
    CheckBox sedimientourinario, presionarterialalta, diabetes, obstruccionvaso, insuficienciacardiaca, enfermedadeshepaticas, enfermedadesrenales, cancer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        peso = findViewById(R.id.peso);
        creatinina = findViewById(R.id.creatinina);
        sedimientourinario = findViewById(R.id.sedimientourinario);
        presionarterialalta = findViewById(R.id.presionarterialalta);
        diabetes = findViewById(R.id.diabetes);
        obstruccionvaso = findViewById(R.id.obstruccionvaso);
        insuficienciacardiaca = findViewById(R.id.insuficienciacardiaca);
        enfermedadeshepaticas = findViewById(R.id.enfermedadeshepaticas);
        enfermedadesrenales = findViewById(R.id.enfermedadesrenales);
        cancer = findViewById(R.id.cancer);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        preferences.getInt("iduser", 0);
    }
    public void menu(View view){
        Intent openMenu = new Intent(CalculadoraRiesgo.this, Menu.class);
        startActivity(openMenu);
    }

    public void calcularButton(View view){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        boolean datoscorrectos = true;
        if (peso.getText().toString().equals("")){
            Toast.makeText(this, "Falta agregar PESO del paciente", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if (creatinina.getText().toString().equals("")){
            Toast.makeText(this, "Falta agregar NIVEL DE CREATININA del paciente", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }

        if(datoscorrectos){
            if(preferences.getInt("iduser", 0) == 0){

            }else{

            }
        }else{
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

}