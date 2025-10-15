/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pe.mispatitas.historial.dao;

import entidad.Asistencia;
import java.util.List;

/**
 *
 * @author Luis Santos
 */
public interface DaoAsistencia {

    List<Asistencia> asistenciaSel();
    Asistencia asistenciaGet(Integer id);
    int asistenciaIns(Asistencia asistencia);
    String asistenciaUpd(Asistencia asistencia);
    String asistenciaDel(Integer id);
    Asistencia buscarPorFechaYVeterinario(Integer idVeterinario, String fecha);
    String getMensaje();
}
