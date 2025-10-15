/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pe.mispatitas.historial.dao.impl;

import com.pe.mispatitas.historial.dao.DaoAsistencia;
import entidad.Asistencia;
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
public class DaoAsistenciaImpl implements DaoAsistencia {

    private final Conexion conexion;
    private String mensaje;

    public DaoAsistenciaImpl() {
        conexion = new Conexion();
    }

    @Override
    public List<Asistencia> asistenciaSel() {

        List<Asistencia> lista = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idAsistencia,")
                .append("idVeterinario,")
                .append("fecha,")
                .append("hora_llegada_real,")
                .append("hora_salida_real,")
                .append("estado ")
                .append("FROM asistencia");

        try (Connection cn = conexion.getConexion()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setIdAsistencia(rs.getInt(1));
                asistencia.setIdVeterinario(rs.getInt(2));
                asistencia.setFecha(rs.getString(3));
                asistencia.setHoraLlegada(rs.getString(4));
                asistencia.setHoraSalida(rs.getString(5));
                asistencia.setEstado(rs.getString(6));
                lista.add(asistencia);
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return lista;

    }

    @Override
    public Asistencia asistenciaGet(Integer id) {

        Asistencia asistencia = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idAsistencia,")
                .append("idVeterinario,")
                .append("fecha,")
                .append("hora_llegada_real,")
                .append("hora_salida_real,")
                .append("estado ")
                .append("FROM asistencia ")
                .append("WHERE idAsistencia = ?");
        try (Connection cn = conexion.getConexion()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            asistencia = new Asistencia();
            if (rs.next()) {
                asistencia.setIdAsistencia(rs.getInt(1));
                asistencia.setIdVeterinario(rs.getInt(2));
                asistencia.setFecha(rs.getString(3));
                asistencia.setHoraLlegada(rs.getString(4));
                asistencia.setHoraSalida(rs.getString(5));
                asistencia.setEstado(rs.getString(6));
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        return asistencia;

    }

    @Override
    public int asistenciaIns(Asistencia asistencia) {
        int idGenerado = -1; // Valor por defecto si la inserción falla

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO asistencia(")
                .append("idVeterinario,")
                .append("fecha,")
                .append("hora_llegada_real,")
                .append("hora_salida_real,")
                .append("estado ")
                .append(") VALUES (?,?,?,?,?)");

        try (Connection cn = conexion.getConexion()) {
            // Establecer el flag de retorno de claves generadas automáticamente
            try (PreparedStatement ps = cn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, asistencia.getIdVeterinario());
                ps.setString(2, asistencia.getFecha());
                ps.setString(3, asistencia.getHoraLlegada());
                ps.setString(4, asistencia.getHoraSalida());
                ps.setString(5, asistencia.getEstado());

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
    public String asistenciaUpd(Asistencia asistencia) {

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE asistencia SET ")
                .append("idVeterinario = ?,")
                .append("fecha = ?,")
                .append("hora_llegada_real = ?,")
                .append("hora_salida_real = ?,")
                .append("estado = ? ")
                .append("WHERE idAsistencia = ?");
        try (Connection cn = conexion.getConexion()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, asistencia.getIdVeterinario());
            ps.setString(2, asistencia.getFecha());
            ps.setString(3, asistencia.getHoraLlegada());
            ps.setString(4, asistencia.getHoraSalida());
            ps.setString(5, asistencia.getEstado());
            ps.setInt(6, asistencia.getIdAsistencia());
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
    public String asistenciaDel(Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM asistencia")
                .append(" WHERE idAsistencia = ?");
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

    @Override
    public Asistencia buscarPorFechaYVeterinario(Integer idVeterinario, String fecha) {
        Asistencia asistencia = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("idAsistencia, ")
                .append("idVeterinario, ")
                .append("fecha, ")
                .append("hora_llegada_real, ")
                .append("hora_salida_real, ")
                .append("estado ")
                .append("FROM asistencia ")
                .append("WHERE idVeterinario = ? AND fecha = ?");

        try (Connection cn = conexion.getConexion()) {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, idVeterinario);
            ps.setString(2, fecha);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                asistencia = new Asistencia();
                asistencia.setIdAsistencia(rs.getInt(1));
                asistencia.setIdVeterinario(rs.getInt(2));
                asistencia.setFecha(rs.getString(3));
                asistencia.setHoraLlegada(rs.getString(4));
                asistencia.setHoraSalida(rs.getString(5));
                asistencia.setEstado(rs.getString(6));
            }

        } catch (Exception e) {
            mensaje = e.getMessage();
        }

        return asistencia;
    }

}
