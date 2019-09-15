package com.example.sendmeal.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Plato {

    private Integer id;
    private String titulo;
    private String descripcion;
    private Double precio;
    private Integer calorias;

    private List<Plato> listaPlatos;

    public static List<Plato> lista(){
        ArrayList<Plato> listaPlatos = new ArrayList<>();

       Plato p1 = new Plato(01, "Hamburguesa", "Completa", 100.00,1000);
       Plato p2 = new Plato(02, "Hamburguesa ", "Americana", 200.00,2000);
       listaPlatos.add(p1);
       listaPlatos.add(p2);

        return listaPlatos;
    }

    public List<Plato> getListaPlatos() {
        return listaPlatos;
    }

    public void setListaPlatos(List<Plato> listaPlatos) {
        this.listaPlatos = listaPlatos;
    }

    public Plato(Integer id, String titulo, String descripcion, Double precio, Integer calorias) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plato plato = (Plato) o;
        return Objects.equals(id, plato.id) &&
                Objects.equals(titulo, plato.titulo) &&
                Objects.equals(descripcion, plato.descripcion) &&
                Objects.equals(precio, plato.precio) &&
                Objects.equals(calorias, plato.calorias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descripcion, precio, calorias);
    }

    @Override
    public String toString() {
        return "Plato{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", calorias=" + calorias +
                '}';
    }
}
