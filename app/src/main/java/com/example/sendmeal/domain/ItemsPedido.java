package com.example.sendmeal.domain;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ItemsPedido {

    @PrimaryKey(autoGenerate = true)
    Integer id;
    Integer cantidad;
    Double precioPedido;

/*    Pedido pedido;
    Plato platoPedido;*/


    public ItemsPedido(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ItemsPedido{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", precioPedido=" + precioPedido +
                '}';
    }
}
