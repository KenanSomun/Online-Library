package ba.unsa.etf.soma.spirala1_16736;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Kenan Somun on 28/03/2018.
 */

public class ListaKategorija {

    private ArrayList<String> kategorije;

    public ListaKategorija() {
        kategorije = new ArrayList<String>();
        // hardkodiranje podataka za prikaz i testiranje
        kategorije.add("Roman");
        kategorije.add("Sport");
        kategorije.add("Strip");
    }

    public ArrayList<String> getKategorije() {
        return kategorije;
    }

    public void setKategorije(ArrayList<String> kategorije) {
        this.kategorije = kategorije;
    }

    public void dodajKategoriju(String s) {
        kategorije.add(s);
    }
}
