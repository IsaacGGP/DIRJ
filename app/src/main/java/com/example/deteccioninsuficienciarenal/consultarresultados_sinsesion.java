package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class consultarresultados_sinsesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarresultados_sinsesion);
    }

    public void loginView(View view){
        Intent openLogin = new Intent(consultarresultados_sinsesion.this, login.class);
        startActivity(openLogin);
    }
}