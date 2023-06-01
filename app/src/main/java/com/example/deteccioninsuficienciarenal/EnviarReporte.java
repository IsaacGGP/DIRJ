package com.example.deteccioninsuficienciarenal;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class EnviarReporte extends AppCompatActivity {

    TextView fullname, email, porcentrisk, risks, date;
    String risk="";
    Usuario usuario;
    Riesgo riesgo;
    String tituloPDF = "Reporte insuficiencia renal";
    String contenido = "Prueba de pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviarreporte);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
           if(!Environment.isExternalStorageManager()){
                    requestPermissions();
           }
        }

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

    public void enviar(View view){
        generarPDF();
    }

    public void  generarPDF(){
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint descripcion = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo paginaInfo = new PdfDocument.PageInfo.Builder(816, 1054, 1).create();
        PdfDocument.Page pagina1 = pdfDocument.startPage(paginaInfo);

        Canvas canvas = pagina1.getCanvas();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_playstore);//Imagen del logo
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 80, false);
        canvas.drawBitmap(bitmapEscala, 368, 20, paint);

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(18);
        canvas.drawText(tituloPDF, 10, 150, titulo);
        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        descripcion.setTextSize(14);

        String[] arrDescription = contenido.split("\n");
        int y = 200;
        for(int i =0; i<arrDescription.length; i++){
            canvas.drawText(arrDescription[i], 10, y, descripcion);
            y += 15;
        }

        pdfDocument.finishPage(pagina1);

        File file = new File(Environment.getExternalStorageDirectory(), "ReporteInsuficienciaRenal.pdf");
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "Creado pdf", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Error no se ha Creado pdf", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private boolean checkPermission(){
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions(){
        try{
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
            startActivityIfNeeded(intent, 101);
        }catch (Exception e){
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            startActivityIfNeeded(intent, 101);
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults){
        if(requestCode == 200){
            if(grantResults.length > 0){
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(writeStorage && readStorage){
                    Toast.makeText(this, "Permiso aceptado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}