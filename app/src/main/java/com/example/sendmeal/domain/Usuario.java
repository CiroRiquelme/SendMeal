package com.example.sendmeal.domain;

import java.util.Objects;

public class Usuario {

    private Integer id;
    private String nombre;
    private String mail;
    private String clave;
    private Boolean notificarMail;
    private Double credito;

    public Usuario(Integer id, String nombre, String mail, String clave, Boolean notificarMail, Double credito) {
        this.id = id;
        this.nombre = nombre;
        this.mail = mail;
        this.clave = clave;
        this.notificarMail = notificarMail;
        this.credito = credito;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getNotificarMail() {
        return notificarMail;
    }

    public void setNotificarMail(Boolean notificarMail) {
        this.notificarMail = notificarMail;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(nombre, usuario.nombre) &&
                Objects.equals(mail, usuario.mail) &&
                Objects.equals(clave, usuario.clave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, mail, clave);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", clave='" + clave + '\'' +
                ", notificarMail=" + notificarMail +
                ", credito=" + credito +
                '}';
    }
}
