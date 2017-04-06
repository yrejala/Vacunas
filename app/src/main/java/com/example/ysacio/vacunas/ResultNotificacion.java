package com.example.ysacio.vacunas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class ResultNotificacion extends AppCompatActivity {

    TextView notif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_notificacion);

        notif=(TextView)findViewById(R.id.txtNotif);

        notif.setText(getIntent().getStringExtra("notificacionID"));


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {

            Intent ir_principal=new Intent(ResultNotificacion.this,Principal.class);
            startActivity(ir_principal);



        }
        return super.onKeyDown(keyCode, event);
    }
}
