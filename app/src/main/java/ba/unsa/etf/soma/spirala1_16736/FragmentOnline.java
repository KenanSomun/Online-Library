package ba.unsa.etf.soma.spirala1_16736;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Kenan Somun on 21/05/2018.
 */

public class FragmentOnline extends Fragment implements DohvatiKnjige.IDohvatiKnjigeDone, DohvatiNajnovije.IDohvatiNajnovijeDone, KnjigeReceiver.Receiver {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ArrayList<String> kategorije;
    private ArrayAdapter<String> adapter;
    private ListaKnjiga listaKnjiga = new ListaKnjiga();
    Spinner spinner;
    IHelp iHelp;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online,container,false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final Spinner spin = (Spinner) view.findViewById(R.id.sKategorije);
        spinner = (Spinner) view.findViewById(R.id.sRezultat);

        if (getArguments().containsKey("kategorije")) {
            kategorije = getArguments().getStringArrayList("kategorije");
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, kategorije);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(adapter);
        }

        Button povratak = (Button) view.findViewById(R.id.dPovratak);

        povratak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        Button pretraga = (Button) view.findViewById(R.id.dRun);
        final EditText tekst = (EditText) view.findViewById(R.id.tekstUpit);

        pretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tekst.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_tekst_pretrage), Toast.LENGTH_SHORT).show();
                    tekst.requestFocus();
                    return;
                }

                if (tekst.getText().toString().contains("autor:") || tekst.getText().toString().contains("Autor:")) {
                    new DohvatiNajnovije(FragmentOnline.this).execute(tekst.getText().toString());
                }
                else if (tekst.getText().toString().contains("korisnik:") || tekst.getText().toString().contains("Korisnik:")) {
                    Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), KnjigePoznanika.class);
                    KnjigeReceiver receiver = new KnjigeReceiver(new Handler());
                    receiver.setReceiver(FragmentOnline.this);

                    intent.putExtra("idKorisnika", tekst.getText().toString().substring(9));
                    intent.putExtra("receiver", receiver);

                    getActivity().startService(intent);
                } else {
                    new DohvatiKnjige(FragmentOnline.this).execute(tekst.getText().toString());
                }
                hideKeyboard();
            }
        });

        Button dodaj = (Button) view.findViewById(R.id.dAdd);

        try {
            iHelp = (IHelp) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner != null && spinner.getSelectedItem() != null) {
                    for (Knjiga knjiga : listaKnjiga.getKnjige()) {
                        if (knjiga.getNaziv().equals(spinner.getSelectedItem().toString())) {
                            iHelp.onDodajOnline(spin.getSelectedItem().toString(), knjiga);
                        }
                    }
                    // str_toast_knjiga_uspjesno_dodana
                    Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_knjiga_uspjesno_dodana), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_bez_pretrage), Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onDohvatiDone(ArrayList<Knjiga> knjige) {
        listaKnjiga.setKnjige(knjige);

        ArrayList<String> naziviKnjiga = new ArrayList<>();
        for(Knjiga knjiga : knjige) {
            naziviKnjiga.add(knjiga.getNaziv());
        }

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, naziviKnjiga);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onNajnovijeDone(ArrayList<Knjiga> knjige) {
        listaKnjiga.setKnjige(knjige);

        ArrayList<String> naziviKnjiga = new ArrayList<>();
        for(Knjiga knjiga : knjige) {
            naziviKnjiga.add(knjiga.getNaziv());
        }

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, naziviKnjiga);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == 0) {
            //Toast.makeText(getActivity(), "Servis pokrenut", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_servis_pokrenut), Toast.LENGTH_SHORT).show();
        }
        else if(resultCode == 1) {
            ArrayList<Knjiga> knjige;
            knjige = resultData.getParcelableArrayList("listaKnjiga");
            listaKnjiga.setKnjige(knjige);
            ArrayList<String> naziviKnjiga = new ArrayList<>();
            for(Knjiga knjiga : knjige) {
                naziviKnjiga.add(knjiga.getNaziv());
            }

            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, naziviKnjiga);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
        else {
            Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_greska), Toast.LENGTH_SHORT).show();
        }
    }

    public void hideKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}
