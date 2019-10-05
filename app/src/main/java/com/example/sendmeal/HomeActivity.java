package com.example.sendmeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sendmeal.domain.Plato;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    Toolbar myToolbar;

    public static List<Plato> LISTA_PLATOS = new ArrayList<>();
    Plato p1 = new Plato(01, "Hamburguesa", "Completa", 100.00,1000);
    Plato p2 = new Plato(02, "Hamburguesa ", "Americana", 200.00,2000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);

        LISTA_PLATOS.add(p1);
        LISTA_PLATOS.add(p2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_crearItem:

                Intent altaPlatos = new Intent(this, AltaPlatosActivity.class);
                startActivity(altaPlatos);
                return true;

            case R.id.home_registrar:

                Intent registrarUsuario = new Intent(this, MainActivity.class);
                startActivity(registrarUsuario);
                return true;

            case R.id.home_listaItem:

                Intent listaPlatos = new Intent(this, ListaPlatosActivity.class);
                startActivity(listaPlatos);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
