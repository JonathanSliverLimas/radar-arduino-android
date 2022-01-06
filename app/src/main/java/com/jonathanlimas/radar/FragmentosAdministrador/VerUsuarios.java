package com.jonathanlimas.radar.FragmentosAdministrador;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jonathanlimas.radar.CustomAdapter;
import com.jonathanlimas.radar.Modelo.DBHelper;
import com.jonathanlimas.radar.Modelo.Persona;
import com.jonathanlimas.radar.R;

import java.util.ArrayList;
import java.util.List;


public class VerUsuarios extends Fragment {

    ListView listaViewUsuarios;
    List<Persona> listPersonas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_ver_usuarios, container, false);

        listaViewUsuarios = view.findViewById(R.id.listaViewUsuarios);

        CustomAdapter adapter = new CustomAdapter(getActivity(), getData());

        listaViewUsuarios.setAdapter(adapter);

        listaViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Persona persona = listPersonas.get(i);
                Toast.makeText(getActivity(), persona.getNombre() + " " + persona.getApellido() + " - " + persona.getRol(), Toast.LENGTH_SHORT).show();
            }
        });

        listaViewUsuarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int item, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                String [] opciones = {"Actualizar", "Eliminar"};
                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            Persona person = listPersonas.get(item);

                            Bundle result = new Bundle();
                            result.putString("rRUN", Integer.toString(person.getRUN()));
                            result.putString("rNombre", person.getNombre());
                            result.putString("rApellido", person.getApellido());
                            result.putString("rEdad", Integer.toString(person.getEdad()));
                            result.putString("rPassword", person.getPassword());

                            getParentFragmentManager().setFragmentResult("requestKey", result);

                            AgregarUsuario agregarUsuario = new AgregarUsuario();

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainer,agregarUsuario)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        if(i == 1 ){
                            Persona person = listPersonas.get(item);
                            eliminarUser(person.getRUN());
                        }
                    }
                });

                builder.create().show();
                return true;
            }
        });

        return view;
    }

    private List<Persona> getData() {
        DBHelper admin = new DBHelper(getActivity(), "dbRadar.db", null,1 );
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor cursor = BaseDeDatos.rawQuery("SELECT * FROM usuarios", null);
        Persona persona = null;

        if(cursor.moveToFirst()){
            listPersonas = new ArrayList<Persona>();
            do{
                persona = new Persona();
                persona.setRUN(cursor.getInt(0));
                persona.setNombre(cursor.getString(1));
                persona.setApellido(cursor.getString(2));
                persona.setEdad(cursor.getInt(3));
                persona.setPassword(cursor.getString(4));
                persona.setRol(cursor.getString(5));

                listPersonas.add(persona);
            }while (cursor.moveToNext());
        }

        BaseDeDatos.close();
        return listPersonas;
    }

    private void eliminarUser(int run){
        try{
            DBHelper admin = new DBHelper(getActivity(), "dbRadar.db",null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


            Cursor buscarRUN = BaseDeDatos.rawQuery("SELECT run FROM usuarios WHERE run = " + run, null);

            if(buscarRUN.moveToFirst()){
                BaseDeDatos.delete("usuarios", "run="+ run, null);
                BaseDeDatos.close();

                CustomAdapter adapter = new CustomAdapter(getActivity(), getData());

                listaViewUsuarios.setAdapter(adapter);
                Toast.makeText(getActivity(),"Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getActivity(), "No existe el usuario", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}