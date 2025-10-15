/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.pe.mispatitas.historial.test;

import com.pe.mispatitas.historial.dao.DaoAsistencia;
import com.pe.mispatitas.historial.dao.impl.DaoAsistenciaImpl;
import entidad.Asistencia;
import java.util.List;

/**
 *
 * @author Luis Santos
 */
public class TestAsistencia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        DaoAsistencia dao=new DaoAsistenciaImpl();
        //Probar Select
        // try {
        //     List<Asistencia> lista = dao.asistenciaSel();
        //     lista.forEach(t->System.out.println(t.getIdAsistencia()));
        // } catch (Exception e) {
        //     dao.getMensaje();
        // }

        //Probar Insert
        try {
            Asistencia asistencia = new Asistencia(null, 6, "2025-06-23", "9:00", "15:00", "TARDANZA");
            System.out.println(dao.asistenciaIns(asistencia));
        } catch (Exception e) {
            dao.getMensaje();
        }
        
        //Probar Update
        // try {
        //     Asistencia asistencia = new Asistencia(1, 6, "2025-06-23", "8:00", "15:00", "ASISTENCIA");
        //     System.out.println(dao.asistenciaUpd(asistencia));
        // } catch (Exception e) {
        //     dao.getMensaje();
        // }
        
        //Probar Delete
        // try {
        //     System.out.println(dao.asistenciaDel(1));
        // } catch (Exception e) {
        //     dao.getMensaje();
        // }
        
    }
    
}
