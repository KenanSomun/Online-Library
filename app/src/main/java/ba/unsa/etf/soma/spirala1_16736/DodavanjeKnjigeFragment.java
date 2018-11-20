package ba.unsa.etf.soma.spirala1_16736;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

public class DodavanjeKnjigeFragment extends Fragment {

    String autor, knjiga, kat;
    private static final int PICK_IMAGE = 1;
    ArrayAdapter<String> SpinerAdapter;
    ImageView slikaKnjige;
    static String putDoSlike;

    IHelp iHelp;

    public DodavanjeKnjigeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dodavanje_knjige, container, false);

        final EditText autorKnjige = (EditText) view.findViewById(R.id.imeAutora);
        final EditText nazivKnjige = (EditText) view.findViewById(R.id.nazivKnjige);
        final Button izborSlike = (Button) view.findViewById(R.id.dNadjiSliku);
        slikaKnjige = (ImageView) view.findViewById(R.id.naslovnaStr);
        final Spinner izborKategorije = (Spinner) view.findViewById(R.id.sKategorijaKnjige);
        final Button ponistiUnos = (Button) view.findViewById(R.id.dPonisti);
        final Button potvrdiUnos = (Button) view.findViewById(R.id.dUpisiKnjigu);

        iHelp = (IHelp)getActivity();

        ArrayList<String> listaKategorija = getArguments().getStringArrayList("listaKategorija"); //////NAPOMENA
        SpinerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaKategorija);
        izborKategorije.setAdapter(SpinerAdapter);

        potvrdiUnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autor = autorKnjige.getText().toString();
                knjiga = nazivKnjige.getText().toString();
                kat = izborKategorije.getSelectedItem().toString();
                if (autor.equals("") || knjiga.equals(""))
                    // str_toast_unesi_naziv_autora_i_knjige
                    Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_unesi_naziv_autora_i_knjige), Toast.LENGTH_SHORT).show();
                else {
                    Knjiga k = new Knjiga();
                    k.setNazivAutora(autor);
                    k.setNazivKnjige(knjiga);
                    k.setKategorijaKnjige(kat);
                    k.sacuvajPutDoSlike(putDoSlike);
                    KategorijeAkt.listaKnjiga.dodajKnjigu(k);
                    // str_toast_knjiga_uspjesno_dodana
                    Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_knjiga_uspjesno_dodana), Toast.LENGTH_SHORT).show();
                    // očisti polja i sakrij tastaturu
                    hideKeyboard();
                    autorKnjige.setText("");
                    nazivKnjige.setText("");

                    Boolean test = false;
                    for(int i = 0; i < KategorijeAkt.listaAutora.getAutori().size(); i++){
                        if(KategorijeAkt.listaAutora.getAutori().get(i).imeAutora.equals(autor)){
                            KategorijeAkt.listaAutora.getAutori().get(i).brojNapisanih += 1;
                            test = true;
                        }
                    }
                    if(!test){
                        KategorijeAkt.listaAutora.getAutori().add(new Autor(autor));
                    }
                }
            }
        });

        ponistiUnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iHelp.povratak();
            }
        });

        izborSlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); // ACTION_OPEN_DOC works
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                //intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*"});
                // str_odaberi_sliku
                startActivityForResult(Intent.createChooser(intent, "Odaberi sliku"), PICK_IMAGE);

            }
        });

        return view;
    }

    public void hideKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            putDoSlike = targetUri.toString();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                slikaKnjige.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

}
