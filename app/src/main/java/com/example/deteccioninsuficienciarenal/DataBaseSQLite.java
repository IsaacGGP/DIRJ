package com.example.deteccioninsuficienciarenal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "DIRDB";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_RISK = "risk";

    public DataBaseSQLite(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                "iduser INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "lastname TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "created_at TEXT NOT NULL," +
                "birth TEXT NOT NULL," +
                "gender INTEGER NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_RISK + "(" +
                "idresults INTEGER PRIMARY KEY AUTOINCREMENT," +
                "porcent_risk INTEGER NOT NULL," +
                "diabetes INTEGER NOT NULL," +
                "blood_preasure INTEGER NOT NULL," +
                "heart_failure INTEGER NOT NULL," +
                "liver_disease INTEGER NOT NULL," +
                "kidney_disease INTEGER NOT NULL," +
                "cancer INTEGER NOT NULL," +
                "created_at TEXT NOT NULL," +
                "weight INTEGER NOT NULL," +
                "creatinine_level INTEGER NOT NULL," +
                "obstruction_blood_vesseles INTEGER NOT NULL," +
                "urinary_sediment_abnormalities INTEGER NOT NULL," +
                "iduser INTEGER NOT NULL," +
                "FOREIGN KEY(iduser) REFERENCES TABLE_USERS(iduser))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_RISK + TABLE_RISK);
        onCreate(sqLiteDatabase);
    }


}
