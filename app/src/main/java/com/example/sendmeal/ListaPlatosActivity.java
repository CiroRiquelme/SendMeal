package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.sendmeal.Utilidades.PlatosRecyclerAdapter;
import com.example.sendmeal.domain.Plato;

import java.util.ArrayList;

public class ListaPlatosActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private  RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_platos);

        Toolbar myToolbar =  findViewById(R.id.toolbarBack);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
/*            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);*/
        }else
        {
            Toast.makeText(getApplicationContext(), "Es nulll.", Toast.LENGTH_SHORT).show();
        }


        mRecyclerView = findViewById(R.id.recyclerPlatos);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager((this));
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Plato> listaPlatos = new ArrayList<>();

        Plato p1 = new Plato(01, "Hamburguesa", "Completa", 100.00,1000);
        Plato p2 = new Plato(02, "Hamburguesa ", "Americana", 200.00,2000);
        Plato p3 = new Plato(03, "Hamburguesa", "Napolitana", 300.00,1000);
        Plato p4 = new Plato(04, "Hamburguesa ", "Doble Chedar", 400.00,2000);
        Plato p5 = new Plato(05, "Hamburguesa", "Simple", 500.00,1000);
        Plato p6 = new Plato(06, "Hamburguesa ", "Triple carne", 600.00,2000);
        Plato p7 = new Plato(07, "Hamburguesa", "Vegana", 700.00,1000);
        Plato p8 = new Plato(8, "Hamburguesa ", "Criolla", 800.00,2000);
        listaPlatos.add(p1);
        listaPlatos.add(p2);
        listaPlatos.add(p3);
        listaPlatos.add(p4);
        listaPlatos.add(p5);
        listaPlatos.add(p6);
        listaPlatos.add(p7);
        listaPlatos.add(p8);

        mAdapter = new PlatosRecyclerAdapter(listaPlatos);

        mRecyclerView.setAdapter(mAdapter);

    }
}
