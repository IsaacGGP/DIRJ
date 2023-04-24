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
        }


        System.out.println(username.getText().toString());
        System.out.println(lastname.getText().toString());
        System.out.println(email.getText().toString());
        System.out.println(password.getText().toString());
        System.out.println(password2.getText().toString());
        System.out.println(txtdia.getText().toString());
        System.out.println(txtmes.getText().toString());
        System.out.println(txtaño.getText().toString());
        System.out.println(splista.getText().toString());

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



}