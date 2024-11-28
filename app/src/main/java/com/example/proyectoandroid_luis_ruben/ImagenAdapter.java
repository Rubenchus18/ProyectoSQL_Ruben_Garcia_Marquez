package com.example.proyectoandroid_luis_ruben;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImagenAdapter extends ArrayAdapter<PonerListView> {

    public ImagenAdapter(Context context, List<PonerListView> items) {
        super(context, R.layout.iteml_ista, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.iteml_ista, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textViewNombreCopa);
        ImageView imageView = convertView.findViewById(R.id.imageView5);
        TextView kilometrosView = convertView.findViewById(R.id.textViewDistancia); // Asegúrate de que este ID coincida con el layout
        Button button =convertView.findViewById(R.id.button3);
        Button button2 =convertView.findViewById(R.id.button5);
        button.setText("Añadir");
        button2.setText("Eliminar");
        PonerListView item = getItem(position);
        if (item != null) {
            textView.setText(item.getNombre());
            imageView.setImageResource(item.getImagenResId());
            kilometrosView.setText(item.getKilometros());
        }

        return convertView;
    }
}