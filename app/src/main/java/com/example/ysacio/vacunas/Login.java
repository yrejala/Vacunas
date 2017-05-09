package com.example.ysacio.vacunas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.GsonConverterFactory;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    private SignInButton signInButton;

    public static final int SIGN_IN_CODE = 777;
    String url = "http://10.0.2.2:8084/";
    String getcorreo;
    String emailgoogle;
    boolean llamada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        llamada=true;
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
            //handleSignInResult(result);
            getRetrofitObject(emailgoogle);
            //goMainScreen();

        }
    }

//    private void handleSignInResult(GoogleSignInResult result) {
//        if (result.isSuccess()) {
//            getRetrofitObject(emailgoogle);
//            goMainScreen();
//        } else {
//            Toast.makeText(this, "No resolvio", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void goMainScreen() {

        Log.d("onResponse", "Correo de webservice "+getcorreo);
        Log.d("onResponse", "Correo de google "+emailgoogle);
        if (emailgoogle.equals(getcorreo)){
            Intent intent = new Intent(this, Principal.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(Login.this,"No Existe el correo", Toast.LENGTH_SHORT).show();
        }
    }


    void getRetrofitObject(String correo) {
        //String correo = "maxvillamayor@technoma.com.py";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitObjectAPI service = retrofit.create(RetrofitObjectAPI.class);

        Call<Correo> call = service.getUserByEmail(correo);

        call.enqueue(new Callback<Correo>() {
            @Override
            public void onResponse(Response<Correo> response, Retrofit retrofit) {

                try {

                    //  text_id_1.setText("StudentId  :  " + response.body().getId());
                    //  text_name_1.setText("StudentName  :  " + response.body().getNombre());
                    getcorreo = response.body().getCorreo();
                    Log.d("onResponse", "Get Correo "+getcorreo);
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
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

}
