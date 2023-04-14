package com.example.deteccioninsuficienciarenal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class registro extends AppCompatActivity {

    AutoCompleteTextView spLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        spLista = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.lista_genero, android.R.layout.simple_list_item_1);
        spLista.setAdapter(adapter);


    }


}