-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-12-2020 a las 22:15:46
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
-- Estructura de tabla para la tabla `creators`
--

CREATE TABLE `creators` (
  `id_creator` int(11) NOT NULL,
  `name` text DEFAULT NULL,
  `rol` text DEFAULT NULL,
  `id_hero_rel` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `creators`
--

INSERT INTO `creators` (`id_creator`, `name`, `rol`, `id_hero_rel`) VALUES
(69, 'Dick Ayers', 'inker', '1'),
(70, 'Jack Kirby', 'penciler', '1'),
(71, 'Stan Lee', 'writer', '1'),
(72, 'Sam Rosen', 'letterer', '1'),
(73, 'Dick Ayers', 'inker', '2'),
(74, 'Jack Kirby', 'penciler', '2'),
(75, 'Stan Lee', 'writer', '2'),
(76, 'Sam Rosen', 'letterer', '2');

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
(1, 'Iron Man', '1009368', '7332', '2020-12-19 20:27:15'),
(2, 'Capitan America', '1009220', '7332', '2020-12-19 20:27:16');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `other_heros`
--

CREATE TABLE `other_heros` (
  `id_other_hero` int(11) NOT NULL,
  `name` text DEFAULT NULL,
  `comic` text DEFAULT NULL,
  `id_hero_related` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `other_heros`
--

INSERT INTO `other_heros` (`id_other_hero`, `name`, `comic`, `id_hero_related`) VALUES
(73, 'Avengers', 'Absolute Carnage: Avengers (2019) #1', '1'),
(74, 'Captain America', 'Acts of Vengeance: Avengers (Trade Paperback)', '1'),
(75, 'Hank Pym', 'Age of Apocalypse (2011) #2 (Avengers Art Appreciation Variant)', '1'),
(76, 'Iron Man', 'Age of Heroes (2010) #1', '1'),
(77, 'Kang', 'Age of Heroes (2010) #2', '1'),
(78, 'Thor', 'Age of Heroes (2010) #3', '1'),
(79, 'Wasp', 'Age of Heroes (2010) #4', '1'),
(80, 'Avengers', 'Absolute Carnage: Avengers (2019) #1', '2'),
(81, 'Captain America', 'Acts of Vengeance: Avengers (Trade Paperback)', '2'),
(82, 'Hank Pym', 'Age of Apocalypse (2011) #2 (Avengers Art Appreciation Variant)', '2'),
(83, 'Iron Man', 'Age of Heroes (2010) #1', '2'),
(84, 'Kang', 'Age of Heroes (2010) #2', '2'),
(85, 'Thor', 'Age of Heroes (2010) #3', '2'),
(86, 'Wasp', 'Age of Heroes (2010) #4', '2');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `creators`
--
ALTER TABLE `creators`
  ADD PRIMARY KEY (`id_creator`);

--
-- Indices de la tabla `heros`
--
ALTER TABLE `heros`
  ADD PRIMARY KEY (`id_hero`);

--
-- Indices de la tabla `other_heros`
--
ALTER TABLE `other_heros`
  ADD PRIMARY KEY (`id_other_hero`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `creators`
--
ALTER TABLE `creators`
  MODIFY `id_creator` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT de la tabla `heros`
--
ALTER TABLE `heros`
  MODIFY `id_hero` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `other_heros`
--
ALTER TABLE `other_heros`
  MODIFY `id_other_hero` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=87;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
