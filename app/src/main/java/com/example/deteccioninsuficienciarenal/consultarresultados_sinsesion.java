package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class consultarresultados_sinsesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarresultados_sinsesion);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void loginView(View view){
        Intent openLogin = new Intent(consultarresultados_sinsesion.this, login.class);
        startActivity(openLogin);
    }

    public void menu(View view){
        Intent openMenu = new Intent(consultarresultados_sinsesion.this, menu.class);
        startActivity(openMenu);
    }
}