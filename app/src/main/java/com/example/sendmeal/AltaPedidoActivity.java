package com.example.sendmeal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.sendmeal.dao.db.DBClient;
import com.example.sendmeal.dao.db.PedidoDao;
import com.example.sendmeal.domain.EstadoPedido;
import com.example.sendmeal.domain.ItemsPedido;
import com.example.sendmeal.domain.Pedido;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.sendmeal.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AltaPedidoActivity extends AppCompatActivity {

    Pedido pedidoActual;

    TextInputEditText etLat;
    TextInputEditText etLng;

    MaterialButton btnCrear;
    MaterialButton btnEnviar;
    MaterialButton btnAgregar;
    List<ItemsPedido> itemsPedidos = new ArrayList<>();

    Double lat;
    Double lng;
    Date fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_pedido);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarBack);
        myToolbar.setTitle("Pedidos");
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        etLat = findViewById(R.id.etPedidoLat);
        etLng = findViewById(R.id.etPedidoLng);

        btnCrear = findViewById(R.id.btnCrearPedido);
        btnEnviar = findViewById(R.id.btnEnvarPedido);
        btnAgregar = findViewById(R.id.buttonAgregarItem);


        btnCrear.setOnClickListener(btnCrearPedido);
        btnEnviar.setOnClickListener(btnEnviarPedido);
        btnAgregar.setOnClickListener(btnAgregarListener);

        btnEnviar.setEnabled(false);


    }

    MaterialButton.OnClickListener btnAgregarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent crearItem = new Intent(AltaPedidoActivity.this, CrearItemPedidoActivity.class);
            startActivityForResult(crearItem,1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                // Do something with the contact here (bigger example below)
                Bundle extras = data.getExtras();
                List<ItemsPedido> list = (List<ItemsPedido>)extras.get("lista");
                itemsPedidos.addAll(list);
                etLng.setText(itemsPedidos.size());
                Snackbar.make(btnCrear, "n = " + itemsPedidos.size(),Snackbar.LENGTH_LONG).show();
            }
        }
    }


    private MaterialButton.OnClickListener btnCrearPedido = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(pedidoActual==null){
                pedidoActual = new Pedido();
            }
            lat = Double.valueOf(etLat.getText().toString());
            lng = Double.valueOf(etLng.getText().toString());


            pedidoActual.setLat(lat);
            pedidoActual.setLng(lng);
            pedidoActual.setEstado(EstadoPedido.PENDIENTE);

            GuardarPedido tareaGuardarPedido = new GuardarPedido();
            tareaGuardarPedido.execute(pedidoActual);

            bloquearCampos();

            Snackbar.make(btnCrear, "Creando Pedido",Snackbar.LENGTH_LONG).show();

        }
    };

    private MaterialButton.OnClickListener btnEnviarPedido = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    class GuardarPedido extends AsyncTask<Pedido, Void, Void> {

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            PedidoDao dao = DBClient.getInstance(AltaPedidoActivity.this).getPedidosDB().pedidoDao();
            if(pedidos[0].getId() != null && pedidos[0].getId() >0) {
                dao.actualizar(pedidos[0]);
            }else {
                dao.insert(pedidos[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Snackbar.make(btnCrear, "Pedido creado con exito",Snackbar.LENGTH_LONG).show();
/*            pedidoActual = null;
            Intent i = new Intent(AltaPedidoActivity.this, ListaPlatosActivity.class);
            startActivity(i)*/;
        }
    }

    private void limpiarCampos(){

    }
    private void bloquearCampos(){
        etLng.setEnabled(false);
        etLat.setEnabled(false);
        btnCrear.setEnabled(false);
        btnEnviar.setEnabled(true);
    }

}

