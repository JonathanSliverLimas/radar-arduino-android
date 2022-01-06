package com.jonathanlimas.radar.FragmentosAdministrador;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jonathanlimas.radar.MainActivity;
import com.jonathanlimas.radar.Modelo.DBHelper;
import com.jonathanlimas.radar.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarRegistro extends Fragment {

    TextView fechaRegistro, horaRegistro;
    Button crearRegistro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_agregar_registro, container, false);

        fechaRegistro = view.findViewById(R.id.fechaRegistro);
        horaRegistro = view.findViewById(R.id.horaRegistro);
        crearRegistro = view.findViewById(R.id.crearRegistro);

        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");

        String SFecha = fecha.format(date);
        String SHora = hora.format(date);

        fechaRegistro.setText(SFecha);
        horaRegistro.setText(SHora);

        crearRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaDeteccion(view);
            }
        });
        return view;
    }

    private void nuevaDeteccion(View view){
        try{
            DBHelper admin = new DBHelper(getActivity(), "dbRadar.db", null,1 );
            SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

            ContentValues registro = new ContentValues();

            registro.put("fecha", fechaRegistro.getText().toString());
            registro.put("hora", horaRegistro.getText().toString());
            registro.put("posicion", 140);
            registro.put("distancia", 20);

            BaseDeDatos.insert("registros", null, registro);

            BaseDeDatos.close();

            Toast.makeText(getActivity(),"Nueva detección guardada correctamente", Toast.LENGTH_SHORT).show();

            Registros registros = new Registros();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer,registros)
                    .addToBackStack(null)
                    .commit();

        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}