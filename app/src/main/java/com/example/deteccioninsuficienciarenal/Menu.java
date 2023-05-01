package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {
    View cerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        cerrarSesion = findViewById(R.id.cerrarSesion);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        preferences.getInt("iduser", 0);

        if (preferences.getInt("iduser", 0) == 0){
            cerrarSesion.setVisibility(View.INVISIBLE);
        }
    }
    public void inicioView(View view){
        Intent openMain = new Intent(Menu.this, Bienvenida.class);
        startActivity(openMain);
    }
    public void calcularRiesgoView(View view){
        Intent openCalculador = new Intent(Menu.this, CalculadoraRiesgo.class);
        startActivity(openCalculador);
    }
    public void consultarResultadosView(View view){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        if (preferences.getInt("iduser", 0) == 0){
            Intent openConsultarresultadosSinSesion = new Intent(Menu.this, ConsultarResultadosSinSesion.class);
            startActivity(openConsultarresultadosSinSesion);
        }else{
            Intent openConsultarresultados = new Intent(Menu.this, ConsultarResultados.class);
            startActivity(openConsultarresultados);
        }
    }
    public void cuentaUsuarioView(View view){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        if (preferences.getInt("iduser", 0) == 0){
            Intent openCuentadeusuario = new Intent(Menu.this, CuentaUsuario.class);
            startActivity(openCuentadeusuario);
        }else{
            Intent openActualizarDatos = new Intent(Menu.this, ActualizarDatos.class);
            startActivity(openActualizarDatos);
        }
    }

    public void cerrarSesion(View view){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("iduser", 0);
        editor.commit();

        Intent openMain = new Intent(Menu.this, Bienvenida.class);
        startActivity(openMain);
    }


}