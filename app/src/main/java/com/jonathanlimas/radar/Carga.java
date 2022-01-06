package com.jonathanlimas.radar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jonathanlimas.radar.FragmentosAdministrador.Inicio;

public class Carga extends AppCompatActivity {

    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carga);

        final int duracion = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Carga.this,Login.class);
                startActivity(intent);
                finish();
            }
        }, duracion);

    }
}