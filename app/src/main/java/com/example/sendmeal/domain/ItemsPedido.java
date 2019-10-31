package com.example.sendmeal.domain;


import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class ItemsPedido {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_item")
    private Integer id;
    private Integer cantidad;
    private Double precioPedido;

    @Embedded
    private Pedido pedido;

    @Embedded
    private Plato platoPedido;





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
}
