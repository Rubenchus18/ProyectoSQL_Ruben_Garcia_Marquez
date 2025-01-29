package com.example.proyectoandroid_luis_ruben;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PilotoAdapter extends ArrayAdapter<Piloto> {
    public PilotoAdapter(Context context, ArrayList<Piloto> pilotos) {
        super(context, 0, pilotos);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Piloto piloto = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemcopa, parent, false);
        }
        TextView nombreTextView = convertView.findViewById(R.id.nombreCopa);
        TextView cocheTextView = convertView.findViewById(R.id.cocheCopa);
        if (piloto != null) {
            nombreTextView.setText(piloto.getNombrepiloto());
            cocheTextView.setText(piloto.getCoche());
        }
        return convertView;
    }
}