package com.example.sendmeal.Utilidades;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sendmeal.AltaPlatosActivity;
import com.example.sendmeal.HomeActivity;
import com.example.sendmeal.ListaPlatosActivity;
import com.example.sendmeal.R;
import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.Plato;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class PlatosRecyclerAdapter extends RecyclerView.Adapter<PlatosRecyclerAdapter.PlatoViewHolder> {

    private static final int CODIGO_EDITAR_PLATO = 987;

    private Context contextActividad;


    private List<Plato> mDataset;

    public static int getCodigoEditarPlato() {
        return CODIGO_EDITAR_PLATO;
    }

    public PlatosRecyclerAdapter (List<Plato> myDataset) {
        this.mDataset= myDataset;
    }

    @NonNull
    @Override
    public PlatosRecyclerAdapter.PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato, parent, false);
        PlatoViewHolder vh = new PlatoViewHolder(v);

        contextActividad=parent.getContext();



        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull final PlatosRecyclerAdapter.PlatoViewHolder holder, final int position) {

        final Plato plato = mDataset.get(position);
        if(plato.getImagenPath()!=null){


            if(plato.getImagenPath().equals(AltaPlatosActivity.NO_IMAGEN)){
                holder.imagenPlato.setImageResource(R.drawable.hamburguesa);
            }else{

                // Get the dimensions of the View
                int targetW = 169;
                int targetH = 187;

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;

                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;


/*                Bitmap bitmap = BitmapFactory.decodeFile(plato.getImagenPath(), bmOptions);
                holder.imagenPlato.setImageBitmap(bitmap);*/

                File file = new File(plato.getImagenPath());
                Bitmap imageBitmap = BitmapFactory.decodeFile(plato.getImagenPath(), bmOptions);

                try {
                    imageBitmap = MediaStore.Images.Media
                            .getBitmap(contextActividad.getContentResolver(), Uri.fromFile(file));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (imageBitmap != null) {
                    holder.imagenPlato.setImageBitmap(imageBitmap);
                }
            }
        }



        holder.tvnombrePlato.setText(plato.getTitulo());
        holder.tvprecioPlato.setText(plato.getPrecio().toString());
        holder.tvdescripcionPlato.setText(plato.getDescripcion());
        final String toString = plato.toString();

        // asignar eventos a los botones.

        Button.OnClickListener btnEditarListener = v -> {


            Intent i = new Intent(contextActividad, AltaPlatosActivity.class);
            i.putExtra("indice",position);
            i.setAction("EDITAR");
            ((Activity)contextActividad).startActivityForResult(i,CODIGO_EDITAR_PLATO);

        };

        Button.OnClickListener btnQuitarListener = v -> {


            AlertDialog.Builder builder = new AlertDialog.Builder(contextActividad);
            builder.setTitle(R.string.titulo_dialog_quitar)
                    .setMessage(R.string.mensaje_dialog_quitar)
                    .setPositiveButton(R.string.confirmir_quitar, (dialog, which) -> {
                        Snackbar.make(holder.btnEditar, "Plato eliminado correctamente",Snackbar.LENGTH_SHORT).show();

                        Plato p = PlatoRepository.getInstance().getListaPlatos().get(position);
                       Handler miHandler = ( (ListaPlatosActivity) contextActividad).getMiHandler();
                       PlatoRepository.getInstance().borrarPlato(p,miHandler);

                    })
                    .setNegativeButton(R.string.cancelar_quitar, (dialog, which) -> Snackbar.make(holder.btnEditar, "El plato no ha sido eliminado",Snackbar.LENGTH_SHORT).show());
            AlertDialog dialog = builder.create();
            dialog.show();
        };

        Button.OnClickListener btnOfertaListener = v -> {

            /*Snackbar.make(holder.btnOferta, "El plato "+HomeActivity.LISTA_PLATOS.get(position).getTitulo()+" se encuentra en oferta",Snackbar.LENGTH_SHORT).show();*/
            //Hilo secundario

            plato.setEnOferta(true);

            Runnable r = () -> {
                try{
                    Thread.currentThread().sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent i = new Intent();
                i.setAction(MyReceiver.EVENTO_01);
                i.putExtra("indice",position);
                contextActividad.sendBroadcast(i);
            };
            Thread t1 = new Thread(r);
            t1.start();
        };

        holder.btnEditar.setOnClickListener(btnEditarListener);
        holder.btnQuitar.setOnClickListener(btnQuitarListener);
        holder.btnOferta.setOnClickListener(btnOfertaListener);

    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class PlatoViewHolder extends RecyclerView.ViewHolder {

        Context contexto;

        ImageView imagenPlato;
        TextView tvnombrePlato;
        TextView tvprecioPlato;
        TextView tvdescripcionPlato;

        // botones
        Button btnEditar;
        Button btnQuitar ;
        Button btnOferta ;



        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);

            contexto = itemView.getContext();

            this.imagenPlato = itemView.findViewById(R.id.plato_imagen);
            this.tvnombrePlato = itemView.findViewById(R.id.tvPlatoNombre);
            this.tvprecioPlato = itemView.findViewById(R.id.tvPlatoPrecio);
            this.tvdescripcionPlato = itemView.findViewById(R.id.tvdescripcionPlato);

            btnEditar = itemView.findViewById(R.id.buttonEditar);
            btnQuitar = itemView.findViewById(R.id.buttonQuitar);
            btnOferta = itemView.findViewById(R.id.buttonOferta);

        }




    }
}
