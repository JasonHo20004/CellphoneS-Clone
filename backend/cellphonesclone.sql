-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 16, 2024 at 03:06 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cellphonesclone`
--

-- --------------------------------------------------------

--
-- Table structure for table `brands`
--

CREATE TABLE `brands` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `brands`
--

INSERT INTO `brands` (`id`, `name`) VALUES
(2, 'Apple'),
(3, 'Samsung'),
(4, 'Xiaomi'),
(5, 'Vivo');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `fullname` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `address` varchar(200) NOT NULL,
  `note` varchar(100) DEFAULT '',
  `order_date` datetime DEFAULT current_timestamp(),
  `status` varchar(255) DEFAULT NULL,
  `total_money` float DEFAULT NULL CHECK (`total_money` >= 0),
  `shipping_method` varchar(100) DEFAULT NULL,
  `shipping_address` varchar(200) DEFAULT NULL,
  `shipping_date` date DEFAULT NULL,
  `tracking_number` varchar(100) DEFAULT NULL,
  `payment_method` varchar(100) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `user_id`, `fullname`, `email`, `phone_number`, `address`, `note`, `order_date`, `status`, `total_money`, `shipping_method`, `shipping_address`, `shipping_date`, `tracking_number`, `payment_method`, `active`) VALUES
(1, 1, 'Nguyen Van A12', 'nva@gmail.com', '0192391248', '01 VVN', 'Hang de vo', '2024-11-04 19:10:25', 'pending', 120, 'express', 'S7.02 Vinhome', '2024-12-24', NULL, 'cod', 0),
(3, 17, 'Nguyễn Văn X12', 'nvx2x@yahoo.com', '12345566', 'Nhà a ngõ B, ngách D', 'Hàng dễ vỡ xin nhẹ tay', '2023-08-05 00:00:00', 'pending', 123.45, 'express', 'nha a ngo b', '2023-08-05', NULL, 'cod', 1);

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `id` bigint(20) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL CHECK (`price` >= 0),
  `number_of_products` int(11) DEFAULT NULL CHECK (`number_of_products` > 0),
  `total_money` float DEFAULT NULL CHECK (`total_money` >= 0),
  `color` varchar(20) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`id`, `order_id`, `product_id`, `price`, `number_of_products`, `total_money`, `color`) VALUES
(6, 3, 12, 1799.99, 2, NULL, ''),
(7, 3, 15, 199.99, 3, NULL, ''),
(8, 3, 10, 429.99, 2, NULL, '');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(300) NOT NULL,
  `price` float NOT NULL CHECK (`price` >= 0),
  `thumbnail` varchar(350) DEFAULT NULL,
  `description` longtext DEFAULT '',
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
  `brands_id` int(11) DEFAULT NULL,
  `in_stock` int(11) DEFAULT NULL,
  `operating_system` varchar(255) DEFAULT NULL,
  `screen_size` decimal(5,2) DEFAULT NULL,
  `battery_capacity` int(11) DEFAULT NULL,
  `ram` int(11) DEFAULT NULL,
  `rom` int(11) DEFAULT NULL,
  `front_camera` varchar(350) DEFAULT NULL,
  `main_camera` varchar(350) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `release_date` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `thumbnail`, `description`, `created_at`, `updated_at`, `brands_id`, `in_stock`, `operating_system`, `screen_size`, `battery_capacity`, `ram`, `rom`, `front_camera`, `main_camera`, `color`, `release_date`) VALUES
(1, 'iPhone 13', 799.99, NULL, 'Latest iPhone 13 with A15 Bionic chip', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 2, 100, 'iOS', 6.10, 3227, 4, 128, '12 MP', '12 MP', 'Blue', 2021),
(2, 'iPhone 14 Pro', 1099.99, NULL, 'iPhone 14 Pro with A16 Bionic chip', '2024-01-05 00:00:00', '2024-01-05 00:00:00', 2, 50, 'iOS', 6.10, 3200, 6, 256, '12 MP', '48 MP', 'Gold', 2022),
(3, 'Samsung Galaxy S21', 699.99, NULL, 'Samsung Galaxy S21 with Exynos 2100', '2024-01-10 00:00:00', '2024-01-10 00:00:00', 3, 150, 'Android', 6.20, 4000, 8, 128, '10 MP', '64 MP', 'Gray', 2021),
(4, 'Samsung Galaxy S22 Ultra', 1199.99, NULL, 'Galaxy S22 Ultra with S Pen support', '2024-01-12 00:00:00', '2024-01-12 00:00:00', 3, 80, 'Android', 6.80, 5000, 12, 512, '40 MP', '108 MP', 'Phantom Black', 2022),
(5, 'Xiaomi Mi 11', 599.99, NULL, 'Xiaomi Mi 11 with Snapdragon 888', '2024-01-15 00:00:00', '2024-01-15 00:00:00', 4, 120, 'Android', 6.81, 4600, 8, 128, '20 MP', '108 MP', 'White', 2021),
(6, 'Xiaomi Redmi Note 11 Pro', 299.99, NULL, 'Redmi Note 11 Pro with Mediatek Dimensity', '2024-01-20 00:00:00', '2024-01-20 00:00:00', 4, 200, 'Android', 6.67, 5000, 6, 128, '16 MP', '108 MP', 'Graphite Gray', 2022),
(7, 'Vivo X70 Pro', 749.99, NULL, 'Vivo X70 Pro with Zeiss optics', '2024-01-25 00:00:00', '2024-01-25 00:00:00', 5, 60, 'Android', 6.56, 4450, 8, 256, '32 MP', '50 MP', 'Aurora Dawn', 2021),
(8, 'Vivo V23 5G', 399.99, NULL, 'Vivo V23 5G with color-changing design', '2024-01-30 00:00:00', '2024-01-30 00:00:00', 5, 90, 'Android', 6.44, 4200, 8, 128, '50 MP', '64 MP', 'Sunshine Gold', 2022),
(9, 'iPhone 12 Mini', 699.99, NULL, 'Compact iPhone 12 Mini with A14 Bionic chip', '2024-02-01 00:00:00', '2024-02-01 00:00:00', 2, 75, 'iOS', 5.40, 2227, 4, 64, '12 MP', '12 MP', 'Green', 2020),
(10, 'iPhone SE 2022', 429.99, NULL, 'Affordable iPhone SE with A15 Bionic chip', '2024-02-02 00:00:00', '2024-02-02 00:00:00', 2, 150, 'iOS', 4.70, 1821, 3, 128, '7 MP', '12 MP', 'Red', 2022),
(11, 'Samsung Galaxy A52', 349.99, NULL, 'Mid-range Samsung Galaxy A52 with Snapdragon 720G', '2024-02-05 00:00:00', '2024-02-05 00:00:00', 3, 200, 'Android', 6.50, 4500, 6, 128, '32 MP', '64 MP', 'Awesome Blue', 2021),
(12, 'Samsung Galaxy Z Fold 3', 1799.99, NULL, 'Foldable Samsung Galaxy Z Fold 3 with Snapdragon 888', '2024-02-06 00:00:00', '2024-02-06 00:00:00', 3, 30, 'Android', 7.60, 4400, 12, 256, '10 MP', '12 MP', 'Black', 2021),
(13, 'Xiaomi Poco F3', 399.99, NULL, 'Xiaomi Poco F3 with Snapdragon 870', '2024-02-10 00:00:00', '2024-02-10 00:00:00', 4, 180, 'Android', 6.67, 4520, 6, 128, '20 MP', '48 MP', 'Arctic White', 2021),
(14, 'Xiaomi Mi 10T', 499.99, NULL, 'Xiaomi Mi 10T with Snapdragon 865', '2024-02-12 00:00:00', '2024-02-12 00:00:00', 4, 100, 'Android', 6.67, 5000, 8, 128, '20 MP', '64 MP', 'Cosmic Black', 2020),
(15, 'Vivo Y20', 199.99, NULL, 'Budget-friendly Vivo Y20 with Snapdragon 460', '2024-02-15 00:00:00', '2024-02-15 00:00:00', 5, 300, 'Android', 6.51, 5000, 4, 64, '8 MP', '13 MP', 'Nebula Blue', 2020),
(16, 'Vivo X50 Pro', 649.99, NULL, 'Vivo X50 Pro with Gimbal stabilization', '2024-02-18 00:00:00', '2024-02-18 00:00:00', 5, 70, 'Android', 6.56, 4315, 8, 256, '32 MP', '48 MP', 'Alpha Grey', 2020),
(17, 'iPhone 14', 899.99, NULL, 'Standard iPhone 14 with A16 Bionic chip', '2024-03-01 00:00:00', '2024-03-01 00:00:00', 2, 120, 'iOS', 6.10, 3279, 6, 128, '12 MP', '48 MP', 'Purple', 2022),
(18, 'Samsung Galaxy S20 FE', 599.99, NULL, 'Samsung Galaxy S20 FE with Snapdragon 865', '2024-03-05 00:00:00', '2024-03-05 00:00:00', 3, 150, 'Android', 6.50, 4500, 6, 128, '32 MP', '12 MP', 'Cloud Navy', 2020),
(19, 'Xiaomi Mi 9', 399.99, NULL, 'Xiaomi Mi 9 with Snapdragon 855', '2024-03-10 00:00:00', '2024-03-10 00:00:00', 4, 80, 'Android', 6.39, 3300, 6, 128, '20 MP', '48 MP', 'Ocean Blue', 2019),
(20, 'Vivo V21', 349.99, NULL, 'Vivo V21 with OIS front camera', '2024-03-15 00:00:00', '2024-03-15 00:00:00', 5, 100, 'Android', 6.44, 4000, 8, 128, '44 MP', '64 MP', 'Dusk Blue', 2021),
(21, 'iPhone XR', 499.99, NULL, 'Affordable iPhone XR with A12 Bionic chip', '2024-03-20 00:00:00', '2024-03-20 00:00:00', 2, 180, 'iOS', 6.10, 2942, 3, 64, '7 MP', '12 MP', 'Yellow', 2018),
(22, 'Samsung Galaxy Note 20', 949.99, NULL, 'Galaxy Note 20 with S Pen and Snapdragon 865+', '2024-03-25 00:00:00', '2024-03-25 00:00:00', 3, 60, 'Android', 6.70, 4300, 8, 256, '10 MP', '64 MP', 'Mystic Bronze', 2020),
(23, 'Xiaomi Redmi 9A', 119.99, NULL, 'Budget Xiaomi Redmi 9A with MediaTek Helio G25', '2024-03-28 00:00:00', '2024-03-28 00:00:00', 4, 400, 'Android', 6.53, 5000, 2, 32, '5 MP', '13 MP', 'Granite Gray', 2020),
(24, 'Vivo Y50', 249.99, NULL, 'Mid-range Vivo Y50 with Snapdragon 665', '2024-04-01 00:00:00', '2024-04-01 00:00:00', 5, 140, 'Android', 6.53, 5000, 8, 128, '16 MP', '13 MP', 'Pearl White', 2020),
(25, 'iPhone 11 Pro', 999.99, NULL, 'iPhone 11 Pro with triple camera system', '2024-04-05 00:00:00', '2024-04-05 00:00:00', 2, 100, 'iOS', 5.80, 3190, 4, 64, '12 MP', '12 MP', 'Midnight Green', 2019),
(26, 'Samsung Galaxy A32', 279.99, NULL, 'Affordable Samsung Galaxy A32 with AMOLED display', '2024-04-10 00:00:00', '2024-04-10 00:00:00', 3, 250, 'Android', 6.40, 5000, 6, 128, '20 MP', '64 MP', 'Awesome Black', 2021),
(27, 'Xiaomi Mi Mix 3', 549.99, NULL, 'Xiaomi Mi Mix 3 with slider design', '2024-04-15 00:00:00', '2024-04-15 00:00:00', 4, 90, 'Android', 6.39, 3200, 6, 128, '24 MP', '12 MP', 'Onyx Black', 2018),
(28, 'Vivo S1 Pro', 319.99, NULL, 'Stylish Vivo S1 Pro with diamond-shaped camera', '2024-04-20 00:00:00', '2024-04-20 00:00:00', 5, 110, 'Android', 6.38, 4500, 8, 128, '32 MP', '48 MP', 'Jazzy Blue', 2019),
(29, 'iPhone XS', 899.99, NULL, 'iPhone XS with Super Retina display', '2024-04-25 00:00:00', '2024-04-25 00:00:00', 2, 70, 'iOS', 5.80, 2658, 4, 64, '7 MP', '12 MP', 'Silver', 2018),
(30, 'Samsung Galaxy S10+', 799.99, NULL, 'Galaxy S10+ with Snapdragon 855', '2024-04-30 00:00:00', '2024-04-30 00:00:00', 3, 85, 'Android', 6.40, 4100, 8, 128, '10 MP', '12 MP', 'Prism White', 2019),
(31, 'Xiaomi Black Shark 4', 499.99, NULL, 'Gaming Xiaomi Black Shark 4 with Snapdragon 870', '2024-05-05 00:00:00', '2024-05-05 00:00:00', 4, 50, 'Android', 6.67, 4500, 6, 128, '20 MP', '48 MP', 'Shadow Black', 2021),
(32, 'Vivo Z1x', 299.99, NULL, 'Vivo Z1x with in-display fingerprint scanner', '2024-05-10 00:00:00', '2024-05-10 00:00:00', 5, 150, 'Android', 6.38, 4500, 6, 64, '32 MP', '48 MP', 'Fusion Blue', 2019),
(33, 'iPhone 11', 699.99, NULL, 'iPhone 11 with dual camera system', '2024-05-15 00:00:00', '2024-05-15 00:00:00', 2, 130, 'iOS', 6.10, 3110, 4, 64, '12 MP', '12 MP', 'Black', 2019),
(34, 'Samsung Galaxy A72', 449.99, NULL, 'Samsung Galaxy A72 with quad camera setup', '2024-05-20 00:00:00', '2024-05-20 00:00:00', 3, 170, 'Android', 6.70, 5000, 8, 256, '32 MP', '64 MP', 'Awesome Violet', 2021),
(35, 'Xiaomi Redmi K30', 349.99, NULL, 'Xiaomi Redmi K30 with dual front cameras', '2024-05-25 00:00:00', '2024-05-25 00:00:00', 4, 110, 'Android', 6.67, 4500, 6, 128, '20 MP', '64 MP', 'Purple', 2020),
(36, 'Vivo Y11', 179.99, NULL, 'Budget-friendly Vivo Y11 with Snapdragon 439', '2024-05-30 00:00:00', '2024-05-30 00:00:00', 5, 300, 'Android', 6.35, 5000, 3, 32, '8 MP', '13 MP', 'Coral Red', 2019),
(37, 'iPhone 12', 799.99, NULL, 'iPhone 12 with A14 Bionic chip and dual cameras', '2024-06-01 00:00:00', '2024-06-01 00:00:00', 2, 140, 'iOS', 6.10, 2815, 4, 64, '12 MP', '12 MP', 'White', 2020),
(38, 'Samsung Galaxy Note 10', 949.99, NULL, 'Samsung Galaxy Note 10 with S Pen', '2024-06-05 00:00:00', '2024-06-05 00:00:00', 3, 50, 'Android', 6.30, 3500, 8, 256, '10 MP', '12 MP', 'Aura Glow', 2019),
(39, 'Xiaomi Mi 8', 349.99, NULL, 'Xiaomi Mi 8 with Snapdragon 845', '2024-06-10 00:00:00', '2024-06-10 00:00:00', 4, 60, 'Android', 6.21, 3400, 6, 128, '20 MP', '12 MP', 'Black', 2018),
(40, 'Vivo Y21', 149.99, NULL, 'Affordable Vivo Y21 with MediaTek Helio P35', '2024-06-15 00:00:00', '2024-06-15 00:00:00', 5, 400, 'Android', 6.51, 5000, 4, 64, '8 MP', '13 MP', 'Diamond Glow', 2021),
(41, 'iPhone XS Max', 1099.99, NULL, 'iPhone XS Max with larger screen', '2024-06-20 00:00:00', '2024-06-20 00:00:00', 2, 50, 'iOS', 6.50, 3174, 4, 64, '7 MP', '12 MP', 'Gold', 2018),
(42, 'Samsung Galaxy A52s', 429.99, NULL, 'Samsung Galaxy A52s with Snapdragon 778G', '2024-06-25 00:00:00', '2024-06-25 00:00:00', 3, 150, 'Android', 6.50, 4500, 6, 128, '32 MP', '64 MP', 'Awesome Mint', 2021),
(43, 'Xiaomi Redmi Note 9', 249.99, NULL, 'Xiaomi Redmi Note 9 with Helio G85', '2024-06-30 00:00:00', '2024-06-30 00:00:00', 4, 180, 'Android', 6.53, 5020, 4, 64, '13 MP', '48 MP', 'Forest Green', 2020),
(44, 'Vivo Y31', 199.99, NULL, 'Vivo Y31 with Snapdragon 662', '2024-07-05 00:00:00', '2024-07-05 00:00:00', 5, 250, 'Android', 6.58, 5000, 6, 128, '16 MP', '48 MP', 'Racing Black', 2021),
(45, 'iPhone X', 899.99, NULL, 'iPhone X with Face ID and OLED display', '2024-07-10 00:00:00', '2024-07-10 00:00:00', 2, 90, 'iOS', 5.80, 2716, 3, 64, '7 MP', '12 MP', 'Space Gray', 2017),
(46, 'Samsung Galaxy S9', 599.99, NULL, 'Galaxy S9 with Snapdragon 845', '2024-07-15 00:00:00', '2024-07-15 00:00:00', 3, 75, 'Android', 5.80, 3000, 4, 64, '8 MP', '12 MP', 'Lilac Purple', 2018),
(47, 'Xiaomi Mi Note 10', 549.99, NULL, 'Xiaomi Mi Note 10 with penta camera', '2024-07-20 00:00:00', '2024-07-20 00:00:00', 4, 60, 'Android', 6.47, 5260, 6, 128, '32 MP', '108 MP', 'Glacier White', 2019),
(48, 'Vivo Y50 Pro', 299.99, NULL, 'Affordable Vivo Y50 Pro with Snapdragon 720G', '2024-07-25 00:00:00', '2024-07-25 00:00:00', 5, 120, 'Android', 6.53, 5000, 6, 128, '16 MP', '48 MP', 'Obsidian Black', 2021);

-- --------------------------------------------------------

--
-- Table structure for table `product_images`
--

CREATE TABLE `product_images` (
  `id` bigint(20) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `image_url` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product_images`
