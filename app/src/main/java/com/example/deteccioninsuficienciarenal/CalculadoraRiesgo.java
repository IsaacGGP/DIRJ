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

            int porcentrisk =0, diabete =0, presionarterial = 0, problemascorazon = 0, enfernedadhepati = 0, enfermedadrenal = 0, cance =0, sobrepeso =0, creatinina =0, obstruccionv =0, sedimientou =0 ;
            float pes, alt, imc;

            if(diabetes.isChecked()){
                System.out.println("check");
                diabete = 1;
                porcentrisk += 1;
            }if (presionarterialalta.isChecked()){
                System.out.println("check");
                presionarterial = 1;
                porcentrisk += 1;
            }if (insuficienciacardiaca.isChecked()){
                System.out.println("check");
                problemascorazon = 1;
                porcentrisk += 1;
            }if(enfermedadeshepaticas.isChecked()){
                System.out.println("check");
                enfernedadhepati = 1;
                porcentrisk += 1;
            }if (enfermedadesrenales.isChecked()){
                System.out.println("check");
                enfermedadrenal = 1;
                porcentrisk += 1;
            }if (cancer.isChecked()){
                System.out.println("check");
                cance =1;
                porcentrisk += 1;
            }if (obstruccionvaso.isChecked()){
                System.out.println("check");
                obstruccionv = 1;
                porcentrisk += 1;
            }if (sedimientourinario.isChecked()){
                System.out.println("check");
                sedimientou = 1;
                porcentrisk += 1;
            }

            pes = Float.valueOf(peso.getText().toString());
            alt = Float.valueOf(altura.getText().toString());
            imc = pes / (alt*alt);

            if (imc >= 25){
                System.out.println("check");
                sobrepeso = 1;
                porcentrisk += 1;
            }

            long ahora = System.currentTimeMillis();
            Date fecha = new Date(ahora);
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            String created = df.format(fecha);

            DataBaseCRUD database = new DataBaseCRUD(CalculadoraRiesgo.this);
            long exito = database.insertarRisk(porcentrisk, diabete, presionarterial, problemascorazon, enfernedadhepati, enfermedadrenal, cance, created,sobrepeso, creatinina, obstruccionv, sedimientou, preferences.getInt("iduser", 0));

            if (exito > 0){
                Toast.makeText(this, "Informacion guardada correctamente", Toast.LENGTH_SHORT).show();

                int idrisk = (int) exito;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("idrisk", idrisk);
                editor.commit();

                Intent openVerResultados = new Intent(CalculadoraRiesgo.this, VerResultados.class);
                startActivity(openVerResultados);
            }else{
                Toast.makeText(this, "Error al guardar informacion", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

}