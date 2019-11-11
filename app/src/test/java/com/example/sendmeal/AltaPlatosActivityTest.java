package com.example.sendmeal;

import android.text.Editable;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AltaPlatosActivityTest {

    AltaPlatosActivity altaPlatosActivity;

    @Mock
    TextInputEditText precioMock;
    @Mock
    Editable precioEditableMock;


    @Test
    public void validarPrecioCorrecto(){
        altaPlatosActivity = new AltaPlatosActivity();
        when(precioEditableMock.toString()).thenReturn("500");
        when(precioMock.getText()).thenReturn(precioEditableMock);

        altaPlatosActivity.etPrecio=precioMock;

        assertTrue(altaPlatosActivity.validarPrecio());

    }

    @Test
    public void validarPrecioIncorrecto(){
        altaPlatosActivity = new AltaPlatosActivity();
        when(precioEditableMock.toString()).thenReturn("");
        when(precioMock.getText()).thenReturn(precioEditableMock);

        altaPlatosActivity.etPrecio=precioMock;

        assertFalse(altaPlatosActivity.validarPrecio());

    }

}