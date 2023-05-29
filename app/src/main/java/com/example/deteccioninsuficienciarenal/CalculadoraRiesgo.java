package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculadoraRiesgo extends AppCompatActivity {
    EditText peso, creatinina, altura;
    CheckBox sedimientourinario, presionarterialalta, diabetes, obstruccionvaso, insuficienciacardiaca, enfermedadeshepaticas, enfermedadesrenales, cancer;
    DataBaseCRUD database = new DataBaseCRUD(CalculadoraRiesgo.this);
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        peso = findViewById(R.id.peso);
        altura = findViewById(R.id.altura);
        creatinina = findViewById(R.id.creatinina);
        sedimientourinario = findViewById(R.id.sedimientourinario);
        presionarterialalta = findViewById(R.id.presionarterialalta);
        diabetes = findViewById(R.id.diabetes);
        obstruccionvaso = findViewById(R.id.obstruccionvaso);
        insuficienciacardiaca = findViewById(R.id.insuficienciacardiaca);
        enfermedadeshepaticas = findViewById(R.id.enfermedadeshepaticas);
        enfermedadesrenales = findViewById(R.id.enfermedadesrenales);
        cancer = findViewById(R.id.cancer);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        preferences.getInt("iduser", 0);

    }
    public void menu(View view){
        Intent openMenu = new Intent(CalculadoraRiesgo.this, Menu.class);
        startActivity(openMenu);
    }

    public void calcularButton(View view){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        boolean datoscorrectos = true;
        if (peso.getText().toString().equals("")){
            Toast.makeText(this, "Falta agregar PESO del paciente", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }if (creatinina.getText().toString().equals("")){
            Toast.makeText(this, "Falta agregar NIVEL DE CREATININA del paciente", Toast.LENGTH_SHORT).show();
            datoscorrectos = false;
        }

        if(datoscorrectos){
            //todo: Se registrara todo en la base de datos pero al aceptar todas las tablas con riesgo con el id = 0 se eliminaran (en la pantalla verResultados) (pendiente implementar)

            int porcentrisk =0, diabete =0, presionarterial = 0, problemascorazon = 0, enfernedadhepati = 0, enfermedadrenal = 0, cance =0, sobrepeso =0, creatinin =0, obstruccionv =0, sedimientou =0 ;
            float pes = 0, alt = 0, imc = 0, porcent;

            if(diabetes.isChecked()){
                System.out.println("check");
                diabete = 1;
                porcentrisk += 40;
            }if (presionarterialalta.isChecked()){
                System.out.println("check");
                presionarterial = 1;
                porcentrisk += 7;
            }if (insuficienciacardiaca.isChecked()){
                System.out.println("check");
                problemascorazon = 1;
                porcentrisk += 34;
            }if(enfermedadeshepaticas.isChecked()){
                System.out.println("check");
                enfernedadhepati = 1;
                porcentrisk += 31;
            }if (enfermedadesrenales.isChecked()){
                System.out.println("check");
                enfermedadrenal = 1;
                porcentrisk += 5;
            }if (cancer.isChecked()){
                System.out.println("check");
                cance =1;
                porcentrisk += 23;
            }if (obstruccionvaso.isChecked()){
                System.out.println("check");
                obstruccionv = 1;
                porcentrisk += 40;
            }if (sedimientourinario.isChecked()){
                System.out.println("check");
                sedimientou = 1;
                porcentrisk += 20;
            }if (preferences.getInt("iduser", 0) != 0){
                System.out.println("Si estamos registrados");
                usuario = database.buscarUser(preferences.getInt("iduser", 0));
                float creatin = Integer.valueOf(creatinina.getText().toString());
                System.out.println(creatin);
                System.out.println(usuario.getGender());
                if(usuario.getGender() == 0){ //masculino
                    if(creatin > 1.3){
                        creatinin = 1;
                        porcentrisk += 50;
                    }
                } else if (usuario.getGender() == 1) { //femenino
                    if(creatin > 1.1){
                        creatinin = 1;
                        porcentrisk += 50;
                    }
                }
            }else{
                Toast.makeText(this, "Para considerar el nivel de CREATININA necesita registrarse", Toast.LENGTH_SHORT).show();
            }

            pes = Float.valueOf(peso.getText().toString());
            alt = Float.valueOf(altura.getText().toString());
            imc = pes / (alt*alt);

            if (imc >= 25){
                System.out.println("check");
                sobrepeso = 1;
                porcentrisk += 6;
            }

            long ahora = System.currentTimeMillis();
            Date fecha = new Date(ahora);
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            String created = df.format(fecha);

            DataBaseCRUD database = new DataBaseCRUD(CalculadoraRiesgo.this);
            long exito = database.insertarRisk(porcentrisk, diabete, presionarterial, problemascorazon, enfernedadhepati, enfermedadrenal, cance, created,sobrepeso, creatinin, obstruccionv, sedimientou, preferences.getInt("iduser", 0));

            if (exito > 0){
                if(preferences.getInt("iduser", 0) != 0){
                    Toast.makeText(this, "Informacion guardada correctamente", Toast.LENGTH_SHORT).show();
                } else if (preferences.getInt("iduser", 0) == 0) {
                    Toast.makeText(this, "informacion NO GUARDADA: Es necesario iniciar sesion para guardar informacion", Toast.LENGTH_SHORT).show();
                }


                int idrisk = (int) exito;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("idrisk", idrisk);
                editor.commit();

                Context context = view.getContext();
                Intent intent = new Intent(context, VerResultados.class);
                intent.putExtra("idrisk", preferences.getInt("iduser", 0));
                context.startActivity(intent);
            }else{
                Toast.makeText(this, "Error al guardar informacion", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

}