package com.example.ysacio.vacunas;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;




public class Principal extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView idTextView;
    ArrayAdapter<String> adaptador,adaptador2;

    private GoogleApiClient googleApiClient;

    private ListView listado;
    private ArrayList<String> nombres;
    //String data1,data2,data3;

    //String nombre[] = {"Mirna","Belen","Carla","Claudia"};

    //String email[] = {"mirna@hotmail.com","belen@gmail.com","carla@yahoo.com","clau@hotmail.com"};

    private UsuariosDB db;
    List list,list1,list2,list3,list4;


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




        Usuario cliente1 = new Usuario("Mirian","mirian@mail.net","Franco","06-03-2017","M");
        Usuario cliente2 = new Usuario("Maria","maria@mail.net","Pedro","22-01-2013","M");
        Usuario cliente3 = new Usuario("Mirta","mirna@mail.net","Carlos","07-03-2017","M");
        Usuario cliente4 = new Usuario("Juana","juana@mail.net","Daniel","22-01-2013","M");
        Usuario cliente5 = new Usuario("Blanca","blanca@mail.net","Emma","22-01-2013","F");



        TipoVacunas tipovac1=new TipoVacunas("BCG","0","1");

        TipoVacunas tipovac2=new TipoVacunas("ROTA","2","2");
        TipoVacunas tipovac3=new TipoVacunas("IPV","2","3");
        TipoVacunas tipovac4=new TipoVacunas("PENTA","2","4");
        TipoVacunas tipovac5=new TipoVacunas("PCV10","2","5");

        TipoVacunas tipovac6=new TipoVacunas("ROTA-2","4","6");
        TipoVacunas tipovac7=new TipoVacunas("OPV","4","7");
        TipoVacunas tipovac8=new TipoVacunas("PENTA-2","4","8");
        TipoVacunas tipovac9=new TipoVacunas("PCV10-2","4","9");



        Vacunas vacu1=new Vacunas(1,1,"30-05-17","Dr.Jorge");
        Vacunas vacu2=new Vacunas(1,1,"30-06-17","Dr.Carlos");
        Vacunas vacu3=new Vacunas(2,2,"30-07-17","Dr.Daniel");
        Vacunas vacu4=new Vacunas(2,2,"30-08-17","Dr.E.Coronel");
        Vacunas vacu5=new Vacunas(3,3,"30-09-17","Dr.R.Portillo");




        Log.i("---> Base de datos: ", "Insertando Clientes....");
        db.insertCliente(cliente1);
        db.insertCliente(cliente2);
        db.insertCliente(cliente3);
        db.insertCliente(cliente4);
        db.insertCliente(cliente5);
        Log.i("---> Base de datos: ", "Mostrando Clientes....");


        db.insertTipo(tipovac1);
        db.insertTipo(tipovac2);
        db.insertTipo(tipovac3);
        db.insertTipo(tipovac4);
        db.insertTipo(tipovac5);
        db.insertTipo(tipovac6);
        db.insertTipo(tipovac7);
        db.insertTipo(tipovac8);
        db.insertTipo(tipovac9);

        db.insertVacuna(vacu1);
        db.insertVacuna(vacu2);
        db.insertVacuna(vacu3);
        db.insertVacuna(vacu4);
        db.insertVacuna(vacu5);


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



        Log.d("Esto es la fecha del telefono:",String.valueOf(getDatePhone()));

        String daytel,monttel,yeartel,d1,m1,y1,d2,m2,y2;

        daytel=getDatePhone().substring(0,2);
        monttel=getDatePhone().substring(3,5);
        yeartel=getDatePhone().substring(6,10);

        Log.d("dia",daytel);
        Log.d("mes",monttel);
        Log.d("año",yeartel);

        list1 = db.loadClientes();
        list2 = db.loadTiposVacunas();

        list3=db.loadVacunas();
        list4=db.loadClientes2();

        Log.d("@Esto es Usuarios:",String.valueOf(list4.get(0)));

        d1=String.valueOf(list4.get(0)).substring(0,2);
        m1=String.valueOf(list4.get(0)).substring(3,5);
        y1=String.valueOf(list4.get(0)).substring(6,10);

        Log.d("dia1",d1);
        Log.d("mes1",m1);
        Log.d("año1",y1);

        Log.d("@Esto es Usuarios:",String.valueOf(list4.get(2)));

        d2=String.valueOf(list4.get(2)).substring(0,2);
        m2=String.valueOf(list4.get(2)).substring(3,5);
        y2=String.valueOf(list4.get(2)).substring(6,10);

        Log.d("dia2",d2);
        Log.d("mes2",m2);
        Log.d("año2",y2);

        String fecha_telef=getDatePhone();
        String fecha_bebe=String.valueOf(list4.get(0));
        String fecha_bebe2=String.valueOf(list4.get(2));




        try {

            SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");

            Date fecha1=sdf.parse(fecha_telef);

            Date fecha2=sdf.parse(fecha_bebe);

            Date fecha3=sdf.parse(fecha_bebe2);


            if(fecha1.before(fecha2)) {

                Log.d("Fecha_Local es Menor","fecha de bebe 1");
            }else{
                if(fecha2.before(fecha1)){

                    Log.d("Fecha_Local es Mayor","fecha de bebe 1");
                }else{

                    Log.d("Fecha_Local es Igual","fecha de bebe 1");
                }

            }


            if(fecha1.before(fecha3)) {

                Log.d("Fecha_Local es Menor","fecha de bebe 2");
            }else{
                if(fecha3.before(fecha1)){

                    Log.d("Fecha_Local es Mayor","fecha de bebe 2");
                }else{

                    Log.d("Fecha_Local es Igual","fecha de bebe 2");
                }

            }



        } catch (ParseException e) {
            e.printStackTrace();
        }

        // int resul1,result2;
