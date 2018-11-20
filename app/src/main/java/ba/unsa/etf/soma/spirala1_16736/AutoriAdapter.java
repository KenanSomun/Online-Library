package ba.unsa.etf.soma.spirala1_16736;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kenan Somun on 14/04/2018.
 */

public class AutoriAdapter extends ArrayAdapter {

    Context context;
    int resource;

    public AutoriAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(getContext());
        Autor a = (Autor) getItem(position);

        View view;
        if (convertView == null)
            view = li.inflate(R.layout.element_lista_autor, null);
        else
            view = convertView;

        TextView imeAutora = view.findViewById(R.id.itemImeAutora);
        TextView brojKnjiga = view.findViewById(R.id.brojKnjiga);

        imeAutora.setText(a.getImeAutora());
        brojKnjiga.setText(Integer.toString(a.brojNapisanih));

        return view;
    }
}
