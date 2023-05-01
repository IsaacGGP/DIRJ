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

public class ActualizarDatos extends AppCompatActivity {
    Usuario usuario;
    EditText username, userlastname, email, password, txtdia, txtmes, txtaño;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizardatos);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        username = findViewById(R.id.username);
        userlastname = findViewById(R.id.userlastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        txtdia = findViewById(R.id.txtDia);
        txtmes = findViewById(R.id.txtMes);
        txtaño = findViewById(R.id.txtAño);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        DataBaseCRUD database = new DataBaseCRUD(ActualizarDatos.this);
        usuario = database.buscarUser(preferences.getInt("iduser", 0));

        username.setText(usuario.getUsername());
        userlastname.setText(usuario.getLastname());
        email.setText(usuario.getEmail());
        password.setText(usuario.getPassword());
        String[] fecha = usuario.getBirth().split("-");
        txtdia.setText(fecha[0]);
        txtmes.setText(fecha[1]);
        txtaño.setText(fecha[2]);

    }
    public void menu(View view){
        Intent openMenu = new Intent(ActualizarDatos.this, Menu.class);
        startActivity(openMenu);
    }

    public void cerrarSesion(View view){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("iduser", 0);
        editor.commit();

        Intent openMain = new Intent(ActualizarDatos.this, Bienvenida.class);
        startActivity(openMain);
    }

    public void registrarUsuario(View view){
        boolean datoscorrectos = true;
        System.out.println(username.getText().toString());
        if (username.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar NOMBRE de usuario", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if (userlastname.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar APELLIDO(S) de usuairo", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if (email.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar CORREO ELECTRONICO", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if (password.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar CONTRASEÑA", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if (txtdia.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar DIA DE NACIMIENO", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if (txtmes.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar MES DE NACIMIENTO", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if (txtaño.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar AÑO DE NACIMIENTO", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if(password.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar la contraseña", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }

    }
}