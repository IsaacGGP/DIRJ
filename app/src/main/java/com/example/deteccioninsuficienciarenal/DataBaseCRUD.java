package com.example.deteccioninsuficienciarenal;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;



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

    public long insertarRisk(int idresult, int porcent_risk, int diabetes, int blood_preasure, int heart_failure, int liver_disease, int kidney_disease, int cancer, int created_at, int weight, int creatinine_level, int obstruction_blood_vesseles, int urinary_sediment_abnormalities){
        long id = 0;
        try {
            DataBaseSQLite dataBase = new DataBaseSQLite(context);
            SQLiteDatabase db = dataBase.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idresult", idresult);
            values.put("porcent_risk", porcent_risk);
            values.put("diabetes", diabetes);
            values.put("blood_preasure", blood_preasure);
            values.put("heart_failure", heart_failure);
            values.put("liver_disease", liver_disease);
            values.put("cancer", cancer);
            values.put("created_at", created_at);
            values.put("weight", weight);
            values.put("creatinine_level", creatinine_level);
            values.put("obstruction_blood_vasseles", obstruction_blood_vesseles);
            values.put("urinary_sediment_abnormalities", urinary_sediment_abnormalities);

            id = db.insert(TABLE_USERS, null, values);
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


}
