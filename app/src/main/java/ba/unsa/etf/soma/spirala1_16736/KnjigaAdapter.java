package ba.unsa.etf.soma.spirala1_16736;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kenan Somun on 28/03/2018.
 */

public class KnjigaAdapter extends ArrayAdapter<Knjiga> {
    int resource;
    Context context;
    IHelp iHelp;

    public KnjigaAdapter(Context context, int _resource, List<Knjiga> items) {
        super(context, _resource, items);
        resource = _resource;
        this.context = context;
    }

    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(getContext());
        final Knjiga k = getItem(position);

        View view = null;
        if (convertView == null)
            view = li.inflate(R.layout.element_lista_knjiga, null);
        else
            view = convertView;

        TextView nazivKnjige = (TextView) view.findViewById(R.id.eNaziv);
        TextView autorKnjige = (TextView) view.findViewById(R.id.eAutor);
        ImageView slikaKnjige = (ImageView) view.findViewById(R.id.eNaslovna);
        // Nova polja
        TextView datumObjaveKnjige = (TextView) view.findViewById(R.id.eDatumObjavljivanja);
        TextView brStranicaKnjige = (TextView) view.findViewById(R.id.eBrojStranica);
        TextView opisKnjige = (TextView) view.findViewById(R.id.eOpis);
        Button dugmePreporuci = (Button) view.findViewById(R.id.dPreporuci);

        nazivKnjige.setText(k.getNazivKnjige());
        autorKnjige.setText(k.getNazivAutora());

        Picasso.with(getContext()).load(String.valueOf(k.getSlika())).into(slikaKnjige);
        datumObjaveKnjige.setText("Datum objavljivanja: "+k.getDatumObjavljivanja());
        String s = String.valueOf(k.getBrojStranica());
        brStranicaKnjige.setText("Broj stranica: "+s);
        opisKnjige.setText("Opis:\n "+k.getOpis());

        try {
            iHelp = (IHelp) context;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(context.toString());
        }

        dugmePreporuci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iHelp.onPreporuci(k);
            }
        });

        /*
        if(k.dajPutDoSlike() != null && !k.dajPutDoSlike().isEmpty()) try {
            slikaKnjige.setImageURI(Uri.parse(k.dajPutDoSlike()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        else slikaKnjige.setImageResource(android.R.drawable.star_on); */

        if (k.isOznaci()) {
            view.setBackgroundColor(context.getResources().getColor(R.color.obojeniElement));
            // postavi sliku opet, iz nekog razloga je "zaboravi"
            Picasso.with(getContext()).load(String.valueOf(k.getSlika())).into(slikaKnjige);
        }

        return view;
    }
}
