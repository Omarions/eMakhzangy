-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 28, 2016 at 02:30 PM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stock_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `document`
--
DROP DATABASE IF EXISTS stock_db;
CREATE DATABASE stock_db;

USE stock_db;

DROP TABLE IF EXISTS document;
CREATE TABLE IF NOT EXISTS document (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operation_id` int(11) NOT NULL,
  `from_name` varchar(100) NOT NULL,
  `to_name` varchar(100) NOT NULL,
  `document_date` date NOT NULL,
  `comments` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Save document info for income items or outgoing items';

--
-- Dumping data for table `document`
--

INSERT INTO `document` (`id`, `operation_id`, `from_name`, `to_name`, `document_date`, `comments`) VALUES
(1, 1, 'from 1', 'to 1', '2016-03-09', 'comments1'),
(2, 2, 'from 1', 'to 2', '2016-02-09', 'comments2'),
(3, 2, 'from 4', 'to 3', '2016-02-09', 'comments3');

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
CREATE TABLE IF NOT EXISTS `inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `available_qty` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='To save the inventory info of stock';

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`id`, `item_id`, `available_qty`) VALUES
(1, 1, 100),
(2, 2, 300),
(3, 3, 50),
(4, 4, 150),
(5, 5, 250);

-- --------------------------------------------------------

--
-- Table structure for table `inventory_operation`
--

DROP TABLE IF EXISTS `inventory_operation`;
CREATE TABLE IF NOT EXISTS `inventory_operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inventory_id` int(11) NOT NULL,
  `document_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `comments` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='To save operations done on stock (receive or deliver) ';

--
-- Dumping data for table `inventory_operation`
--

INSERT INTO `inventory_operation` (`id`, `inventory_id`, `document_id`, `project_id`, `quantity`, `comments`) VALUES
(1, 1, 1, 1, 20, 'commetns1'),
(2, 2, 2, 2, 30, 'comments 2'),
(3, 4, 3, 2, 40, '');

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
CREATE TABLE IF NOT EXISTS `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `tags` varchar(256) NOT NULL COMMENT 'tags for catogrization',
  `unit` varchar(25) NOT NULL COMMENT 'unit of measure(piece, m, kg)',
  `unit_price` double NOT NULL DEFAULT '0',
  `comments` varchar(256) NOT NULL DEFAULT ' ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Saves the item info';

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `name`, `tags`, `unit`, `unit_price`, `comments`) VALUES
(1, 'Part 1', 'tag1_tag2', 'm', 0, 'Comments1'),
(2, 'Part 2', 'tag1_tag3', 'piece', 0, 'Comments 2'),
(3, 'Part 3', 'tag2_tag3', 'Kg', 0, 'Comments 3'),
(4, 'Part 4', 'tag1_tag2', 'Kg', 0, 'Comments4'),
(6, 'Cable120', 'Cables\nCable 120 Ohm\nCables Twisted Pairs', 'm', 1, 'Comments 6');

-- --------------------------------------------------------

--
-- Table structure for table `operation`
--

DROP TABLE IF EXISTS `operation`;
CREATE TABLE IF NOT EXISTS `operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `comments` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Enumeration for in/out operations ';

--
-- Dumping data for table `operation`
--

INSERT INTO `operation` (`id`, `name`, `comments`) VALUES
(1, 'in', 'income to inventory'),
(2, 'out', 'outgoing from inventory');

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `manager_name` varchar(50) NOT NULL,
  `comments` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='To save projects info';

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`id`, `name`, `manager_name`, `comments`) VALUES
(1, 'Project A', 'Manager A', 'Comments A'),
(2, 'Project B', 'Manager A', 'COmments B'),
(3, 'Project C', 'Manager C', 'COmments C');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
