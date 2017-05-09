package com.androidtutorialpoint.retrofitandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Correo {

@SerializedName("correo")
@Expose
private String correo;

public String getCorreo() {
return correo;
}

public void setCorreo(String correo) {
this.correo = correo;
}

    @Override
    public String toString() {
        return "Correo{" +
                "correo='" + correo + '\'' +
                '}';
    }
}