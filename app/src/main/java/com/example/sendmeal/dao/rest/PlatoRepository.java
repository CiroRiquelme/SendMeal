package com.example.sendmeal.dao.rest;

import android.util.Log;

import com.example.sendmeal.domain.Plato;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlatoRepository {


    public static String _SERVER = "http://10.0.2.2:5000/";
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
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("APP_2","INSTANCIA CREADA");

        this.platoRest = this.rf.create(PlatoRest.class);
    }





}
