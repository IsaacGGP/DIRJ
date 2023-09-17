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
import android.os.StrictMode;
import android.provider.Settings;
import android.text.TextPaint;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnviarReporte extends AppCompatActivity {

    TextView fullname, email, porcentrisk, risks, date;
    String risk="";
    Usuario usuario;
    Riesgo riesgo;
    String tituloPDF = "Reporte de insuficiencia renal";
    String contenido = "";
    String pietexto = "La aplicación DIR (detección de insuficiencia renal) realiza una estimación que solo es una guía estadística y preventiva, no sustituye \nde ninguna manera las valoraciones médicas hechas por profesionales de la salud ya que la insuficiencia renal tiene muchos factores\n que lo causan y es necesario realizar exámenes de laboratorio para estar seguros sobre los resultados.\n  Este resultado no es un diagnóstico médico,  sin embargo se recomienda consultar a su médico familiar o a un especialista nefrólogo.";
    Session session;
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

        fullname.setText(usuario.getUsername() +" "+ usuario.getLastname());
        String sexo = "Desconocido";
        if(usuario.getGender() == 0){
            sexo = "Masculino";
        }else if(usuario.getGender() == 1){
            sexo = "Femenino";
        }
        contenido  = "Paciente: " + usuario.getUsername()+ " " + usuario.getLastname() + " "+ " "+" "+ " "+ " "+ " "+" "+ " "+" "+ " "+ " "+ " "+" "+ " "+ " "+ " "+ " "+ " "+"Fecha de nacimiento: "+usuario.getBirth()+" "+ " "+ " "+ " "+" "+ " "+ " "+ " "+" "+ " "+" "+ " "+ " "+ " "+" "+ " "+"Sexo: "+sexo +"\n"+"\n";
        email.setText(usuario.getEmail());
        contenido = contenido + "Correo electrónico: "+ usuario.getEmail() + " "+ " "+" "+ " "+ " "+ " "+" "+ " "+ " "+ " "+" "+ " "+ " "+ " "+" "+ " "+ " "+ " "+" "+ " "+ " "+ " "+" "+ " "+ " "+ " "+" "+ " "+" "+ " "+ " "+" "+ " "+ " "+ " "+ " "+" "+ " "+" "+ " "+ " "+" "+ " "+ " "+ " "+ " "+" "+ " "+ " "+ "Fecha de cálculo de riesgo: "+riesgo.getCreatedat()+"\n"+"\n"+"\n"+"\n";
        contenido = contenido + "El porcentaje que tiene el paciente de contraer insuficiencia renal es de "+riesgo.getPorcentrisk()+ "% este es un aproximado y no \nremplaza a una prueba de laboratorio completa, para obtener información más acertada visite a su médico.\n" + "\n"+ "\n"+ "\n" ;
        contenido = contenido + "Los factores de riesgo considerados son: \n"+"\n";



        if(riesgo.getDiabetes() == 1){
            risk = risk + "* Diabetes 40%\n";
        }if(riesgo.getBloodpreasure() == 1){
            risk = risk+"* Presion arterial alta 7%\n";
        }if(riesgo.getHeartfailure() == 1){
            risk = risk+"* Problemas cardiacos 34%\n";
        }if(riesgo.getLiverdiseasease() == 1){
            risk = risk + "* Enfermedades del hígado 31%\n";
        }if(riesgo.getKidneydisease() == 1){
            risk = risk + "* Enfermedades renales 5%\n";
        }if(riesgo.getCancer() == 1){
            risk = risk + "* Tratamiento de cancer 23%\n";
        }if(riesgo.getOverweight() == 1){
            risk = risk + "* Sobre peso 6%\n";
        }if(riesgo.getCreatinine() == 1){
            risk = risk + "* Nivel alto de creatinina 50%\n";
        }if(riesgo.getObstruccionbloodv() == 1){
            risk = risk + "* Obstruccion en vasos sanguineos 40%\n";
        }if(riesgo.getUrinarysediment() == 1){
            risk = risk + "* Anormalidad en sedimiento urinario 20%\n";
        }
        if(risk == ""){
            risk = "* Sin factor de riesgo encontrado";
        }

        contenido = contenido + risk;

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
        send();
        Intent openConsultarResultados = new Intent(EnviarReporte.this, ConsultarResultados.class);
        startActivity(openConsultarResultados);
        delete();
    }


    public void  generarPDF(){
        Toast.makeText(this, "Enviando reporte. Por favor espere...", Toast.LENGTH_SHORT).show();
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint descripcion = new TextPaint();
        TextPaint pie = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo paginaInfo = new PdfDocument.PageInfo.Builder(720, 1080, 1).create();
        PdfDocument.Page pagina1 = pdfDocument.startPage(paginaInfo);

        Canvas canvas = pagina1.getCanvas();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_playstore);//Imagen del logo
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 80, false);
        canvas.drawBitmap(bitmapEscala, 50, 20, paint);

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(18);
        canvas.drawText(tituloPDF, 250, 150, titulo);

        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        descripcion.setTextSize(14);

        pie.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        pie.setTextSize(8);

        String[] arrDescription = contenido.split("\n");
        int y = 200;
        for(int i =0; i<arrDescription.length; i++){
            canvas.drawText(arrDescription[i], 20, y, descripcion);
            y += 20;
        }

        String[] arrPie = pietexto.split("\n");
        int y2 = 1000;
        for(int i =0; i<arrPie.length; i++){
            canvas.drawText(arrPie[i], 100, y2, pie);
            y2 += 15;
        }

        pdfDocument.finishPage(pagina1);

        File file = new File(Environment.getExternalStorageDirectory(), "ReporteInsuficienciaRenal.pdf");
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
        }catch (Exception e){
            e.printStackTrace();
        }



        pdfDocument.close();
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

    private void send(){
        String correo = "deteccion.insuficiencia.renal@gmail.com";
        String password = "jcebudyinztyxbnj";



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host", "smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        try{
            session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo, password);
                }
            });
            if(session != null){

                BodyPart adjunto = new MimeBodyPart();
                System.out.println(Environment.getExternalStorageDirectory()+"/ReporteInsuficienciaRenal.pdf");
                adjunto.setDataHandler(new DataHandler(new FileDataSource(Environment.getExternalStorageDirectory()+"/ReporteInsuficienciaRenal.pdf")));
                adjunto.setFileName("ReporteInsuficienciaRenal.pdf");
                MimeMultipart m = new MimeMultipart();
                m.addBodyPart(adjunto);


                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correo));
                message.setSubject("Reporte insuficiencia renal "+ riesgo.getCreatedat());
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(usuario.getEmail()));
                message.setContent(m);

                Transport.send(message);
                Toast.makeText(this, "Reporte enviado correctamente", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error al enviar reporte", Toast.LENGTH_SHORT).show();
        }


    }
    public void delete(){
        File file = new File(Environment.getExternalStorageDirectory(), "ReporteInsuficienciaRenal.pdf");
        file.delete();
    }

}