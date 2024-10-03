-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 03, 2024 at 12:12 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shop_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `DESCRIPTION` text NOT NULL,
  `DATE` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`ID`, `NAME`, `DESCRIPTION`, `DATE`) VALUES
(1, 'tech', 'computers tech', '12 / 9 / 2024'),
(2, 'perfume', 'perfume', '12 / 9 / 2024'),
(3, 'drinks', 'cold Drinks /soft Drinks', '12 / 9 / 2024'),
(4, 'Stationery', ' Stationery & Office Supplies', '14 / 9 / 2024'),
(5, 'snacks', 'snacks food', '15 / 9 / 2024'),
(6, 'Apparel', 'Apparel & Accessories.', '17 / 9 / 2024');

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `ID` int(10) NOT NULL,
  `UNAME` varchar(50) NOT NULL,
  `USERNAME` varchar(50) NOT NULL,
  `PHONE` varchar(20) NOT NULL,
  `CNIC` varchar(20) DEFAULT NULL,
  `EMAIL` varchar(30) DEFAULT NULL,
  `ADDRESS` text DEFAULT NULL,
  `PASSWORD` varchar(10) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `CODE` varchar(20) NOT NULL DEFAULT '123456'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`ID`, `UNAME`, `USERNAME`, `PHONE`, `CNIC`, `EMAIL`, `ADDRESS`, `PASSWORD`, `TYPE`, `CODE`) VALUES
(1, 'Admin', 'admin', '000000', NULL, NULL, NULL, '1234', 'ADMIN', '00000'),
(12, 'UZAIR', 'UZAIR', '000000000', '0000000', 'mu566@gmail.com', 'swabi mallara', '12345678', 'SALESMAN', '123456'),
(13, 'yahya', 'yahya', '0000000000', '00000000', 'musdfs@gmaill.com', 'swabi', '12345', 'SALESMAN', '123456'),
(14, 'KHAN', 'KHAN', '03335523353', '00000000', 'mu5667733@gmail.com', 'swabi', '12345', 'SALESMAN', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `ID` int(11) NOT NULL,
  `product_name` varchar(50) NOT NULL,
  `DESCRIPTION` text DEFAULT NULL,
  `category` varchar(20) NOT NULL,
  `price` double NOT NULL,
  `stock` int(10) NOT NULL,
  `added_date` varchar(20) NOT NULL,
  `updated_date` varchar(20) NOT NULL,
  `product_barcode` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`ID`, `product_name`, `DESCRIPTION`, `category`, `price`, `stock`, `added_date`, `updated_date`, `product_barcode`) VALUES
