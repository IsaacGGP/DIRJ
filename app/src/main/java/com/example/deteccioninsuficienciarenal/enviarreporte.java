package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class enviarreporte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviarreporte);
    }
    public void menu(View view){
        Intent openMenu = new Intent(enviarreporte.this, menu.class);
        startActivity(openMenu);
    }
}