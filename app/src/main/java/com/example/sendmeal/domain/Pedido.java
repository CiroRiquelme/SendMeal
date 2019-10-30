package com.example.sendmeal.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;


@Entity
public class Pedido {


    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Double lat;
    private Double lbg;

/*    private Date fechaCreacion;
    private List<ItemsPedido> items;
    private EstadoPedido estado;*/

    public Pedido(Integer id, Double lat, Double lbg) {
        this.id = id;
        this.lat = lat;
        this.lbg = lbg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLbg() {
        return lbg;
    }

    public void setLbg(Double lbg) {
        this.lbg = lbg;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", lat=" + lat +
                ", lbg=" + lbg +
                '}';
    }
}