(1, 'BANDA mouse', 'mouse', 'tech', 500, 100, '12/9/2024', '3/10/2024', '6922951500047'),
(3, 'SPRITE 1.5L', 'drink', 'drinks', 200, 100, '12/9/2024', '15/9/2024', '5449000012203'),
(4, 'gourmet  1liter cold drink cola', 'drink', 'drinks', 120, 100, '14/9/2024', '16/9/2024', '46876380023'),
(5, 'piano jelflo', 'jel pen', 'Stationery', 20, 300, '14/9/2024', '3/10/2024', '8961100472314'),
(6, 'COLA NEXT', 'drink', 'drinks', 60, 100, '14/9/2024', '14/9/2024', '8964001179290'),
(7, 'SOOPER', 'SNACKES', 'snacks', 40, 700, '15/9/2024', '3/10/2024', '8964002758937'),
(9, 'COCA COLA 1L', 'drink', 'drinks', 120, 100, '15/9/2024', '15/9/2024', '5449000054227'),
(10, 'musk ul mahal', 'perfume', 'perfume', 150, 20, '15/9/2024', '15/9/2024', 'mu700904'),
(11, 'Black leather wallet', 'wallet', 'Apparel', 800, 10, '17/9/2024', '17/9/2024', 'Bl401263'),
(12, 'PEPSI 345ml', 'drink', 'drinks', 70, 100, '17/9/2024', '17/9/2024', '8964000103227'),
(13, 'COCA COLA 2L', 'drink', 'drinks', 240, 100, '17/9/2024', '17/9/2024', '5449000000286'),
(14, 'INK JOY paper mate', 'pen', 'Stationery', 20, 100, '23/9/2024', '23/9/2024', '3501170960509');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `ID` int(10) NOT NULL,
  `SALESMAN_ID` int(10) NOT NULL,
  `DATE_TIME` varchar(50) NOT NULL,
  `SUB_TOTAL` double NOT NULL,
  `TAX_PER` double NOT NULL,
  `TOTAL` double NOT NULL,
  `RECIVED` double NOT NULL,
  `CHANGE_GIVEN` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sales_details`
--

CREATE TABLE `sales_details` (
  `ID` int(10) NOT NULL,
  `SID` int(10) NOT NULL,
  `ITEM` varchar(50) NOT NULL,
  `UNIT_PRICE` double NOT NULL,
  `QTY` int(10) NOT NULL,
  `PRICE` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `store_details`
--

CREATE TABLE `store_details` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `PHONE` varchar(20) NOT NULL,
  `ADDRESS` text NOT NULL,
  `LOGO` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `store_details`
--

INSERT INTO `store_details` (`ID`, `NAME`, `PHONE`, `ADDRESS`, `LOGO`) VALUES
(1, 'SMART MART', '00000000000', 'City :SWABI jahangira road mallara', 0x89504e470d0a1a0a0000000d494844520000006400000064080600000070e29554000000097048597300000b1300000b1301009a9c180000057149444154789ced9d79cc1d5314c07fafaafdd412422d9514114522828a2f156a2942aab5d59a58523edb1ff48f5a524b45ad514b9a08fda79faa9d4808822a49ad8d2d628db5d6a068136af95a466e1dc997c999f7cdbc37efdd3b73cf2f397fbd3b3367ee7973ee9d73cf3d03866118866118866118f56643601a707613e903f6f5ad680c6c0c7c082439658e6f85ebce29058c9100ab81e1be95ae33c7153448026cea5be93a330278c10c1216c380f1c0218a3c634f4858f49b41c2a2df0c12be41fa81f935923b80d9c089c04654d020498de557e01a99ec04c982003ac9872c9397e6e0b82880cef1254f020d026314f004f04f001de4434e225046019bd554b604f6079e530cf281ef8e8f9991c03b8a5176f0ad58cc9ca318e448df4ac5ccb18a418ef7ad54ccdcaa1864826fa562a5017c9532c6804c680c0f4c509e8ea77d2862fcc72d8a41ce94df0c0feeeacb9431d600a3bbad8891edaedce29c1190bb3aabe8495cf6c7edc0cf6dc66b564a54b787386964b8ab2d8a9ee8bc920369971227bd4a5f3cdbca89e6956c90bb8993b94a5fb84ccdc24c2ed9202e692e4677b5bc0c77f53f2ecef208b0b80d7914389538e955fe98ae4f0c4fdc5c96bb323ae3aed6ca829551e5d99511d8ecca28c75d7d61ee2a1cf6b1d95558dca418c46ddd333cf199b9abb0dd95cbc9320272572efd27d74ce030e004d9ba9c07b7c23555422a9d9249c0fa5413d7a79fb7eaaee60f3ae86d608321daef0efc5272c031c99057424ed96fc2decabd2cc973e076ca81a705b697632ad5e346e53ececd73e0583348d766575be53d78b0cb5a2ac9c0a1b8ac972be8b23477f57cd1016852c141d42dac4ce9f0a07e7045ab39dca018c42d831b9ef8b41d776504e6ae8ccecfaecc5d05e4aefe06b6f1a950ccf42a4f872bb86378e21ec520e7fb522676f613f7942ec2e676df1a5dc6859fbe559e8e3bbbad88c1ba829d9a3156d960dedd426c6e005fa4b8a996b71884c038e01260a1ecb15b5c017935471cef362a865ba4b9afa6754de6c91354197652f294ea20abaa98fcb609f051009d979428ab65f9a2926fe3d706d081491be236f77f278563ee02a657b9b6f00879acd337f9900cee0ddf0ac6c68119719e4a0d807562ba629058775305419f62102b47e491231483dceb53a1d871a550ff508c72794513196ac182264585df02dee8b2b89ab98f4b958a6362ac57b52df05300ef134986b818d5cc1c7969b59bfefe1640e72743e4136f4d44ec9151263509483e8e6db56f9854e65c24c6f9415c46b765a089519ef2dd4931321c3854dc946614aba5ebd1300f280679d1974206eba6bd5fa70c6225303c335b794a5c94211a76068e96e0e3e4000ab24c89b1846b433698bea7dcfc5a4978709fd7f3c1b8d882a03d12541cea3d600d706107ae7f86cca81e6ef279888583f478adce6fee0dc93629f282d657e2f54f4f9dfbcd21f6791c50e16dd91df9407102fc29a99aed32461212d2e70ffef3759d44cb38714903178b9f9e2b0648b771dfffeb44cd2a978e142dbb291df28d52f37ca20cec83db7d5fc2da7b7a238d93a332dabaef44bd2e069b454d3959e99019196d1f53dab6137d1d59e00ddc65c7ac48b53d9c1aa255c77673febc153c776de3daa395f3b9bc2a8d3d95b6975143a629377a5546dba54adbcddbb8f67a8a1b7c372317ec82580a8e8d5592abdd42d55e39be5ef64909d75fa69c7796123548bbab44d66f6ac94bcacdfe05dc2f69a64b32a6be579770ed1919e7763a5d2ff5e87f577e7f9f1a33b1852d083f96943fdbd362d67d152b1115e28a029d31001c54e2b5c767bc1cd666e34dabcc945855b3ce5821c567caa637634fe06071dbd4e6c49600be8bf8ed95a9ce580e5cd7e1040397b477a54c16d2f9610fd67910cf830bde6d2ff3ff311eaeefaaf43803ec18425dae7f01c2f33553a17e43350000000049454e44ae426082);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `sales_details`
--
ALTER TABLE `sales_details`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `store_details`
--
ALTER TABLE `store_details`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sales_details`
--
ALTER TABLE `sales_details`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `store_details`
--
ALTER TABLE `store_details`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
