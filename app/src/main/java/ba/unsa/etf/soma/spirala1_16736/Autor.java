package ba.unsa.etf.soma.spirala1_16736;

import java.util.ArrayList;

/**
 * Created by Kenan Somun on 14/04/2018.
 */

class Autor {
    String imeAutora;
    int brojNapisanih;

    private String imeiPrezime;
    ArrayList<String> knjige = new ArrayList<>();

    public void dodajKnjigu(String id) {
        knjige.add(id);
        if(brojNapisanih != 1)
            brojNapisanih++;
    }

    public Autor(String imeiPrezime, ArrayList<String> knjige) {
        this.imeiPrezime = imeiPrezime;
        this.knjige = knjige;
    }

    public Autor(String imeiPrezime, String idKnjige) {
        this.imeiPrezime=imeiPrezime;
        knjige.add(idKnjige);
        brojNapisanih=1;
    }

    public String getImeiPrezime() {
        return imeiPrezime;
    }

    public void setImeiPrezime(String imeiPrezime) {
        this.imeiPrezime = imeiPrezime;
    }

    public ArrayList<String> getKnjige() {
        return knjige;
    }

    public void setKnjige(ArrayList<String> knjige) {
        this.knjige = knjige;
    }

    public Autor(String imeAutora) {
        this.imeAutora = imeAutora;
        brojNapisanih = 1;
    }

    public Autor(String imeAutora, int brojNapisanih) {
        this.imeAutora = imeAutora;
        this.brojNapisanih = brojNapisanih;
    }

    public String getImeAutora() {
        return imeAutora;
    }

    public void setImeAutora(String imeAutora) {
        this.imeAutora = imeAutora;
    }

    public int getBrojNapisanih() {
        return brojNapisanih;
    }

    public void setBrojNapisanih(int brojNapisanih) {
        this.brojNapisanih = brojNapisanih;
    }
}
