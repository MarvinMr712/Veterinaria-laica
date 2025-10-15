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
        <link href="css/styleCreateUsers.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/encabezadoAdministrador.jspf" %>
        <div class="form-container">
            <h2 style="text-align: center">Registro de Usuario</h2>
            
            <c:if test="${not empty mensajeExito}">
                <div id="mensajeExito" style="color: green; font-weight: bold; text-align: center; margin-bottom: 20px;">
                    ${mensajeExito}
                </div>
            </c:if>
            <c:if test="${not empty mensajeAlerta}">
                <div id="mensajeAlerta" style="color: red; font-weight: bold; text-align: center; margin-bottom: 20px;">
                    ${mensajeAlerta}
                </div>
            </c:if>

            
            <form action="createUsers" method="post" class="form-registrar">
                <!-- Selector del tipo de usuario -->
                <div class="selectUser">
                   <label for="tipoUsuario">Tipo de usuario:</label>
                    <select id="tipoUsuario" name="tipoUsuario" onchange="mostrarCampos()">
                        <option value="">-- Seleccione --</option>
                        <option value="administrador">Administrador</option>
                        <option value="veterinario">Veterinario</option>
                    </select>
                </div>
                

                <!-- Formulario para Administrador -->
                <div id="camposAdministrador" style="display: none; margin-top: 25px;">
                    <h3 class="titleFormulario">Datos del Administrador</h3>
                    <label>Nombre:</label>
                    <input type="text" name="nombreAdministrador"  />

                    <label>Apellido:</label>
                    <input type="text" name="apellidoAdministrador"  />

                    <label>Correo:</label>
                    <input type="email" name="correoAdministrador"  />

                    <label>Contraseña:</label>
                    <input type="password" name="contraseñaAdministrador"  />
                </div>

                <!-- Formulario para Veterinario -->
                <div id="camposVeterinario" style="display: none; margin-top: 25px;">
                    <h3 class="titleFormulario">Datos del Veterinario</h3>
                    <label>Nombre:</label>
                    <input type="text" name="nombreVeterinario"  />

                    <label>Correo:</label>
                    <input type="email" name="correoVeterinario"  />

                    <label>Contraseña:</label>
                    <input type="password" name="contraseñaVeterinario"  />

                    <label>Sueldo:</label>
                    <input type="number" name="sueldo" step="0.01"  />

                    <label>Hora de ingreso:</label>
                    <input type="time" name="hora_ingreso"  />

                    <label>Hora de salida:</label>
                    <input type="time" name="hora_salida"  />
                </div>

<button type="submit" style="margin-top: 15px;display: none" id="btnRegistrarUser" onclick="logRegistrarClick()">Registrar</button>
            </form>
        </div>

<!-- JavaScript para mostrar los campos según el tipo -->
       <script>
            // Mostrar los campos según el tipo seleccionado
            function mostrarCampos() {
                const tipo = document.getElementById("tipoUsuario").value;
                const adminDiv = document.getElementById("camposAdministrador");
                const vetDiv = document.getElementById("camposVeterinario");
                const btnRegistrar = document.getElementById("btnRegistrarUser");

                adminDiv.style.display = tipo === "administrador" ? "flex" : "none";
                vetDiv.style.display = tipo === "veterinario" ? "flex" : "none";
                btnRegistrar.style.display = tipo !== "" ? "block" : "none";
            }

            // Log para depurar
            function logRegistrarClick() {
                console.log("🧪 Se hizo clic en el botón Registrar");
            }

            // Oculta automáticamente los mensajes con desvanecido
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

            // Ejecutar al cargar la página
            window.addEventListener("DOMContentLoaded", () => {
                ocultarMensaje("mensajeExito");
                ocultarMensaje("mensajeAlerta");
            });
        </script>


        <%@include file="WEB-INF/jspf/pie.jspf" %>
    
    </body>
</html>