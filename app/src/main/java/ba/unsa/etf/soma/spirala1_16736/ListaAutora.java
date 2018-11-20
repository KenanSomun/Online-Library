package ba.unsa.etf.soma.spirala1_16736;

import java.util.ArrayList;

/**
 * Created by Kenan Somun on 14/04/2018.
 */

class ListaAutora {
    private ArrayList<Autor> autori = new ArrayList<>();

    public ListaAutora() {
        autori.add(new Autor("Ivo Andric", 2));
        autori.add(new Autor("Mesa Selimovic", 2));
        autori.add(new Autor("Alex Ferguson", 1));
        autori.add(new Autor("Pierluigi Collina", 1));
    }

    public ArrayList<Autor> getAutori() {
        return autori;
    }

    public void setAutori(ArrayList<Autor> autori) {
        this.autori = autori;
    }
}
