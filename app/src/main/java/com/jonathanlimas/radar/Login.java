package com.jonathanlimas.radar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jonathanlimas.radar.FragmentosAdministrador.VerUsuarios;
import com.jonathanlimas.radar.Modelo.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Login extends AppCompatActivity {

    EditText runUser, passwordUser;
    Button iniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        runUser = (EditText) findViewById(R.id.RUN_Login);
        passwordUser = (EditText) findViewById(R.id.Password_Login);
        iniciarSesion = (Button) findViewById(R.id.iniciarSesion);

        buscarAdminPorDefecto("10100100");
        crearDeteccionPorDefecto();

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String run = runUser.getText().toString();
                String pass = passwordUser.getText().toString();

                if(run.equals("") || pass.equals("")){
                    Toast.makeText(Login.this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    buscarUsuario(view);
                }
            }
        });

    }

    private void buscarUsuario(View view){
        try{
            DBHelper admin = new DBHelper(this, "dbRadar.db",null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            String runLogin = runUser.getText().toString();

            Cursor registro = BaseDeDatos.rawQuery("SELECT run, password FROM usuarios WHERE run = " + runLogin, null);


            if(registro.moveToFirst()){
                if(runUser.getText().toString().equals(registro.getString(0)) && passwordUser.getText().toString().equals(registro.getString(1))){
                    startActivity(new Intent(this, MainActivity.class));
                    overridePendingTransition(R.anim.to_left, R.anim.from_rigth);
                    finish();
                }else if(!passwordUser.getText().equals(registro.getString(1))){
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "No existe el usuario", Toast.LENGTH_SHORT).show();
            }
            BaseDeDatos.close();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarAdminPorDefecto(String runAdmin){
        try{
            DBHelper admin = new DBHelper(this, "dbRadar.db",null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            Cursor registro = BaseDeDatos.rawQuery("SELECT run FROM usuarios WHERE run = " + runAdmin, null);

            if(!registro.moveToFirst()){
                ContentValues contentValues = new ContentValues();
                contentValues.put("run", 10100100);
                contentValues.put("nombre", "Jonathan");
                contentValues.put("apellido", "Limas");
                contentValues.put("edad", "21");
                contentValues.put("password", "123456");
                contentValues.put("rol", "admin");

                BaseDeDatos.insert("usuarios", null, contentValues);

                Toast.makeText(this, "Se creo un admintrador por defecto", Toast.LENGTH_SHORT).show();
            }

            BaseDeDatos.close();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void crearDeteccionPorDefecto(){
        try {
            DBHelper admin = new DBHelper(this, "dbRadar.db", null, 1);
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}