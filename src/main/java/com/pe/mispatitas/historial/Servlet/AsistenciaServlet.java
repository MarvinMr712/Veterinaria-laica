/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.pe.mispatitas.historial.Servlet;

import com.pe.mispatitas.historial.dao.DaoAsistencia;
import com.pe.mispatitas.historial.dao.impl.DaoAsistenciaImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import entidad.Asistencia;

/**
 *
 * @author Luis Santos
 */
@WebServlet(name = "AsistenciaServlet", urlPatterns = {"/AsistenciaServlet"})
public class AsistenciaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Parámetros del formulario
        String idVetStr = request.getParameter("idVeterinario");
        String tipoRegistro = request.getParameter("tipoRegistro");

        if (idVetStr == null || tipoRegistro == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos incompletos");
            return;
        }

        int idVeterinario = Integer.parseInt(idVetStr);
        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        String fecha = fechaActual.toString(); // formato: YYYY-MM-DD
        String hora = horaActual.toString().substring(0, 5); // HH:mm

        DaoAsistencia dao = new DaoAsistenciaImpl();

        try {
            String mensaje = "";

            if ("ingreso".equals(tipoRegistro)) {
                // Obtener hora esperada desde tu DAO (si aplicas lógica de tardanza real)
                // String estado = determinarEstado(hora, horaEsperada);

                String estado = determinarEstado(hora, "08:00"); // Aquí puedes poner la hora configurada
                Asistencia asistencia = new Asistencia(null, idVeterinario, fecha, hora, null, estado);
                dao.asistenciaIns(asistencia);
                mensaje = "Ingreso registrado correctamente como " + estado;

            } else if ("salida".equals(tipoRegistro)) {
                Asistencia asistencia = dao.buscarPorFechaYVeterinario(idVeterinario, fecha);

                if (asistencia != null) {
                    asistencia.setHoraSalida(hora);
                    dao.asistenciaUpd(asistencia);
                    mensaje = "Salida registrada correctamente";
                } else {
                    mensaje = "No se encontró registro de ingreso para hoy";
                }
            }

            // Enviar mensaje al JSP
            request.setAttribute("mensaje", mensaje);
            request.getRequestDispatcher("MarcarAsistencia.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error en el registro de asistencia: " + e.getMessage());
            request.getRequestDispatcher("MarcarAsistencia.jsp").forward(request, response);
        }

    }

    private String determinarEstado(String horaLlegadaReal, String horaEsperada) {
        LocalTime llegada = LocalTime.parse(horaLlegadaReal);
        LocalTime horaLimite = LocalTime.parse(horaEsperada);

        if (llegada.isAfter(horaLimite)) {
            return "TARDANZA";
        } else {
            return "ASISTENCIA";
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
