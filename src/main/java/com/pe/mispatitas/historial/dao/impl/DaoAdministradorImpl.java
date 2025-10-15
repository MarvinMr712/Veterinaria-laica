/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pe.mispatitas.historial.dao.impl;

import com.pe.mispatitas.historial.dao.DaoAdministrador;
import entidad.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import java.sql.Statement;

/**
 *
 * @author Luis Santos
 */
public class DaoAdministradorImpl implements DaoAdministrador{

    private final Conexion conexion;
    private String mensaje;

    public DaoAdministradorImpl() {
        conexion = new Conexion();
    }

    @Override
    public List<Administrador> administradorSel() {

        List<Administrador> lista = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idAdministrador,")
                .append("nombreAdministrador,")
                .append("apellidoAdministrador,")
                .append("correo,")
                .append("CAST(AES_DECRYPT(contraseña,'AES') AS CHAR) ")
                .append("FROM administrador");
       
        try (Connection cn = conexion.getConexion()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                Administrador administrador = new Administrador();
                administrador.setIdAdministrador(rs.getInt(1));
                administrador.setNombreAdministrador(rs.getString(2));
                administrador.setApellidoAdministrador(rs.getString(3));
                administrador.setCorreo(rs.getString(4));
                administrador.setContra(rs.getString(5));
                lista.add(administrador);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return lista;

    }

    @Override
    public String calcularMD5MySQL(String contrasena) {
        String sql="SELECT CAST(AES_DECRYPT(?,'AES') AS CHAR) AS contra_Desencriptada FROM administrador";
//        String sql = "SELECT MD5(?) AS hash_md5";
        try (Connection cn = conexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("contra_Desencriptada");
                }
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return null;
    }

    @Override
    public Administrador administradorGet(Integer id) {

        Administrador administrador = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idAdministrador,")
                .append("nombreAdministrador,")
                .append("apellidoAdministrador,")
                .append("correo,")
                .append("CAST(AES_DECRYPT(contraseña,'AES') AS CHAR) ")
                .append("FROM administrador ")
                .append("WHERE idAdministrador = ?");
        try (Connection cn = conexion.getConexion()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            administrador = new Administrador();
            if (rs.next()) {
                administrador.setIdAdministrador(rs.getInt(1));
                administrador.setNombreAdministrador(rs.getString(2));
                administrador.setApellidoAdministrador(rs.getString(3));
                administrador.setCorreo(rs.getString(4));
                administrador.setContra(rs.getString(5));
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return administrador;

    }

    @Override
    public int administradorIns(Administrador administrador) {
        int idGenerado = -1; // Valor por defecto si la inserción falla

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO administrador(")
                .append("nombreAdministrador,")
                .append("apellidoAdministrador,")
                .append("correo,")
                .append("contraseña ")
                .append(") VALUES (?,?,?,AES_ENCRYPT(?,'AES'))");// Utilizar la función MD5 de MySQL

        try (Connection cn = conexion.getConexion()) {
            // Establecer el flag de retorno de claves generadas automáticamente
            try (PreparedStatement ps = cn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, administrador.getNombreAdministrador());
                ps.setString(2, administrador.getApellidoAdministrador());
                ps.setString(3, administrador.getCorreo());
                ps.setString(4, administrador.getContra());

                int resultado = ps.executeUpdate();

                if (resultado == 0) {
                    System.err.println("Cero registros agregados");
                } else {
                    // Recuperar el ID generado automáticamente
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            idGenerado = generatedKeys.getInt(1);
                            System.out.println("ID generado: " + idGenerado);
                        } else {
                            System.err.println("No se pudo obtener el ID generado");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return idGenerado;

    }

    @Override
    public String administradorUpd(Administrador administrador) {

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE administrador SET ")
                .append("nombreAdministrador = ?,")
                .append("apellidoAdministrador = ?,")
                .append("correo = ?,")
                .append("contraseña = ? ")
                .append("WHERE idAdministrador = ?");
        try (Connection cn = conexion.getConexion()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, administrador.getNombreAdministrador());
            ps.setString(2, administrador.getApellidoAdministrador());
            ps.setString(3, administrador.getCorreo());
            ps.setString(4, administrador.getContra());
            ps.setInt(5, administrador.getIdAdministrador());
            int resultado = ps.executeUpdate();
            if (resultado == 0) {
                mensaje = "Cero registros actualizados";
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return mensaje;

    }

    @Override
    public String administradorDel(Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM administrador")
                .append(" WHERE idAdministrador = ?");
        try (Connection cn = conexion.getConexion()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            int resultado = ps.executeUpdate();
            if (resultado == 0) {
                mensaje = "Cero registros eliminados";
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
}
