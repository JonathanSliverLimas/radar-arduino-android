package com.jonathanlimas.radar.FragmentosAdministrador;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jonathanlimas.radar.Modelo.DBHelper;
import com.jonathanlimas.radar.Modelo.Persona;
import com.jonathanlimas.radar.R;

import java.util.HashMap;

public class AgregarUsuario extends Fragment {

    private EditText RUN, nombre, apellido, edad, password;
    private Button registrar, modificar, eliminar, buscar;

    Persona persona = new Persona();

    String rRUN, rNombre, rApellido, rEdad, rPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_agregar_usuario, container, false);

        RUN = view.findViewById(R.id.RUN);
        nombre = view.findViewById(R.id.Nombre);
        apellido = view.findViewById(R.id.Apellido);
        edad = view.findViewById(R.id.Edad);
        password = view.findViewById(R.id.Password);
        registrar = view.findViewById(R.id.Registrar);
        modificar = view.findViewById(R.id.Modificar);
        eliminar = view.findViewById(R.id.Eliminar);
        buscar = view.findViewById(R.id.BuscarUser);

        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if(result != null){
                    rRUN = result.getString("rRUN");
                    rNombre = result.getString("rNombre");
                    rApellido = result.getString("rApellido");
                    rEdad = result.getString("rEdad");
                    rPassword = result.getString("rPassword");

                    RUN.setText(rRUN);
                    nombre.setText(rNombre);
                    apellido.setText(rApellido);
                    edad.setText(rEdad);
                    password.setText(rPassword);

                }
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String run = RUN.getText().toString();
                String Nombre = nombre.getText().toString();
                String Apellido = apellido.getText().toString();
                String Edad = edad.getText().toString();
                String Password = password.getText().toString();

                // Todos los campos son obligatorios
                if(run.equals("")
                        || run.equals("0")
                        || Nombre.equals("")
                        || Apellido.equals("")
                        || Edad.equals("")
                        || Edad.equals("0")
                        || Password.equals("")){
                    Toast.makeText(getActivity(), "Por favor rellene todos los datos", Toast.LENGTH_SHORT).show();
                }
                else{
                    registrarUsuario(view);
                }
            }
        });


        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String run = RUN.getText().toString();
                String Nombre = nombre.getText().toString();
                String Apellido = apellido.getText().toString();
                String Edad = edad.getText().toString();
                String Password = password.getText().toString();

                // Todos los campos son obligatorios
                if(run.equals("")
                        || run.equals("0")
                        || Nombre.equals("")
                        || Apellido.equals("")
                        || Edad.equals("")
                        || Edad.equals("0")
                        || Password.equals("")){
                    Toast.makeText(getActivity(), "Por favor rellene todos los datos", Toast.LENGTH_SHORT).show();
                }
                else{
                    modificarUsuario(view);
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String run = RUN.getText().toString();

                // Todos los campos son obligatorios
                if(run.equals("") || run.equals("0")){
                    Toast.makeText(getActivity(), "Por favor ingrese el RUN", Toast.LENGTH_SHORT).show();
                }else if(run.equals("10100100")){
                    Toast.makeText(getActivity(), "El RUN ingresado es el Super Usuario, por lo tanto no puedes eliminarlo.", Toast.LENGTH_SHORT).show();
                } else{
                    eliminarUsuario(view);
                }
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String run = RUN.getText().toString();

                // Todos los campos son obligatorios
                if(run.equals("") || run.equals("0")){
                    Toast.makeText(getActivity(), "Por favor ingrese el RUN", Toast.LENGTH_SHORT).show();
                }
                else{
                    buscarUsuario(view);
                }
            }
        });

        return view;
    }

    private void registrarUsuario(View view){
        try{
            DBHelper admin = new DBHelper(getActivity(), "dbRadar.db", null,1 );
            SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

            persona.setRUN(Integer.parseInt(RUN.getText().toString()));
            persona.setNombre(nombre.getText().toString());
            persona.setApellido(apellido.getText().toString());
            persona.setEdad(Integer.parseInt(edad.getText().toString()));
            persona.setPassword(password.getText().toString());
            persona.setRol("admin");

            ContentValues registro = new ContentValues();

            registro.put("run", persona.getRUN());
            registro.put("nombre", persona.getNombre());
            registro.put("apellido", persona.getApellido());
            registro.put("edad", persona.getEdad());
            registro.put("password", persona.getPassword());
            registro.put("rol", persona.getRol());

            BaseDeDatos.insert("usuarios", null, registro);

            BaseDeDatos.close();

            limpiarFormulario();

            Toast.makeText(getActivity(),"Usuario agregado", Toast.LENGTH_SHORT).show();
            VerUsuarios verUsuarios = new VerUsuarios();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer,verUsuarios)
                    .addToBackStack(null)
                    .commit();

        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void buscarUsuario(View view){

        try{
            DBHelper admin = new DBHelper(getActivity(), "dbRadar.db",null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            persona.setRUN(Integer.parseInt(RUN.getText().toString()));

            Cursor registro = BaseDeDatos.rawQuery("SELECT nombre, apellido, edad, password FROM usuarios WHERE run = " + persona.getRUN(), null);


            if(registro.moveToFirst()){
                nombre.setText(registro.getString(0));
                apellido.setText(registro.getString(1));
                edad.setText(registro.getString(2));
                password.setText(registro.getString(3));
            }else{
                Toast.makeText(getActivity(), "No existe el usuario", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            }

            BaseDeDatos.close();

        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void modificarUsuario(View view){
        try{
            DBHelper admin = new DBHelper(getActivity(), "dbRadar.db",null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            String runUpdate = RUN.getText().toString();
            Cursor buscarRUN = BaseDeDatos.rawQuery("SELECT run FROM usuarios WHERE run = " + runUpdate, null);

            if(buscarRUN.moveToFirst()){
                persona.setNombre(nombre.getText().toString());
                persona.setApellido(apellido.getText().toString());
                persona.setEdad(Integer.parseInt(edad.getText().toString()));
                persona.setPassword(password.getText().toString());

                ContentValues registro = new ContentValues();

                registro.put("nombre", persona.getNombre());
                registro.put("apellido", persona.getApellido());
                registro.put("edad", persona.getEdad());
                registro.put("password", persona.getPassword());

                BaseDeDatos.update("usuarios", registro, "run="+ runUpdate, null);

                BaseDeDatos.close();

                limpiarFormulario();

                Toast.makeText(getActivity(),"Usuario modificado", Toast.LENGTH_SHORT).show();
                VerUsuarios verUsuarios = new VerUsuarios();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer,verUsuarios)
                        .addToBackStack(null)
                        .commit();

            }else{
                Toast.makeText(getActivity(), "No existe el usuario", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            }

        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarUsuario(View view){
        try{
            DBHelper admin = new DBHelper(getActivity(), "dbRadar.db",null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            String runDelete = RUN.getText().toString();

            Cursor buscarRUN = BaseDeDatos.rawQuery("SELECT run FROM usuarios WHERE run = " + runDelete, null);

            if(buscarRUN.moveToFirst()){
                BaseDeDatos.delete("usuarios", "run="+ runDelete, null);
                limpiarFormulario();
                BaseDeDatos.close();

                Toast.makeText(getActivity(),"Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                VerUsuarios verUsuarios = new VerUsuarios();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer,verUsuarios)
                        .addToBackStack(null)
                        .commit();

            }else{
                Toast.makeText(getActivity(), "No existe el usuario", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void limpiarFormulario(){
        RUN.setText("");
        nombre.setText("");
        apellido.setText("");
        edad.setText("");
        password.setText("");

        persona.setRUN(0);
        persona.setNombre("");
        persona.setApellido("");
        persona.setEdad(0);
        persona.setPassword("");
    }
}