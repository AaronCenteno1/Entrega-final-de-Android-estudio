package com.example.entregafinalaaron.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Usuarios.db";
    private static final int DB_VERSION = 3;

    private static final String TABLE_USUARIOS = "usuarios";
    private static final String COL_ID = "id";
    private static final String COL_USUARIO = "usuario";
    private static final String COL_PASSWORD = "password";
    private static final String COL_TIPO = "tipo";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creacion de la tabla
        String createTable = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USUARIO + " TEXT UNIQUE," +
                COL_PASSWORD + " TEXT," +
                COL_TIPO + " TEXT)";
        db.execSQL(createTable);

        // Crear admin por defecto
        ContentValues cv = new ContentValues();
        cv.put(COL_USUARIO, "admin");   // usuario fijo
        cv.put(COL_PASSWORD, "1234");   // contraseña fija
        cv.put(COL_TIPO, "admin");      // tipo admin
        db.insert(TABLE_USUARIOS, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    //  se Registrar usuario normal
    public boolean registrarUsuario(String usuario, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USUARIO, usuario);
        cv.put(COL_PASSWORD, password);
        cv.put(COL_TIPO, "usuario"); // todos son usuarios normales
        long resultado = db.insert(TABLE_USUARIOS, null, cv);
        return resultado != -1;
    }

    // Validar login y devolver
    public String validarLogin(String usuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_TIPO + " FROM " + TABLE_USUARIOS + " WHERE " + COL_USUARIO + "=? AND " + COL_PASSWORD + "=?",
                new String[]{usuario, password}
        );

        if (cursor.moveToFirst()) {
            String tipo = cursor.getString(0);
            cursor.close();
            return tipo; // "admin" o "usuario"
        }

        cursor.close();
        return null; // usuario no existe
    }
}