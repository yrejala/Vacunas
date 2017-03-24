package com.example.ysacio.vacunas;

/**
 * Created by maxvillamayor on 20/3/17.
 */

public class TipoVacunas {

    private int id;
    private String description;
    private String date_vac;
    private String lote_vac;


    public TipoVacunas() {}

    public TipoVacunas(String description ,String date_vac,String lote_vac) {
        this.description = description;
        this.date_vac = date_vac;
        this.lote_vac=lote_vac;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_vac() {
        return date_vac;
    }

    public void setDate_vac(String date_vac) {
        this.date_vac = date_vac;
    }

    public String getLote_vac() {
        return lote_vac;
    }

    public void setLote_vac(String lote_vac) {
        this.lote_vac = lote_vac;
    }

    @Override
    public String toString() {

        return description;
//        return "TipoVacunas{" +
//                "id=" + id +
//                ", description='" + description + '\'' +
//                ", date_vac='" + date_vac + '\'' +
//                ", lote_vac='" + lote_vac + '\'' +
//                '}';
    }

}
