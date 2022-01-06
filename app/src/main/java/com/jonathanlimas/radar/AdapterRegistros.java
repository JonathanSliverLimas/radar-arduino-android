package com.jonathanlimas.radar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonathanlimas.radar.Modelo.Persona;
import com.jonathanlimas.radar.Modelo.Registro;
import java.util.List;

public class AdapterRegistros extends BaseAdapter {
    Context context;
    List<Registro> list;


    public AdapterRegistros(Context context, List<Registro> list){
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

        TextView codigo, fecha, hora, posicion, distancia;

        Registro registro = list.get(i);

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_registros, null);
        }

        codigo = view.findViewById(R.id.codigoDetectado);
        fecha = view.findViewById(R.id.fechaDetectado);
        hora = view.findViewById(R.id.horaDetectado);
        posicion = view.findViewById(R.id.posicionDetectada);
        distancia = view.findViewById(R.id.distanciaDetectada);

        codigo.setText("Código: " + String.valueOf(registro.getCodigo()));
        fecha.setText("Fecha: " + registro.getFecha());
        hora.setText("Hora:" + registro.getHora());
        posicion.setText("Posicion: " + registro.getPosicion() + "°");
        distancia.setText("Distancia: " + registro.getDistancia() + " cm");

        return view;
    }

}
