<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script src="Javas/app.js" type="module"></script>
        <link href="css/estilo1.css" rel="stylesheet" type="text/css"/>
        <%@include file="WEB-INF/jspf/enlaces.jspf" %>
        <!-- FullCalendar CSS -->
        <link href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.11/index.global.min.css" rel="stylesheet"/>
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.11/locales-all.global.min.js"></script>

        <!-- Estilos personalizados -->
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            #calendar {
                max-width: 900px;
                margin: 0 auto;
            }
            h2 {
                text-align: center;
                margin-bottom: 30px;
            }
            .form-calendar {
                min-height: calc(100vh - 80px);
                width: 100%;
                margin-top: 80px;
                background: #ffffff;
                padding: 40px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                border-radius: 12px;

            }
        </style>
    </head>
    <body>

        <%-- Encabezado --%>
        <%@ include file="WEB-INF/jspf/encabezadoAdministrador.jspf" %>
        <div class="form-calendar">


            <h2>Asistencia de Veterinarios</h2>

            <div style="max-width: 400px; margin: 0 auto 20px; text-align: center;">
                <label for="searchVet"><strong>Buscar Veterinario:</strong></label><br>
                <input type="text" id="searchVet" placeholder="Escriba un nombre..." style="width: 100%; padding: 8px; margin-top: 5px;">

                <label for="selectVet" style="margin-top: 15px; display: block;"><strong>Seleccionar Veterinario:</strong></label>
                <select id="selectVet" style="width: 100%; padding: 8px;">
                    <option value="">-- Seleccione --</option>

                </select>
            </div>


            <div id="calendar"></div>

        </div>


        <!-- FullCalendar JS -->
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.11/index.global.min.js"></script>

        <!-- Script para inicializar el calendario -->
        <script>
            document.addEventListener('DOMContentLoaded', () => {
                (async function () {
                    const calendarEl = document.getElementById('calendar');
                    const inputFiltro = document.getElementById("searchVet");
                    const selectVet = document.getElementById("selectVet");

                    const eventosPorVeterinario = {};


                    const calendar = new FullCalendar.Calendar(calendarEl, {
                        initialView: 'dayGridMonth',
                        locale: 'es',
                        headerToolbar: {
                            left: 'prev,next today',
                            center: 'title',
                            right: 'dayGridMonth,timeGridWeek,timeGridDay'
                        },
                        events: []
                    });

                    async function cargarVeterinarios() {
                        try {
                            const response = await fetch('api/veterinarios');
                            const data = await response.json();

                            // Limpiar select y eventos anteriores
                            selectVet.innerHTML = '<option value="">-- Seleccione --</option>';
                            Object.keys(eventosPorVeterinario).forEach(k => delete eventosPorVeterinario[k]);

                            // Construir opciones del select y eventos por veterinario
                            data.veterinarios.forEach(vet => {
                                const option = document.createElement("option");
                                option.value = vet.idVeterinario;
                                option.textContent = vet.nombreVeterinario;
                                selectVet.appendChild(option);

                                // Inicializar eventos vacíos
                                eventosPorVeterinario[vet.idVeterinario] = [];
                            });

                            // Construir los eventos desde asistencias
                            data.asistencias.forEach(asistencia => {
                                const idVet = asistencia.idVeterinario;
                                const estado = asistencia.estado.toUpperCase();
                                let color = '#19b103'; // Presente
                                if (estado === 'TARDANZA')
                                    color = '#f39c12';
                                else if (estado === 'AUSENTE')
                                    color = '#e74c3c';

                                eventosPorVeterinario[idVet]?.push({
                                    title: estado,
                                    start: asistencia.fecha,
                                    color: color
                                });
                            });

                        } catch (error) {
                            console.error("Error cargando veterinarios:", error);
                        }
                    }
                    await cargarVeterinarios();
                    calendar.render();

                    inputFiltro.addEventListener("input", function () {
                        const filtro = this.value.toLowerCase();
                        for (let option of selectVet.options) {
                            const texto = option.text.toLowerCase();
                            option.style.display = texto.includes(filtro) ? "block" : "none";
                        }
                    });

                    selectVet.addEventListener("change", function () {
                        const seleccionado = this.value;
                        cargarEventosPorVeterinario(seleccionado);
                    });

                    function cargarEventosPorVeterinario(veterinario) {
                        calendar.removeAllEvents();
                        if (!veterinario || veterinario === "")
                            return;

                        const eventos = eventosPorVeterinario[veterinario] || [];
                        eventos.forEach(evento => calendar.addEvent(evento));
                    }

                    cargarEventosPorVeterinario("");
                })();
            });
        </script>



        <%@include file="WEB-INF/jspf/pie.jspf" %>

    </body>
</html>