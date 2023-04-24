package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CuentaUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentadeusuario);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    public void loginView(View view){
        Intent openLogin = new Intent(CuentaUsuario.this, IniciarSesion.class);
        startActivity(openLogin);
    }

    public void registroView(View view){
        Intent openLogin = new Intent(CuentaUsuario.this, RegistrarUsuarios.class);
        startActivity(openLogin);
    }

    public void menu(View view){
        Intent openMenu = new Intent(CuentaUsuario.this, Menu.class);
        startActivity(openMenu);
    }
}