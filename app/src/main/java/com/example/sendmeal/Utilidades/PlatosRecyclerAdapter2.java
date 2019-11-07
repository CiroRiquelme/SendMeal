package com.example.sendmeal.Utilidades;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sendmeal.R;
import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.ItemsPedido;
import com.example.sendmeal.domain.Plato;

import java.util.ArrayList;
import java.util.List;


public class PlatosRecyclerAdapter2 extends RecyclerView.Adapter<PlatosRecyclerAdapter2.PlatoViewHolder>  {


    private static final int CODIGO_EDITAR_PLATO = 987;

    Context contextActividad;

    private List<Plato> mDataset;

    public PlatosRecyclerAdapter2 (List<Plato> myDataset) {
        this.mDataset= myDataset;
    }

    private static ArrayList<ItemsPedido> listaItemsPedido = new ArrayList<>();

    public static ArrayList<ItemsPedido> getListaItemsPedido() {
        return listaItemsPedido;
    }

    @NonNull
    @Override
    public PlatosRecyclerAdapter2.PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.platos_pedidos, parent, false);
        PlatoViewHolder vh = new PlatoViewHolder(v);

        contextActividad=parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlatosRecyclerAdapter2.PlatoViewHolder holder, final int position) {

        final Plato plato = mDataset.get(position);

        holder.imagenPlato.setImageResource(R.drawable.hamburguesa);
        holder.tvnombrePlato.setText(plato.getTitulo());
        holder.tvprecioPlato.setText(plato.getPrecio().toString());
        holder.tvdescripcionPlato.setText(plato.getDescripcion());




        // asignar eventos a los botones.

        Button.OnClickListener btnAñadirListener = v -> {

            holder.btnAñadir.setEnabled(false);
            holder.btnQuitar.setEnabled(true);

            Plato p = PlatoRepository.getInstance().getListaPlatos().get(position);

            ItemsPedido itemsPedido = new ItemsPedido();

            itemsPedido.setCantidad(Integer.valueOf(holder.tvCantidadPlato.getText().toString()));

            itemsPedido.setPlatoPedido(p);

            Double precio = itemsPedido.getCantidad() * p.getPrecio();
            itemsPedido.setPrecioPlato(precio);

            //PlatoRepository.getInstance().addItems(itemsPedido);
            listaItemsPedido.add(itemsPedido);



        };

        Button.OnClickListener btnQuitarListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnQuitar.setEnabled(false);
                holder.btnAñadir.setEnabled(true);

            }
        };

         SeekBar.OnSeekBarChangeListener regCantidadListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    //  creditoInicial.setText(String.valueOf(regCredito.getProgress()));
                    holder.tvCantidadPlato.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        holder.btnAñadir.setOnClickListener(btnAñadirListener);
        holder.btnQuitar.setOnClickListener(btnQuitarListener);
        holder.sbCantidadPlato.setOnSeekBarChangeListener(regCantidadListener);


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
        TextView tvCantidadPlato;

        SeekBar sbCantidadPlato;



        // botones
        Button btnAñadir;
        Button btnQuitar ;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);

            contexto = itemView.getContext();

            this.imagenPlato = itemView.findViewById(R.id.plato_imagen);
            this.tvnombrePlato = itemView.findViewById(R.id.tvPlatoNombre);
            this.tvprecioPlato = itemView.findViewById(R.id.tvPlatoPrecio);
            this.tvdescripcionPlato = itemView.findViewById(R.id.tvdescripcionPlato);
            this.tvCantidadPlato = itemView.findViewById(R.id.tvCantidadPlato);
            this.sbCantidadPlato = itemView.findViewById(R.id.sbCantidadPlato);

            btnAñadir = itemView.findViewById(R.id.buttonAñadir);
            btnQuitar = itemView.findViewById(R.id.buttonQuitar);
        }
    }

}
