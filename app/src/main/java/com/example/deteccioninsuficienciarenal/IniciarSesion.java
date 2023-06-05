package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class IniciarSesion extends AppCompatActivity {
    EditText correo, contraseña;
    Boolean datosCorrectos;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        correo = findViewById(R.id.correo);
        contraseña = findViewById(R.id.contraseña);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        preferences.getInt("iduser", 0);
    }
    public void menu(View view){
        Intent openMenu = new Intent(IniciarSesion.this, Menu.class);
        startActivity(openMenu);
    }

    public void iniciarSesion (View view){
        datosCorrectos = true;
        System.out.println(correo.getText().toString());
        System.out.println(contraseña.getText().toString());
        if(correo.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar CORREO ELECTRONICO", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
            System.out.println("agregar");
        }if(contraseña.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar CONTRASEÑA", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
            System.out.println("contra");
        }if(!correoCorrecto(correo.getText().toString())){
            datosCorrectos = false;
            Toast.makeText(this, "Correo invalido", Toast.LENGTH_SHORT).show();
        }if(!contraseñaCorrecta(contraseña.getText().toString())){
            datosCorrectos = false;
            Toast.makeText(this, "Contraseña invalida", Toast.LENGTH_SHORT).show();
        }

        if (datosCorrectos){
            DataBaseCRUD database = new DataBaseCRUD(IniciarSesion.this);
            usuario = database.iniciarSesionUser(correo.getText().toString(), contraseña.getText().toString());
            if (usuario != null){
                Toast.makeText(this, "Sesion iniciada", Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("iduser", usuario.getIduser());
                editor.commit();

                Intent openMain = new Intent(IniciarSesion.this, Bienvenida.class);
                startActivity(openMain);
            }else{
                Toast.makeText(this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Boolean correoCorrecto(String correo){
        if(correo.contains("@") && correo.contains(".") && correo.length()<121){
            return true;
        }else{
            return false;
        }
    }

    public Boolean contraseñaCorrecta(String contraseña){
        if (contraseña.length() < 6 ||  contraseña.length() > 60){
            return false;
        }else {
            return true;
        }

    }
}