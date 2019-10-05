package com.example.sendmeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sendmeal.Utilidades.MyReceiver;
import com.example.sendmeal.Utilidades.PlatosRecyclerAdapter;
import com.example.sendmeal.domain.Plato;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ListaPlatosActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_platos);

        Toolbar myToolbar =  findViewById(R.id.toolbarBack);
        myToolbar.setTitle("Lista de platos");
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);*/
        }else
        {
            Toast.makeText(getApplicationContext(), "Es null.", Toast.LENGTH_SHORT).show();
        }

        // Creacion del recycler view
        mRecyclerView = findViewById(R.id.recyclerPlatos);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager((this));
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PlatosRecyclerAdapter(HomeActivity.LISTA_PLATOS);
        mRecyclerView.setAdapter(mAdapter);

        BroadcastReceiver br = new MyReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(MyReceiver.EVENTO_01);
        getApplication().getApplicationContext().registerReceiver(br,filtro);


        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        mAdapter.notifyDataSetChanged();
    }


    //Cree un metodo static para poder actualizar la lista
    public static void actualizarLista(){
        mAdapter.notifyDataSetChanged();
    }



};
