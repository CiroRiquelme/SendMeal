package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sendmeal.domain.Plato;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AltaPlatosActivity extends AppCompatActivity {

    TextInputEditText etId;
    TextInputEditText etNombre;
    TextInputEditText etDescripcion;
    TextInputEditText etPrecio;
    TextInputEditText etCalorias;

    MaterialButton btnGuardar;
    MaterialButton btnCancelar;

    Integer pid;
    String ptitulo;
    String pdescripcion;
    Double pprecio;
    Integer pcalorias;

    private Plato p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_platos);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarBack);
        myToolbar.setTitle("Alta de platos");
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);*/
        }else
        {
            Toast.makeText(getApplicationContext(), "Es null.", Toast.LENGTH_SHORT).show();
        }



        etId = findViewById(R.id.etPlatoId);
        etNombre = findViewById(R.id.etPlatoNombre);
        etDescripcion= findViewById(R.id.etPlatoDescripcion);
        etPrecio = findViewById(R.id.etPlatoPrecio);
        etCalorias = findViewById(R.id.etPlatoCalorias);

        btnGuardar = findViewById(R.id.btnPlatoGuardar);
        btnGuardar.setOnClickListener(btnGuardarListener);

        btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(btnCancelarListener);





        if(getIntent().getExtras()!= null){
            if(getIntent().getAction()=="EDITAR"){
                Snackbar.make(btnGuardar, "Hay que editar",Snackbar.LENGTH_SHORT).show();

                Integer indice = getIntent().getExtras().getInt("indice");

                p = HomeActivity.LISTA_PLATOS.get(indice);

                etId.setText(p.getId().toString());
                etNombre.setText(p.getTitulo());
                etDescripcion.setText((p.getDescripcion()));
                etPrecio.setText(p.getPrecio().toString());
                etCalorias.setText(p.getCalorias().toString());
            }else{
                if(getIntent().getAction()=="OFERTA"){

                    etId.setEnabled(false);
                    etNombre.setEnabled(false);
                    etDescripcion.setEnabled(false);
                    etPrecio.setEnabled(false);
                    etCalorias.setEnabled(false);

                    Integer indiceOferta = getIntent().getExtras().getInt("indiceOferta");
                    Snackbar.make(btnGuardar, "Hay que mostrar oferta "+"Indice "+indiceOferta,Snackbar.LENGTH_SHORT).show();

                    p = HomeActivity.LISTA_PLATOS.get(indiceOferta);

                    etId.setText(p.getId().toString());
                    etNombre.setText(p.getTitulo());
                    etDescripcion.setText((p.getDescripcion()));
                    etPrecio.setText(p.getPrecio().toString());
                    etCalorias.setText(p.getCalorias().toString());
                }
            }



        }
    }


    private MaterialButton.OnClickListener btnGuardarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(getIntent().getExtras()!= null){
                if(validarId() & validarNombre() & validarDescripcion() & validarPrecio() & validarCalorias()){
                    p.setId(pid);
                    p.setCalorias(pcalorias);
                    p.setDescripcion(pdescripcion);
                    p.setPrecio(pprecio);
                    p.setTitulo(ptitulo);
                    finish();
                }
            }else{
                if(validarId() & validarNombre() & validarDescripcion() & validarPrecio() & validarCalorias()){

                    Plato nuevoPlato = new Plato(pid, ptitulo, pdescripcion, pprecio,pcalorias);
                    HomeActivity.LISTA_PLATOS.add(nuevoPlato);


                    Snackbar.make(btnGuardar, "Plato creado con exito",Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    };

    private MaterialButton.OnClickListener btnCancelarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private boolean validarId() {
        String sId = etId.getText().toString().trim();
        Integer id;
        if (sId.isEmpty()) {
            etId.setError("id obligatorio");
            return false;
        } else{
            id= Integer.valueOf(sId);
            if(id<0){
                etId.setError("id es un numero mayor a 0");
                return false;
            }
        }
        etId.setError(null);
        AltaPlatosActivity.this.pid = id;
        return true;
    }
    private boolean validarNombre(){
        String nombre = etNombre.getText().toString().trim();
        if(nombre.isEmpty()){
            etNombre.setError("nombre obligatorio");
            return  false;
        }
        etNombre.setError(null);
        ptitulo = nombre;
        return true;
    }
    private boolean validarDescripcion() {
        String desc = etDescripcion.getText().toString().trim();
        if(desc.isEmpty()){
            etDescripcion.setError("descripcion obligatoria");
            return false;
        }

        pdescripcion=desc;
        etDescripcion.setError(null);
        return true;
    }
    private boolean validarPrecio(){
        String precio = etPrecio.getText().toString().trim();
        if(precio.isEmpty()){
            etPrecio.setError("precio obligatorio");
            return  false;
        }
        etPrecio.setError(null);
        pprecio = Double.valueOf(precio);
        return true;
    }
    private boolean validarCalorias(){
        String calorias = etCalorias.getText().toString().trim();
        if(calorias.isEmpty()){
            etCalorias.setError("calorias obligatorias");
            return  false;
        }

        etCalorias.setError(null);

        pcalorias = Integer.valueOf(calorias);
        return true;
    }
}
