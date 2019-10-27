package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.SolitarioCelta.R;

public class RecordAdapter extends ArrayAdapter {

    private Context contexto;
    private int idLayout;
    private List<String> misDatos;

    public RecordAdapter(@NonNull Context context, int resource, @NonNull List<String> data) {
        super(context, resource, data);
        contexto = context;
        idLayout = resource;
        misDatos = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout vista;

        if (null != convertView) {
            vista = (LinearLayout) convertView;
        } else {
            LayoutInflater inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vista = (LinearLayout) inflador.inflate(idLayout, parent, false);
        }

        // Asignar contenido a los elementos de la vista
        TextView tvLinea1 =(TextView) vista.findViewById(R.id.tvItemLinea1);
        TextView tvLinea2 = vista.findViewById(R.id.tvItemLinea2);
        TextView tvLinea3 = vista.findViewById(R.id.tvItemLinea3);

        String hola = misDatos.get(position);
        Log.i("tg", hola);

        tvLinea1.setText(hola);
        return vista;
    }
}
