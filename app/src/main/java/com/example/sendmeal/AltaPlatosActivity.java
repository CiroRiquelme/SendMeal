package com.example.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.Plato;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AltaPlatosActivity extends AppCompatActivity {

    TextInputEditText etId;
    TextInputEditText etNombre;
    TextInputEditText etDescripcion;
    TextInputEditText etPrecio;
    TextInputEditText etCalorias;


    MaterialButton btnGuardar;
    MaterialButton btnCancelar;
    MaterialButton btnAgregarImagen;
    ImageView imageView;

    Integer pid;
    String ptitulo;
    String pdescripcion;
    Double pprecio;
    Integer pcalorias;
    String currentPhotoPath = NO_IMAGEN;

    private Plato platoActual;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    static final int REQUEST_TAKE_PHOTO = 3;

    public static final String NO_IMAGEN="no posee imagen";

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

        btnAgregarImagen = findViewById(R.id.btnAgregarImagen);
        btnAgregarImagen.setOnClickListener(btnAgregarImagenListener);
        imageView = findViewById(R.id.imageView);

        if(getIntent().getExtras()!= null){
            if(getIntent().getAction()=="EDITAR"){


                Integer indice = getIntent().getExtras().getInt("indice");
                if(indice>=0){
                    platoActual = PlatoRepository.getInstance().getListaPlatos().get(indice);
                    etId.setText(platoActual.getId().toString());
                    etNombre.setText(platoActual.getTitulo());
                    etDescripcion.setText((platoActual.getDescripcion()));
                    etPrecio.setText(platoActual.getPrecio().toString());
                    etCalorias.setText(platoActual.getCalorias().toString());
                }
            }else{
                if(getIntent().getAction()=="OFERTA"){

                    etId.setEnabled(false);
                    etNombre.setEnabled(false);
                    etDescripcion.setEnabled(false);
                    etPrecio.setEnabled(false);
                    etCalorias.setEnabled(false);

                    Integer indiceOferta = getIntent().getExtras().getInt("indiceOferta");


                    platoActual = PlatoRepository.getInstance().getListaPlatos().get(indiceOferta);

                    etId.setText(platoActual.getId().toString());
                    etNombre.setText(platoActual.getTitulo());
                    etDescripcion.setText((platoActual.getDescripcion()));
                    etPrecio.setText(platoActual.getPrecio().toString());
                    etCalorias.setText(platoActual.getCalorias().toString());
                }
            }
        }
    }

    private MaterialButton.OnClickListener btnAgregarImagenListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(AltaPlatosActivity.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission();
                return;
            }
            //dispatchTakePictureIntent();
            dispatchTakePictureIntent2();
        }
    };



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent2() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.sendmeal",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }



    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AltaPlatosActivity.this);
            builder.setTitle(R.string.permisos_titulo)
                    .setMessage(R.string.permiso_camera)
                    .setPositiveButton(R.string.confirmir_quitar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AltaPlatosActivity.this.requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA_PERMISSION);
                        }
                    })
                    .setNegativeButton(R.string.cancelar_quitar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Snackbar.make(btnAgregarImagen, "Camera Error",Snackbar.LENGTH_LONG).show();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(btnAgregarImagen, "Camera Error",Snackbar.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            File file = new File(currentPhotoPath);
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media
                        .getBitmap(getContentResolver(), Uri.fromFile(file));

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (imageBitmap != null) {
                imageView.setImageBitmap(imageBitmap);
            }

        }
    }


    private MaterialButton.OnClickListener btnGuardarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            if(platoActual!=null){
                // Cuando se edita o se entra por la notificacion.
                if(validarId() & validarNombre() & validarDescripcion() & validarPrecio() & validarCalorias()){
                    platoActual.setId(pid);
                    platoActual.setCalorias(pcalorias);
                    platoActual.setDescripcion(pdescripcion);
                    platoActual.setPrecio(pprecio);
                    platoActual.setTitulo(ptitulo);
                    PlatoRepository.getInstance().actualizarPlato(platoActual,miHandler);
                    finish();
                }
            }else{
                // Cuando se quiere crear un nuevo plato
                if(validarId() & validarNombre() & validarDescripcion() & validarPrecio() & validarCalorias()){

                    platoActual = new Plato(pid, ptitulo, pdescripcion, pprecio,pcalorias);
                    platoActual.setImagenPath(currentPhotoPath);

                    PlatoRepository.getInstance().crearPlato(platoActual,miHandler);

                    Snackbar.make(btnGuardar, "Plato creado con exito",Snackbar.LENGTH_LONG).show();

                    limpiarCampos();

/*                    Intent listaPlatos = new Intent(AltaPlatosActivity.this, ListaPlatosActivity.class);
                    startActivity(listaPlatos);*/
                }
            }
        }
    };

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            Log.d("ALTA - EDICION ","Vuelve al handler"+msg.arg1);

            switch (msg.arg1 ){
                case PlatoRepository._ALTA_PLATO: {
                    Intent i = new Intent(AltaPlatosActivity.this, ListaPlatosActivity.class);
                    startActivity(i);
                    break;
                }
                case PlatoRepository._UPDATE_PLATO: {
                    Intent i = new Intent(AltaPlatosActivity.this, ListaPlatosActivity.class);
                    startActivity(i);
                    break;
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

    private void limpiarCampos(){
        etId.setText("");
        etNombre.setText("");
        etDescripcion.setText("");
        etPrecio.setText("");
        etCalorias.setText("");

    }
}
