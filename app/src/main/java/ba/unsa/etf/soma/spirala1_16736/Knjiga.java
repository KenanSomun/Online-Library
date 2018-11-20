package ba.unsa.etf.soma.spirala1_16736;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Kenan Somun on 28/03/2018.
 */

public class Knjiga implements Parcelable {

    private String id;
    private String naziv;
    private ArrayList<Autor> autori = new ArrayList<>();
    private String opis;
    private String datumObjavljivanja;
    private URL slika;
    private int brojStranica;

    public Knjiga(String id, String naziv, ArrayList<Autor> autori, String opis, String datumObjavljivanja, URL slika, int brojStranica) {
        this.id = id;
        this.naziv = naziv;
        this.autori = autori;
        this.opis = opis;
        this.datumObjavljivanja = datumObjavljivanja;
        this.slika = slika;
        this.brojStranica = brojStranica;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ArrayList<Autor> getAutori() {
        return autori;
    }

    public void setAutori(ArrayList<Autor> autori) {
        this.autori = autori;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(String datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }

    public URL getSlika() {
        return slika;
    }

    public void setSlika(URL slika) {
        this.slika = slika;
    }

    public int getBrojStranica() {
        return brojStranica;
    }

    public void setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
    }

    private String nazivAutora, nazivKnjige, kategorijaKnjige, putDoSlike = "";
    private boolean oznaci = false;

    public Knjiga() {}

    public Knjiga(String _nazivAutora, String _nazivKnjige, String _kategorijaKnjige) {
        nazivAutora = _nazivAutora;
        nazivKnjige = _nazivKnjige;
        kategorijaKnjige = _kategorijaKnjige;
    }

    public String getNazivAutora() {
        return nazivAutora;
    }

    public void setNazivAutora(String nazivAutora) {
        this.nazivAutora = nazivAutora;
    }

    public String getNazivKnjige() {
        return nazivKnjige;
    }

    public void setNazivKnjige(String nazivKnjige) {
        this.nazivKnjige = nazivKnjige;
    }

    public String getKategorijaKnjige() {
        return kategorijaKnjige;
    }

    public void setKategorijaKnjige(String kategorijaKnjige) {
        this.kategorijaKnjige = kategorijaKnjige;
    }

    public String dajPutDoSlike() {
        return putDoSlike;
    }

    public void sacuvajPutDoSlike(String putDoSlike) {
        this.putDoSlike = putDoSlike;
    }

    public boolean isOznaci() {
        return oznaci;
    }

    public void setOznaci(boolean oznaci) {
        this.oznaci = oznaci;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(naziv);
        dest.writeString(nazivKnjige);
        dest.writeString(nazivAutora);
        dest.writeString(opis);
        dest.writeString(datumObjavljivanja);
        dest.writeString(kategorijaKnjige);
        dest.writeInt(brojStranica);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Knjiga(Parcel in) {
        naziv=in.readString();
        nazivKnjige=in.readString();
        nazivAutora=in.readString();
        autori.add(new Autor(nazivAutora));
        kategorijaKnjige=in.readString();
        brojStranica=in.readInt();
        opis=in.readString();
        datumObjavljivanja=in.readString();
        id=in.readString();
    }

    public static final Creator<Knjiga> CREATOR=new Creator<Knjiga>() {
        @Override
        public Knjiga createFromParcel(Parcel parcel) {
            return new Knjiga(parcel);
        }

        @Override
        public Knjiga[] newArray(int i) {
            return new Knjiga[i];
        }
    };

}
