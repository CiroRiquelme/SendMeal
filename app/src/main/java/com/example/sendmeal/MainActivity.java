package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.sendmeal.Utilidades.DatePickerFragment;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;




public class MainActivity extends AppCompatActivity   {



    // Claves Validas
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                  //  "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    View esVendedor;
    Switch optEsVendedor;

    EditText regNombre;
    EditText regClave;
    EditText regClave2;
    EditText regMail;

    EditText regNro;
    EditText regDigito;
    EditText regVto;
    Calendar regFecha = Calendar.getInstance();

    RadioGroup regTipoCuenta;
    RadioButton optBase;
    RadioButton optPremium;
    RadioButton optFull;

    SeekBar regCredito;
    TextView creditoInicial;

    ToggleButton optNotificarMail;
    CheckBox aceptaCondiciones;

    Button btnRegistrar;

    EditText regCtaCbu;
    EditText regCtaAlias;

    int vtoMes;
    int vtoAño;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Context context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        esVendedor = findViewById(R.id.layoutCuenta);
        optEsVendedor = (Switch) findViewById(R.id.optEsVendedor);

        regNombre = findViewById(R.id.regNombre);
        regClave = findViewById(R.id.regClave);
        regClave2 = findViewById(R.id.regClave2);
        regMail = findViewById(R.id.regMail);
        regNro = findViewById(R.id.regNro);
        regDigito = findViewById(R.id.regDigito);
        regVto = findViewById(R.id.regVto);

        regTipoCuenta = findViewById(R.id.regTipoCuenta);
        optBase = findViewById(R.id.optBase);
        optPremium = findViewById(R.id.optPremium);
        optFull = findViewById(R.id.optFull);

        regCredito = findViewById(R.id.regCredito);
        creditoInicial = findViewById(R.id.creditoInicial);

        optNotificarMail = findViewById(R.id.optNotificarMail);
        aceptaCondiciones = findViewById(R.id.aceptaCondiciones);

        btnRegistrar = findViewById(R.id.btnRegistrar);

        regCtaAlias = findViewById(R.id.regCtaAlias);
        regCtaCbu = findViewById(R.id.regCtaCbu);

        optEsVendedor.setOnCheckedChangeListener(this.optEsVendedorListener);
        aceptaCondiciones.setOnCheckedChangeListener(this.aceptaCondicionesListener);
        regTipoCuenta.setOnCheckedChangeListener(this.regTipoCuentaListener);
        regCredito.setOnSeekBarChangeListener(this.regCreditoListener);
        btnRegistrar.setOnClickListener(this.btnRegistrarListener);

        regVto.setOnClickListener(this.regVtoListener);

    }

    // Definicion de los listener

    private View.OnClickListener regVtoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDatePickerDialog();
        }
    };

/*    private void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }*/

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
               // final String selectedDate = day + " / " + (month+1) + " / " + year;
                vtoAño=year;
                vtoMes=month+1;
                final String selectedDate =  (month+1) + " / " + year;
                regVto.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private CompoundButton.OnCheckedChangeListener optEsVendedorListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                esVendedor.setVisibility(View.VISIBLE);
            } else {
                esVendedor.setVisibility(View.GONE);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener aceptaCondicionesListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                btnRegistrar.setEnabled(true);
            } else {
                btnRegistrar.setEnabled(false);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener regTipoCuentaListener = new RadioGroup.OnCheckedChangeListener() {
        @Override

        public void onCheckedChanged(RadioGroup radioGroup, int id) {
            switch (id) {
                case R.id.optBase: {
                    regCredito.setProgress(100);
                    creditoInicial.setText(String.valueOf(regCredito.getProgress()));

                    break;
                }
                case R.id.optPremium: {
                    regCredito.setProgress(250);
                    creditoInicial.setText(String.valueOf(regCredito.getProgress()));
                    break;
                }
                case R.id.optFull: {
                    regCredito.setProgress(500);
                    creditoInicial.setText(String.valueOf(regCredito.getProgress()));
                    break;
                }
            }
        }
    };

    private SeekBar.OnSeekBarChangeListener regCreditoListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            int progressChanged = 100;
                if (b) {
                    progressChanged = 100+ progress;
                  //  creditoInicial.setText(String.valueOf(regCredito.getProgress()));
                    creditoInicial.setText(String.valueOf(progressChanged));
                }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private Button.OnClickListener btnRegistrarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(validarMail() && validarClave() && validarDatosTarjeta() && validarTipoCuenta() && validarDatosVendedor()){
                Toast.makeText(getApplicationContext(),"Creando Usuario ...",Toast.LENGTH_LONG).show();
            }

        }
    };

    private  boolean validarMail(){
        Context context = getApplicationContext();
        String mail = regMail.getText().toString().trim();
        if (mail.isEmpty()) {
            Toast.makeText(context, "Debe ingresar un email.", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((!Patterns.EMAIL_ADDRESS.matcher(mail).matches())){
            Toast.makeText(context, "Debo ingresar un email válido.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validarClave() {
         Context context = getApplicationContext();
        String clave = regClave.getText().toString().trim();
        String clave2 = regClave2.getText().toString().trim();
        if(clave.isEmpty()){
            Toast.makeText(context, "Debe establecer un clave.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!PASSWORD_PATTERN.matcher(clave).matches()){
            Toast.makeText(context, "Ingrese una clave valida.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!clave.equals(clave2)){
            Toast.makeText(context, "Las claves deben concidir.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validarDatosTarjeta(){
        Context context = getApplicationContext();

        String nroTajeta = regNro.getText().toString().trim();
        String ccvTarjeta =regDigito.getText().toString();
        String vencimientoTarjeta = regVto.getText().toString().trim();


        if(nroTajeta.isEmpty()||ccvTarjeta.isEmpty()||vencimientoTarjeta.isEmpty()){
            Toast.makeText(context, "Debe completar los datos de la tarjeta.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!validarVencimiento()){
            return false;
        }
        return true;
    }

    private boolean validarTipoCuenta(){
        Context context = getApplicationContext();
        int optTipoCuenta = regTipoCuenta.getCheckedRadioButtonId();
        if (optTipoCuenta == -1) {
            Toast.makeText(context, "Debe elegir un tipo de cuenta.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validarDatosVendedor(){
        Context context = getApplicationContext();

        String ctaAlias = regCtaAlias.getText().toString().trim();
        String ctaCbu = regCtaCbu.getText().toString().trim();
        if (optEsVendedor.isChecked()) {
            if (ctaAlias.isEmpty() || ctaCbu.isEmpty()) {
                Toast.makeText(context, "Debe completar los datos de la cuenta bancaria.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean validarVencimiento(){
        Context context = getApplicationContext();

        Calendar fecha = Calendar.getInstance();
        int añoActual = fecha.get(Calendar.YEAR);
        int mesActual = fecha.get(Calendar.MONTH) + 1;

        añoActual=añoActual +(int) (mesActual+3)/12 ;
        mesActual=(mesActual+3)%12;

        if(vtoAño<añoActual){
            return false;
        }else{
            if(añoActual==vtoAño) {
                if (vtoMes < mesActual) {
                    Toast.makeText(context, "Fecha de vencimiento no valida.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        return true;
    }




}
