package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class registro extends AppCompatActivity {

    AutoCompleteTextView spLista;
    EditText username, lastname, email, password, password2, txtDia, txtMes, txtAño;
    Button registrarse;
    boolean datosCorrectos = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        spLista = (AutoCompleteTextView) findViewById(R.id.sexo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.lista_genero, android.R.layout.simple_list_item_1);
        spLista.setAdapter(adapter);

        username = findViewById(R.id.username);
        lastname = findViewById(R.id.userlastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        txtDia = findViewById(R.id.txtDia);
        txtMes = findViewById(R.id.txtMes);
        txtAño = findViewById(R.id.txtAño);
        registrarse = findViewById(R.id.btnRegistrar);
    }

    public void registrar(View view){
        datosCorrectos = true;
        int sexo = 3;
        if (username.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar NOMBRE de usuario", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
        }if (lastname.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar APELLIDO(S) de usuairo", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
        }if (email.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar CORREO ELECTRONICO", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
        }if (password.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar CONTRASEÑA", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
        }if (txtDia.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar DIA DE NACIMIENO", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
        }if (txtMes.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar MES DE NACIMIENTO", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
        }if (txtAño.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta agregar AÑO DE NACIMIENTO", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
        }if(spLista.getText().toString().isEmpty()){
            Toast.makeText(this, "Falta seleccionar SEXO", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
        }if(!password.getText().toString().equals(password2.getText().toString())){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
        }if(spLista.getText().toString().equals("M")){
            sexo = 0;//0 es masculino
        }else if (spLista.getText().toString().equals("F")) {
            sexo = 1;//1 es femenino}
        }if (sexo == 3){
            datosCorrectos = false;
        }


        System.out.println(username.getText().toString());
        System.out.println(lastname.getText().toString());
        System.out.println(email.getText().toString());
        System.out.println(password.getText().toString());
        System.out.println(password2.getText().toString());
        System.out.println(txtDia.getText().toString());
        System.out.println(txtMes.getText().toString());
        System.out.println(txtAño.getText().toString());
        System.out.println(spLista.getText().toString());

        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String salida = df.format(fecha);

        if (datosCorrectos == true){
            db db = new db(registro.this);
            long id = db.insetarUser(username.getText().toString(), lastname.getText().toString(), email.getText().toString(), password.getText().toString(), salida, txtDia.getText().toString() + "-" + txtMes.getText().toString() + "-" + txtAño.getText().toString(), sexo);
            if(id > 0){
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(registro.this, login.class);
                startActivity(login);
            }else {
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        }else {
        Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
    }

    }



}