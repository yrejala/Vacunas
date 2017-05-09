package com.example.ysacio.vacunas;


import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;


public interface RetrofitObjectAPI {
    //String correo = "maxvillamayor@technoma.com.py" ;
    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
    */
    //@GET("api/RetrofitAndroidObjectResponse")
    //Call<Email> getStudentDetails();
    @GET("ws/usuario/email/{correo}")
    //Call<Email> getUserByEmail(@Query("correo") String correo);

    Call<Correo> getUserByEmail(@Path("correo") String correo);
}
