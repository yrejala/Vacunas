package com.example.ysacio.vacunas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Date;
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
    Integer idHijo;
    String getNombreHijo;
    String getApellidoHijo;
    String getSexoHijo;
    Date getFechaNacHijo;

    //
    private String TAG = ListaVacunas.class.getSimpleName();

    private ProgressDialog pDialog;
    ListView lv;

    // URL to get contacts JSON
    private static String url = "http://10.0.2.2:8084/ws/registro/1";

    ArrayList<HashMap<String, String>> ListaVacunas;
    ListView listaVacunas;
    ArrayAdapter<String> items;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vacunas);
        Bundle extras = getIntent().getExtras();
        Integer idHijo = extras.getInt("idHijo");
        Log.d("onResponse", "Recibo idHijo en la clase ListaVacunas " + idHijo);

//        Integer idUsuario = getIntent().getIntExtra("idUsuario", 1);
//        Log.d("onResponse", "Intent " + idUsuario);
//        getHijo(idUsuario);



        ListaVacunas = new ArrayList<>();

        lv = (ListView) findViewById(R.id.listaVacunas);

        new GetContacts().execute();
    }

    void getHijo(Integer usuarioId) {
        //Integer usuarioId = 2;
        Log.d("onResponse", "Entra en intent metodo get hijo del usuarioId: " + usuarioId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        GetHijoByUsuarioId service = retrofit.create(GetHijoByUsuarioId.class);
        Call<List<Hijo>> call = service.getHijo(usuarioId);
        call.enqueue(new Callback<List<Hijo>>() {

            public void onResponse(Call<List<Hijo>> call, Response<List<Hijo>> response) {
                List<Hijo> hijo = response.body();
                String dato = "";


                for (int i = 0; i < hijo.size(); i++) {
                    getIdHijo = hijo.get(i).getId();
                    Log.d("onResponse", "Get id hijo " + getIdHijo);
                    getNombreHijo = hijo.get(i).getNombre();
                    Log.d("onResponse", "Get nombre hijo " + getNombreHijo);
                    //dato += "\n\nname: "+ getNombreHijo;
                    getApellidoHijo = hijo.get(i).getApellido();
                    Log.d("onResponse", "Get Apellido hijo " + getApellidoHijo);
                    getSexoHijo = hijo.get(i).getSexo();
                    Log.d("onResponse", "Get sexo hijo " + getSexoHijo);
                    getFechaNacHijo = hijo.get(i).getFechaNac();
                    Log.d("onResponse", "Get nombre FechaNac " + getFechaNacHijo);

                }
                //dato.setText(dato);
            }

            @Override
            public void onFailure(Call<List<Hijo>> call, Throwable throwable) {

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
            String jsonStr = sh.makeServiceCall(url);

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


            ListAdapter adapter = new SimpleAdapter(
                    ListaVacunas.this, ListaVacunas,
                    R.layout.lista_vacunas, new String[]{ "nombreVacuna",
                    "descripcionVacuna", "dosis", "meses", "fechaApli", "responsable"}, new int[]{
                    R.id.nombreVacuna, R.id.descripcionVacuna, R.id.dosis, R.id.meses, R.id.fechaApli,R.id.responsable  });

            lv.setAdapter(adapter);
        }


    }
}
