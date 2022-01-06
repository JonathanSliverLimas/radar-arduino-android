package com.jonathanlimas.radar.FragmentosAdministrador;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jonathanlimas.radar.AdapterRegistros;
import com.jonathanlimas.radar.Modelo.DBHelper;
import com.jonathanlimas.radar.Modelo.Persona;
import com.jonathanlimas.radar.Modelo.Registro;
import com.jonathanlimas.radar.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Registros extends Fragment {

    ListView listaViewRegistros;
    List<Registro> listRegistros;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_registros, container, false);
        crearDeteccionPorDefecto();

        listaViewRegistros = view.findViewById(R.id.listaViewRegistros);
        AdapterRegistros adapter = new AdapterRegistros(getActivity(), getData());
        listaViewRegistros.setAdapter(adapter);

        listaViewRegistros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Mantener presionado para eliminar", Toast.LENGTH_SHORT).show();
            }
        });

        listaViewRegistros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int item, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                String [] opciones = {"Eliminar"};
                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            Registro registro = listRegistros.get(item);
                            eliminarDeteccion(registro.getCodigo());
                        }
                    }
                });

                builder.create().show();
                return true;
            }
        });

        return view;
    }

    private List<Registro> getData() {
        DBHelper admin = new DBHelper(getActivity(), "dbRadar.db", null,1 );
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor cursor = BaseDeDatos.rawQuery("SELECT * FROM registros", null);
        Registro registro = null;

        if(cursor.moveToFirst()){
            listRegistros = new ArrayList<Registro>();
            do{
                registro = new Registro();
                registro.setCodigo(cursor.getInt(0));
                registro.setFecha(cursor.getString(1));
                registro.setHora(cursor.getString(2));
                registro.setPosicion(cursor.getInt(3));
                registro.setDistancia(cursor.getInt(4));

                listRegistros.add(registro);
            }while (cursor.moveToNext());
        }

        BaseDeDatos.close();
        return listRegistros;
    }

    private void eliminarDeteccion(int codigo) {
        try{
            DBHelper admin = new DBHelper(getActivity(), "dbRadar.db",null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            Cursor buscarRUN = BaseDeDatos.rawQuery("SELECT codigo FROM registros WHERE codigo = " + codigo, null);

            if(buscarRUN.moveToFirst()){
                BaseDeDatos.delete("registros", "codigo="+ codigo, null);

                BaseDeDatos.close();

                AdapterRegistros adapter = new AdapterRegistros(getActivity(), getData());
                listaViewRegistros.setAdapter(adapter);
                Toast.makeText(getActivity(),"Eliminado correctamente", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getActivity(), "No existe la deteccion", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void crearDeteccionPorDefecto(){
        try {
            DBHelper admin = new DBHelper(getActivity(), "dbRadar.db", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            Cursor registro = BaseDeDatos.rawQuery("SELECT * FROM registros", null);

            if(registro.getCount() == 0){
                Date date = new Date();
                SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");

                String SFecha = fecha.format(date);
                String SHora = hora.format(date);

                ContentValues detectAuto = new ContentValues();

                detectAuto.put("fecha", SFecha);
                detectAuto.put("hora", SHora);
                detectAuto.put("posicion", 140);
                detectAuto.put("distancia", 20);

                BaseDeDatos.insert("registros", null, detectAuto);

                BaseDeDatos.close();
            }
            BaseDeDatos.close();

        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}