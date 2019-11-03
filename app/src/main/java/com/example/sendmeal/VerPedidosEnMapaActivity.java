package com.example.sendmeal;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.EstadoPedido;
import com.example.sendmeal.domain.Pedido;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import static com.example.sendmeal.domain.EstadoPedido.ACEPTADO;
import static com.example.sendmeal.domain.EstadoPedido.CANCELADO;
import static com.example.sendmeal.domain.EstadoPedido.ENTREGADO;
import static com.example.sendmeal.domain.EstadoPedido.ENVIADO;
import static com.example.sendmeal.domain.EstadoPedido.EN_ENVIO;
import static com.example.sendmeal.domain.EstadoPedido.EN_PREPARACION;
import static com.example.sendmeal.domain.EstadoPedido.PENDIENTE;
import static com.example.sendmeal.domain.EstadoPedido.RECHAZADO;

public class VerPedidosEnMapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Spinner spinner;

    private  ArrayList<Pedido> listaPedidos = new ArrayList<>();


    PolylineOptions lineaEnviados = new PolylineOptions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedidos_en_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

         spinner =  findViewById(R.id.spinnerEstadoPedido);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.estado_pedido, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spinnerListener);







    }

    private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String estadoSeleccionado = adapterView.getItemAtPosition(i).toString();
            Log.d("ESTADO PEDIDO :", estadoSeleccionado);

            ArrayList<Pedido> listaPedidos2 = new ArrayList<>(listaPedidos);


            switch (estadoSeleccionado) {
                case  "TODOS":{
                    mostrarPedidos(listaPedidos);
                    break;
                }
                case "PENDIENTE": {
                    //Numbers.removeIf(n -> (n % 3 == 0));
                    listaPedidos2.removeIf( n -> ( !n.getEstado().equals(PENDIENTE)));
                    mostrarPedidos(listaPedidos2);
                    break;
                }
                case "ENVIADO": {
                    listaPedidos2.removeIf( n -> ( !n.getEstado().equals(ENVIADO)));
                    mostrarPedidos(listaPedidos2);
                    lineaEnviados.color(Color.BLUE);
                    mMap.addPolyline(lineaEnviados);

                    break;
                }
                case "ACEPTADO": {
                    listaPedidos2.removeIf( n -> ( !n.getEstado().equals(ACEPTADO)));
                    mostrarPedidos(listaPedidos2);


                    break;
                }
                case "RECHAZADO": {
                    listaPedidos2.removeIf( n -> ( !n.getEstado().equals(RECHAZADO)));
                    mostrarPedidos(listaPedidos2);


                    break;
                }
                case "EN_PREPARACION": {
                    listaPedidos2.removeIf( n -> ( !n.getEstado().equals(EN_PREPARACION)));
                    mostrarPedidos(listaPedidos2);
                    break;
                }
                case "EN_ENVIO": {
                    listaPedidos2.removeIf( n -> ( !n.getEstado().equals(EN_ENVIO)));
                    mostrarPedidos(listaPedidos2);
                    break;
                }
                case "ENTREGADO": {
                    listaPedidos2.removeIf( n -> ( !n.getEstado().equals(ENTREGADO)));
                    mostrarPedidos(listaPedidos2);
                    break;
                }
                case "CANCELADO": {
                    listaPedidos2.removeIf( n -> ( !n.getEstado().equals(CANCELADO)));
                    mostrarPedidos(listaPedidos2);
                    break;
                }

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng a = new LatLng(-31.6545647, -60.7273674);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(a));

        UiSettings mapUiSettings = mMap.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        PlatoRepository.getInstance().listarPedidos(miHandler);





    }

    private void mostrarPedidos(List<Pedido> listaPedidos){
        mMap.clear();

        BitmapDescriptor bitmapDescriptorPENDIENTE = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        BitmapDescriptor bitmapDescriptorENVIADO = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        BitmapDescriptor bitmapDescriptorACEPTADO = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
        BitmapDescriptor bitmapDescriptorRECHAZADO = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        BitmapDescriptor bitmapDescriptorEN_PREPARACION = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
        BitmapDescriptor bitmapDescriptorEN_ENVIO = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
        BitmapDescriptor bitmapDescriptorENTREGADO = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        BitmapDescriptor bitmapDescriptorCANCELADO = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE);



        if(listaPedidos!=null){
            for( Pedido p : listaPedidos){
                LatLng latLng = new LatLng(p.getLat(),p.getLng());
                String titulo = p.getId().toString();
                String descripcion = p.getId() + " - " + p.getEstado().toString() + " - ";

                switch (p.getEstado()){
                    case PENDIENTE:{
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(titulo)
                                .snippet(descripcion)
                                .icon(bitmapDescriptorPENDIENTE));
                        break;
                    }
                    case ENVIADO:{
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(titulo)
                                .snippet(descripcion)
                                .icon(bitmapDescriptorENVIADO));
                        lineaEnviados.add(latLng);
                        break;
                    }
                    case ACEPTADO:{
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(titulo)
                                .snippet(descripcion)
                                .icon(bitmapDescriptorACEPTADO));
                        break;
                    }
                    case RECHAZADO:{
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(titulo)
                                .snippet(descripcion)
                                .icon(bitmapDescriptorRECHAZADO));
                        break;
                    }
                    case EN_PREPARACION:{
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(titulo)
                                .snippet(descripcion)
                                .icon(bitmapDescriptorEN_PREPARACION));
                        break;
                    }
                    case EN_ENVIO:{
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(titulo)
                                .snippet(descripcion)
                                .icon(bitmapDescriptorEN_ENVIO));
                        break;
                    }
                    case ENTREGADO:{
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(titulo)
                                .snippet(descripcion)
                                .icon(bitmapDescriptorENTREGADO));
                        break;
                    }
                    case CANCELADO:{
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(titulo)
                                .snippet(descripcion)
                                .icon(bitmapDescriptorCANCELADO));
                        break;
                    }
                }
            }

        }



    };

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            Log.d(" VER PEDIDOS MAP ","Vuelve al handler"+msg.arg1);

            switch (msg.arg1 ){
                case PlatoRepository._CONSULTA_PEDIDO: {
                    listaPedidos = PlatoRepository.getInstance().getListaPedidos();
                    mostrarPedidos(listaPedidos);

                }
                case PlatoRepository._ERROR_PEDIDO: {

                }
            }
        }
    };
}


