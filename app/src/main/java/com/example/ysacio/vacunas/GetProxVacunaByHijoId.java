package com.example.ysacio.vacunas;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Esta interfaz se usa para conectarse al Web Service

public interface GetProxVacunaByHijoId {
    //Se obtiene la proxima vacuna que el hijo debe aplicarse

    @GET("ws/proximavacuna/{hijoId}")
    Call<List<ProximaVacuna>> getListProxVacunaByHijoId(@Path("hijoId") String hijoId);

}