//        resul1=Integer.parseInt(monttel)-Integer.parseInt(m1);

        String mensa= String.valueOf(list4.get(0))+"\n"+String.valueOf(list4.get(2));

        showNotification(mensa+"\n"+"Deben aplicarse:"+"ROTA1"+"\n"+"IPV"+"\n"+"PENTA"+"\n"+"PCV10"+"\n");


//        Log.d("@Esto es Vacunas:",String.valueOf(list3.get(0)));
//        Log.d("@Esto es tipo vacuna:",String.valueOf(list2.get(0)));

//        if()
//






    }

    private String getDatePhone()

    {

        Calendar cal = new GregorianCalendar();

        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String formatteDate = df.format(date);

        return formatteDate;

    }


    private void mostrarClientesLog() {
        list= db.loadClientes();

        //final List list2 = db.loadTiposVacunas();


        Log.d("Esto es usuarios",list.toString());



        adaptador = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,list.toArray());

        //adaptador2 = new ArrayAdapter(this,
        //      android.R.layout.simple_list_item_1,list2.toArray());

        listado.setAdapter(adaptador);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                Intent pasardetalle=new Intent(Principal.this,DetalleUsuario.class);
                pasardetalle.putExtra("llave",String.valueOf(adaptador.getItem(position))+"\n"+"Tiene 1 mes"+"\n"+"vacunas:"+"BCG"+"\n"+"pendientes: ninguno");

                startActivity(pasardetalle);

//                AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
//                builder.setTitle("Mostrar Lista");
//                builder.setMessage(String.valueOf(adaptador.getItem(position)));
//                //builder.setMessage(String.valueOf(response.getPrimitiveProperty("descripcionResultado")));
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        dialog.cancel();
//
//                    }
//                });
//
//                builder.show();




            }
        });


        for (Object usuario : list) {
            Log.i("---> Base de datos: ", usuario.toString());



        }


    }


    public void showNotification(String mensaje) {


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setContentTitle("My notification")
                        .setContentText(mensaje);
// Crea una intención explícita para una actividad en tu aplicación.
        Intent resultIntent = new Intent(this, ResultNotificacion.class);
        resultIntent.putExtra("notificacionID",mensaje);

// El objeto de constructor de pila contendrá una pila posterior artificial para
// Actividad iniciada.
// Esto asegura que navegar hacia atrás desde la Actividad
// Su aplicación a la pantalla de inicio.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Agrega la pila trasera para el intento (pero no para el intent)
        stackBuilder.addParentStack(Principal.class);
// Añade la intención que inicia la actividad en la parte superior de la pila
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// MId le permite actualizar la notificación más adelante.
        int mId=1;
        mNotificationManager.notify(mId, mBuilder.build());
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
            idTextView.setText(account.getId())
            ;

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
