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

import com.example.sendmeal.Utilidades.PlatosRecyclerAdapter2;
import com.example.sendmeal.dao.PlatoRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class CrearItemPedidoActivity extends AppCompatActivity {

    TextInputEditText etNombre;
    TextInputEditText etPrecioMin;
    TextInputEditText etPrecioMax;

    MaterialButton btnBuscar;
    MaterialButton btnCancelar;
    MaterialButton btnAceptar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_de_platos);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarBack);
//        myToolbar.setTitle("Busqueda de platos");
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
        btnBuscar = findViewById(R.id.btnBuscar);
        btnAceptar = findViewById(R.id.btnAceptar);

        btnBuscar.setOnClickListener(btnBuscarListener);
        btnCancelar.setOnClickListener(btnCancelarListener);
        btnAceptar.setOnClickListener(btnAceptarListener);

        // Creacion del recycler view
        mRecyclerView = findViewById(R.id.recyclerPlatos);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager((this));
        mRecyclerView.setLayoutManager(mLayoutManager);

        PlatoRepository.getInstance().listarPlato(miHandler);
        mAdapter = new PlatosRecyclerAdapter2(PlatoRepository.getInstance().getListaPlatos());
        mRecyclerView.setAdapter(mAdapter);




    }

    private MaterialButton.OnClickListener btnCancelarListener = v -> finish();

    MaterialButton.OnClickListener btnBuscarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String nombre = etNombre.getText().toString().trim();
            String precioMins = etPrecioMin.getText().toString().trim();
            String precioMaxs = etPrecioMax.getText().toString().trim();

            Integer precioMin;
            Integer precioMax;

            if(nombre.isEmpty()){
                if(precioMins.isEmpty()){
                    if (precioMaxs.isEmpty()) {
                        // todos vacios -> todos los platos
                        PlatoRepository.getInstance().listarPlato(miHandler);
                    }else{
                        //Solo por precio maximo.
                    }
                }else{
                    // nombre vacio -> precio minimo
                    if (precioMaxs.isEmpty()) {
                        //Solo precio minimo
                    }else{
                        // solo precio minimo y precio maximo
                        precioMin = Integer.valueOf(precioMins);
                        precioMax = Integer.valueOf(precioMaxs);
                        PlatoRepository.getInstance().buscarPlatoPorPrecio(precioMin,precioMax,miHandler);
                    }
                }
            }else{
                // nombre no vacio
                if(precioMins.isEmpty()){

                    if(precioMaxs.isEmpty()){
                        //Solo nombre
                        PlatoRepository.getInstance().buscarPlatoPorNombre(nombre,miHandler);
                    }else{
                        // Por nombre y por precio maximo.
                    }
                }else{
                    if(precioMaxs.isEmpty()){
                        // Por nombre y precio minimo
                    }else{
                        // Por nombre , precio minimo y precio maximo.
                    }
                }

            }
        }
    };

    MaterialButton.OnClickListener btnAceptarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(10);
            finish();
        }
    };

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1 ){
                case PlatoRepository._CONSULTA_PLATO: {
                    mAdapter.notifyDataSetChanged();
                    Snackbar.make(btnBuscar, "En el h " +" " + + PlatoRepository.getInstance().getListaPlatos().size(),Snackbar.LENGTH_LONG).show();
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

