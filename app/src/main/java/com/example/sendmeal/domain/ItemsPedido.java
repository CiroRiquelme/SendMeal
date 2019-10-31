package com.example.sendmeal.domain;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ItemsPedido implements Parcelable  {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_item")
    private Integer id;
    private Integer cantidad;
    private Double precioPedido;

    @Embedded
    private Pedido pedido;

    @Embedded
    private Plato platoPedido;

    @Ignore
    public ItemsPedido() {
    }

    public ItemsPedido(Parcel parcel){
        id = parcel.readInt();
        cantidad = parcel.readInt();
        precioPedido = parcel.readDouble();
    }

    public ItemsPedido(Integer id, Integer cantidad, Double precioPedido, Pedido pedido, Plato platoPedido) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioPedido = precioPedido;
        this.pedido = pedido;
        this.platoPedido = platoPedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioPedido() {
        return precioPedido;
    }

    public void setPrecioPedido(Double precioPedido) {
        this.precioPedido = precioPedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Plato getPlatoPedido() {
        return platoPedido;
    }

    public void setPlatoPedido(Plato platoPedido) {
        this.platoPedido = platoPedido;
    }

    @Override
    public String toString() {
        return "ItemsPedido{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", precioPedido=" + precioPedido +
                ", pedido=" + pedido +
                ", platoPedido=" + platoPedido +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if(id!=null)parcel.writeInt(id);
        if (cantidad!=null)parcel.writeInt(cantidad);
        if(precioPedido!=null)parcel.writeDouble(precioPedido);
        if(pedido!=null)parcel.writeInt(pedido.getId());
        if(platoPedido!=null)parcel.writeInt(platoPedido.getId());
    }

    public static final Creator<ItemsPedido> CREATOR = new Creator<ItemsPedido>() {
        @Override
        public ItemsPedido createFromParcel(Parcel parcel) {
            return new ItemsPedido(parcel);
        }

        @Override
        public ItemsPedido[] newArray(int i) {
            return new ItemsPedido[i];
        }

        public List<ItemsPedido> newArrayList(){
            return new ArrayList<ItemsPedido>();
        }
    };






}
