package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class actualizardatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizardatos);
    }
    public void menu(View view){
        Intent openMenu = new Intent(actualizardatos.this, menu.class);
        startActivity(openMenu);
    }
}