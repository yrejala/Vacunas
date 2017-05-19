package com.example.ysacio.vacunas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.GsonConverterFactory;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private GoogleApiClient googleApiClient;

    private SignInButton signInButton;

    public static final int SIGN_IN_CODE = 777;
    String url = "http://10.0.2.2:8084/";
    String getcorreo;
    Integer getIdUsuario;
    String getcorreo2;
    Integer getIdUsuario2;
    String emailgoogle;
    //Declaracion de variables de Hijo
    Integer getIdHijo;
    String getNombreHijo;
    String getApellidoHijo;
    String getSexoHijo;
    Date getFechaNacHijo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //getRetrofitObject();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.btnLogin);

        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);

            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = result.getSignInAccount();
            emailgoogle = account.getEmail();
            Log.d("Recibe Email de google", account.getEmail());
            getEmailByEMail(emailgoogle);
        }
    }

    //Este metodo compara si el correo devuelto por la API de Google es igual al correo
    //devuelto por el Web Service. Si son Iguales va a la clase Principal.
    //Si no son iguales despliega un error "No existe el correo"

    private void goMainScreen() {


        Log.d("onResponse", "Correo de webservice "+getcorreo);
        Log.d("onResponse", "Correo de google "+emailgoogle);
        if (emailgoogle.equals(getcorreo)){

            //getHijo(getIdUsuario);
            //Intent intent = new Intent(this, Principal.class);
            Intent intent = new Intent(this, ListaHijos.class);
            intent.putExtra("getIdUsuario",getIdUsuario);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(Login.this,"No Existe el correo", Toast.LENGTH_SHORT).show();
        }
    }


    //Este metodo llama a la interface GetEmailByEmail que consulta y compara
    //si el email devuelto por el API de google existe en el Web Service.
    //Si el email existe va a goMainScreen();
    //Si el correo no existe da un mensaje de error "No existe el correo, pruebe otro.

    void getEmailByEMail(String correo) {
        //String correo = "maxvillamayor@technoma.com.py";
        Log.d("onResponse", "Recibiendo URL" + url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetEmailByEmail service = retrofit.create(GetEmailByEmail.class);
        Call<Correo> call = service.getUserByEmail(correo);
        call.enqueue(new Callback<Correo>() {
            @Override
            public void onResponse(Call<Correo> call, Response<Correo> response) {

                try {
                    getcorreo = response.body().getCorreo();
                    getIdUsuario = response.body().getId();
                    Log.d("onResponse", "Get Correo "+getcorreo);
                    Log.d("onResponse", "Get Correo "+getIdUsuario);
                    goMainScreen();

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
                    alertDialog.setTitle("Mensaje de Error");
                    alertDialog.setMessage("No existe el correo, pruebe otro");
                    // Alert dialog button
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Alert dialog action goes here
                                    // onClick button code here
                                    dialog.dismiss();// use dismiss to cancel alert dialog
                                }
                            });
                    alertDialog.show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Correo> call, Throwable throwable) {
            }
        });
    }

    void getHijo(Integer usuarioId) {
        //Integer usuarioId = 2;
        Log.d("onResponse", "Entra en metodo get hijo del usuarioId: "+usuarioId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        GetHijoByUsuarioId service = retrofit.create(GetHijoByUsuarioId.class);
        Call<List<Hijo>> call=service.getHijo(usuarioId);
        call.enqueue(new Callback<List<Hijo>>() {

            public void onResponse(Call<List<Hijo>> call, Response<List<Hijo>> response) {
                List<Hijo> hijo=response.body();
                String dato="";

                for (int i=0;i<hijo.size();i++){
                    getIdHijo = hijo.get(i).getId();
                    Log.d("onResponse", "Get id hijo "+getIdHijo);
                    getNombreHijo =hijo.get(i).getNombre();
                    Log.d("onResponse", "Get nombre hijo "+getNombreHijo);
                    //dato += "\n\nname: "+ getNombreHijo;
                    getApellidoHijo = hijo.get(i).getApellido();
                    Log.d("onResponse", "Get Apellido hijo "+getApellidoHijo);
                    getSexoHijo = hijo.get(i).getSexo();
                    Log.d("onResponse", "Get sexo hijo "+getSexoHijo);
                    getFechaNacHijo = hijo.get(i).getFechaNac();
                    Log.d("onResponse", "Get nombre FechaNac "+getFechaNacHijo);

                }
                //dato.setText(dato);
            }

            @Override
            public void onFailure(Call<List<Hijo>> call, Throwable throwable) {

            }
        });
    }
}
