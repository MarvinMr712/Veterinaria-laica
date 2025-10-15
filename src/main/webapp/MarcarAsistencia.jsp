<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Registro de Asistencia</title>
        <link href="css/estilo1.css" rel="stylesheet" type="text/css"/>
        <%@include file="WEB-INF/jspf/enlaces.jspf" %>
        <style>
            .form-container {
                max-width: 500px;
                margin: 120px auto;
                padding: 30px;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }
            .form-container h2 {
                text-align: center;
                margin-bottom: 20px;
            }
            .form-container label {
                font-weight: bold;
                display: block;
                margin-top: 15px;
            }
            .form-container input, .form-container button {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
            }
            .btn-ingreso {
                background-color: #2ecc71;
                color: white;
                cursor: pointer;
            }
            .btn-salida {
                background-color: #e74c3c;
                color: white;
                cursor: pointer;
            }
        </style>
    </head>
    <body>

        <%@ include file="WEB-INF/jspf/encabezadoVeterinario.jspf" %>
        <div class="form-container">
            <c:if test="${not empty mensaje}">
                <div id="mensajeExito" style="color: green; font-weight: bold; text-align: center; margin-bottom: 20px;">
                    ${mensaje}
                </div>
            </c:if>
            <h2>Registro de Asistencia</h2>

            <form action="AsistenciaServlet" method="post">
                <label for="idVeterinario">ID del Veterinario</label>
                <input type="text" id="idVeterinario" name="idVeterinario" readonly required>

                <label>Hora actual</label>
                <input type="text" id="horaActual" readonly>

                <input type="hidden" name="tipoRegistro" id="tipoRegistro" value="">

                <button type="submit" class="btn-ingreso" onclick="setTipo('ingreso')">Marcar Ingreso</button>
                <button type="submit" class="btn-salida" onclick="setTipo('salida')">Marcar Salida</button>
            </form>
        </div>

        <script>
            // Mostrar hora actual solo como referencia visual
            const horaInput = document.getElementById('horaActual');
            setInterval(() => {
                const ahora = new Date();
                horaInput.value = ahora.toLocaleTimeString();
            }, 1000);

            // Establecer tipo de registro (ingreso o salida)
            function setTipo(tipo) {
                document.getElementById('tipoRegistro').value = tipo;
            }
            function ocultarMensaje(id) {
                const mensaje = document.getElementById(id);
                if (mensaje) {
                    setTimeout(() => {
                        mensaje.style.transition = "opacity 0.5s ease";
                        mensaje.style.opacity = 0;
                        setTimeout(() => mensaje.style.display = "none", 500);
                    }, 3000);
                }
            }

            // Cargar ID desde localStorage
            window.addEventListener('DOMContentLoaded', () => {
                const id = localStorage.getItem('idVeterinario');
                if (id) {
                    document.getElementById('idVeterinario').value = id;
                } else {
                    alert("No se encontró el ID del veterinario en localStorage.");
                }
                ocultarMensaje("mensajeExito");
            });
        </script>

    </body>
</html>