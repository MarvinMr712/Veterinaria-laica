package com.pe.mispatitas.historial.Servlet;

import com.pe.mispatitas.historial.dao.DaoMascota;
import com.pe.mispatitas.historial.dao.impl.DaoMascotaImpl;
import entidad.Mascota;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Base64;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "listarMascotaServlet", urlPatterns = {"/listarMascota"})
public class listarMascotaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        DaoMascota dao = new DaoMascotaImpl();
        List<Mascota> listaMascota = dao.mascotaSel();

        // Convertir fotos a Base64 (si existen)
        for (Mascota mascota : listaMascota) {
            InputStream inputStream = mascota.getFoto();
            if (inputStream != null) {
                byte[] bytes = inputStream.readAllBytes();
                String imagenBase64 = Base64.getEncoder().encodeToString(bytes);
                mascota.setImagenBase64(imagenBase64);
            } else {
                mascota.setImagenBase64(null);
            }
        }

        // Obtener idUsuario desde parámetro o sesión
        String idUsuarioParam = request.getParameter("idUsuario");
        Integer idUsuario = null;
        if (idUsuarioParam != null) {
            idUsuario = Integer.valueOf(idUsuarioParam);
            // Guardar en sesión para futuras consultas
            request.getSession().setAttribute("idUsuario", idUsuario);
        } else if (request.getSession().getAttribute("idUsuario") != null) {
            idUsuario = (Integer) request.getSession().getAttribute("idUsuario");
        }

        request.setAttribute("idUsuario", idUsuario);
        request.setAttribute("listaMascota", listaMascota);

        request.getRequestDispatcher("mascotaUsuario.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para listar mascotas del usuario";
    }
}