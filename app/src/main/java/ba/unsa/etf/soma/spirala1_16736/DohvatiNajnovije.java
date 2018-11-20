package ba.unsa.etf.soma.spirala1_16736;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Kenan Somun on 21/05/2018.
 */

public class DohvatiNajnovije extends AsyncTask<String, Integer, Void> {

    private ArrayList<Knjiga> listaKnjiga = new ArrayList<>();

    public interface IDohvatiNajnovijeDone {
        public void onNajnovijeDone(ArrayList<Knjiga> knjige);
    }

    private IDohvatiNajnovijeDone pozivatelj;
    public DohvatiNajnovije(IDohvatiNajnovijeDone p) { pozivatelj=p; }

    @Override
    protected Void doInBackground(String... params) {

        String[] naziv = params[0].split(":");

        String query = null;
        try {
            query = URLEncoder.encode(naziv[1], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url1 = "https://www.googleapis.com/books/v1/volumes?q=inauthor:"+query+"&maxResults=5&orderBy=newest";

        try {
            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);

            JSONObject jo = new JSONObject(rezultat);
            JSONArray items = jo.getJSONArray("items");
            for (int j = 0; j < items.length(); j++) {
                JSONObject book = items.getJSONObject(j);

                String id = null;
                try {
                    id = book.getString("id");
                } catch(JSONException e) {
                    id = "Nema id-a knjige*";
                }


                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                String nazivKnjige = null;
                try {
                    nazivKnjige = volumeInfo.getString("title");
                } catch(JSONException e) {
                    nazivKnjige = "Nema naziva knjige*";
                }

                ArrayList<Autor> autori = new ArrayList<>();

                JSONArray authors = null;
                try {
                    authors = volumeInfo.getJSONArray("authors");
                    for (int k = 0; k < authors.length(); k++) {
                        autori.add(new Autor(authors.getString(k), id));
                    }
                } catch(JSONException e) {
                    autori.add(new Autor("Nema autora knjige*", id));
                }

                String opis = null;
                try {
                    opis = volumeInfo.getString("description");
                } catch(JSONException e) {
                    opis = "Nema opisa knjige";
                }

                String datumObjavljivanja = null;
                try {
                    datumObjavljivanja = volumeInfo.getString("publishedDate");
                } catch(JSONException e) {
                    datumObjavljivanja = "Nema datuma objave knjige*";
                }

                JSONObject image = null;
                String urlSlika = null;
                try {
                    image = volumeInfo.getJSONObject("imageLinks");
                    urlSlika = image.getString("smallThumbnail");
                } catch(JSONException e) {
                    //Hardkodirana slika: book cover not available
                    urlSlika = "http://i.imgur.com/J5LVHEL.jpg";
                }
                URL slika = new URL(urlSlika);

                int brojStranica;
                try {
                    brojStranica = volumeInfo.getInt("pageCount");
                } catch(JSONException e) {
                    brojStranica = 0;
                }

                listaKnjiga.add(new Knjiga(id,nazivKnjige,autori,opis,datumObjavljivanja,slika,brojStranica));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        pozivatelj.onNajnovijeDone(listaKnjiga);
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line+"\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }
}
