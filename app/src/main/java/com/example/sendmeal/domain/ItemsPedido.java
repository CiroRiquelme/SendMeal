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

@Entity(foreignKeys = @ForeignKey(parentColumns = "id_pedido", childColumns = "id_pedido_item", entity = Pedido.class , onDelete = ForeignKey.CASCADE))
public class ItemsPedido  {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_item")
    private Integer id;

    private Integer cantidad;

    private Double precioPlato;

/*    @Embedded
    private Pedido pedido;*/

    @ColumnInfo(name = "id_pedido_item")
    private Integer idPedido;

    @Embedded
    private Plato platoPedido;

    public ItemsPedido() {
    }

    @Override
    public String toString() {
        return "ItemsPedido{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", precioPlato=" + precioPlato +
                ", idPedido=" + idPedido +
                ", platoPedido=" + platoPedido +
                '}';
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
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

    public Double getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(Double precioPlato) {
        this.precioPlato = precioPlato;
    }

    public Plato getPlatoPedido() {
        return platoPedido;
    }

    public void setPlatoPedido(Plato platoPedido) {
        this.platoPedido = platoPedido;
    }








}
