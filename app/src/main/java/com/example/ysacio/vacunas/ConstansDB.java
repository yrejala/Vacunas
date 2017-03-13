package com.example.ysacio.vacunas;

/**
 * Created by maxvillamayor on 13/3/17.
 */

public class ConstansDB {

    //General
    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;


    //TABLA CLIENTES
    public static final String TABLA_USUARIOS = "usuarios";

    public static final String USU_ID = "_id";
    public static final String USU_NOMBRE = "nombre";
    public static final String USU_MAIL = "email";
    public static final String USU_NOMBRE_BEBE = "nombre_bebe";
    public static final String USU_FECHA_NAC = "fecha_nac";
    public static final String USU_SEXO = "sexo";

    public static final String TABLA_USUARIO_SQL =
            "CREATE TABLE  " + TABLA_USUARIOS + "(" +
                    USU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USU_NOMBRE + " TEXT NOT NULL," +
                    USU_MAIL   + " TEXT," +
                    USU_NOMBRE_BEBE   + " TEXT," +
                    USU_FECHA_NAC   + " TEXT," +
                    USU_SEXO   + " TEXT);" ;

    //Otras tablas
}
