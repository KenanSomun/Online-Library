package ba.unsa.etf.soma.spirala1_16736;

/**
 * Created by Kenan Somun on 14/04/2018.
 */

public interface IHelp {
    void dodajKnjigu();
    void onItemClickKat(int position, Boolean vrstaAdaptera);
    void povratak();
    void dodajKnjiguOnline();
    void onDodajOnline(String kategorija, Knjiga knjiga);
    void onPreporuci(Knjiga k);
}
