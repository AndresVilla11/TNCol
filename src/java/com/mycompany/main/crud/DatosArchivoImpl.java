package com.mycompany.main.crud;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatosArchivoImpl implements IDatosArchivo {

    @Override
    public boolean registrar(datoArchivo cliente) {
        boolean registrar = false;

        Statement stm = null;
        Connection con = null;
        
        String sql = "INSERT INTO tnc.new_table values (NULL,'" + cliente.getNombre() + "','"
                + cliente.getDependencia() + "','" + cliente.getFecha() + "','"
                + cliente.getArchivo() + "')";
        try {
            
            con = Conexion.conectar();
            stm = con.createStatement();
            stm.execute(sql);
            registrar = true;
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: Clase ClienteDaoImple, método registrar");
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registrar;
    }

    @Override
    public List<datoArchivo> obtener() {
        Connection co = null;
        Statement stm = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tnc.clientetnc ORDER BY ID";

        List<datoArchivo> listaCliente = new ArrayList<datoArchivo>();

        try {
            co = Conexion.conectar();
            stm = co.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                datoArchivo c = new datoArchivo();
                c.setId(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setDependencia(rs.getString(3));
                c.setFecha(rs.getString(4));
                c.setArchivo(rs.getString(5));
                listaCliente.add(c);
            }
            stm.close();
            rs.close();
            co.close();
        } catch (SQLException e) {
            System.out.println("Error: Clase ClienteDaoImple, método obtener");
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCliente;
    }

    @Override
    public boolean actualizar(datoArchivo cliente) {
        Connection connect = null;
        Statement stm = null;

        boolean actualizar = false;

        String sql = "UPDATE tnc.clientetnc SET nombre='" + cliente.getNombre() + "', dependencia='" + cliente.getDependencia()
                + "', fecha='" + cliente.getFecha() + "', archivo='" + cliente.getArchivo()
                + "'" + " WHERE ID=" + cliente.getId();
        try {
            connect = Conexion.conectar();
            stm = connect.createStatement();
            stm.execute(sql);
            actualizar = true;
        } catch (SQLException e) {
            System.out.println("Error: Clase ClienteDaoImple, método actualizar");
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actualizar;
    }

    @Override
    public boolean eliminar(datoArchivo cliente) {
        Connection connect = null;
        Statement stm = null;

        boolean eliminar = false;

        String sql = "DELETE FROM tnc.clientetnc WHERE ID=" + cliente.getId();
        try {
            connect = Conexion.conectar();
            stm = connect.createStatement();
            stm.execute(sql);
            eliminar = true;
        } catch (SQLException e) {
            System.out.println("Error: Clase ClienteDaoImple, método eliminar");
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatosArchivoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eliminar;
    }

}
