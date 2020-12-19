-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-12-2020 a las 02:25:21
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `marvel_repo_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `heros`
--

CREATE TABLE `heros` (
  `id_hero` int(11) NOT NULL,
  `name` text DEFAULT NULL,
  `marvel_id` text DEFAULT NULL,
  `marvel_comic_id` text DEFAULT NULL,
  `last_sync` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `heros`
--

INSERT INTO `heros` (`id_hero`, `name`, `marvel_id`, `marvel_comic_id`, `last_sync`) VALUES
(1, 'Iron Man', '1009368', '7332', '2020-12-19 01:09:39'),
(2, 'Capitan America', '1009220', '7332', '2020-12-19 01:09:39');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `heros`
--
ALTER TABLE `heros`
  ADD PRIMARY KEY (`id_hero`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `heros`
--
ALTER TABLE `heros`
  MODIFY `id_hero` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
