Este proyecto simula la administracion de una veterinaria

CREATE TABLE `administrador` (
  `idAdministrador` int(11) NOT NULL,
  `nombreAdministrador` varchar(50) NOT NULL,
  `apellidoAdministrador` varchar(50) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contraseña` blob NOT NULL,
  `usuario_creacion` varchar(255) DEFAULT NULL,
  `usuario_modificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `idAsistencia` int(11) NOT NULL,
  `idVeterinario` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora_llegada_real` time DEFAULT NULL,
  `hora_salida_real` time DEFAULT NULL,
  `estado` enum('ASISTENCIA','TARDANZA','FALTA') NOT NULL,
  `usuario_creacion` varchar(255) DEFAULT NULL,
  `usuario_modificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `asistencia`
--
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacto`
--

CREATE TABLE `contacto` (
  `idContacto` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `mensaje` varchar(250) NOT NULL,
  `usuario_creacion` varchar(255) DEFAULT NULL,
  `usuario_modificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Disparadores `contacto`
--
DELIMITER $$
CREATE TRIGGER `before_insert_contacto` BEFORE INSERT ON `contacto` FOR EACH ROW BEGIN
    SET NEW.usuario_creacion = CURRENT_USER();
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_creacion = CURRENT_TIMESTAMP();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_update_contacto` BEFORE UPDATE ON `contacto` FOR EACH ROW BEGIN
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallehistorial`
--

CREATE TABLE `detallehistorial` (
  `idHistorial` int(11) NOT NULL,
  `idVeterinario` int(11) NOT NULL,
  `usuario_creacion` varchar(255) DEFAULT NULL,
  `usuario_modificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Disparadores `detallehistorial`
--
DELIMITER $$
CREATE TRIGGER `before_insert_detallehistorial` BEFORE INSERT ON `detallehistorial` FOR EACH ROW BEGIN
    SET NEW.usuario_creacion = CURRENT_USER();
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_creacion = CURRENT_TIMESTAMP();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_update_detallehistorial` BEFORE UPDATE ON `detallehistorial` FOR EACH ROW BEGIN
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial`
--

CREATE TABLE `historial` (
  `idHistorial` int(11) NOT NULL,
  `idVeterinario` int(11) NOT NULL,
  `idMascota` int(11) NOT NULL,
  `fechaHistorial` date NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `tratamiento` varchar(200) NOT NULL,
  `usuario_creacion` varchar(255) DEFAULT NULL,
  `usuario_modificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Disparadores `historial`
--
DELIMITER $$
CREATE TRIGGER `before_insert_historial` BEFORE INSERT ON `historial` FOR EACH ROW BEGIN
    SET NEW.usuario_creacion = CURRENT_USER();
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_creacion = CURRENT_TIMESTAMP();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_update_historial` BEFORE UPDATE ON `historial` FOR EACH ROW BEGIN
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mascota`
--

CREATE TABLE `mascota` (
  `idMascota` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `nombreMascota` varchar(50) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `razaMascota` varchar(20) NOT NULL,
  `descripcion` varchar(150) NOT NULL,
  `sexo` char(1) NOT NULL,
  `foto` blob NOT NULL,
  `usuario_creacion` varchar(255) DEFAULT NULL,
  `usuario_modificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mascota`
--

--
-- Disparadores `mascota`
--
DELIMITER $$
CREATE TRIGGER `before_insert_mascota` BEFORE INSERT ON `mascota` FOR EACH ROW BEGIN
    SET NEW.usuario_creacion = CURRENT_USER();
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_creacion = CURRENT_TIMESTAMP();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_update_mascota` BEFORE UPDATE ON `mascota` FOR EACH ROW BEGIN
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio`
--

CREATE TABLE `servicio` (
  `idServicio` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(250) NOT NULL,
  `foto` blob NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `usuario_creacion` varchar(255) DEFAULT NULL,
  `usuario_modificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `servicio`
--

--
-- Disparadores `servicio`
--
DELIMITER $$
CREATE TRIGGER `before_insert_servicio` BEFORE INSERT ON `servicio` FOR EACH ROW BEGIN
    SET NEW.usuario_creacion = CURRENT_USER();
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_creacion = CURRENT_TIMESTAMP();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_update_servicio` BEFORE UPDATE ON `servicio` FOR EACH ROW BEGIN
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombreUsuario` varchar(50) NOT NULL,
  `correoUsuario` varchar(100) NOT NULL,
  `telefono` char(9) NOT NULL,
  `contraseña` blob NOT NULL,
  `usuario_creacion` varchar(255) DEFAULT NULL,
  `usuario_modificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

--
-- Disparadores `usuario`
--
DELIMITER $$
CREATE TRIGGER `before_insert_usuario` BEFORE INSERT ON `usuario` FOR EACH ROW BEGIN
    SET NEW.usuario_creacion = CURRENT_USER();
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_creacion = CURRENT_TIMESTAMP();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_update_usuario` BEFORE UPDATE ON `usuario` FOR EACH ROW BEGIN
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `veterinario`
--

CREATE TABLE `veterinario` (
  `idVeterinario` int(11) NOT NULL,
  `nombreVeterinario` varchar(50) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contraseña` blob NOT NULL,
  `sueldo` decimal(10,2) DEFAULT NULL,
  `hora_ingreso` time DEFAULT NULL,
  `hora_salida` time DEFAULT NULL,
  `usuario_creacion` varchar(255) DEFAULT NULL,
  `usuario_modificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `veterinario`
--
--
-- Disparadores `veterinario`
--
DELIMITER $$
CREATE TRIGGER `before_insert_veterinaio` BEFORE INSERT ON `veterinario` FOR EACH ROW BEGIN
    SET NEW.usuario_creacion = CURRENT_USER();
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_creacion = CURRENT_TIMESTAMP();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_update_veterinario` BEFORE UPDATE ON `veterinario` FOR EACH ROW BEGIN
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `idPago` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idServicio` int(11) NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `metodo_pago` enum('EFECTIVO','TARJETA','TRANSFERENCIA','YAPE','PLIN') NOT NULL,
  `estado` enum('PENDIENTE','COMPLETADO','CANCELADO') NOT NULL DEFAULT 'PENDIENTE',
  `usuario_creacion` varchar(255) DEFAULT NULL,
  `usuario_modificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Disparadores `pago`
--
DELIMITER $$
CREATE TRIGGER `before_insert_pago` BEFORE INSERT ON `pago` FOR EACH ROW BEGIN
    SET NEW.usuario_creacion = CURRENT_USER();
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_creacion = CURRENT_TIMESTAMP();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_update_pago` BEFORE UPDATE ON `pago` FOR EACH ROW BEGIN
    SET NEW.usuario_modificacion = CURRENT_USER();
    SET NEW.fecha_modificacion = CURRENT_TIMESTAMP();
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Indices de la tabla `pago`
--


--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`idAdministrador`);

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`idAsistencia`),
  ADD KEY `asistencia_ibfk_1` (`idVeterinario`);

--
-- Indices de la tabla `contacto`
--
ALTER TABLE `contacto`
  ADD PRIMARY KEY (`idContacto`),
  ADD KEY `idUsuario` (`idUsuario`);

--
-- Indices de la tabla `detallehistorial`
--
ALTER TABLE `detallehistorial`
  ADD PRIMARY KEY (`idHistorial`,`idVeterinario`),
  ADD KEY `idVeterinario` (`idVeterinario`);

--
-- Indices de la tabla `historial`
--
ALTER TABLE `historial`
  ADD PRIMARY KEY (`idHistorial`),
  ADD KEY `idVeterinario` (`idVeterinario`),
  ADD KEY `idMascota` (`idMascota`);

--
-- Indices de la tabla `mascota`
--
ALTER TABLE `mascota`
  ADD PRIMARY KEY (`idMascota`),
  ADD KEY `idUsuario` (`idUsuario`);

--
-- Indices de la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD PRIMARY KEY (`idServicio`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`);

--
-- Indices de la tabla `veterinario`
--
ALTER TABLE `veterinario`
  ADD PRIMARY KEY (`idVeterinario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `administrador`
--
ALTER TABLE `administrador`
  MODIFY `idAdministrador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `idAsistencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `contacto`
--
ALTER TABLE `contacto`
  MODIFY `idContacto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `historial`
--
ALTER TABLE `historial`
  MODIFY `idHistorial` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT de la tabla `mascota`
--
ALTER TABLE `mascota`
  MODIFY `idMascota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT de la tabla `servicio`
--
ALTER TABLE `servicio`
  MODIFY `idServicio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT de la tabla `veterinario`
--
ALTER TABLE `veterinario`
  MODIFY `idVeterinario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD CONSTRAINT `asistencia_ibfk_1` FOREIGN KEY (`idVeterinario`) REFERENCES `veterinario` (`idVeterinario`);

--
-- Filtros para la tabla `contacto`
--
ALTER TABLE `contacto`
  ADD CONSTRAINT `contacto_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`);

--
-- Filtros para la tabla `detallehistorial`
--
ALTER TABLE `detallehistorial`
  ADD CONSTRAINT `detallehistorial_ibfk_1` FOREIGN KEY (`idHistorial`) REFERENCES `historial` (`idHistorial`),
  ADD CONSTRAINT `detallehistorial_ibfk_2` FOREIGN KEY (`idVeterinario`) REFERENCES `veterinario` (`idVeterinario`);

--
-- Filtros para la tabla `historial`
--
ALTER TABLE `historial`
  ADD CONSTRAINT `historial_ibfk_2` FOREIGN KEY (`idMascota`) REFERENCES `mascota` (`idMascota`);

--
-- Filtros para la tabla `mascota`
--
ALTER TABLE `mascota`
  ADD CONSTRAINT `mascota_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;



ALTER TABLE `pago`
  ADD PRIMARY KEY (`idPago`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `idServicio` (`idServicio`);

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `idPago` int(11) NOT NULL AUTO_INCREMENT;

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pago_ibfk_2` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`) ON DELETE CASCADE ON UPDATE CASCADE;
