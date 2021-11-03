-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-11-2021 a las 22:46:39
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ferreteriavillamil`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `imagen` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idCategoria`, `nombre`, `imagen`) VALUES
(1, 'Herramientas', 'https://www.hogarmania.com/archivos/201904/herramientas-de-bricolaje-848x477x80xX.jpg'),
(2, 'Pintura', 'https://previews.123rf.com/images/banky405/banky4051701/banky405170100047/70405935-vista-del-departamento-de-pintura-del-hogar-en-la-ferreter%C3%ADa.jpg'),
(5, 'Materiales de Construcción', 'https://img5.s3wfg.com/web/img/images_uploaded/9/8/cbmateriales1.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `domicilio`
--

CREATE TABLE `domicilio` (
  `idDomicilio` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idFactura` int(11) NOT NULL,
  `ciudad` varchar(80) NOT NULL,
  `barrio` varchar(80) NOT NULL,
  `direccion` varchar(80) NOT NULL,
  `latitud` varchar(30) NOT NULL,
  `longitud` varchar(30) NOT NULL,
  `estado` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `idFactura` int(11) NOT NULL,
  `fecha` varchar(20) NOT NULL,
  `precioTotal` varchar(1000) NOT NULL,
  `cantidadTotal` int(11) NOT NULL,
  `descripcion` varchar(1000) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idTipoPago` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`idFactura`, `fecha`, `precioTotal`, `cantidadTotal`, `descripcion`, `idUsuario`, `idTipoPago`) VALUES
(1, '2021-11-03', '1188713.6', 4, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-Vinilo Rojo Repynta-Esmalte Azul Pintulux-', 12, 1),
(106, '2021-11-03', '1188713.6', 4, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-Vinilo Rojo Repynta-Esmalte Azul Pintulux-', 12, 1),
(107, '2021-11-03', '3570.0', 1, 'Alicate Universales Beta 1150 BM Tools.-', 12, 1),
(108, '2021-11-03', '3570.0', 1, 'Alicate Universales Beta 1150 BM Tools.-', 12, 1),
(109, '2021-11-01', '1188713.6', 4, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-Vinilo Rojo Repynta-Esmalte Azul Pintulux-', 12, 1),
(110, '2021-11-03', '1188713.6', 4, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-Vinilo Rojo Repynta-Esmalte Azul Pintulux-', 12, 1),
(111, '2021-11-03', '6887208.5', 8, 'Esmalte Azul Pintulux-Cemento Argos-Ladrillo Ladrillos en farol-', 12, 1),
(112, '2021-11-01', '5950.0', 2, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-', 12, 1),
(113, '2021-11-03', '5793661.5', 8, 'Martillo Bellota-Vinilo Rojo Repynta-Esmalte Azul Pintulux-', 12, 1),
(114, '2021-11-03', '2319536.0', 4, 'Esmalte Azul Pintulux-Cemento Argos-Ladrillo Ladrillos en farol-', 12, 1),
(115, '2021-11-05', '2301686.0', 3, 'Esmalte Azul Pintulux-Cemento Argos-Ladrillo Ladrillos en farol-', 12, 1),
(116, '2021-11-03', '2301686.0', 3, 'Esmalte Azul Pintulux-Cemento Argos-Ladrillo Ladrillos en farol-', 12, 1),
(117, '2021-11-03', '3570.0', 1, 'Alicate Universales Beta 1150 BM Tools.-', 12, 1),
(118, '2021-11-06', '1188713.6', 4, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-Vinilo Rojo Repynta-Esmalte Azul Pintulux-', 12, 1),
(119, '2021-11-02', '1188713.6', 4, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-Vinilo Rojo Repynta-Esmalte Azul Pintulux-', 12, 1),
(120, '2021-11-03', '2342531.8', 4, 'Vinilo Rojo Repynta-Esmalte Azul Pintulux-Cemento Argos-Ladrillo Ladrillos en farol-', 12, 1),
(121, '2021-11-07', '2301686.0', 3, 'Esmalte Azul Pintulux-Cemento Argos-Ladrillo Ladrillos en farol-', 12, 1),
(122, '2021-11-02', '2289786.0', 4, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-Esmalte Azul Pintulux-Cemento Argos-', 12, 1),
(123, '2021-11-03', '1188713.6', 4, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-Vinilo Rojo Repynta-Esmalte Azul Pintulux-', 12, 1),
(124, '2021-11-03', '1188713.6', 4, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-Vinilo Rojo Repynta-Esmalte Azul Pintulux-', 12, 1),
(125, '2021-11-03', '1188713.6', 4, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-Vinilo Rojo Repynta-Esmalte Azul Pintulux-', 12, 1),
(126, '2021-11-03', '2301686.0', 3, 'Esmalte Azul Pintulux-Cemento Argos-Ladrillo Ladrillos en farol-', 12, 1),
(127, '2021-11-03', '3570.0', 1, 'Alicate Universales Beta 1150 BM Tools.', 12, 1),
(128, '2021-11-03', '356397.88', 1, 'prueba nfdmmd', 12, 1),
(129, '2021-11-04', '3570', 1, 'Alicate Universales Beta 1150 BM Tools.', 12, 1),
(130, '2021-11-03', '5950.0', 2, 'Alicate Universales Beta 1150 BM Tools.-Martillo Bellota-', 12, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `idProducto` int(11) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `marca` varchar(80) NOT NULL,
  `descripcion` varchar(300) NOT NULL,
  `precio` varchar(20) NOT NULL,
  `imagen` varchar(1000) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`idProducto`, `nombre`, `marca`, `descripcion`, `precio`, `imagen`, `idCategoria`, `cantidad`) VALUES
(1, 'Alicate', 'Universales Beta 1150 BM Tools.', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries', '3000', 'https://www.loencuentras.com.co/1161/alicate-de-electricista-ligeros-7pulg.jpg', 1, 20),
(2, 'Martillo', 'Bellota', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', '2000', 'https://www.picapala.com/wp-content/uploads/2014/02/18-MARTILLOS.jpg', 1, 73),
(4, 'Vinilo Rojo', 'Repynta', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', '34324', 'https://http2.mlstatic.com/D_NQ_NP_936648-MCO45173429314_032021-O.jpg', 2, 97),
(5, 'Esmalte Azul', 'Pintulux', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.', '959595', 'https://ferreteriadonkike.com/wp-content/uploads/2020/07/ferreteria-don-kike-barranquilla-143.png', 2, 55),
(6, 'Cemento', 'Argos', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries', '959595', 'https://www.easycem.com.co/wp-content/uploads/2020/04/C.-Gris-UG-50-kg-Producto.png', 5, 33),
(7, 'Ladrillo', 'Ladrillos en farol', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries', '15000', 'https://e.rpp-noticias.io/xlarge/2018/05/08/432743_606290.jpg', 5, 70),
(90311017, 'prueba', 'nfdmmd', 'ndnd', '299494', 'ndndnd', 1, 9593);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productosxfactura`
--

CREATE TABLE `productosxfactura` (
  `idProducto` int(11) NOT NULL,
  `idFactura` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productosxfactura`
--

INSERT INTO `productosxfactura` (`idProducto`, `idFactura`, `cantidad`, `precio`) VALUES
(1, 107, 1, '3000'),
(1, 108, 1, '3000'),
(2, 108, 1, '2000'),
(4, 108, 1, '34324'),
(5, 108, 1, '959595'),
(1, 110, 1, '3000'),
(2, 110, 1, '2000'),
(4, 110, 1, '34324'),
(5, 110, 1, '959595'),
(1, 112, 1, '3000'),
(2, 112, 1, '2000'),
(2, 113, 1, '2000'),
(4, 113, 2, '34324'),
(5, 113, 5, '959595'),
(6, 116, 1, '959595'),
(7, 116, 1, '15000'),
(1, 117, 1, '3000'),
(1, 117, 1, '3000'),
(2, 117, 1, '2000'),
(4, 117, 1, '34324'),
(5, 117, 1, '959595'),
(1, 119, 1, '3000'),
(2, 119, 1, '2000'),
(4, 119, 1, '34324'),
(5, 119, 1, '959595'),
(5, 121, 1, '959595'),
(6, 121, 1, '959595'),
(7, 121, 1, '15000'),
(1, 122, 1, '3000'),
(2, 122, 1, '2000'),
(5, 122, 1, '959595'),
(6, 122, 1, '959595'),
(1, 125, 1, '3000'),
(2, 125, 1, '2000'),
(4, 125, 1, '34324'),
(5, 125, 1, '959595'),
(5, 126, 1, '959595'),
(6, 126, 1, '959595'),
(7, 126, 1, '15000'),
(1, 127, 1, '3570.0'),
(90311017, 128, 1, '356397.88'),
(1, 130, 1, '3000'),
(2, 130, 1, '2000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`idRol`, `Nombre`) VALUES
(1, 'Usuario'),
(2, 'Administrador'),
(3, 'Proveedor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipopago`
--

CREATE TABLE `tipopago` (
  `idTipoPago` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipopago`
--

INSERT INTO `tipopago` (`idTipoPago`, `nombre`) VALUES
(1, 'Efectivo'),
(2, 'Tarjeta');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `correo` varchar(80) NOT NULL,
  `contrasena` varchar(80) NOT NULL,
  `telefono` int(11) NOT NULL,
  `direccion` varchar(40) NOT NULL,
  `identificacion` int(11) NOT NULL,
  `idRol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombre`, `correo`, `contrasena`, `telefono`, `direccion`, `identificacion`, `idRol`) VALUES
(12, 'Sebastian Ortiz', 'sebastianortizvillamil@gmail.com', 'sebastian123', 258147369, 'Carrera 26#80-2', 976463251, 1),
(24, 'Alejandro', 'alejandroriico@gmail.com', 'alejandro123', 322434, 'dfsf', 12343, 2),
(31, 'Johan Camilo', 'johancamilo@gmail.com', 'johan123', 465659, 'dbdbdb', 564695, 3),
(32, 'Daniel Alejandro', 'nffnnf', 'ntnfnf', 959595, 'ndndndnfbf', 959595, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idCategoria`);

--
-- Indices de la tabla `domicilio`
--
ALTER TABLE `domicilio`
  ADD PRIMARY KEY (`idDomicilio`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `idFactura` (`idFactura`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`idFactura`),
  ADD KEY `factura_ibfk_2` (`idTipoPago`),
  ADD KEY `FK_id_usuario3` (`idUsuario`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`),
  ADD KEY `FK_id_categoria` (`idCategoria`);

--
-- Indices de la tabla `productosxfactura`
--
ALTER TABLE `productosxfactura`
  ADD KEY `FK_id_producto2` (`idProducto`),
  ADD KEY `FK_id_factura` (`idFactura`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`idRol`);

--
-- Indices de la tabla `tipopago`
--
ALTER TABLE `tipopago`
  ADD PRIMARY KEY (`idTipoPago`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD KEY `FK_id_rol` (`idRol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `domicilio`
--
ALTER TABLE `domicilio`
  MODIFY `idDomicilio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `idFactura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=131;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `idProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9780201379625;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `idRol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tipopago`
--
ALTER TABLE `tipopago`
  MODIFY `idTipoPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `domicilio`
--
ALTER TABLE `domicilio`
  ADD CONSTRAINT `domicilio_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `domicilio_ibfk_2` FOREIGN KEY (`idFactura`) REFERENCES `factura` (`idFactura`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `factura_ibfk_2` FOREIGN KEY (`idTipoPago`) REFERENCES `tipopago` (`idTipoPago`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `factura_ibfk_3` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idCategoria`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `productosxfactura`
--
ALTER TABLE `productosxfactura`
  ADD CONSTRAINT `productosxfactura_ibfk_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `productosxfactura_ibfk_2` FOREIGN KEY (`idFactura`) REFERENCES `factura` (`idFactura`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
