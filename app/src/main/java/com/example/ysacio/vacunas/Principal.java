package com.example.ysacio.vacunas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;


public class Principal extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView idTextView;
    ArrayAdapter<String> adaptador;

    private GoogleApiClient googleApiClient;

    private ListView listado;
    private ArrayList<String> nombres;
    //String data1,data2,data3;

    //String nombre[] = {"Mirna","Belen","Carla","Claudia"};

    //String email[] = {"mirna@hotmail.com","belen@gmail.com","carla@yahoo.com","clau@hotmail.com"};

    private UsuariosDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        photoImageView = (ImageView) findViewById(R.id.imgFoto);
        nameTextView = (TextView) findViewById(R.id.txtnombre);
        emailTextView = (TextView) findViewById(R.id.txtcorreo);
        idTextView = (TextView) findViewById(R.id.txtid);


        // Inicializamos el ListView y le asignamos el Adapter.
        listado=(ListView)findViewById(R.id.lvTabla2);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(Principal.this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        db = new UsuariosDB(this);

        Usuario cliente1 = new Usuario("Mirian","mirian@mail.net","Franco","22-01-2013","M");
        Usuario cliente2 = new Usuario("Maria","maria@mail.net","Pedro","22-01-2013","M");
        Usuario cliente3 = new Usuario("Mirta","mirna@mail.net","Carlos","22-01-2013","M");
        Usuario cliente4 = new Usuario("Juana","juana@mail.net","Daniel","22-01-2013","M");
        Usuario cliente5 = new Usuario("Blanca","blanca@mail.net","Emma","22-01-2013","F");

        Log.i("---> Base de datos: ", "Insertando Clientes....");
        db.insertCliente(cliente1);
        db.insertCliente(cliente2);
        db.insertCliente(cliente3);
        db.insertCliente(cliente4);
        db.insertCliente(cliente5);
        Log.i("---> Base de datos: ", "Mostrando Clientes....");
        //mostrarClientesLog();

        //Log.i("---> Base de datos: ", "Borrando Cliente con id 1....");
       // db.deleteCliente(1);
        //mostrarClientesLog();

//        Log.i("---> Base de datos: ", "Cambiando el nombre de cliente2....");
//        cliente2.setName("María");
//        db.updateCliente(cliente2);
        //mostrarClientesLog();

        Log.i("---> Base de datos: ", "Buscando datos de cliente....");
        Usuario usuario = new Usuario();
        usuario = db.buscarUsuario("Juana");
        Log.i("---> Base de datos: ", "Los datos de pedro son: "+usuario.toString());

        Log.i("---> Base de datos: ", "Cambiando el nombre de Juan....");
        usuario.setName("Mirna");
        db.updateCliente(usuario);
        mostrarClientesLog();







    }


    private void mostrarClientesLog() {
        List list = db.loadClientes();



        adaptador = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list.toArray());

        listado.setAdapter(adaptador);

        for (Object usuario : list) {
            Log.i("---> Base de datos: ", usuario.toString());



        }


    }




    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

            nameTextView.setText(account.getDisplayName());
            emailTextView.setText(account.getEmail());
            idTextView.setText(account.getId());

            Glide.with(this).load(account.getPhotoUrl()).into(photoImageView);

        } else {
            goLogInScreen();
        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(),"No puedo cerrar session", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void remove(View view) {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "no puedo remover", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
