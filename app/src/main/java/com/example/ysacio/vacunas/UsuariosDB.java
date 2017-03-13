package com.example.ysacio.vacunas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by isabelinorolandolopezroman on 13/3/17.
 */

public class UsuariosDB {

        private SQLiteDatabase db;
        private DBHelper dbHelper;

        public UsuariosDB(Context context) {
            dbHelper = new DBHelper(context);
        }

        private void openReadableDB() {
            db = dbHelper.getReadableDatabase();
        }

        private void openWriteableDB() {
            db = dbHelper.getWritableDatabase();
        }

        private void closeDB() {
            if(db!=null){
                db.close();
            }
        }

// CRUD...

private static class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, ConstansDB.DB_NAME, null, ConstansDB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConstansDB.TABLA_USUARIO_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


    private ContentValues clienteMapperContentValues(Usuario usuario) {
        ContentValues cv = new ContentValues();
        cv.put(ConstansDB.USU_NOMBRE, usuario.getName());
        cv.put(ConstansDB.USU_MAIL, usuario.getMail());
        cv.put(ConstansDB.USU_NOMBRE_BEBE, usuario.getName_baby());
        cv.put(ConstansDB.USU_FECHA_NAC, usuario.getDate_baby());
        cv.put(ConstansDB.USU_SEXO, usuario.getSex());
        return cv;
    }


    public long insertCliente(Usuario usuario) {
        this.openWriteableDB();
        long rowID = db.insert(ConstansDB.TABLA_USUARIOS, null, clienteMapperContentValues(usuario));
        this.closeDB();

        return rowID;
    }


    public void updateCliente(Usuario usuario) {
        this.openWriteableDB();
        String where = ConstansDB.USU_ID + "= ?";
        db.update(ConstansDB.TABLA_USUARIOS, clienteMapperContentValues(usuario), where, new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    public void deleteCliente(int id) {
        this.openWriteableDB();
        String where = ConstansDB.USU_ID + "= ?";
        db.delete(ConstansDB.TABLA_USUARIOS, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList loadClientes() {

        ArrayList list = new ArrayList<>();

        this.openReadableDB();
        String[] campos = new String[]{ConstansDB.USU_ID, ConstansDB.USU_NOMBRE, ConstansDB.USU_MAIL, ConstansDB.USU_NOMBRE_BEBE,ConstansDB.USU_FECHA_NAC,ConstansDB.USU_SEXO};
        Cursor c = db.query(ConstansDB.TABLA_USUARIOS, campos, null, null, null, null, null,null);

        try {
            while (c.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(c.getInt(0));
                usuario.setName(c.getString(1));
                usuario.setMail(c.getString(2));
                usuario.setName_baby(c.getString(3));
                usuario.setDate_baby(c.getString(4));
                usuario.setSex(c.getString(5));
                list.add(usuario);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }


    public Usuario buscarUsuario(String nombre) {
        Usuario usuario = new Usuario();
        this.openReadableDB();
        String where = ConstansDB.USU_NOMBRE + "= ?";
        String[] whereArgs = {nombre};
        Cursor c = db.query(ConstansDB.TABLA_USUARIOS, null, where, whereArgs, null, null, null,null);

        if( c != null || c.getCount() <=0) {
            c.moveToFirst();
            usuario.setId(c.getInt(0));
            usuario.setName(c.getString(1));
            usuario.setMail(c.getString(2));
            usuario.setName_baby(c.getString(3));
            usuario.setDate_baby(c.getString(4));
            usuario.setSex(c.getString(5));
            c.close();
        }
        this.closeDB();
        return usuario;
    }

}