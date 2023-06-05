package com.example.deteccioninsuficienciarenal;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.BoringLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DataBaseCRUD extends DataBaseSQLite {
    Context context;
    public DataBaseCRUD(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insetarUser(String username, String lastname, String email, String password, String created_at, String birth, int gender){
        long id = 0;
        try {
            DataBaseSQLite dataBase = new DataBaseSQLite(context);
            SQLiteDatabase db = dataBase.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("lastname", lastname);
            values.put("email", email);
            values.put("password", password);
            values.put("created_at", created_at);
            values.put("birth", birth);
            values.put("gender", gender);

            id = db.insert(TABLE_USERS, null, values);
            return id;
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public long insertarRisk( int porcent_risk, int diabetes, int blood_preasure, int heart_failure, int liver_disease, int kidney_disease, int cancer, String created_at, int weight, int creatinine_level, int obstruction_blood_vesseles, int urinary_sediment_abnormalities, int iduser){
        long id = 0;
        try {
            DataBaseSQLite dataBase = new DataBaseSQLite(context);
            SQLiteDatabase db = dataBase.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("porcent_risk", porcent_risk);
            values.put("diabetes", diabetes);
            values.put("blood_preasure", blood_preasure);
            values.put("heart_failure", heart_failure);
            values.put("liver_disease", liver_disease);
            values.put("kidney_disease", kidney_disease);
            values.put("cancer", cancer);
            values.put("created_at", created_at);
            values.put("weight", weight);
            values.put("creatinine_level", creatinine_level);
            values.put("obstruction_blood_vesseles", obstruction_blood_vesseles);
            values.put("urinary_sediment_abnormalities", urinary_sediment_abnormalities);
            values.put("iduser", iduser);

            id = db.insert(TABLE_RISK, null, values);
            return id;

        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public Usuario iniciarSesionUser(String email, String password){
        int id = 0;
        DataBaseSQLite dataBase = new DataBaseSQLite(context);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        Usuario usuario = null;
        Cursor cursorUser;
        cursorUser = db.rawQuery("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "' LIMIT 1", null);

        if (cursorUser.moveToFirst()){
            usuario = new Usuario();
            usuario.setIduser(cursorUser.getInt(0));
            usuario.setUsername(cursorUser.getString(1));
            usuario.setLastname(cursorUser.getString(2));
            usuario.setEmail(cursorUser.getString(3));
            usuario.setPassword(cursorUser.getString(4));
            usuario.setCreated_at(cursorUser.getString(5));
            usuario.setBirth(cursorUser.getString(6));
            usuario.setGender(cursorUser.getInt(7));
        }

        cursorUser.close();

        return usuario;
    }

    public Usuario buscarUser(int id){
        DataBaseSQLite dataBase = new DataBaseSQLite(context);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        Usuario usuario = null;
        Cursor cursorUser;
        cursorUser = db.rawQuery("SELECT * FROM users WHERE iduser = " + id +  " LIMIT 1", null);

        if (cursorUser.moveToFirst()){
            usuario = new Usuario();
            usuario.setIduser(cursorUser.getInt(0));
            usuario.setUsername(cursorUser.getString(1));
            usuario.setLastname(cursorUser.getString(2));
            usuario.setEmail(cursorUser.getString(3));
            usuario.setPassword(cursorUser.getString(4));
            usuario.setCreated_at(cursorUser.getString(5));
            usuario.setBirth(cursorUser.getString(6));
            usuario.setGender(cursorUser.getInt(7));
        }

        cursorUser.close();

        return usuario;
    }

    public Riesgo buscarRisk(int id){
        DataBaseSQLite dataBase = new DataBaseSQLite(context);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        Riesgo riesgo = null;
        Cursor cursorRisk;
        cursorRisk = db.rawQuery("SELECT * FROM risk WHERE idresults = " + id +  " LIMIT 1", null);

        if (cursorRisk.moveToFirst()){
            riesgo = new Riesgo();
            riesgo.setIdresults(cursorRisk.getInt(0));
            riesgo.setPorcentrisk(cursorRisk.getInt(1));
            riesgo.setDiabetes(cursorRisk.getInt(2));
            riesgo.setBloodpreasure(cursorRisk.getInt(3));
            riesgo.setHeartfailure(cursorRisk.getInt(4));
            riesgo.setLiverdiseasease(cursorRisk.getInt(5));
            riesgo.setKidneydisease(cursorRisk.getInt(6));
            riesgo.setCancer(cursorRisk.getInt(7));
            riesgo.setCreatedat(cursorRisk.getString(8));
            riesgo.setOverweight(cursorRisk.getInt(9));
            riesgo.setCreatinine(cursorRisk.getInt(10));
            riesgo.setObstruccionbloodv(cursorRisk.getInt(11));
            riesgo.setUrinarysediment(cursorRisk.getInt(12));
            riesgo.setIduser(cursorRisk.getInt(13));
        }

        cursorRisk.close();

        return riesgo;
    }

    public ArrayList<Riesgo> leerRiesgos(int id){
       DataBaseSQLite dataBase = new DataBaseSQLite(context);
       SQLiteDatabase db = dataBase.getWritableDatabase();

       ArrayList<Riesgo> listaRiesgos = new ArrayList<>();
       Riesgo riesgo = null;
       Cursor cursorRiesgo = null;

       cursorRiesgo = db.rawQuery("SELECT * FROM risk WHERE iduser = " + id + "", null);

       if(cursorRiesgo.moveToLast()){
           do{
               riesgo = new Riesgo();
               riesgo.setIdresults(cursorRiesgo.getInt(0));
               riesgo.setPorcentrisk(cursorRiesgo.getInt(1));
               riesgo.setDiabetes(cursorRiesgo.getInt(2));
               riesgo.setBloodpreasure(cursorRiesgo.getInt(3));
               riesgo.setHeartfailure(cursorRiesgo.getInt(4));
               riesgo.setLiverdiseasease(cursorRiesgo.getInt(5));
               riesgo.setKidneydisease(cursorRiesgo.getInt(6));
               riesgo.setCancer(cursorRiesgo.getInt(7));
               riesgo.setCreatedat(cursorRiesgo.getString(8));
               riesgo.setOverweight(cursorRiesgo.getInt(9));
               riesgo.setCreatinine(cursorRiesgo.getInt(10));
               riesgo.setObstruccionbloodv(cursorRiesgo.getInt(11));
               riesgo.setUrinarysediment(cursorRiesgo.getInt(12));
               riesgo.setIduser(cursorRiesgo.getInt(13));
               listaRiesgos.add(riesgo);
           } while (cursorRiesgo.moveToPrevious());
       }
       cursorRiesgo.close();

       return listaRiesgos;
    }

    public boolean editarUser(int id, String username, String lastname, String email, String password, String birth){
        DataBaseSQLite dataBase = new DataBaseSQLite(context);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        try{
            db.execSQL("UPDATE " + TABLE_USERS + " SET username = '" + username + "', lastname = '" + lastname + "',email = '" + email + "', password = '" + password + "', birth = '" + birth + "' WHERE iduser = " + id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Usuario buscarEmail(String email){
        int id = 0;
        DataBaseSQLite dataBase = new DataBaseSQLite(context);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        Usuario usuario = null;
        Cursor cursorUser;
        cursorUser = db.rawQuery("SELECT * FROM users WHERE email = '" + email + "' LIMIT 1", null);

        if (cursorUser.moveToFirst()){
            usuario = new Usuario();
            usuario.setIduser(cursorUser.getInt(0));
            usuario.setUsername(cursorUser.getString(1));
            usuario.setLastname(cursorUser.getString(2));
            usuario.setEmail(cursorUser.getString(3));
            usuario.setPassword(cursorUser.getString(4));
            usuario.setCreated_at(cursorUser.getString(5));
            usuario.setBirth(cursorUser.getString(6));
            usuario.setGender(cursorUser.getInt(7));
        }

        cursorUser.close();

        return usuario;
    }


}
