package com.example.ysacio.vacunas;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Esta interfaz se usa para conectarse al Web Service

public interface GetHijoByUsuarioId {
    //Con el GET le estoy pasando al Web Service el UsuarioId para obtener
    //la lista de hijos que tiene ese usuario.
    @GET("ws/hijos/{usuarioId}")
    Call<List<Hijo>> getHijo(@Path("usuarioId") Integer usuarioId);

}
