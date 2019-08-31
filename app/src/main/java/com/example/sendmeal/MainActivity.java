package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    View esVendedor;
    Switch optEsVendedor;

    EditText regNombre;
    EditText regClave;
    EditText regClave2;
    EditText regMail;

    EditText regNro;
    EditText regDigito;
    EditText regVto;

    RadioGroup regTipoCuenta;
    RadioButton optBase;
    RadioButton optPremium;
    RadioButton optFull;

    SeekBar regCredito;
    TextView creditoInicial;

    ToggleButton optNotificarMail;
    CheckBox aceptaCondiciones;

    Button btnRegistrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

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









       int optTipoCuenta = regTipoCuenta.getCheckedRadioButtonId();
       regTipoCuenta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int id) {
               switch (id){
                   case R.id.optBase:{
                        regCredito.setProgress(100);
                        creditoInicial.setText(String.valueOf(regCredito.getProgress()));

                       break;
                   }
                   case R.id.optPremium:{
                       regCredito.setProgress(250);
                       creditoInicial.setText(String.valueOf(regCredito.getProgress()));
                       break;
                   }
                   case R.id.optFull:{
                       regCredito.setProgress(500);
                       creditoInicial.setText(String.valueOf(regCredito.getProgress()));
                       break;
                   }
               }
           }
       });

       regCredito.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               if(b){
                   creditoInicial.setText(String.valueOf(regCredito.getProgress()));
               }
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });


        optEsVendedor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    esVendedor.setVisibility(View.VISIBLE);
                }else{
                    esVendedor.setVisibility(View.GONE);
                }
            }
        });


        aceptaCondiciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    btnRegistrar.setEnabled(true);
                }else{
                    btnRegistrar.setEnabled(false);
                }
            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(regMail.getText().length()==0){

                    Context context = getApplicationContext();
                    CharSequence text = "El mail es obligatorio.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
                if(!regClave.getText().toString().equals(regClave2.getText().toString())){

                    Context context = getApplicationContext();
                    CharSequence text = "Las claves no coinciden.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });







    }
}
