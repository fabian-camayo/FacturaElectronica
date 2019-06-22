-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-06-2019 a las 20:46:28
-- Versión del servidor: 10.1.31-MariaDB
-- Versión de PHP: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `invoice`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `tipo_documento` varchar(2) NOT NULL DEFAULT 'CC',
  `documento` varchar(30) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `pais` varchar(45) NOT NULL,
  `ciudad` varchar(45) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `correo` varchar(60) NOT NULL,
  `id_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id`, `tipo_documento`, `documento`, `nombre`, `pais`, `ciudad`, `direccion`, `telefono`, `correo`, `id_user`) VALUES
(25, 'CC', '1234567890', 'Juan Mera', 'Colombia', 'Medellin', 'Calle 10h #12', '1234567890', 'peter@peter.com', 1),
(26, 'CE', '1234567890', 'Fernando Fernandez', 'Colombia', 'Popayan', 'Cr21a 12 - #1', '1234567890', 'fernando@fernandez.com', 2),
(28, 'CE', '5469871323', 'Diana Fuentes', 'Venezuela', 'Valencia', 'calle 20a 2b 24s', '3698521457', 'diana@fuentes.com', 1),
(29, 'PS', '564987132154', 'Ginna Valencia', 'Mexico', 'Ciudad de mexico', 'Calle 11h #13', '32131465478', 'ginna@valencia.com', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `id` int(11) NOT NULL,
  `nit` varchar(20) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `ciudad` varchar(60) NOT NULL,
  `direccion` varchar(60) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `email` varchar(60) NOT NULL,
  `id_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`id`, `nit`, `nombre`, `ciudad`, `direccion`, `telefono`, `email`, `id_user`) VALUES
(10, '1234567890', 'QA Ltda', 'Popayan', 'Cr20a 52 - #5', '1234567890', 'qa@qa.com', 2),
(11, '1234567890', 'ABC', 'Bogota', 'calle 20a 2b 24', '123456789', 'abc@abc.com', 1),
(13, '7974654114', 'New company', 'New York', '151 W. 34th St', '3256987414', 'newcompany@newcompany.com', 1),
(14, '965322451454', 'Aldea hoja', 'Ecuador', 'calle 23a 5b 14', '32145698741', 'aldeahoja@aldeahoja.com', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id` int(11) NOT NULL,
  `codigo` longtext NOT NULL,
  `fecha_creacion` date NOT NULL,
  `cliente` int(11) DEFAULT NULL,
  `empresa` int(11) DEFAULT NULL,
  `producto` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `total_producto` int(20) DEFAULT NULL,
  `total` int(20) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`id`, `codigo`, `fecha_creacion`, `cliente`, `empresa`, `producto`, `cantidad`, `total_producto`, `total`, `id_user`) VALUES
(59, '6452391816656413000', '2019-06-18', 25, 11, 34, 1, 600000, 600000, 1),
(62, '-7270698933528595000', '2019-06-18', 26, 10, 35, 3, 1500000, 1500000, 2),
(66, '7605214507951242000', '2019-06-19', 25, 11, 34, 1, 600000, 600000, 1),
(76, '8526176285750534000', '2019-06-19', 28, 14, 38, 2, 1400000, 1400000, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persistent_logins`
--

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int(11) NOT NULL,
  `codigo` varchar(45) NOT NULL,
  `marca` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `garantia` varchar(45) NOT NULL,
  `precio` int(11) NOT NULL,
  `descripcion` varchar(500) NOT NULL,
  `id_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `codigo`, `marca`, `nombre`, `garantia`, `precio`, `descripcion`, `id_user`) VALUES
(34, '1234567890', 'Lenovo', 'Laptop', '6', 600000, 'laptop lenovo', 1),
(35, '1234567890', 'Huawei', 'Y 5', '6', 500000, 'Huawei Y5', 2),
(38, '369258147', 'ASUS', 'Laptop', '12', 700000, 'Laptop ASUS', 1),
(39, '789456123', 'MAC', 'Laptop', '6', 1200000, 'Laptop MAC', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `role`
--

INSERT INTO `role` (`role_id`, `role`) VALUES
(1, 'ADMIN'),
(2, 'USER');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `active` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `firstname`, `lastname`, `email`, `password`, `active`) VALUES
(1, 'Admin', 'Admin', 'admin@admin.com', '$2a$10$R5Sw6Nr424GkzdCLeYCS6ebmjceJZrYqa8IR1vBgygYnJZKNp3IQi', 1),
(2, 'Usuario', 'Usuario', 'usuario@usuario.com', '$2a$10$fsYHXN1xALHHvIUqTlMiKe/UDyxM3C8h93KXgsrjq4WNWng1vWdna', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`) USING BTREE;

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`) USING BTREE;

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_producto_idx` (`producto`),
  ADD KEY `id_user` (`id_user`) USING BTREE,
  ADD KEY `fk_usuario_idx` (`empresa`) USING BTREE,
  ADD KEY `fk_cliente_idx` (`cliente`) USING BTREE;

--
-- Indices de la tabla `persistent_logins`
--
ALTER TABLE `persistent_logins`
  ADD PRIMARY KEY (`series`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`) USING BTREE;

--
-- Indices de la tabla `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `user_role_key` (`role_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT de la tabla `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD CONSTRAINT `empresa_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `fk_cliente` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_empresa` FOREIGN KEY (`empresa`) REFERENCES `empresa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_producto` FOREIGN KEY (`producto`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `role_userrole` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  ADD CONSTRAINT `user_userrole` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
