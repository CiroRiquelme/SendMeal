package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.example.sendmeal.Utilidades.PlatosRecyclerAdapter;
import com.example.sendmeal.dao.PlatoRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class BusquedaDePlatosActivity extends AppCompatActivity {

    TextInputEditText etNombre;
    TextInputEditText etPrecioMin;
    TextInputEditText etPrecioMax;

    MaterialButton btnGuardar;
    MaterialButton btnCancelar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_de_platos);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarBack);
        myToolbar.setTitle("Busqueda de platos");
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);*/
        }

        etNombre = findViewById(R.id.etPlatoNombreBuscar);
        etPrecioMin = findViewById(R.id.etPlatoPrecioMin);
        etPrecioMax = findViewById(R.id.etPlatoPrecioMax);

        btnCancelar = findViewById(R.id.btnCancelarBusqueda);
        btnGuardar = findViewById(R.id.btnBuscar);

        btnGuardar.setOnClickListener(btnGuardarListener);

        // Creacion del recycler view
        mRecyclerView = findViewById(R.id.recyclerPlatos);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager((this));
        mRecyclerView.setLayoutManager(mLayoutManager);

        PlatoRepository.getInstance().listarPlato(miHandler);
        mAdapter = new PlatosRecyclerAdapter(PlatoRepository.getInstance().getListaPlatos());
        mRecyclerView.setAdapter(mAdapter);
    }

    MaterialButton.OnClickListener btnGuardarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String nombre = etNombre.getText().toString().trim();
            String precioMins = etPrecioMin.getText().toString().trim();
            String precioMaxs = etPrecioMax.getText().toString().trim();

            Integer precioMin=0;
            Integer precioMax=1000;

            if(!precioMins.isEmpty()){
                precioMin = Integer.valueOf(precioMins);
            }
            if(!precioMaxs.isEmpty()){
                precioMax = Integer.valueOf(precioMaxs);
            }



            // Faltan validaciones para que no salten excepciones.
            //Faltan validar las distintas posibilidades de busqueda.

//            PlatoRepository.getInstance().listarPlato(miHandler);
//            PlatoRepository.getInstance().buscarPlatoPorNombre(nombre,miHandler);
            PlatoRepository.getInstance().buscarPlatoPorPrecio(precioMin,precioMax,miHandler);


        }
    };

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1 ){
                case PlatoRepository._CONSULTA_PLATO: {
                    mAdapter.notifyDataSetChanged();
                    Snackbar.make(btnGuardar, "En el h " + PlatoRepository.getInstance().getListaPlatos().size(),Snackbar.LENGTH_LONG).show();
                    break;
                }
                case PlatoRepository._UPDATE_PLATO: {
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    };
}
