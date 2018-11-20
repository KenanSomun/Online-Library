package ba.unsa.etf.soma.spirala1_16736;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;

public class ListeFragment extends Fragment {

    ArrayAdapter<String> adapterKategorija;
    AutoriAdapter autoriAdapter;
    IHelp iHelp;
    Boolean vrstaAdaptera = true;

    public ListeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste, container, false);

        final Button pretraga = (Button) view.findViewById(R.id.dPretraga);
        final Button dodajKategoriju = (Button) view.findViewById(R.id.dDodajKategoriju);
        final Button dodajKnjigu = (Button) view.findViewById(R.id.dDodajKnjigu);
        final Button kategorije = view.findViewById(R.id.dKategorije);
        final Button autori = view.findViewById(R.id.dAutori);
        final EditText tekstPretraga = (EditText) view.findViewById(R.id.tekstPretraga);
        final ListView listaKat = (ListView) view.findViewById(R.id.listaKategorija);
        final Button dodajOnline=(Button)view.findViewById(R.id.dDodajOnline);

        iHelp = (IHelp)getActivity();

        dodajKategoriju.setEnabled(false);

        adapterKategorija = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, KategorijeAkt.listaKategorija.getKategorije());
        listaKat.setAdapter(adapterKategorija);

        autoriAdapter = new AutoriAdapter(getActivity(), R.layout.element_lista_autor, KategorijeAkt.listaAutora.getAutori());

        dodajOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iHelp.dodajKnjiguOnline();
            }
        });

        kategorije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tekstPretraga.setVisibility(View.VISIBLE);
                pretraga.setVisibility(View.VISIBLE);
                dodajKategoriju.setVisibility(View.VISIBLE);
                listaKat.setAdapter(adapterKategorija);
                vrstaAdaptera = true;
            }
        });

        autori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tekstPretraga.setVisibility(View.GONE);
                pretraga.setVisibility(View.GONE);
                dodajKategoriju.setVisibility(View.GONE);
                listaKat.setAdapter(autoriAdapter);
                vrstaAdaptera = false;
            }
        });

        dodajKategoriju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tekstPretraga.getText().toString();
                KategorijeAkt.listaKategorija.dodajKategoriju(str);
                adapterKategorija.add(str);
                adapterKategorija.notifyDataSetChanged();
                tekstPretraga.setText("");
                adapterKategorija.getFilter().filter(""); // da vrati na prikaz cijele liste
                dodajKategoriju.setEnabled(false);
            }
        });

        pretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterKategorija.getFilter().filter(tekstPretraga.getText().toString(), new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        if (count == 0)
                            dodajKategoriju.setEnabled(true);
                        else
                            dodajKategoriju.setEnabled(false);
                    }
                });
            }
        });

        dodajKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iHelp.dodajKnjigu();
            }
        });

        listaKat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iHelp.onItemClickKat(position, vrstaAdaptera);
            }
        });



        return view;
    }
}
