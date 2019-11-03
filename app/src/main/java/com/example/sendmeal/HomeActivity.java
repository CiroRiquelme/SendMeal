package com.example.sendmeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sendmeal.dao.db.DBClient;
import com.example.sendmeal.dao.db.PedidoDao;
import com.example.sendmeal.domain.Pedido;
import com.example.sendmeal.domain.Plato;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    Toolbar myToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);


        //Para probar la db
/*        PedidoDao daoPedido= DBClient.getInstance(this).getPedidosDB().pedidoDao();
        Pedido p1 = new Pedido(1,10.0,10.0);
        daoPedido.insert(p1);*/



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_crearItemPlato:

                Intent altaPlatos = new Intent(this, AltaPlatosActivity.class);
                startActivity(altaPlatos);
                return true;

            case R.id.home_registrar:

                Intent registrarUsuario = new Intent(this, RegistroUsuarioActivity.class);
                startActivity(registrarUsuario);
                return true;

            case R.id.home_listaItem:

                Intent listaPlatos = new Intent(this, ListaPlatosActivity.class);
                startActivity(listaPlatos);
                return true;

            case R.id.home_buscarItem:
                Intent busquedaPlatos = new Intent(this, BusquedaDePlatosActivity.class);
                startActivity(busquedaPlatos);
                return true;
            case R.id.home_altaItemPedido:
                Intent altaPedido = new Intent(this, AltaPedidoActivity.class);
                startActivity(altaPedido);
                return true;
            case R.id.home_crearItemPedido:
                Intent crearItem = new Intent(this, CrearItemPedidoActivity.class);
                startActivity(crearItem);
                return true;
            case R.id.home_abrirMapa:
                Intent abrirMapa = new Intent(this, MapsActivity.class);
                startActivity(abrirMapa);
                return true;
            case R.id.home_verPedidosMap:
                Intent verPedidosMap = new Intent(this, VerPedidosEnMapaActivity.class);
                startActivity(verPedidosMap);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
