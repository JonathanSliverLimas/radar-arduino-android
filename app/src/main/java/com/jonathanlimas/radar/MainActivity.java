package com.jonathanlimas.radar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jonathanlimas.radar.FragmentosAdministrador.AgregarRegistro;
import com.jonathanlimas.radar.FragmentosAdministrador.AgregarUsuario;
import com.jonathanlimas.radar.FragmentosAdministrador.Inicio;
import com.jonathanlimas.radar.FragmentosAdministrador.Registros;
import com.jonathanlimas.radar.FragmentosAdministrador.VerUsuarios;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigationDrawerOpen, R.string.navigationDrawerClose);

        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        if(savedInstanceState  == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Inicio()).commit();
            navigationView.setCheckedItem(R.id.inicioAdmin);
            getSupportActionBar().setTitle("Inicio");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.inicioAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new Inicio()).commit();
                getSupportActionBar().setTitle("Inicio");
                break;

            case R.id.registros:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new Registros()).commit();
                getSupportActionBar().setTitle("Detectados");
                break;

            case R.id.agregarUsuario:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new AgregarUsuario()).commit();
                getSupportActionBar().setTitle("Formulario");
                break;

            case R.id.verUsuarios:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new VerUsuarios()).commit();
                getSupportActionBar().setTitle("Usuarios");
                break;

            case R.id.agregarRegistro:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new AgregarRegistro()).commit();
                getSupportActionBar().setTitle("Detección automática");
                break;

            case R.id.salir:
                Toast.makeText(this, "Cerraste sesión", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Login.class));
                overridePendingTransition(R.anim.to_left, R.anim.from_rigth);
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}