package com.example.ysacio.vacunas;



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


    //TABLA TIPO VACUNAS

    public static final String TABLA_TIPO_VACUNAS = "tipo_vacunas";

    public static final String VAC_TIPO_ID_TIPO= "_id_tipo";
    public static final String VAC_TIPO_DESC = "descripcion";
    public static final String VAC_TIPO_FECHA_APLI = "fecha_apli_vac";
    public static final String VAC_LOT_VAC="lot_vac";




    public static final String TABLA_TIPO_VACUNA_SQL =
            "CREATE TABLE  " + TABLA_TIPO_VACUNAS + "(" +
                    VAC_TIPO_ID_TIPO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    VAC_TIPO_DESC + " TEXT," +
                    VAC_TIPO_FECHA_APLI + " TEXT," +
                    VAC_LOT_VAC + " TEXT);" ;






    //TABLA VACUNAS

    public static final String TABLA_VACUNAS = "vacunas";

    public static final String VAC_ID = "_id_vac";
    public static final String VAC_ID_USU = "_id_usu";
    public static final String VAC_ID_TIPO= "_id_tipo";
    public static final String VAC_FECHA_APLI = "fecha_apli_vac";
    public static final String VAC_RESPONSABLE = "responsable_vac";



    public static final String TABLA_VACUNA_SQL =
            "CREATE TABLE  " + TABLA_VACUNAS + "(" +
                    VAC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    VAC_ID_USU + " INTEGER NOT NULL CONSTRAINT fk_id_usu REFERENCES vacunas(_id_) ON DELETE CASCADE ON UPDATE CASCADE,"+
                    VAC_ID_TIPO + " INTEGER NOT NULL CONSTRAINT fk_id_tipo REFERENCES vacunas(_id_tipo) ON DELETE CASCADE ON UPDATE CASCADE,"+
                    VAC_FECHA_APLI   + " TEXT," +
                    VAC_RESPONSABLE   + " TEXT);" ;





}
