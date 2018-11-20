package ba.unsa.etf.soma.spirala1_16736;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class KnjigeFragment extends Fragment {

    public KnjigeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    KnjigaAdapter adapter;
    ListView mojaListaKnjiga;
    Button povratak;

    IHelp iHelp;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_knjige, container, false);

        povratak = (Button) view.findViewById(R.id.dPovratak);
        mojaListaKnjiga = (ListView) view.findViewById(R.id.listaKnjiga);

        iHelp = (IHelp)getActivity();

        String odabranaKat = getArguments().getString("kategorija"); ///////NAPOMENA
        Boolean izKategorija = getArguments().getBoolean("izKategorija");
        final ArrayList<Knjiga> listaKnjigaZaPrikazati = new ArrayList<>();
        final ArrayList<Knjiga> listaKnjigaZaPretragu = KategorijeAkt.listaKnjiga.getKnjige();
        for(Knjiga k : listaKnjigaZaPretragu) {
            if(izKategorija) {
                if (k.getKategorijaKnjige().equalsIgnoreCase(odabranaKat))
                    listaKnjigaZaPrikazati.add(k);
            } else {
                if(k.getNazivAutora().equals(odabranaKat))
                    listaKnjigaZaPrikazati.add(k);
            }
        }

        if(listaKnjigaZaPrikazati.isEmpty())
            Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_nema_knjiga), Toast.LENGTH_LONG).show();

        mojaListaKnjiga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < KategorijeAkt.listaKnjiga.duzinaListe(); i++)
                    if(Objects.equals(KategorijeAkt.listaKnjiga.knjigaNaPoziciji(i).getNazivKnjige(), listaKnjigaZaPrikazati.get(position).getNazivKnjige())
                            && Objects.equals(KategorijeAkt.listaKnjiga.knjigaNaPoziciji(i).getNazivAutora(), listaKnjigaZaPrikazati.get(position).getNazivAutora())) {
                        KategorijeAkt.listaKnjiga.oznaciKnjiguZaBojenje(i);
                        adapter.notifyDataSetChanged();
                    }
            }
        });

        adapter = new KnjigaAdapter(getActivity(), R.layout.element_lista_knjiga, listaKnjigaZaPrikazati);
        mojaListaKnjiga.setAdapter(adapter);

        povratak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iHelp.povratak();
            }
        });

        return view;
    }
}
