package com.example.ysacio.vacunas;

/**
 * Created by maxvillamayor on 20/3/17.
 */

public class Vacunas {


    private int id;
    private int id_usu;
    private int id_tipo;
    private String fecha;
    private String responsable;



    public Vacunas() {}

    public Vacunas(int id_usu ,int id_tipo,String fecha,String responsable) {
        this.id_usu = id_usu;
        this.id_tipo = id_tipo;
        this.fecha = fecha;
        this.responsable = responsable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usu() {
        return id_usu;
    }

    public void setId_usu(int id_usu) {
        this.id_usu = id_usu;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }


    @Override
    public String toString() {
        return "Vacunas{" +
                "id=" + id +
                ", id_usu=" + id_usu +
                ", id_tipo=" + id_tipo +
                ", fecha='" + fecha + '\'' +
                ", responsable='" + responsable + '\'' +
                '}';
    }
}
