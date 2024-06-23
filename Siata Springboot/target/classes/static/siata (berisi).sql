-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Waktu pembuatan: 22 Bulan Mei 2024 pada 13.28
-- Versi server: 8.0.30
-- Versi PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `siata`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `calendar`
--

CREATE TABLE `calendar` (
  `calendar_id` int NOT NULL,
  `event_id` int NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `summary` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `calendar`
--

INSERT INTO `calendar` (`calendar_id`, `event_id`, `start_time`, `end_time`, `summary`) VALUES
(1, 1, '2024-05-22 10:00:00', '2024-05-22 12:00:00', 'Beach Cleanup Event'),
(2, 2, '2024-06-05 09:00:00', '2024-06-05 11:00:00', 'Coastal Conservation Day'),
(3, 3, '2024-06-15 08:30:00', '2024-06-15 10:30:00', 'Shoreline Restoration Project'),
(4, 4, '2024-06-30 11:00:00', '2024-06-30 13:00:00', 'Ocean Cleanup Drive'),
(5, 5, '2024-07-10 10:30:00', '2024-07-10 12:30:00', 'Beach Beautification Campaign'),
(6, 6, '2024-07-20 09:00:00', '2024-07-20 11:00:00', 'Marine Debris Removal Initiative'),
(7, 7, '2024-08-05 08:00:00', '2024-08-05 10:00:00', 'Coastal Cleanup Day'),
(8, 8, '2024-08-20 09:30:00', '2024-08-20 11:30:00', 'Sand Dune Preservation Program'),
(9, 9, '2024-09-10 11:00:00', '2024-09-10 13:00:00', 'Beach Erosion Control Project'),
(10, 10, '2024-09-25 10:00:00', '2024-09-25 12:00:00', 'Seabird Protection Initiative');

-- --------------------------------------------------------

--
-- Struktur dari tabel `comments`
--

CREATE TABLE `comments` (
  `comment_id` int NOT NULL,
  `user_id` int NOT NULL,
  `destination_id` int DEFAULT NULL,
  `event_id` int DEFAULT NULL,
  `comment_text` text NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `comments`
--

INSERT INTO `comments` (`comment_id`, `user_id`, `destination_id`, `event_id`, `comment_text`, `created_at`) VALUES
(1, 1, NULL, 1, 'Excited to join the cleanup!', '2024-05-22 09:30:00'),
(2, 2, NULL, 2, 'Looking forward to making a difference!', '2024-06-05 08:45:00'),
(3, 3, NULL, 3, 'Let\'s protect our coastlines!', '2024-06-15 08:00:00'),
(4, 4, NULL, 4, 'Happy to be part of the solution!', '2024-06-30 10:45:00'),
(5, 5, NULL, 5, 'Can\'t wait to beautify the beach!', '2024-07-10 10:00:00'),
(6, 6, NULL, 6, 'Ready to remove marine debris!', '2024-07-20 08:30:00'),
(7, 7, NULL, 7, 'Let\'s clean up our coastline together!', '2024-08-05 07:45:00'),
(8, 8, NULL, 8, 'Preserving our coastal ecosystems!', '2024-08-20 09:00:00'),
(9, 9, NULL, 9, 'Protecting against beach erosion!', '2024-09-10 10:15:00'),
(10, 10, NULL, 10, 'Helping safeguard seabird habitats!', '2024-09-25 09:30:00');

-- --------------------------------------------------------

--
-- Struktur dari tabel `destinations`
--

CREATE TABLE `destinations` (
  `destination_id` int NOT NULL,
  `destination_name` varchar(100) NOT NULL,
  `description` text,
  `location` varchar(255) DEFAULT NULL,
  `photo_url` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `destinations`
--

INSERT INTO `destinations` (`destination_id`, `destination_name`, `description`, `location`, `photo_url`) VALUES
(1, 'Sandy Shores Beach', 'A popular tourist beach with beautiful sands and clear waters.', 'Coastal City, XYZ', NULL),
(2, 'Rocky Cove Coastal Reserve', 'A protected natural area with rugged coastline and diverse marine life.', 'Seaside Town, ABC', NULL),
(3, 'Sunrise Bay', 'Known for its stunning sunrise views and sandy shores.', 'Beachfront Village, LMN', NULL),
(4, 'Marine Park', 'Home to various marine species and coral reefs, perfect for snorkeling.', 'Island Paradise, PQR', NULL),
(5, 'Turtle Beach', 'Famous for its nesting sea turtles and pristine coastline.', 'Tropical Island, UVW', NULL),
(6, 'Lighthouse Point', 'Offers panoramic views of the coastline and opportunities for birdwatching.', 'Cliffside Retreat, XYZ', NULL),
(7, 'Paradise Cove', 'A secluded beach with crystal-clear waters and white sands.', 'Remote Island, STU', NULL),
(8, 'Coral Reef Sanctuary', 'Protecting delicate coral ecosystems and promoting underwater conservation.', 'Reef Island, DEF', NULL),
(9, 'Mangrove Estuary', 'Home to mangrove forests and diverse wildlife, ideal for eco-tours.', 'Estuarine Region, GHI', NULL),
(10, 'Seagrass Meadows', 'Critical habitat for marine life, supporting various fish and invertebrate species.', 'Coastal Inlet, JKL', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `events`
--

CREATE TABLE `events` (
  `event_id` int NOT NULL,
  `event_name` varchar(100) NOT NULL,
  `event_description` text,
  `event_date` date DEFAULT NULL,
  `event_time` time DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `event_img` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `events`
--

INSERT INTO `events` (`event_id`, `event_name`, `event_description`, `event_date`, `event_time`, `location`, `event_img`) VALUES
(1, 'Sandy Shores Cleanup', 'Join us for a community beach cleanup at Sandy Shores Beach!', '2024-05-22', '10:00:00', 'Coastal City, XYZ', NULL),
(2, 'Rocky Cove Conservation Day', 'Help protect Rocky Cove Coastal Reserve through conservation activities!', '2024-06-05', '09:00:00', 'Seaside Town, ABC', NULL),
(3, 'Shoreline Restoration Project', 'Contribute to restoring the natural beauty of Sunrise Bay\'s coastline!', '2024-06-15', '08:30:00', 'Beachfront Village, LMN', NULL),
(4, 'Ocean Cleanup Drive', 'Participate in cleaning up marine debris along the coast at Marine Park!', '2024-06-30', '11:00:00', 'Island Paradise, PQR', NULL),
(5, 'Beach Beautification Campaign', 'Beautify Turtle Beach and make it a cleaner, more welcoming habitat!', '2024-07-10', '10:30:00', 'Tropical Island, UVW', NULL),
(6, 'Marine Debris Removal Initiative', 'Join efforts to remove marine debris from Lighthouse Point and protect coastal ecosystems!', '2024-07-20', '09:00:00', 'Cliffside Retreat, XYZ', NULL),
(7, 'Coastal Cleanup Day', 'Come together for a day dedicated to cleaning up Paradise Cove and preserving its natural beauty!', '2024-08-05', '08:00:00', 'Remote Island, STU', NULL),
(8, 'Sand Dune Preservation Program', 'Help preserve the sand dunes at Coral Reef Sanctuary and maintain coastal biodiversity!', '2024-08-20', '09:30:00', 'Reef Island, DEF', NULL),
(9, 'Beach Erosion Control Project', 'Join efforts to mitigate beach erosion at Mangrove Estuary and protect coastal habitats!', '2024-09-10', '11:00:00', 'Estuarine Region, GHI', NULL),
(10, 'Seabird Protection Initiative', 'Contribute to protecting seabird habitats at Seagrass Meadows and promote coastal conservation!', '2024-09-25', '10:00:00', 'Coastal Inlet, JKL', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `media_gallery`
--

CREATE TABLE `media_gallery` (
  `media_id` int NOT NULL,
  `destination_id` int DEFAULT NULL,
  `event_id` int DEFAULT NULL,
  `media_type` enum('Image','Video') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `media_url` mediumblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `media_gallery`
--

INSERT INTO `media_gallery` (`media_id`, `destination_id`, `event_id`, `media_type`, `media_url`) VALUES
(1, 1, NULL, 'Image', 0x73616e64795f73686f7265735f636c65616e75702e6a7067),
(2, 2, NULL, 'Image', 0x726f636b795f636f76655f636f6e736572766174696f6e2e6a7067),
(3, 3, NULL, 'Image', 0x73686f72656c696e655f726573746f726174696f6e2e6a7067),
(4, 4, NULL, 'Image', 0x6f6365616e5f636c65616e75705f64726976652e6a7067),
(5, 5, NULL, 'Image', 0x62656163685f6265617574696669636174696f6e2e6a7067),
(6, 6, NULL, 'Image', 0x6d6172696e655f6465627269735f72656d6f76616c2e6a7067),
(7, 7, NULL, 'Image', 0x636f617374616c5f636c65616e75705f6461792e6a7067),
(8, 8, NULL, 'Image', 0x73616e645f64756e655f707265736572766174696f6e2e6a7067),
(9, 9, NULL, 'Image', 0x62656163685f65726f73696f6e5f636f6e74726f6c2e6a7067),
(10, 10, NULL, 'Image', 0x736561626972645f70726f74656374696f6e2e6a7067);

-- --------------------------------------------------------

--
-- Struktur dari tabel `statistics`
--

CREATE TABLE `statistics` (
  `stat_id` int NOT NULL,
  `user_id` int NOT NULL,
  `event_id` int NOT NULL,
  `hours_volunteered` int DEFAULT NULL,
  `date_recorded` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `statistics`
--

INSERT INTO `statistics` (`stat_id`, `user_id`, `event_id`, `hours_volunteered`, `date_recorded`) VALUES
(1, 1, 1, 3, '2024-05-22'),
(2, 2, 2, 4, '2024-06-05'),
(3, 3, 3, 2, '2024-06-15'),
(4, 4, 4, 5, '2024-06-30'),
(5, 5, 5, 3, '2024-07-10'),
(6, 6, 6, 4, '2024-07-20'),
(7, 7, 7, 3, '2024-08-05'),
(8, 8, 8, 2, '2024-08-20'),
(9, 9, 9, 4, '2024-09-10'),
(10, 10, 10, 3, '2024-09-25');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `user_id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `profile_pic` mediumblob,
  `gender` enum('Male','Female') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `no_telp` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `email`, `full_name`, `profile_pic`, `gender`, `no_telp`) VALUES
(1, 'volunteer1', 'password1', 'volunteer1@example.com', 'John Doe', NULL, 'Male', '1234567890'),
(2, 'volunteer2', 'password2', 'volunteer2@example.com', 'Jane Smith', NULL, 'Female', '2345678901'),
(3, 'volunteer3', 'password3', 'volunteer3@example.com', 'David Johnson', NULL, 'Male', '3456789012'),
(4, 'volunteer4', 'password4', 'volunteer4@example.com', 'Emily Brown', NULL, 'Female', '4567890123'),
(5, 'volunteer5', 'password5', 'volunteer5@example.com', 'Michael Wilson', NULL, 'Male', '5678901234'),
(6, 'volunteer6', 'password6', 'volunteer6@example.com', 'Jessica Lee', NULL, 'Female', '6789012345'),
(7, 'volunteer7', 'password7', 'volunteer7@example.com', 'Christopher Martinez', NULL, 'Male', '7890123456'),
(8, 'volunteer8', 'password8', 'volunteer8@example.com', 'Amanda Taylor', NULL, 'Female', '8901234567'),
(9, 'volunteer9', 'password9', 'volunteer9@example.com', 'Daniel Anderson', NULL, 'Male', '9012345678'),
(10, 'volunteer10', 'password10', 'volunteer10@example.com', 'Sarah Garcia', NULL, 'Female', '0123456789');

-- --------------------------------------------------------

--
-- Struktur dari tabel `volunteers`
--

CREATE TABLE `volunteers` (
  `user_id` int NOT NULL,
  `event_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `volunteers`
--

INSERT INTO `volunteers` (`user_id`, `event_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `calendar`
--
ALTER TABLE `calendar`
  ADD PRIMARY KEY (`calendar_id`),
  ADD KEY `event_id` (`event_id`);

--
-- Indeks untuk tabel `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `destination_id` (`destination_id`),
  ADD KEY `event_id` (`event_id`);

--
-- Indeks untuk tabel `destinations`
--
ALTER TABLE `destinations`
  ADD PRIMARY KEY (`destination_id`);

--
-- Indeks untuk tabel `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`event_id`);

--
-- Indeks untuk tabel `media_gallery`
--
ALTER TABLE `media_gallery`
  ADD PRIMARY KEY (`media_id`),
  ADD KEY `destination_id` (`destination_id`),
  ADD KEY `event_id` (`event_id`);

--
-- Indeks untuk tabel `statistics`
--
ALTER TABLE `statistics`
  ADD PRIMARY KEY (`stat_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `event_id` (`event_id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Indeks untuk tabel `volunteers`
--
ALTER TABLE `volunteers`
  ADD KEY `user_id` (`user_id`),
  ADD KEY `event_id` (`event_id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `calendar`
--
ALTER TABLE `calendar`
  MODIFY `calendar_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `comments`
--
ALTER TABLE `comments`
  MODIFY `comment_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `destinations`
--
ALTER TABLE `destinations`
  MODIFY `destination_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `events`
--
ALTER TABLE `events`
  MODIFY `event_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `media_gallery`
--
ALTER TABLE `media_gallery`
  MODIFY `media_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `statistics`
--
ALTER TABLE `statistics`
  MODIFY `stat_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `calendar`
--
ALTER TABLE `calendar`
  ADD CONSTRAINT `calendar_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`);

--
-- Ketidakleluasaan untuk tabel `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`destination_id`) REFERENCES `destinations` (`destination_id`),
  ADD CONSTRAINT `comments_ibfk_3` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`);

--
-- Ketidakleluasaan untuk tabel `media_gallery`
--
ALTER TABLE `media_gallery`
  ADD CONSTRAINT `media_gallery_ibfk_1` FOREIGN KEY (`destination_id`) REFERENCES `destinations` (`destination_id`),
  ADD CONSTRAINT `media_gallery_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`);

--
-- Ketidakleluasaan untuk tabel `statistics`
--
ALTER TABLE `statistics`
  ADD CONSTRAINT `statistics_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `statistics_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`);

--
-- Ketidakleluasaan untuk tabel `volunteers`
--
ALTER TABLE `volunteers`
  ADD CONSTRAINT `volunteers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `volunteers_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
