package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrarUsuarios extends AppCompatActivity {

    AutoCompleteTextView splista;
    EditText username, lastname, email, password, password2, txtdia, txtmes, txtaño;
    Button registrarse;
    boolean datoscorrectos = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        splista = (AutoCompleteTextView) findViewById(R.id.sexo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.lista_genero, android.R.layout.simple_list_item_1);
        splista.setAdapter(adapter);

        username = findViewById(R.id.username);
        lastname = findViewById(R.id.userlastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        txtdia = findViewById(R.id.txtDia);
        txtmes = findViewById(R.id.txtMes);
        txtaño = findViewById(R.id.txtAño);
        registrarse = findViewById(R.id.btnRegistrar);
    }

    public void registrar(View view){
        datoscorrectos = true;
        int sexo = 3;
        if (username.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar NOMBRE de usuario", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if (lastname.getText().toString().isEmpty()){
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
        }if(splista.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta seleccionar SEXO", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if(!password.getText().toString().equals(password2.getText().toString())){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if(splista.getText().toString().equals("M")){
            sexo = 0;//0 es masculino
        }else if (splista.getText().toString().equals("F")) {
            sexo = 1;//1 es femenino}
        }if (sexo == 3){
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
        }if(!nombreApellidoCorrecto(lastname.getText().toString())){
            datoscorrectos = false;
            Toast.makeText(this, "Apellido invalido", Toast.LENGTH_SHORT).show();
        }if(!fechaCorrecta(txtdia.getText().toString(), txtmes.getText().toString(), txtaño.getText().toString())){
            datoscorrectos = false;
            Toast.makeText(this, "Fecha invalida. dd/mm/yy", Toast.LENGTH_SHORT).show();
        }

        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String salida = df.format(fecha);

        if (datoscorrectos == true){
            DataBaseCRUD DataBaseCRUD = new DataBaseCRUD(RegistrarUsuarios.this);
            long id = DataBaseCRUD.insetarUser(username.getText().toString(), lastname.getText().toString(), email.getText().toString(), password.getText().toString(), salida, txtdia.getText().toString() + "-" + txtmes.getText().toString() + "-" + txtaño.getText().toString(), sexo);
            if(id > 0){
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(RegistrarUsuarios.this, IniciarSesion.class);
                startActivity(login);
            }else {
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        }else {
        Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
        }
    }

    public void menu(View view){
        Intent openMenu = new Intent(RegistrarUsuarios.this, Menu.class);
        startActivity(openMenu);
    }

    public Boolean correoCorrecto(String correo){
        if(correo.contains("@") && correo.contains(".") && correo.length()<121){
            DataBaseCRUD DataBaseCRUD = new DataBaseCRUD(RegistrarUsuarios.this);
            //Correo repetido
            Usuario usuario;
            DataBaseCRUD database = new DataBaseCRUD(RegistrarUsuarios.this);
            usuario = database.buscarEmail(email.getText().toString());
            if (usuario != null){
                return false;
            }else{
                return true;
            }
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