--

INSERT INTO `product_images` (`id`, `product_id`, `image_url`) VALUES
(65, 1, '24313ab2-bd53-437c-bee4-f034fd86f4e4_464103824_1758322974986511_3813455476472043058_n.jpg'),
(66, 1, '5100dcda-bc62-4941-a52d-38088a1044d7_464466510_1151917299875427_7813064931254440397_n.jpg'),
(67, 1, '9a9d92ed-e1cf-4a45-95c8-e7d7a9bde355_464579600_925630809474245_1366559687421735498_n.jpg'),
(68, 2, '212c32ac-6563-4023-b55f-a9869358a2cb_464599814_1257180382374464_7825998651641052001_n.jpg'),
(69, 2, '94d8dfc6-edb3-483e-b359-4fe36d85a5fc_464599815_868199612128905_8473598377538470916_n.jpg'),
(70, 2, '2c8bed81-3d36-43ae-afe5-919df791d83c_464658262_1237624973949562_7211833606302339533_n.jpg'),
(71, 3, 'f89cbf68-46dd-4087-a1ba-062c9a344107_464675059_877944907805016_416288875432623371_n.jpg'),
(72, 3, '1055ad5a-e4d9-4865-ba34-a868c070bc56_464679357_3687852978192378_1192960148848954113_n.jpg'),
(73, 3, 'dcb36760-503f-4388-95cf-227a7308b65d_464682335_547048831405590_270080917255800842_n.jpg'),
(74, 4, 'ddab3c62-0e78-437b-8fc8-39205a506dba_464755417_1073478110906863_6270199450890447407_n.jpg'),
(75, 4, '66f0381e-dd38-40f8-ac1d-5a96d959b2a7_464766302_2638503683019245_7907720538363963900_n.jpg'),
(76, 4, '715d765d-dea1-43b3-b1aa-a00375e95c21_464771563_1359138628824758_1840242753619147956_n.jpg'),
(77, 5, 'cead113a-7557-4221-8f47-98e256414a7f_467806314_1279612529835021_6334856744296857320_n.jpg'),
(78, 5, '2e054487-303f-4f33-96d2-5132ee07f142_468037867_580810904330452_4126561454533464688_n.jpg'),
(79, 5, 'd89d8f58-2b79-48c1-8204-2d2acc73ea10_468112552_581783381037241_8092978282070681987_n.jpg'),
(80, 6, 'cecfb9f4-6a16-4297-afeb-e7bfdb2206d6_468123579_583228804177852_8290230880589128467_n.jpg'),
(81, 6, '864f45b0-a73b-4016-a66a-37e7fe518fd9_468148617_547685287881595_1523933184800524396_n.jpg'),
(82, 6, '0a73d906-0f20-45e1-94a4-a632f405f11b_468243601_8750541658348790_7867436104343020634_n.jpg'),
(83, 7, '99fa04e5-4e5e-4767-8886-f09efe49a76e_468976950_897853695869984_7685968113549246725_n.jpg'),
(84, 7, '1093403a-5329-4096-b0ac-11cc6a2069cd_468980047_888073223485226_366249300294276249_n.jpg'),
(85, 7, '89bbf591-b3dd-4a2a-9083-6febd33c37aa_468982792_8711210865663621_5980914808246136663_n.jpg'),
(86, 8, '833302d4-256b-43fe-9381-255d8eb1e796_468986591_918792086502782_1650082841237509140_n.jpg'),
(87, 8, 'b7dac1b7-0af3-4115-96d2-017dce861bfa_469139134_1567784797439594_1617114848937342743_n.jpg'),
(88, 8, 'a3b11967-e374-4d14-baaa-90171f6f93ef_469157411_595007279611340_8820685975383812816_n.jpg'),
(89, 9, 'be7d56da-6b95-4138-b0d2-cbc04aa11419_464103824_1758322974986511_3813455476472043058_n.jpg'),
(90, 9, '60134c15-e2e1-4aa6-ba1b-f21d9ef55346_469207019_945125720795672_8354160371159659408_n.jpg'),
(91, 9, 'c093bdc9-5268-49bd-803e-ea382148239b_469209607_552377257640027_1702362245548331690_n.jpg'),
(92, 10, 'f036f8ee-166b-4810-b5be-8db334f42c86_464466510_1151917299875427_7813064931254440397_n.jpg'),
(93, 10, 'a7ce4b74-73b1-442e-bb65-906198bcd8a6_464579600_925630809474245_1366559687421735498_n.jpg'),
(94, 10, '1b26811c-74f4-461f-ae4d-a90d9533c15a_464599814_1257180382374464_7825998651641052001_n.jpg'),
(95, 11, '96ac403b-8003-4950-94ca-4f07ef71f76c_464599815_868199612128905_8473598377538470916_n.jpg'),
(96, 11, '353d901a-7f76-4255-b6b7-5a4f40173215_464658262_1237624973949562_7211833606302339533_n.jpg'),
(97, 11, '426dbe18-1e53-4f8c-8593-7c981879f4d5_464675059_877944907805016_416288875432623371_n.jpg'),
(98, 12, 'bad42e7a-eb80-4f8f-962d-993fdfc85f24_464679357_3687852978192378_1192960148848954113_n.jpg'),
(99, 12, '1b04383f-3a74-4ba8-a534-76dc2c2f1c27_464682335_547048831405590_270080917255800842_n.jpg'),
(100, 12, '784e6885-49bd-4472-9ae7-7ccf4fa7fcde_464755417_1073478110906863_6270199450890447407_n.jpg'),
(101, 13, '530ffab7-fb6f-4e42-a1e1-836c2ac46683_464766302_2638503683019245_7907720538363963900_n.jpg'),
(102, 13, 'fe42f4bb-e817-4e45-9f38-31f98c7d1e55_464771563_1359138628824758_1840242753619147956_n.jpg'),
(103, 13, '46225bdf-b4ae-4d5b-9c08-682f7a2c7258_467806314_1279612529835021_6334856744296857320_n.jpg'),
(104, 14, '17e6b2d9-86fd-4571-9b86-48d9964d2f57_468037867_580810904330452_4126561454533464688_n.jpg'),
(105, 14, '6a345abd-f6aa-404b-855b-0ecc14e5fa77_468112552_581783381037241_8092978282070681987_n.jpg'),
(106, 14, '6ef091bb-ad7d-4e2a-81f7-80c194ad488e_468123579_583228804177852_8290230880589128467_n.jpg'),
(107, 15, '9dd81922-ada3-4502-8847-125e6b02ff79_468243601_8750541658348790_7867436104343020634_n.jpg'),
(108, 15, 'c76aa5be-2a9a-476d-8d68-81b9308d00ee_468976950_897853695869984_7685968113549246725_n.jpg'),
(109, 15, 'd3ccc79c-2478-46d5-a235-8ea1861321aa_468980047_888073223485226_366249300294276249_n.jpg'),
(110, 16, '345238e4-4b3b-4c41-9ae4-40edbd8bb469_468980047_888073223485226_366249300294276249_n.jpg'),
(111, 16, 'f0bf92e4-e630-4125-9568-d3f14d6508a5_468982792_8711210865663621_5980914808246136663_n.jpg'),
(112, 16, '73b51419-042a-4924-add7-f3f0490d2603_468986591_918792086502782_1650082841237509140_n.jpg'),
(113, 17, '322c42df-9746-496e-89a4-818919e120e6_469139134_1567784797439594_1617114848937342743_n.jpg'),
(114, 17, 'd4fcb4ff-2e10-4de9-a167-c13364bc978f_469157411_595007279611340_8820685975383812816_n.jpg'),
(115, 17, '4300e24b-8d43-4014-abd4-39154c18144f_469207019_945125720795672_8354160371159659408_n.jpg'),
(116, 18, '6d76d509-0054-4814-9dba-fdd41f44fa4a_464103824_1758322974986511_3813455476472043058_n.jpg'),
(117, 18, '999de3d8-acbe-4898-826a-d9e67357f8ef_464466510_1151917299875427_7813064931254440397_n.jpg'),
(118, 18, '0e17958b-d867-4e4b-ac82-a02d9da1624b_469209607_552377257640027_1702362245548331690_n.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `id` bigint(20) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `comment` longtext DEFAULT NULL,
  `date` datetime DEFAULT current_timestamp(),
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'user'),
(2, 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `social_account`
--

CREATE TABLE `social_account` (
  `id` bigint(20) NOT NULL,
  `provider` varchar(20) NOT NULL,
  `providerid` varchar(50) NOT NULL,
  `email` varchar(150) NOT NULL COMMENT 'User account email',
  `name` varchar(100) NOT NULL COMMENT 'User name',
  `user_id` int(11) DEFAULT NULL,
  `provider_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tokens`
--

CREATE TABLE `tokens` (
  `token_id` int(11) NOT NULL,
  `token_value` varchar(255) NOT NULL,
  `token_type` varchar(50) NOT NULL,
  `expiration_date` datetime DEFAULT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expired` tinyint(1) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `token_type, length =255` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `fullname` varchar(100) DEFAULT '',
  `phone_number` varchar(10) NOT NULL,
  `user_address` varchar(200) DEFAULT '',
  `user_password` varchar(100) NOT NULL DEFAULT '',
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
  `is_active` tinyint(1) DEFAULT 1,
  `date_of_birth` datetime(6) DEFAULT NULL,
  `facebook_account_id` int(11) DEFAULT 1,
  `google_account_id` int(11) DEFAULT 1,
  `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fullname`, `phone_number`, `user_address`, `user_password`, `created_at`, `updated_at`, `is_active`, `date_of_birth`, `facebook_account_id`, `google_account_id`, `role_id`) VALUES
(1, 'Nguyen Van A', '0192391248', '01 VVN', 'hased pass', '2024-11-04 18:12:00', '2024-11-04 18:57:58', 1, '1991-11-13 00:00:00.000000', 1, 1, NULL),
(11, 'Ho Thanh Dat', '0961826304', 'abc123123', '$2a$10$1ulGuMpC/8DweHvMa2ryP.CyF77mlbBdtbzpNo8FAftWsigcParsa', '2024-12-01 14:16:15', '2024-12-01 14:16:15', 1, '2004-03-25 00:00:00.000000', 0, 0, 1),
(17, 'John Smith', '223456789', 'This is John\'s address', '12345', '2024-12-11 10:22:09', '2024-12-11 10:22:09', 1, '1999-10-23 00:00:00.000000', 0, 0, 1),
(18, 'Adam Smith', '091236451', 'This is John\'s address', '123456', '2024-12-16 08:51:24', '2024-12-16 08:51:24', 1, '1999-10-23 00:00:00.000000', 0, 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brands`
--
ALTER TABLE `brands`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `brands_id` (`brands_id`);

--
-- Indexes for table `product_images`
--
ALTER TABLE `product_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_product_images_product_id` (`product_id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `social_account`
--
ALTER TABLE `social_account`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`token_id`),
  ADD UNIQUE KEY `token_value` (`token_value`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brands`
--
ALTER TABLE `brands`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `order_details`
--
ALTER TABLE `order_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `product_images`
--
ALTER TABLE `product_images`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `social_account`
--
ALTER TABLE `social_account`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tokens`
--
ALTER TABLE `tokens`
  MODIFY `token_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`brands_id`) REFERENCES `brands` (`id`);

--
-- Constraints for table `product_images`
--
ALTER TABLE `product_images`
  ADD CONSTRAINT `fk_product_images_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `social_account`
--
ALTER TABLE `social_account`
  ADD CONSTRAINT `social_account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `tokens`
--
ALTER TABLE `tokens`
  ADD CONSTRAINT `tokens_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
