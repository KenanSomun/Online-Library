package ba.unsa.etf.soma.spirala1_16736;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class DodavanjeKnjigeAkt extends FragmentActivity {

    String autor, knjiga, kat;
    private static final int PICK_IMAGE = 1;
    ArrayAdapter<String> SpinerAdapter;
    ImageView slikaKnjige;
    static String putDoSlike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodavanje_knjige_akt);



        /*
        Staticka k = ((Staticka)getApplicationContext());
        Log.e("Korak 2",k.getNesto());

        k.setNesto("hello2");
        Log.e("Korak 3",k.getNesto());
        */



    }

    // moiže se delete sada

}


