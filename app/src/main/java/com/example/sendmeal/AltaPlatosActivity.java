package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
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

    Integer pid;
    String ptitulo;
    String pdescripcion;
    Double pprecio;
    Integer pcalorias;


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
            Toast.makeText(getApplicationContext(), "Es nulll.", Toast.LENGTH_SHORT).show();
        }



        etId = findViewById(R.id.etPlatoId);
        etNombre = findViewById(R.id.etPlatoNombre);
        etDescripcion= findViewById(R.id.etPlatoDescripcion);
        etPrecio = findViewById(R.id.etPlatoPrecio);
        etCalorias = findViewById(R.id.etPlatoCalorias);
        btnGuardar = findViewById(R.id.btnPlatoGuardar);

        btnGuardar.setOnClickListener(btnGuardarListener);
    }


    private MaterialButton.OnClickListener btnGuardarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(validarId() & validarNombre() & validarDescripcion() & validarPrecio() & validarCalorias()){

                Plato nuevoPlato = new Plato(pid, ptitulo, pdescripcion, pprecio,pcalorias);


                Snackbar.make(btnGuardar, "Plato creado con exito",Snackbar.LENGTH_SHORT).show();
            }



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
