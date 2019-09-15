package com.example.sendmeal.Utilidades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sendmeal.R;
import com.example.sendmeal.domain.Plato;

import java.util.List;

public class PlatosRecyclerAdapter extends RecyclerView.Adapter<PlatosRecyclerAdapter.PlatoViewHolder> {

    private List<Plato> mDataset;

    public PlatosRecyclerAdapter (List<Plato> myDataset) {

        this.mDataset= myDataset;
    }

    @NonNull
    @Override
    public PlatosRecyclerAdapter.PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato, parent, false);
        PlatoViewHolder vh = new PlatoViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatosRecyclerAdapter.PlatoViewHolder holder, int position) {

        Plato plato = mDataset.get(position);

        holder.imagenPlato.setImageResource(R.drawable.hamburguesa);
        holder.tvnombrePlato.setText(plato.getTitulo());
        holder.tvprecioPlato.setText(plato.getPrecio().toString());
        holder.tvdescripcionPlato.setText(plato.getDescripcion());

    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class PlatoViewHolder extends RecyclerView.ViewHolder {

        ImageView imagenPlato;
        TextView tvnombrePlato;
        TextView tvprecioPlato;
        TextView tvdescripcionPlato;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imagenPlato = itemView.findViewById(R.id.plato_imagen);
            this.tvnombrePlato = itemView.findViewById(R.id.tvPlatoNombre);
            this.tvprecioPlato = itemView.findViewById(R.id.tvPlatoPrecio);
            this.tvdescripcionPlato = itemView.findViewById(R.id.tvdescripcionPlato);
        }
    }
}
