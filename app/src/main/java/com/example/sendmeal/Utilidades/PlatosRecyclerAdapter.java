package com.example.sendmeal.Utilidades;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


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
import com.example.sendmeal.domain.Plato;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;



public class PlatosRecyclerAdapter extends RecyclerView.Adapter<PlatosRecyclerAdapter.PlatoViewHolder> {

    private static final int CODIGO_EDITAR_PLATO = 987;

    Context contextActividad;


    private List<Plato> mDataset;

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

        holder.imagenPlato.setImageResource(R.drawable.hamburguesa);
        holder.tvnombrePlato.setText(plato.getTitulo());
        holder.tvprecioPlato.setText(plato.getPrecio().toString());
        holder.tvdescripcionPlato.setText(plato.getDescripcion());
        final String toString = plato.toString();

        // asignar eventos a los botones.

        Button.OnClickListener btnEditarListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(contextActividad, AltaPlatosActivity.class);
                i.putExtra("indice",position);
                i.setAction("EDITAR");
                ((Activity)contextActividad).startActivityForResult(i,CODIGO_EDITAR_PLATO);

            }
        };

        Button.OnClickListener btnQuitarListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(contextActividad);
                builder.setTitle(R.string.titulo_dialog_quitar)
                        .setMessage(R.string.mensaje_dialog_quitar)
                        .setPositiveButton(R.string.confirmir_quitar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar.make(holder.btnEditar, "Plato eliminado correctamente",Snackbar.LENGTH_SHORT).show();

                                HomeActivity.LISTA_PLATOS.remove(position);
                                ListaPlatosActivity.actualizarLista();


                            }
                        })
                        .setNegativeButton(R.string.cancelar_quitar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar.make(holder.btnEditar, "El plato no ha sido eliminado",Snackbar.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };

        Button.OnClickListener btnOfertaListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Snackbar.make(holder.btnOferta, "El plato "+HomeActivity.LISTA_PLATOS.get(position).getTitulo()+" se encuentra en oferta",Snackbar.LENGTH_SHORT).show();*/
                //Hilo secundario

                plato.setEnOferta(true);

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.currentThread().sleep(10000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent i = new Intent();
                        i.setAction(MyReceiver.EVENTO_01);
                        i.putExtra("indice",position);
                        contextActividad.sendBroadcast(i);


                    }
                };
                Thread t1 = new Thread(r);
                t1.start();
            }
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
