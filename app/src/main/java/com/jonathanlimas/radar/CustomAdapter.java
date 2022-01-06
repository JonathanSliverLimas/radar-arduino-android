package com.jonathanlimas.radar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonathanlimas.radar.Modelo.Persona;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Persona> list;

    public CustomAdapter(Context context, List<Persona> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageUser;
        TextView run, nombre, apellido, edad;

        Persona persona = list.get(i);

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_usuarios, null);
        }
            imageUser = view.findViewById(R.id.imageViewUser);
            run = view.findViewById(R.id.run_User);
            nombre = view.findViewById(R.id.nombreUser);
            apellido = view.findViewById(R.id.apellidoUser);
            edad = view.findViewById(R.id.edad_User);
            imageUser.setImageResource(R.drawable.admin);


            run.setText(String.valueOf(persona.getRUN()));
            nombre.setText(persona.getNombre());
            apellido.setText(persona.getApellido());
            edad.setText(String.valueOf(persona.getEdad()) + " años");

        return view;
    }
}
