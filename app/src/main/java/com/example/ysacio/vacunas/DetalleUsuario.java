package com.example.ysacio.vacunas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleUsuario extends AppCompatActivity {


    TextView datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_usuario);

        datos=(TextView)findViewById(R.id.txtDetalle);

        datos.setText(getIntent().getStringExtra("llave"));



    }
}
