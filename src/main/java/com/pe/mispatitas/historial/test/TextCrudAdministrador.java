/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.pe.mispatitas.historial.test;

import com.pe.mispatitas.historial.dao.DaoAdministrador;
import com.pe.mispatitas.historial.dao.impl.DaoAdministradorImpl;
import entidad.Administrador;
import java.util.List;


/**
 *
 * @author Luis Santos
 */
public class TextCrudAdministrador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        DaoAdministrador dao = new DaoAdministradorImpl();
//  Probar SELECT
    //    try {
    //        List<Administrador> lista = dao.administradorSel();
    //        lista.forEach(t->System.out.println(t.getIdAdministrador()+"--"+t.getContra()));
    //    } catch (Exception e) {
    //        dao.getMensaje();
    //    }

        // Probar SELECT
        // try {
        //     Administrador cat = dao.administradorGet(1);
        //     System.out.println(cat.getContra());
        // } catch (Exception e) {
        //     dao.getMensaje();
        // }

   
        // Probar INSERT
       try {
           Administrador cat = new Administrador(null, "luis", "santos","luisadmin@gmail.com", "123456");
           System.out.println(dao.administradorIns(cat));
       } catch (Exception e) {
           dao.getMensaje();
       }

    // Probar UPDATE
    //    try {
    //        Administrador cat = new Administrador(1, "leonel", "messi10","messi@gmail.com", "123456");
    //        System.out.println(dao.administradorUpd(cat));
    //    } catch (Exception e) {
    //        dao.getMensaje();
    //    }
        //probar delete
    //    try {
    //        System.out.println(dao.administradorDel(1));
    //    } catch (Exception e) {
    //        dao.getMensaje();
    //    }


    }
    
}
