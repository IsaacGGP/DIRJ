package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class Bienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        DataBaseSQLite dataBase = new DataBaseSQLite(Bienvenida.this);
        SQLiteDatabase db = dataBase.getWritableDatabase();
    }

    public void menu(View view){
        Intent openMenu = new Intent(Bienvenida.this, Menu.class);
        startActivity(openMenu);
    }
}