package com.example.ysacio.vacunas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Correo {

    private Integer id;
    @SerializedName("correo")
    @Expose
    private String correo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Correo{" +
                "id=" + id +
                ", correo='" + correo + '\'' +
                '}';
    }

}