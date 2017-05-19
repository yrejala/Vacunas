package com.example.ysacio.vacunas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

import static com.example.ysacio.vacunas.R.layout.activity_lista_hijos;

public class ListaHijos extends AppCompatActivity {

    //Declaracion de variables de Hijo
    Integer idHijo;
    Integer getIdHijo;
    String getNombreHijo;
    String getApellidoHijo;
    String getSexoHijo;
    Date getFechaNacHijo;

    //
    private String TAG = ListaHijos.class.getSimpleName();

    private ProgressDialog pDialog;
    ListView lv;

    // URL to get contacts JSON
    private static String url = "http://10.0.2.2:8084/ws/hijos/";
    String urlHijo;


    ArrayList<HashMap<String, String>> ListaHijos;
    ListView listView;
    ArrayAdapter<String> items;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_lista_hijos);

//        Integer idUsuario = getIntent().getIntExtra("idUsuario", 1);
//        Log.d("onResponse", "Intent " + idUsuario);
//        getHijo(idUsuario);


        Bundle extras = getIntent().getExtras();
        Integer idUsuario = extras.getInt("getIdUsuario");
        urlHijo = url+idUsuario;
        Log.d("onResponse", "Recibo la variable idUsuario " + idUsuario);
        ListaHijos = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listaHijos);
        new GetContacts().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                    Intent myIntent = new Intent(view.getContext(), ListaVacunas.class);
                    myIntent.putExtra("idHijo", idHijo);

                    Log.d("onResponse", "Envio idHijo a la clase ListaVacunas " + idHijo);
                    startActivityForResult(myIntent, 0);

            }
        });



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
            pDialog = new ProgressDialog(ListaHijos.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            Log.d("onResponse", "Conexion al web - Busqueda de Hijos con IdUsuario " + urlHijo);
            String jsonStr = sh.makeServiceCall(urlHijo);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {


                    JSONArray jsonarray = new JSONArray(jsonStr);

                    //Loop the Array
                    for(int i=0;i < jsonarray.length();i++) {
                        Log.e("Message","loop");
                        HashMap<String, String> datosHijo = new HashMap<>();
                        JSONObject e = jsonarray.getJSONObject(i);
                        datosHijo.put("idHijo", "id Hijo:" + e.getString("id"));
                        datosHijo.put("nombre", "Nombre :" + e.getString("nombre"));
                        datosHijo.put("apellido", "Apellido : " +  e.getString("apellido"));
                        datosHijo.put("sexo", "Sexo : " +  e.getString("sexo"));
                        datosHijo.put("fechaNac", "Fecha de Nacimiento : " +  e.getString("fechaNac"));
                        ListaHijos.add(datosHijo);


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
                    ListaHijos.this, ListaHijos,
                    R.layout.lista_hijos, new String[]{ "idHijo","nombre",
                    "apellido", "sexo", "fechaNac"}, new int[]{
                    R.id.idHijo, R.id.nombre, R.id.apellido, R.id.sexo, R.id.fechaNac});

            lv.setAdapter(adapter);
        }


    }
}
