-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 20, 2023 at 06:46 PM
-- Server version: 8.0.34
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `applestock`
--

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `category` enum('iPhone','Apple Watch','iPad','AirPods','Mac') NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock_quantity` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `name`, `category`, `price`, `stock_quantity`) VALUES
(1, 'iPhone 13', 'iPhone', 800.00, 100),
(2, 'AppleWatch7', 'Apple Watch', 400.00, 100),
(3, 'iPad Pro', 'iPad', 1100.00, 100),
(4, 'AirPods Pro', 'AirPods', 250.00, 100),
(5, 'appleDesk', 'Mac', 1000.00, 100),
(6, 'ApplePhoneSE', 'iPhone', 600.00, 100);

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `sale_id` int NOT NULL,
  `userid` int NOT NULL,
  `product_id` int DEFAULT NULL,
  `sale_quantity` int DEFAULT NULL,
  `sale_price` decimal(10,2) DEFAULT NULL,
  `invoice_number` varchar(10) NOT NULL,
  `nic` varchar(45) DEFAULT NULL,
  `sale_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`sale_id`, `userid`, `product_id`, `sale_quantity`, `sale_price`, `invoice_number`, `nic`, `sale_date`) VALUES
(1, 7, 5, 1, 1000.00, '20561', '633362266', '2023-11-20'),
(2, 7, 6, 1, 600.00, '20561', '633362266', '2023-11-20'),
(3, 7, 3, 1, 1100.00, '20561', '633362266', '2023-11-20'),
(4, 7, 1, 1, 800.00, '20561', '633362266', '2023-11-20');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `userid` int NOT NULL,
  `position` enum('Cashier','Manager') NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `nic` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`, `userid`, `position`, `name`, `address`, `email`, `phone`, `nic`) VALUES
('adhi', '1234', 1, 'Manager', 'Adithya Bandara', 'No 31, Kurundeniya Akurana', 'adithyabandara69@gmail.com', '0712494420', '200102501325'),
('sunil', '2001', 2, 'Cashier', 'Sunil T Perera', 'No 31, Colombo', 'sunit123@gmail.com', '0769494420', '102356216'),
('nimal', '12345', 3, 'Cashier', 'Nimal Bandara', 'no13, Bakamuna, Sri Lanka', 'nimal@gmail.com', '0123456789', '123456789'),
('awaawf', 'asa', 5, 'Cashier', 'asas', 'awfvawfvwaf', 'aaaaavw', 'awfvawf', 'vawfvawfv'),
('adithya', 'ABandara2001', 6, 'Cashier', 'adithya shameen', 'no 24. kandy', 'adithyabandara69@gmail.com', '071249442', '200102501235'),
('saman', 'samankumara123', 7, 'Cashier', 'samankumara', 'asacscascvawf', 'samankumara@gmail.com', '0712655641', '633362266'),
('upul', 'upul1234', 8, 'Cashier', 'upul santha', 'asacvawfvwfv', 'upul@123.com', '07123664663', '123466952'),
('gihan', 'gehanblock1234', 9, 'Cashier', 'Gihan Block', 'sasavvgq3vfwavf', 'Gehanblock@gmail.com', '01256256325', '200102533651');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`sale_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `userid` (`userid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `sale_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  ADD CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
