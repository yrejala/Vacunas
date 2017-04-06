package com.example.ysacio.vacunas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by maxvillamayor on 13/3/17.
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
            db.execSQL(ConstansDB.TABLA_TIPO_VACUNA_SQL);
            db.execSQL(ConstansDB.TABLA_VACUNA_SQL);
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




    private ContentValues tipoMapperContentValues(TipoVacunas tipovacunas) {
        ContentValues cv2 = new ContentValues();
        cv2.put(ConstansDB.VAC_TIPO_DESC,tipovacunas.getDescription());
        cv2.put(ConstansDB.VAC_TIPO_FECHA_APLI,tipovacunas.getDate_vac());
        cv2.put(ConstansDB.VAC_LOT_VAC,tipovacunas.getLote_vac()
        );
        return cv2;
    }

    private  ContentValues vacunasMapperContentValues(Vacunas vacunas) {
        ContentValues cv3 = new ContentValues();
        cv3.put(ConstansDB.VAC_ID_USU,vacunas.getId_usu());
        cv3.put(ConstansDB.VAC_ID_TIPO,vacunas.getId_tipo());
        cv3.put(ConstansDB.VAC_FECHA_APLI,vacunas.getFecha());
        cv3.put(ConstansDB.VAC_RESPONSABLE,vacunas.getResponsable());
        return cv3;

    }


    public long insertCliente(Usuario usuario) {
        this.openWriteableDB();
        long rowID = db.insert(ConstansDB.TABLA_USUARIOS, null, clienteMapperContentValues(usuario));
        this.closeDB();

        return rowID;
    }


    public long insertTipo(TipoVacunas tipovacunas) {
        this.openWriteableDB();
        long rowID = db.insert(ConstansDB.TABLA_TIPO_VACUNAS, null, tipoMapperContentValues(tipovacunas));
        this.closeDB();

        return rowID;
    }



    public long insertVacuna(Vacunas vacunas) {
        this.openWriteableDB();
        long rowID = db.insert(ConstansDB.TABLA_VACUNAS, null, vacunasMapperContentValues(vacunas));
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
        String[] campos = new String[]{ConstansDB.USU_ID, ConstansDB.USU_NOMBRE, ConstansDB.USU_MAIL,ConstansDB.USU_NOMBRE_BEBE,ConstansDB.USU_FECHA_NAC,ConstansDB.USU_SEXO};
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



    public ArrayList loadClientes2() {

        ArrayList list = new ArrayList<>();

        this.openReadableDB();
        String[] campos = new String[]{ConstansDB.USU_ID, ConstansDB.USU_NOMBRE, ConstansDB.USU_MAIL,ConstansDB.USU_NOMBRE_BEBE,ConstansDB.USU_FECHA_NAC,ConstansDB.USU_SEXO};
        Cursor c = db.query(ConstansDB.TABLA_USUARIOS, campos, null, null, null, null, null,null);

        try {
            while (c.moveToNext()) {





                Usuario2 usuario2 = new Usuario2();

                usuario2.setId(c.getInt(0));
                usuario2.setName(c.getString(1));
                usuario2.setMail(c.getString(2));
                usuario2.setName_baby(c.getString(3));
                usuario2.setDate_baby(c.getString(4));
                usuario2.setSex(c.getString(5));
                list.add(usuario2);

                list.add(usuario2);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public ArrayList loadVacunas() {

        ArrayList list3 = new ArrayList<>();

        this.openReadableDB();
        String[] campos3 = new String[]{ConstansDB.VAC_ID,ConstansDB.VAC_ID_USU, ConstansDB.VAC_ID_TIPO, ConstansDB.VAC_FECHA_APLI,ConstansDB.VAC_RESPONSABLE};
        Cursor c3 = db.query(ConstansDB.TABLA_VACUNAS,campos3,null,null,null,null,null);

        try {
            while (c3.moveToNext()) {
                Vacunas vacunas = new Vacunas();
                vacunas.setId(c3.getInt(0));
                vacunas.setId_usu(c3.getInt(1));
                vacunas.setId_tipo(c3.getInt(2));
                vacunas.setFecha(c3.getString(3));
                vacunas.setResponsable(c3.getString(4));
                list3.add(vacunas);
            }
        } finally {
            c3.close();
        }
        this.closeDB();

        return list3;
    }

    public ArrayList loadTiposVacunas() {

        ArrayList list2 = new ArrayList<>();

        this.openReadableDB();
        String[] campos2 = new String[]{ConstansDB.VAC_TIPO_ID_TIPO,ConstansDB.VAC_TIPO_DESC, ConstansDB.VAC_TIPO_FECHA_APLI, ConstansDB.VAC_LOT_VAC};
        Cursor c2 = db.query(ConstansDB.TABLA_TIPO_VACUNAS,campos2,null,null,null,null,null);

        try {
            while (c2.moveToNext()) {
                TipoVacunas tipvacunas = new TipoVacunas();
                tipvacunas.setId(c2.getInt(0));
                tipvacunas.setDescription(c2.getString(1));
                tipvacunas.setDate_vac(c2.getString(2));
                tipvacunas.setLote_vac(c2.getString(3));
                list2.add(tipvacunas);
            }
        } finally {
            c2.close();
        }
        this.closeDB();

        return list2;
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