package com.example.sendmeal.dao;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.sendmeal.HomeActivity;
import com.example.sendmeal.dao.rest.PlatoRest;
import com.example.sendmeal.domain.Plato;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlatoRepository {


    public static String _SERVER = "http://192.168.1.11:5000/";
    private List<Plato> listaPlatos;

    public static final int _ALTA_PLATO = 1;
    public static final int _UPDATE_PLATO = 2;
    public static final int _BORRADO_PLATO = 3;
    public static final int _CONSULTA_PLATO = 4;
    public static final int _ERROR_PLATO = 9;

    private static PlatoRepository _INSTANCE;

    private PlatoRepository() {}

    public static PlatoRepository getInstance(){
        if(_INSTANCE==null){
            _INSTANCE = new PlatoRepository();
            _INSTANCE.configurarRetrofit();
            _INSTANCE.listaPlatos = new ArrayList<>();
        }
        return _INSTANCE;
    }

    private Retrofit rf;
    private PlatoRest platoRest;

    private void configurarRetrofit(){
        this.rf = new Retrofit.Builder()
                .baseUrl(_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("APP_2","INSTANCIA CREADA");

        this.platoRest = this.rf.create(PlatoRest.class);
    }

    public void actualizarPlato(final Plato p, final Handler h){
        Call<Plato> llamada = this.platoRest.actualizar(p.getId(),p);
        llamada.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                Log.d("ACTUALIZAR","Despues que ejecuta"+ response.isSuccessful());
                Log.d("ACTUALIZAR","CODIGO RESPUESTA"+ response.code());

                if(response.isSuccessful()){
                    Log.d("ACTUALIZAR","EJECUTO");
                    listaPlatos.remove(p);
                    listaPlatos.add(response.body());
                    Message m = new Message();
                    m.arg1 = _UPDATE_PLATO;
                    h.sendMessage(m);
                }
            }
            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Log.d("ACTUALIZAR","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public void crearPlato(Plato p, final Handler h){
        Call<Plato> llamada = this.platoRest.crear(p);
        llamada.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                Log.d("APP_2","Despues que ejecuta"+ response.isSuccessful());
                Log.d("APP_2","COdigo"+ response.code());

                if(response.isSuccessful()){
                    Log.d("APP_2","EJECUTO");
                    listaPlatos.add(response.body());
                    Message m = new Message();
                    m.arg1 = _ALTA_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

        public  void buscarPlatoPorPrecio(final Integer precioMin, final Integer precioMax, final Handler h){
        Call<List<Plato>> llamada = this.platoRest.buscarPorPrecio(precioMin, precioMax);
        llamada.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                if(response.isSuccessful()){
                    listaPlatos.clear();
                    listaPlatos.addAll(response.body());
                    Message m = new Message();
                    m.arg1 = _CONSULTA_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }
    public void buscarPlatoPorNombre(final String nombre, final Handler h){
        Call<List<Plato>> llamada = this.platoRest.buscarPorNombre(nombre);
        llamada.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                listaPlatos.clear();
                listaPlatos.addAll(response.body());
                Message m = new Message();
                m.arg1 = _CONSULTA_PLATO;
                h.sendMessage(m);
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public void listarPlato(final Handler h){
        Call<List<Plato>> llamada = this.platoRest.buscarTodas();
        llamada.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                if(response.isSuccessful()){
                    listaPlatos.clear();
                    listaPlatos.addAll(response.body());
                    Message m = new Message();
                    m.arg1 = _CONSULTA_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public void borrarPlato(final Plato p, final Handler h){
        Call<Void> llamada = this.platoRest.borrar(p.getId());
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("APP_2","Despues que ejecuta"+ response.isSuccessful());
                Log.d("APP_2","COdigo"+ response.code());

                if(response.isSuccessful()){
                    Log.d("APP_2","EJECUTO");
                    for(Plato p : listaPlatos){
                        Log.d("APP_2","Obra "+p.getId());
                    }
                    Log.d("APP_2","BORRA Obra "+p.getId());
                    listaPlatos.remove(p);
                    for(Plato p : listaPlatos){
                        Log.d("APP_2","Obra "+p.getId());
                    }
                    Message m = new Message();
                    m.arg1 = _BORRADO_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }
    public List<Plato> getListaPlatos() {
        return listaPlatos;
    }

}
