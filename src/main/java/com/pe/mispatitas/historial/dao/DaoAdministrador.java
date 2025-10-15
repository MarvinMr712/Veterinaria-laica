/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pe.mispatitas.historial.dao;

import entidad.Administrador;
import java.util.List;

/**
 *
 * @author Luis Santos
 */
public interface DaoAdministrador {

    List<Administrador> administradorSel();

    Administrador administradorGet(Integer id);

    int administradorIns(Administrador administrador);

    String administradorUpd(Administrador administrador);

    String administradorDel(Integer id);
    
    String calcularMD5MySQL(String contrasena);

    String getMensaje();

}
