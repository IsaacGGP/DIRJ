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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        }if(!correoCorrecto(email.getText().toString())){
            datoscorrectos = false;
            Toast.makeText(this, "Correo invalido", Toast.LENGTH_SHORT).show();
        }if(!contraseñaCorrecta(password.getText().toString())){
            datoscorrectos = false;
            Toast.makeText(this, "Contraseña invalida", Toast.LENGTH_SHORT).show();
        }if(!nombreApellidoCorrecto(username.getText().toString())){
            datoscorrectos = false;
            Toast.makeText(this, "Nombre invalido", Toast.LENGTH_SHORT).show();
        }if(!nombreApellidoCorrecto(userlastname.getText().toString())){
            datoscorrectos = false;
            Toast.makeText(this, "Apellido invalido", Toast.LENGTH_SHORT).show();
        }if(!fechaCorrecta(txtdia.getText().toString(), txtmes.getText().toString(), txtaño.getText().toString())){
            datoscorrectos = false;
            Toast.makeText(this, "Fecha invalida. dd/mm/yy", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        DataBaseCRUD database = new DataBaseCRUD(ActualizarDatos.this);
        if( datoscorrectos){
            if( database.editarUser(preferences.getInt("iduser", 0), username.getText().toString(), userlastname.getText().toString(), email.getText().toString(), password.getText().toString(), txtdia.getText().toString() + "-" + txtmes.getText().toString() + "-" + txtaño.getText().toString()) ){
                Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                Intent openMain = new Intent(ActualizarDatos.this, Bienvenida.class);
                startActivity(openMain);
            }else{
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
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

    public Boolean nombreApellidoCorrecto(String nombre){
        if(nombre.matches("[A-Za-záéíóú ]*")){
            if(nombre.length() < 50 && nombre.length() > 2){
                return true;
            }else return false;
        }else  return false;
    }

    public Boolean fechaCorrecta(String dia, String mes, String año){
        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String created = df.format(fecha);
        String year[] = created.split("/");

        if(dia.length() == 2 && mes.length() == 2 && año.length() == 2){
            if(Integer.parseInt(dia) > 31 || Integer.parseInt(dia) < 01){
                return false;
            }
            if (Integer.parseInt(mes) > 12 || Integer.parseInt(mes) < 01) {
                return false;
            }else return true;
        } else
            return false;
    }
}