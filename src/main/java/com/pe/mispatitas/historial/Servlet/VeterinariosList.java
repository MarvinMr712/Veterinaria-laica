/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.pe.mispatitas.historial.Servlet;

import com.google.gson.Gson;
import com.pe.mispatitas.historial.dao.DaoAsistencia;
import com.pe.mispatitas.historial.dao.DaoVeterinario;
import com.pe.mispatitas.historial.dao.impl.DaoAsistenciaImpl;
import com.pe.mispatitas.historial.dao.impl.DaoVeterinarioImpl;
import entidad.Asistencia;
import entidad.Veterinario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author Luis Santos
 */
@WebServlet(name = "api/veterinarios", urlPatterns = {"/api/veterinarios"})
public class VeterinariosList extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        DaoVeterinario dao = new DaoVeterinarioImpl();
        List<Veterinario> veterinarios = dao.veterinarioSel();

        //asistencia
        DaoAsistencia daoAsistencia = new DaoAsistenciaImpl();
        List<Asistencia> listaAsistencia = daoAsistencia.asistenciaSel();
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("veterinarios", veterinarios);
        resultado.put("asistencias", listaAsistencia);

        String json = new Gson().toJson(resultado);
        response.getWriter().write(json);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
