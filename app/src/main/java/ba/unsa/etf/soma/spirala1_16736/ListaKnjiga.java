package ba.unsa.etf.soma.spirala1_16736;

import java.util.ArrayList;

/**
 * Created by Kenan Somun on 28/03/2018.
 */

public class ListaKnjiga {

    private ArrayList<Knjiga> knjige;

    public ListaKnjiga() {
        knjige = new ArrayList<Knjiga>();
        // hardkodiranje podataka za prikaz i testiranje
        /*
        knjige.add(new Knjiga("Ivo Andric", "Znakovi pokraj puta", "Roman"));
        knjige.add(new Knjiga("Ivo Andric", "Prokleta avlija", "Roman"));
        knjige.add(new Knjiga("Mesa Selimovic", "Tisine", "Roman"));
        knjige.add(new Knjiga("Mesa Selimovic", "Tvrdjava", "Roman"));
        knjige.add(new Knjiga("Alex Ferguson", "My Autobiography", "Sport"));
        knjige.add(new Knjiga("Pierluigi Collina", "The Rules of the Game", "Sport")); */
    }


    public ArrayList<Knjiga> getKnjige() {
        return knjige;
    }

    public void setKnjige(ArrayList<Knjiga> knjige) {
        this.knjige = knjige;
    }

    public void dodajKnjigu(Knjiga k) {
        knjige.add(k);
    }

    public void oznaciKnjiguZaBojenje(int i) {
        knjige.get(i).setOznaci(true);
    }

    public int duzinaListe() {
        return knjige.size();
    }

    public Knjiga knjigaNaPoziciji(int i) {
        return knjige.get(i);
    }
}
