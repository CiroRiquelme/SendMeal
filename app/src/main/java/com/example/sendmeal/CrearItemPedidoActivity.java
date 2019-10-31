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
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sendmeal.Utilidades.PlatosRecyclerAdapter;
import com.example.sendmeal.Utilidades.PlatosRecyclerAdapter2;
import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.ItemsPedido;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrearItemPedidoActivity extends AppCompatActivity {

    TextInputEditText etNombre;
    TextInputEditText etPrecioMin;
    TextInputEditText etPrecioMax;

    MaterialButton btnGuardar;
    MaterialButton btnCancelar;
    MaterialButton btnAceptar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<ItemsPedido> itemsPedidos = new ArrayList<>();

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
        btnGuardar = findViewById(R.id.btnBuscar);
        btnAceptar = findViewById(R.id.btnAceptar);

        btnGuardar.setOnClickListener(btnGuardarListener);
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

    private MaterialButton.OnClickListener btnCancelarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

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

    MaterialButton.OnClickListener btnAceptarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    };

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1 ){
                case PlatoRepository._CONSULTA_PLATO: {
                    mAdapter.notifyDataSetChanged();
                    Snackbar.make(btnGuardar, "En el h " +itemsPedidos.size()+" " + + PlatoRepository.getInstance().getListaPlatos().size(),Snackbar.LENGTH_LONG).show();
                    break;
                }
                case PlatoRepository._UPDATE_PLATO: {
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    };

    public void addItem(ItemsPedido ip){
        itemsPedidos.add(ip);
    }


}

