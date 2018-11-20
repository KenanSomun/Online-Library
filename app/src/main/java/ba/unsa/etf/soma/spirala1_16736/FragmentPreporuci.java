package ba.unsa.etf.soma.spirala1_16736;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kenan Somun on 04/06/2018.
 */

public class FragmentPreporuci extends android.support.v4.app.Fragment {

    private Knjiga preporucenaKnjiga;
    private ArrayList<String> kontaktiImenika = new ArrayList<>();
    private ArrayList<String> mailoviKontakata = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    Spinner kontaktiSpiner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preporuci, container, false);

        preporucenaKnjiga = getArguments().getParcelable("preporucenaKnjiga");
        TextView nazivKnjige = (TextView) v.findViewById(R.id.eNaziv2);
        TextView autorKnjige = (TextView) v.findViewById(R.id.eAutor2);
        ImageView slikaKnjige = (ImageView) v.findViewById(R.id.eNaslovna2);
        TextView datumObjaveKnjige = (TextView) v.findViewById(R.id.eDatumObjavljivanja2);
        TextView brStranicaKnjige = (TextView) v.findViewById(R.id.eBrojStranica2);
        TextView opisKnjige = (TextView) v.findViewById(R.id.eOpis2);
        Button dugmePreporuci = (Button) v.findViewById(R.id.dPosalji);

        nazivKnjige.setText(preporucenaKnjiga.getNazivKnjige());
        autorKnjige.setText(preporucenaKnjiga.getNazivAutora());

        Picasso.with(getContext()).load(String.valueOf(preporucenaKnjiga.getSlika())).into(slikaKnjige);
        datumObjaveKnjige.setText("Datum objavljivanja: "+preporucenaKnjiga.getDatumObjavljivanja());
        String s = String.valueOf(preporucenaKnjiga.getBrojStranica());
        brStranicaKnjige.setText("Broj stranica: "+s);
        opisKnjige.setText("Opis: "+preporucenaKnjiga.getOpis());

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoadContacts();
        kontaktiSpiner = (Spinner) getView().findViewById(R.id.sKontakti);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mailoviKontakata);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kontaktiSpiner.setAdapter(adapter);

        Button posaljiMail = (Button) getView().findViewById(R.id.dPosalji);
        posaljiMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // provjera za prazan imenik
                Integer tmp = kontaktiSpiner.getCount();
                if(tmp == 0)
                    Toast.makeText(getActivity(), getResources().getString(R.string.str_toast_prazan_imenik), Toast.LENGTH_LONG).show();
                else {
                    SendEmail();
                }
            }
        });
    }

    // https://stackoverflow.com/questions/38180436/how-to-pick-a-contact-from-phone-book-in-android-to-spinner
    public void LoadContacts() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor c = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
            while(c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                while (cursor.moveToNext()) {
                    //String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    //contacts.add(contactName + ":" + phNumber);
                    String ime = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    if(email != null){
                        kontaktiImenika.add(ime);
                        mailoviKontakata.add(email);
                    }
                }
                cursor.close();
            }
        c.close();
    }

    //https://c2.etf.unsa.ba/pluginfile.php/53004/mod_resource/content/0/RMA-AndroidSlanjeEmailaOpis.pdf
    protected void SendEmail() {

        //Log.i("Send email", "");
        String odabraniKontakt = kontaktiSpiner.getSelectedItem().toString();
        String[] TO = {odabraniKontakt};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preporuka za knjigu");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Zdravo " + kontaktiImenika.get(kontaktiSpiner.getSelectedItemPosition())+", \nPročitaj knjigu " + preporucenaKnjiga.getNaziv() + " od " + preporucenaKnjiga.getAutori().get(0).getImeiPrezime()+"!");
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            //Log.i("Finished sending email", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), R.string.str_toast_no_email_app, Toast.LENGTH_SHORT).show();
        }
    }

}
