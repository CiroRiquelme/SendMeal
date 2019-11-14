package com.example.sendmeal;

import android.app.Activity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;


@RunWith(RobolectricTestRunner.class)
public class AltaPlatosActivityTest {

    private AltaPlatosActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(AltaPlatosActivity.class).create().resume().get();
    }

    @Test
    public void guardarPlato() throws Exception{


        TextInputEditText etId;
        TextInputEditText etNombre;
        TextInputEditText etDescripcion;
        TextInputEditText etPrecio;
        TextInputEditText etCalorias;

        MaterialButton btnGuardar;

        etId = activity.findViewById(R.id.etPlatoId);
        etNombre = activity.findViewById(R.id.etPlatoNombre);
        etDescripcion= activity.findViewById(R.id.etPlatoDescripcion);
        etPrecio = activity.findViewById(R.id.etPlatoPrecio);
        etCalorias = activity.findViewById(R.id.etPlatoCalorias);

        btnGuardar = activity.findViewById(R.id.btnPlatoGuardar);

        // si todos los datos son validos , al apretar el boton guardar
        //  se limpiaran los campos.

        etId.setText("01");
        etNombre.setText("Pizza");
        etDescripcion.setText("con queso");
        etPrecio.setText("100");
        etCalorias.setText("1000");

        btnGuardar.performClick();

        assertEquals("",etId.getText().toString());
        assertEquals("",etNombre.getText().toString());
        assertEquals("",etDescripcion.getText().toString());
        assertEquals("",etPrecio.getText().toString());
        assertEquals("",etCalorias.getText().toString());

    }

    @Test
    public void guardarPlatoIncorrecto() throws Exception{


        TextInputEditText etId;
        TextInputEditText etNombre;
        TextInputEditText etDescripcion;
        TextInputEditText etPrecio;
        TextInputEditText etCalorias;

        MaterialButton btnGuardar;

        etId = activity.findViewById(R.id.etPlatoId);
        etNombre = activity.findViewById(R.id.etPlatoNombre);
        etDescripcion= activity.findViewById(R.id.etPlatoDescripcion);
        etPrecio = activity.findViewById(R.id.etPlatoPrecio);
        etCalorias = activity.findViewById(R.id.etPlatoCalorias);

        btnGuardar = activity.findViewById(R.id.btnPlatoGuardar);

        // si alguno de estos valores es incorrecto -> los datos ingresados no se borran
        // al apretar el boton guardar.

        etId.setText("01");
        etNombre.setText("Pizza");
        etDescripcion.setText("");
        etPrecio.setText("100");
        etCalorias.setText("1000");

        btnGuardar.performClick();

        assertEquals("01",etId.getText().toString());
        assertEquals("Pizza",etNombre.getText().toString());
        assertEquals("",etDescripcion.getText().toString());
        assertEquals("100",etPrecio.getText().toString());
        assertEquals("1000",etCalorias.getText().toString());

    }
}