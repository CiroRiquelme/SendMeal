package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.sendmeal.Utilidades.PlatosRecyclerAdapter2;
import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.dao.db.DBClient;
import com.example.sendmeal.dao.db.ItemsPedidoDao;
import com.example.sendmeal.dao.db.PedidoDao;
import com.example.sendmeal.domain.ItemsPedido;
import com.example.sendmeal.domain.Pedido;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

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

    private static ArrayList<ItemsPedido> listaItemsPedido = new ArrayList<>();


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
            listaItemsPedido =PlatosRecyclerAdapter2.getListaItemsPedido();
            GuardarItemsPedido guardarItemsPedido = new GuardarItemsPedido();
            guardarItemsPedido.execute(listaItemsPedido);

/*            setResult(10);
            finish();*/
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


    class GuardarItemsPedido extends AsyncTask<List<ItemsPedido>, Void, Void> {

        @Override
        protected Void doInBackground(List<ItemsPedido>... listaItems) {

            ItemsPedidoDao itemsPedidoDao = DBClient.getInstance(CrearItemPedidoActivity.this).getPedidosDB().itemsPedidoDao();
            PedidoDao pedidoDao = DBClient.getInstance(CrearItemPedidoActivity.this).getPedidosDB().pedidoDao();
            List<Pedido> pedidos = pedidoDao.getAll();
            Integer id = pedidos.size();

            for (ItemsPedido i : listaItems[0]){
                i.setIdPedido(id);
            }
            itemsPedidoDao.insertAllList(listaItems[0]);
            return  null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("ITEM","Se creo el item");
            PlatosRecyclerAdapter2.getListaItemsPedido().clear();
            setResult(10);
            finish();

        }
    }




}

