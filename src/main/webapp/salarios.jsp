<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@page
contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>JSP Page</title>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="Javas/app.js" type="module"></script>
    <link href="css/estilo1.css" rel="stylesheet" type="text/css" />
    <%@include file="WEB-INF/jspf/enlaces.jspf" %>
    <style>
      body {
        font-family: Arial, sans-serif;
      }

      h2 {
        text-align: center;
        margin-bottom: 30px;
      }
      .form-salario {
        min-height: calc(100vh - 80px);
        width: 100%;
        margin-top: 80px;
        background: #ffffff;
        padding: 40px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        border-radius: 12px;
      }
    </style>
    <script>
      document.addEventListener("DOMContentLoaded", () => {
        (async function () {
          const inputFiltro = document.getElementById("searchVet");
          const selectVet = document.getElementById("selectVet");
          const tablaBody = document.getElementById("tablaAsistenciaBody");

          async function cargarVeterinarios() {
            try {
              const response = await fetch("api/veterinarios");
              const data = await response.json();

              // Limpiar select
              selectVet.innerHTML =
                '<option value="">-- Seleccione --</option>';

              // Llenar opciones del select
              data.veterinarios.forEach((vet) => {
                const option = document.createElement("option");
                option.value = vet.idVeterinario;
                option.textContent = vet.nombreVeterinario;
                selectVet.appendChild(option);
              });

              console.log("data.veterinarios", data.veterinarios);
              console.log("data.asistencias", data.asistencias);

              // Llenar tabla de asistencia
              llenarTabla(data.veterinarios, data.asistencias);
            } catch (error) {
              console.error("Error cargando veterinarios:", error);
            }
          }

          function llenarTabla(veterinarios, asistencias) {
            tablaBody.innerHTML = "";

            veterinarios.forEach((vet) => {
              const asistenciasVet = asistencias.filter(
                (a) => a.idVeterinario === vet.idVeterinario
              );
              const diasTarde = asistenciasVet.filter(
                (a) => a.estado === "TARDANZA"
              ).length;
              const diasFalta = asistenciasVet.filter(
                (a) => a.estado === "AUSENTE"
              ).length;

              // Calculamos los minutos de tardanza
              let totalMinutosTarde = 0;
              const horaIngreso = vet.hora_ingreso; // formato: "08:00:00"
              const [ingresoH, ingresoM] = horaIngreso.split(":").map(Number);
              const ingresoEnMinutos = ingresoH * 60 + ingresoM;

              asistenciasVet.forEach((asistencia) => {
                if (asistencia.estado === "TARDANZA") {
                  const [llegadaH, llegadaM] = asistencia.horaLlegada
                    .split(":")
                    .map(Number);
                  const llegadaEnMinutos = llegadaH * 60 + llegadaM;
                  const minutosTarde = Math.max(
                    0,
                    llegadaEnMinutos - ingresoEnMinutos
                  );
                  totalMinutosTarde += minutosTarde;
                }
              });

              // Descuentos
              const descuentoPorMinuto = 0.5;
              const descuentoPorFalta = 80.0;

              const descuentoTardanza = totalMinutosTarde * descuentoPorMinuto;
              const descuentoFaltas = diasFalta * descuentoPorFalta;
              const descuentoTotal = descuentoTardanza + descuentoFaltas;

              const sueldoBase = parseFloat(vet.sueldo);
              const sueldoFinal = sueldoBase - descuentoTotal;

              const fila = document.createElement("tr");

              fila.innerHTML =
                "<td>" +
                vet.idVeterinario +
                "</td>" +
                "<td>" +
                vet.nombreVeterinario +
                "</td>" +
                "<td>" +
                diasTarde +
                "</td>" +
                "<td>" +
                diasFalta +
                "</td>" +
                "<td>" +
                totalMinutosTarde +
                " min</td>" +
                "<td><strong>S/. " +
                descuentoTotal.toFixed(2) +
                "</strong></td>" +
                "<td><strong>S/. " +
                sueldoBase.toFixed(2) +
                "</strong></td>" +
                "<td><strong>S/. " +
                sueldoFinal.toFixed(2) +
                "</strong></td>" +
                '<td><button class="btnDetalles" data-id="' +
                vet.idVeterinario +
                '">Ver detalles</button></td>';

              tablaBody.appendChild(fila);

              // Attach event to the "Ver detalles" button
              const btnDetalles = fila.querySelector(".btnDetalles");
              btnDetalles.addEventListener("click", function () {
                mostrarModal(vet, asistenciasVet);
              });
            });
          }

          // Function to show modal with details
          function mostrarModal(vet, asistenciasVet) {
            const modal = document.getElementById("detalleModal");
            const contenido = document.getElementById("modalContenido");
            console.log("mostrarModal", vet, asistenciasVet);

            let asistenciasHTML = "";
            for (let i = 0; i < asistenciasVet.length; i++) {
              const a = asistenciasVet[i];
              asistenciasHTML +=
                "<li>" +
                a.fecha +
                " - " +
                a.estado +
                " - " +
                (a.horaLlegada || "") +
                " - " +
                (a.horaSalida || "No marcado") +
                "</li>";
            }

            contenido.innerHTML =
              "<p><strong>ID:</strong> " +
              vet.idVeterinario +
              "</p>" +
              "<p><strong>Nombre:</strong> " +
              vet.nombreVeterinario +
              "</p>" +
              "<p><strong>Sueldo Base:</strong> S/. " +
              parseFloat(vet.sueldo).toFixed(2) +
              "</p>" +
              "<p><strong>Hora de Ingreso:</strong> " +
              vet.hora_ingreso +
              "</p>" +
              "<p><strong>Hora de Salida:</strong> " +
              vet.hora_salida +
              "</p>" +
              "<hr>" +
              "<h4>Asistencias:</h4>" +
              '<ul style="max-height:200px; overflow:auto;">' +
              asistenciasHTML +
              "</ul>";

            modal.style.display = "flex";
          }

          // Close modal logic
          document.getElementById("cerrarModal").onclick = function () {
            document.getElementById("detalleModal").style.display = "none";
          };
          window.onclick = function (event) {
            const modal = document.getElementById("detalleModal");
            if (event.target === modal) {
              modal.style.display = "none";
            }
          };

          inputFiltro.addEventListener("input", function () {
            const filtro = this.value.toLowerCase();
            for (let option of selectVet.options) {
              const texto = option.text.toLowerCase();
              option.style.display = texto.includes(filtro) ? "block" : "none";
            }
          });

          selectVet.addEventListener("change", async function () {
            const seleccionado = parseInt(this.value);
            if (!seleccionado) return;

            try {
              const response = await fetch("api/veterinarios");
              const data = await response.json();

              const veterinarioSeleccionado = data.veterinarios.filter(
                (v) => v.idVeterinario === seleccionado
              );
              const asistenciasSeleccionadas = data.asistencias.filter(
                (a) => a.idVeterinario === seleccionado
              );

              llenarTabla(veterinarioSeleccionado, asistenciasSeleccionadas);
            } catch (error) {
              console.error("Error filtrando veterinarios:", error);
            }
          });

          await cargarVeterinarios();
        })();
      });
    </script>
  </head>
  <body>
    <%-- Encabezado --%> <%@ include
    file="WEB-INF/jspf/encabezadoAdministrador.jspf" %>
    <div class="form-salario">
      <h2>Salario de Veterinarios</h2>

      <div style="max-width: 400px; margin: 0 auto 20px; text-align: center">
        <label for="searchVet"><strong>Buscar Veterinario:</strong></label
        ><br />
        <input
          type="text"
          id="searchVet"
          placeholder="Escriba un nombre..."
          style="width: 100%; padding: 8px; margin-top: 5px"
        />

        <label for="selectVet" style="margin-top: 15px; display: block"
          ><strong>Seleccionar Veterinario:</strong></label
        >
        <select id="selectVet" style="width: 100%; padding: 8px">
          <option value="">-- Seleccione --</option>
        </select>
      </div>

      <div class="btnAgregar">
        <button id="btnImprimirPDF" class="imprimir">Imprimir PDF</button>
      </div>

      <div id="tablaAsistenciaContainer" style="margin-top: 30px">
        <table
          border="1"
          width="100%"
          style="border-collapse: collapse; text-align: center"
        >
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Días tarde</th>
              <th>Días faltas</th>
              <th>Total Minutos tarde</th>
              <th>Descuento total</th>
              <th>Sueldo base</th>
              <th>Sueldo final</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody id="tablaAsistenciaBody">
            <!-- Las filas se llenarán por JavaScript -->
          </tbody>
        </table>
      </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.debug.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.3.2/html2canvas.js"></script>
    <script>
      const tabla = document.querySelector("#tablaAsistenciaContainer");
      const boton = document.querySelector(".imprimir");
      boton.addEventListener("click", () => {
        const doc = new jsPDF("p", "pt", "letter");
        const margin = 20;
        const scale =
          (doc.internal.pageSize.width - margin * 2) /
          document.body.scrollWidth;
        doc.html(tabla, {
          x: margin,
          y: margin,
          html2canvas: {
            scale: scale,
          },
          callback: function (doc) {
            doc.save("salarios");
          },
        });
      });
    </script>

    <!-- Modal Structure -->
    <div
      id="detalleModal"
      style="
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100vw;
        height: 100vh;
        background: rgba(0, 0, 0, 0.5);
        align-items: center;
        justify-content: center;
        z-index: 1000;
      "
    >
      <div
        style="
          background: #fff;
          padding: 30px;
          border-radius: 10px;
          min-width: 300px;
          max-width: 90vw;
          position: relative;
        "
      >
        <span
          id="cerrarModal"
          style="
            position: absolute;
            top: 10px;
            right: 15px;
            cursor: pointer;
            font-size: 22px;
          "
          >&times;</span
        >
        <h3>Detalles del Veterinario</h3>
        <div id="modalContenido">
          <!-- Details will be filled by JS -->
        </div>
      </div>
    </div>

    <%@include file="WEB-INF/jspf/pie.jspf" %>
  </body>
</html>
