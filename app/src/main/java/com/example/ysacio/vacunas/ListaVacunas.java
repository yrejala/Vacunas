package com.example.ysacio.vacunas;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaVacunas extends AppCompatActivity {

    //Declaracion de variables de Hijo
    Integer getIdHijo;
    String idHijo;
    String getNombreHijo;
    String getNombreVacuna;
    String getSexoHijo;
    String getFechaProxApli;
    String fechaTelefono=getDatePhone();

    //
    private String TAG = ListaVacunas.class.getSimpleName();

    private ProgressDialog pDialog;
    ListView lv;

    // URL to get contacts JSON

    private static String urlRegistro = "http://10.0.2.2:8084/ws/registro/";
    String urlVacuna;
    String urlProximaVacuna = "http://10.0.2.2:8084/";

    ArrayList<HashMap<String, String>> ListaVacunas;
    ListView listaVacunas;
    ArrayAdapter<String> items;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vacunas);
        Bundle extras = getIntent().getExtras();
        idHijo = extras.getString("idHijo");
        Log.d("onResponse", "Recibo idHijo en la clase ListaVacunas " + idHijo);
        urlVacuna = urlRegistro+idHijo;
        Log.d("onResponse", "Conexion al web - Busqueda de Vacuna con IdHijo " + urlVacuna);

//        Integer idUsuario = getIntent().getIntExtra("idUsuario", 1);
//        Log.d("onResponse", "Intent " + idUsuario);
//        getHijo(idUsuario);
//        getListProxVacunaByHijoId(idHijo);


        ListaVacunas = new ArrayList<>();

        lv = (ListView) findViewById(R.id.listaVacunas);

        new GetContacts().execute();
    }

    void getListProxVacunaByHijoId(String idHijo) {
        //Integer usuarioId = 2;
        Log.d("onResponse", "Entra en intent metodo get hijo del usuarioId: " + idHijo);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlProximaVacuna)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        GetProxVacunaByHijoId service = retrofit.create(GetProxVacunaByHijoId.class);
        Call<List<ProximaVacuna>> call = service.getListProxVacunaByHijoId(idHijo);
        call.enqueue(new Callback<List<ProximaVacuna>>() {

            public void onResponse(Call<List<ProximaVacuna>> call, Response<List<ProximaVacuna>> response) {

                try {
                List<ProximaVacuna> proximaVacuna = response.body();
                String dato = "";


                for (int i = 0; i < proximaVacuna.size(); i++) {
                    getNombreHijo = proximaVacuna.get(i).getNombreHijo();
                    Log.d("onResponse", "Get nombre hijo " + getNombreHijo);
                    getNombreVacuna = proximaVacuna.get(i).getNombreVacuna();
                    Log.d("onResponse", "Get nombre vacuna " + getNombreVacuna);
                    getFechaProxApli = proximaVacuna.get(i).getFechaProxApli();
                    Log.d("onResponse", "Get fecha prox apli " + getFechaProxApli);
                    Log.d("onResponse", "Get fecha del telefono " + fechaTelefono);

                }

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();

                }

                //dato.setText(dato);
                try {

                    SimpleDateFormat sdfProximaVacuna=new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdfTelefono=new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha1=sdfTelefono.parse(fechaTelefono);
                    Date fecha2=sdfProximaVacuna.parse(getFechaProxApli);
                    Log.d("onResponse", "Get fecha prox apli parseado " +fecha1);
                    Log.d("onResponse", "Get fecha del telefono parseado " +fecha2);


                if(fecha1.before(fecha2)) {
                    showNotification("\n"+getNombreHijo+"\n"+"Debe aplicarse: "+getNombreVacuna+"\n"+"En la fecha "+getFechaProxApli+"\n");
                    Log.d("Fecha_Local es Menor","fecha de aplicacion");
                }else{
                    if(fecha2.before(fecha1)){

                        Log.d("Fecha_Local es Mayor","fecha de aplicacion");
                    }else{

                        Log.d("Fecha_Local es Igual","fecha de aplicacion");
                    }

                }

                } catch (Exception e) {
                    showNotification("No tiene vacunas pendientes");
//                    Log.d("onResponse", "There is an error");
//                    e.printStackTrace();
//                    Log.d("onResponse", "Este hijo no tiene vacunas pendientes");
//                    AlertDialog alertDialog = new AlertDialog.Builder(ListaVacunas.this).create();
//                    alertDialog.setTitle("Mensaje de Vacunas Pendientes");
//                    alertDialog.setMessage("No tiene Vacunas pendientes");
//                    // Alert dialog button
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Alert dialog action goes here
//                                    // onClick button code here
//                                    dialog.dismiss();// use dismiss to cancel alert dialog
//                                }
//                            });
//                    alertDialog.show();
//                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<ProximaVacuna>> call, Throwable throwable) {

            }
        });
    }
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListaVacunas.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlVacuna);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {


                    JSONArray jsonarray = new JSONArray(jsonStr);

                    //Loop the Array
                    for(int i=0;i < jsonarray.length();i++) {
                        Log.e("Message","loop");
                        HashMap<String, String> datosVacuna = new HashMap<>();
                        JSONObject e = jsonarray.getJSONObject(i);
                        datosVacuna.put("nombreVacuna", "Vacuna :" + e.getString("nombreVacuna"));
                        datosVacuna.put("descripcionVacuna", "Descripcion :" + e.getString("descripcionVacuna"));
                        datosVacuna.put("dosis", "Dosis : " +  e.getString("dosis"));
                        datosVacuna.put("meses", "Mes : " +  e.getString("meses"));
                        datosVacuna.put("fechaApli", "Fecha de Aplicacion : " +  e.getString("fechaApli"));
                        datosVacuna.put("responsable", "Responsable : " +  e.getString("responsable"));
                        ListaVacunas.add(datosVacuna);


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });


                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            getListProxVacunaByHijoId(idHijo);
            ListAdapter adapter = new SimpleAdapter(
                    ListaVacunas.this, ListaVacunas,
                    R.layout.lista_vacunas, new String[]{ "nombreVacuna",
                    "descripcionVacuna", "dosis", "meses", "fechaApli", "responsable"}, new int[]{
                    R.id.nombreVacuna, R.id.descripcionVacuna, R.id.dosis, R.id.meses, R.id.fechaApli,R.id.responsable  });

            lv.setAdapter(adapter);
        }


    }



    public void showNotification(String mensaje) {
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        int icono = R.mipmap.ic_launcher;
        Intent i = new Intent(ListaVacunas.this, ResultNotificacion.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ListaVacunas.this, 0, i, 0);

        mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle("Notificacion de Vacunas")
                .setContentText(mensaje)
                .setVibrate(new long[]{100, 250, 100, 500})
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, ResultNotificacion.class);
        resultIntent.putExtra("notificacionID", mensaje);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Principal.class);
        stackBuilder.addParentStack(ListaVacunas.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int mId = 1;


        mNotificationManager.notify(mId, mBuilder.build());
    }

    String getDatePhone()

    {

        Calendar cal = new GregorianCalendar();

        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String formatteDate = df.format(date);
        Log.d("onResponse", "Formateo de fecha " + formatteDate);

        return formatteDate;

    }
}
