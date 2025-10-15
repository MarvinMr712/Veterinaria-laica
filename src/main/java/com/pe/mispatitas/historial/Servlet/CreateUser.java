/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.pe.mispatitas.historial.Servlet;

import com.pe.mispatitas.historial.dao.DaoAdministrador;
import com.pe.mispatitas.historial.dao.DaoVeterinario;
import com.pe.mispatitas.historial.dao.impl.DaoAdministradorImpl;
import com.pe.mispatitas.historial.dao.impl.DaoVeterinarioImpl;
import entidad.Administrador;
import entidad.Veterinario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Luis Santos
 */
@WebServlet(name = "CreateUser", urlPatterns = {"/createUsers"})
public class CreateUser extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            String tipoUsuario = request.getParameter("tipoUsuario");
            String mensajeAlerta;

            //veterinario List
            DaoVeterinario daoVeterinario = new DaoVeterinarioImpl();
            List<Veterinario> veterinarios = daoVeterinario.veterinarioSel();
            
            //administrador List
            
            DaoAdministrador daoAdministrador=new DaoAdministradorImpl();
            List<Administrador> administradores=daoAdministrador.administradorSel();

        if ("administrador".equals(tipoUsuario)) {
            String nombre = request.getParameter("nombreAdministrador");
            String apellido = request.getParameter("apellidoAdministrador");
            String correo = request.getParameter("correoAdministrador");
            String contraseña = request.getParameter("contraseñaAdministrador");

            for ( Administrador administrador : administradores) {
            if (administrador.getCorreo().equals(correo)) {
                System.out.println("validando usuario");
                mensajeAlerta = "Correo ya existente";
                request.setAttribute("mensajeAlerta", mensajeAlerta);
                request.getRequestDispatcher("/createUsers.jsp").forward(request, response);
                return;
            }
            }
            
            //register admin
            
            Administrador cat = new Administrador(null, nombre, apellido,correo, contraseña);
           System.out.println(daoAdministrador.administradorIns(cat));
            
        } else if ("veterinario".equals(tipoUsuario)) {
            String nombre = request.getParameter("nombreVeterinario");
            String correo = request.getParameter("correoVeterinario");
            String contraseña = request.getParameter("contraseñaVeterinario");
            String sueldoStr = request.getParameter("sueldo");
            String horaIngreso = request.getParameter("hora_ingreso");
            String horaSalida = request.getParameter("hora_salida");
            
            //double sueldo = Double.parseDouble(sueldoStr);

            for (Veterinario veterinario : veterinarios) {
            if (veterinario.getCorreo().equals(correo)) {
                System.out.println("validando usuario");
                mensajeAlerta = "Correo ya existente";
                request.setAttribute("mensajeAlerta", mensajeAlerta);
                request.getRequestDispatcher("/createUsers.jsp").forward(request, response);
                return;
            }
            }
            
            //register veterinario
            
            Veterinario cat = new Veterinario(null, nombre, correo,contraseña,sueldoStr,horaIngreso,horaSalida);
            System.out.println(daoVeterinario.veterinarioIns(cat));

        }
            request.setAttribute("mensajeExito", "✅ Usuario registrado con éxito.");
            request.getRequestDispatcher("createUsers.jsp").forward(request, response);

        //response.sendRedirect("usuarios.jsp"); 
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
        processRequest(request, response);
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
