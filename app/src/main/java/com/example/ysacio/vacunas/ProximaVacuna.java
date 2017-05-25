package com.example.ysacio.vacunas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ProximaVacuna {

    @SerializedName("nombreHijo")
    @Expose
    private String nombreHijo;
    @SerializedName("nombreVacuna")
    @Expose
    private String nombreVacuna;
    @SerializedName("fechaProxApli")
    @Expose
    private String fechaProxApli;

    public String getNombreHijo() {
        return nombreHijo;
    }

    public void setNombreHijo(String nombreHijo) {
        this.nombreHijo = nombreHijo;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }

    public String getFechaProxApli() {
        return fechaProxApli;
    }

    public void setFechaProxApli(String fechaProxApli) {
        this.fechaProxApli = fechaProxApli;
    }

}