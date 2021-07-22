-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 15, 2021 at 07:18 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `clashroyale2d`
--

-- --------------------------------------------------------

--
-- Table structure for table `chosencards`
--

CREATE TABLE `chosencards` (
  `id` int(11) NOT NULL,
  `user_id` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL,
  `card_1` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL,
  `card_2` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL,
  `card_3` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL,
  `card_4` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL,
  `card_5` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL,
  `card_6` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL,
  `card_7` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL,
  `card_8` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_persian_ci;

--
-- Dumping data for table `chosencards`
--

INSERT INTO `chosencards` (`id`, `user_id`, `card_1`, `card_2`, `card_3`, `card_4`, `card_5`, `card_6`, `card_7`, `card_8`) VALUES
(7, '10', 'archer', 'barbarian', 'babyDragon', ' ', ' ', ' ', ' ', ' ');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL,
  `password` varchar(20) COLLATE utf8mb4_persian_ci DEFAULT NULL,
  `cupsCount` int(11) DEFAULT 0,
  `level` int(11) DEFAULT 0,
  `bot_type` int(10) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_persian_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `cupsCount`, `level`, `bot_type`) VALUES
(8, 'sdfcxd', 'sdfcxxdsfv', 0, 0, 0),
(9, 'zdvcdsgfsed', 'szdcvxcv', 0, 0, 0),
(10, 'admin', 'admin', 0, 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chosencards`
--
ALTER TABLE `chosencards`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chosencards`
--
ALTER TABLE `chosencards`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
