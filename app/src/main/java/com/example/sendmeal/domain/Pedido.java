package com.example.sendmeal.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.sendmeal.dao.db.Converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@TypeConverters(Converter.class)
public class Pedido {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_pedido")
    private Integer id;
    private Double lat = 0.0;
    private Double lng = 0.0;


    private Date fechaCreacion = new Date();
    private EstadoPedido estado ;

    @Ignore
    private List<ItemsPedido> items = new ArrayList<>();

    @Ignore
    public Pedido() {
    }

    public Pedido(Integer id, Double lat, Double lng, Date fechaCreacion, EstadoPedido estado) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public Pedido(Integer id, Double lat, Double lng, Date fechaCreacion, List<ItemsPedido> items, EstadoPedido estado) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.fechaCreacion = fechaCreacion;
        this.items = items;
        this.estado = estado;
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

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<ItemsPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemsPedido> items) {
        this.items = items;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", lat=" + lat +
                ", lbg=" + lng +
                ", fechaCreacion=" + fechaCreacion +
                ", items=" + items +
                ", estado=" + estado +
                '}';
    }
}
