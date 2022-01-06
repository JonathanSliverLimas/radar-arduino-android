package com.jonathanlimas.radar.FragmentosAdministrador;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jonathanlimas.radar.Modelo.DBHelper;
import com.jonathanlimas.radar.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Inicio extends Fragment {

    TextView fechaHoy, detectados;
    Button verDetectados;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_inicio, container, false);

        fechaHoy = view.findViewById(R.id.fechaHoy);
        detectados = view.findViewById(R.id.detectados);
        verDetectados = view.findViewById(R.id.verDetectados);

        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        String SFecha = fecha.format(date);
        
        fechaHoy.setText("Hoy: " + SFecha);
        cantidadDetectadosHoy(SFecha);

        verDetectados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registros registros = new Registros();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer,registros)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void cantidadDetectadosHoy(String fecha) {
        try {
            DBHelper admin = new DBHelper(getActivity(), "dbRadar.db", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            Cursor registro = BaseDeDatos.rawQuery("SELECT * FROM registros WHERE fecha='" + fecha + "'", null);
            detectados.setText("Detectados: " + Integer.toString(registro.getCount()));
            BaseDeDatos.close();

        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}