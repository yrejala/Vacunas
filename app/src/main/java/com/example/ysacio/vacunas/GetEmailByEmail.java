package com.example.ysacio.vacunas;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// Esta interfaz se usa para conectarse al Web Service

public interface GetEmailByEmail {
    //Con el GET le estoy pasando al Web Service el correo que desea consultar si existe.
    //Si el correo exixte, me devuelve el mismo correo. Si no existe, me devuelve vacio
    //@GET("ws/correo/{correo}")
    @GET("ws/correo")
    Call<Correo> getUserByEmail(@Query("correo") String correo);

}
