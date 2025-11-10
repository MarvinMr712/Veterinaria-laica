<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Mis Mascotas</title>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="Javas/app.js" type="module"></script>
    <link href="css/estilo1.css" rel="stylesheet" type="text/css"/>
    <%@include file="WEB-INF/jspf/enlaces.jspf" %>
    <link href="css/mascotaUsuario.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="WEB-INF/jspf/encabezadoUsuario.jspf" %>

<div class="madreMascotaUsuario">

    <div class="btnAgregar">
        <a href="/Historial/registrarMascota.jsp" id="btnAgregar">Agregar Mascota</a>
    </div>

    <c:if test="${empty listaMascota}">
        <div class="componenteError" style="border-left: 4px solid red; padding-left: 20px; text-transform: uppercase; background-color: white; text-align: center;">
            NO HAY MASCOTAS REGISTRADAS
        </div>
    </c:if>

    <c:forEach items="${listaMascota}" var="g" varStatus="loopStatus">
        <c:if test="${g.idUsuario eq idUsuario}">
            <div class="contenedorGeneral">

                <c:if test="${not empty g.imagenBase64}">
                    <div class="contenedorImagen">
                        <img class="fotoMascota" src="data:image/jpeg;base64,${g.imagenBase64}" alt="${g.nombreMascota}">
                    </div>
                </c:if>

                <div class="datosMascota">
                    <p><strong>Nombre:</strong> ${g.nombreMascota}</p>
                    <p><strong>Raza:</strong> ${g.razaMascota}</p>
                    <p><strong>Especie:</strong> ${g.especie}</p>
                    <p><strong>Fecha de Nacimiento:</strong> ${g.fechaNacimiento}</p>
                    <p><strong>Género:</strong> ${g.sexo}</p>
                    <p><strong>Descripción:</strong> ${g.descripcion}</p>
                </div>

            </div>
        </c:if>
    </c:forEach>

</div>

<%@include file="WEB-INF/jspf/pie.jspf" %>

</body>
</html>