package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EnviarReporte extends AppCompatActivity {

    TextView fullname, email, porcentrisk, risks, date;
    String risk="";
    Usuario usuario;
    Riesgo riesgo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviarreporte);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        fullname = findViewById(R.id.txtFullName);
        email = findViewById(R.id.txtCorreo);
        porcentrisk = findViewById(R.id.RiskPorcent);
        risks = findViewById(R.id.riesgosenviar);
        date = findViewById(R.id.Fecha);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        DataBaseCRUD database = new DataBaseCRUD(EnviarReporte.this);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("idrisk", 0);
                editor.commit();
            }else{
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("idrisk", extras.getInt("idrisk"));
                editor.commit();
            }
        }else{
            int idriesgo;
            idriesgo = (int) savedInstanceState.getSerializable("idrisk");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("idrisk", idriesgo);
            editor.commit();

        }

        usuario = database.buscarUser(preferences.getInt("iduser", 0));
        riesgo = database.buscarRisk(preferences.getInt("idrisk", 0));

        fullname.setText(usuario.getUsername() + usuario.getLastname());
        email.setText(usuario.getEmail());

        if(riesgo.getDiabetes() == 1){
            risk = risk + "* Diabetes\n";
        }if(riesgo.getBloodpreasure() == 1){
            risk = risk+"* Presion arterial alta\n";
        }if(riesgo.getHeartfailure() == 1){
            risk = risk+"* Problemas cardiacos\n";
        }if(riesgo.getLiverdiseasease() == 1){
            risk = risk + "* Enfermedades del hígado\n";
        }if(riesgo.getKidneydisease() == 1){
            risk = risk + "* Enfermedades renales\n";
        }if(riesgo.getCancer() == 1){
            risk = risk + "* Tratamiento de cancer \n";
        }if(riesgo.getOverweight() == 1){
            risk = risk + "* Sobre peso\n";
        }if(riesgo.getCreatinine() == 1){
            risk = risk + "* Nivel alto de creatinina\n";
        }if(riesgo.getObstruccionbloodv() == 1){
            risk = risk + "* Obstruccion en vasos sanguineos\n";
        }if(riesgo.getUrinarysediment() == 1){
            risk = risk + "* Anormalidad en sedimiento urinario\n";
        }
        if(risk == ""){
            risk = "Sin riesgos registrados";
        }

        risks.setText(risk);
        porcentrisk.setText("Con un riesgo del "+riesgo.getPorcentrisk()+"%");
        date.setText("Fecha: "+riesgo.getCreatedat());

    }
    public void menu(View view){
        Intent openMenu = new Intent(EnviarReporte.this, Menu.class);
        startActivity(openMenu);
    }
}