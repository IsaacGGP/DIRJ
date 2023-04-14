package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void inicioView(View view){
        Intent openMain = new Intent(menu.this, MainActivity.class);
        startActivity(openMain);
    }
    public void calcularRiesgoView(View view){
        Intent openCalculador = new Intent(menu.this, calculadora.class);
        startActivity(openCalculador);
    }
    public void consultarResultadosView(View view){
        Intent openConsultarresultados = new Intent(menu.this, consultarresultados.class);
        startActivity(openConsultarresultados);
    }
    public void cuentaUsuarioView(View view){
        Intent openCuentadeusuario = new Intent(menu.this, cuentadeusuario.class);
        startActivity(openCuentadeusuario);
    }

}