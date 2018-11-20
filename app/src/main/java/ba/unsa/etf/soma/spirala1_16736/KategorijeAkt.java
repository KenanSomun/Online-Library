package ba.unsa.etf.soma.spirala1_16736;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

public class KategorijeAkt extends FragmentActivity implements IHelp {

    public static ListaKnjiga listaKnjiga;
    public static ListaKategorija listaKategorija;
    public static ListaAutora listaAutora;
    private static final String[] CONTACTS_PERMISSION = {Manifest.permission.READ_CONTACTS};

    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorije_akt);

        listaKategorija = new ListaKategorija();
        listaKnjiga = new ListaKnjiga();
        listaAutora = new ListaAutora();

        verifyPermissions();

        fragmentManager = getSupportFragmentManager();

        ListeFragment listeFragment = new ListeFragment();
        fragmentManager.beginTransaction().
                replace(R.id.mjestoF1, listeFragment, "listeFragment")
                .commit();
    }

    @Override
    public void dodajKnjigu() {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("listaKategorija", listaKategorija.getKategorije());
        DodavanjeKnjigeFragment dodavanjeKnjigeFragment = new DodavanjeKnjigeFragment();
        dodavanjeKnjigeFragment.setArguments(bundle);
        fragmentManager.beginTransaction().
                replace(R.id.mjestoF1, dodavanjeKnjigeFragment, "dodavanjeKnjigeFragment").
                addToBackStack(null).commit();
    }

    @Override
    public void onItemClickKat(int position, Boolean vrstaAdaptera) {
        String k;
        Bundle bundle = new Bundle();
        if(vrstaAdaptera) {
            k = listaKategorija.getKategorije().get(position);
        } else {
            k = listaAutora.getAutori().get(position).getImeAutora();
        }
        bundle.putString("kategorija", k);
        bundle.putBoolean("izKategorija", vrstaAdaptera);
        KnjigeFragment knjigeFragment = new KnjigeFragment();
        knjigeFragment.setArguments(bundle);
        fragmentManager.beginTransaction().
                replace(R.id.mjestoF1, knjigeFragment, "knjigeFragment").
                addToBackStack(null).commit();
    }

    @Override
    public void povratak() {
        fragmentManager.popBackStack();
    }

    @Override
    public void dodajKnjiguOnline() {
        String k;
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("kategorije", listaKategorija.getKategorije());
        FragmentOnline fragmentOnline = new FragmentOnline();
        fragmentOnline.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.mjestoF1, fragmentOnline).addToBackStack(null).commit();
    }

    @Override
    public void onDodajOnline(String kategorija, Knjiga knjiga) {
        knjiga.setNazivAutora(knjiga.getAutori().get(0).getImeiPrezime());
        knjiga.setNazivKnjige(knjiga.getNaziv());

        knjiga.setKategorijaKnjige(kategorija);

        listaKnjiga.dodajKnjigu(knjiga);
    }

    @Override
    public void onPreporuci(Knjiga knjiga) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("preporucenaKnjiga", knjiga);
        FragmentPreporuci fragmentPreporuci = new FragmentPreporuci();
        fragmentPreporuci.setArguments(bundle);
        fragmentManager.beginTransaction().
                replace(R.id.mjestoF1, fragmentPreporuci, "fragmentPreporuci").
                addToBackStack(null).commit();
    }

    //https://www.youtube.com/watch?v=OywtFleIXnw
    private void verifyPermissions() {
        int permissionReadContacts = ActivityCompat.checkSelfPermission(KategorijeAkt.this, Manifest.permission.READ_CONTACTS);
        if (permissionReadContacts != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(KategorijeAkt.this, CONTACTS_PERMISSION, 1);
        }
    }
}
