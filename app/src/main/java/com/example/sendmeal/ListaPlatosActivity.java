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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.sendmeal.Utilidades.MyReceiver;
import com.example.sendmeal.Utilidades.PlatosRecyclerAdapter;
import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.Plato;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ListaPlatosActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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


        //Aca van los datos a mostrar
        // mAdapter = new PlatosRecyclerAdapter(HomeActivity.LISTA_PLATOS);

        PlatoRepository.getInstance().listarPlato(miHandler);



        BroadcastReceiver br = new MyReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(MyReceiver.EVENTO_01);
        getApplication().getApplicationContext().registerReceiver(br,filtro);


        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        mAdapter.notifyDataSetChanged();
    }

    public Handler getMiHandler() {
        return miHandler;
    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1 ){
                case PlatoRepository._CONSULTA_PLATO:
                    mAdapter = new PlatosRecyclerAdapter(PlatoRepository.getInstance().getListaPlatos());
                    mRecyclerView.setAdapter(mAdapter);
                    break;
                case PlatoRepository._BORRADO_PLATO:
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };



}
