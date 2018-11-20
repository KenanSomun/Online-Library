package ba.unsa.etf.soma.spirala1_16736;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListaKnjigaAkt extends AppCompatActivity {

    KnjigaAdapter adapter;
    ListView mojaListaKnjiga;
    Button povratak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_knjiga_akt);


    }
        // može se brisati sada
}
