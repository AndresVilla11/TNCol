package com.mycompany.main.crud;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class datoArchivo {

    private int id;
    
    private String nombre;
    private String dependencia;
    private String fecha;
    private String archivo;
    private String ext;

    public datoArchivo(String nombre, String dependencia, String fecha, String archivo, String ext) {
        this.nombre = nombre;
        this.dependencia = dependencia;
        this.fecha = fecha;
        this.archivo = archivo;
        this.ext = ext;
    }

    public datoArchivo() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "datoArchivo{" + "nombre=" + nombre + ", dependencia=" + dependencia + ", fecha=" + fecha + ", archivo=" + archivo + '}';
    }

}
