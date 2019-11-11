package com.example.sendmeal;

import android.content.Context;
import android.text.Editable;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class testRegistroUsuario {

    RegistroUsuarioActivity activityRegistroUsuario;

    @Mock    EditText regMailMock;
    @Mock    Editable edtMockMail;

    @Mock EditText regNroMock;
    @Mock Editable edtNroMock;




/*    @Test
    public void validarEmailCorrecto(){
        activityRegistroUsuario = new RegistroUsuarioActivity();


        when(edtMockMail.toString()).thenReturn("ciroriquelme05@gmail.com");
        when(regMailMock.getText()).thenReturn(edtMockMail);


        activityRegistroUsuario.setRegMail(regMailMock);

       assertTrue(activityRegistroUsuario.validarMail());
    }*/

    @Test
    public void validarNumeroTarjetaCorrecta(){
        activityRegistroUsuario = new RegistroUsuarioActivity();

       when(edtNroMock.toString()).thenReturn("123456789");
        when(regNroMock.getText()).thenReturn(edtNroMock);
        activityRegistroUsuario.setRegNro(regNroMock);

        activityRegistroUsuario.validarNumeroTarjeta();
    }

    @Test
    public void validarNumeroTarjetaIncorrecta(){
        activityRegistroUsuario = new RegistroUsuarioActivity();

        when(edtNroMock.toString()).thenReturn("");
        when(regNroMock.getText()).thenReturn(edtNroMock);
        activityRegistroUsuario.setRegNro(regNroMock);

        assertFalse(activityRegistroUsuario.validarNumeroTarjeta());
    }




}
