-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 30 Apr 2020 pada 07.49
-- Versi server: 10.4.11-MariaDB
-- Versi PHP: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_majelis`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `majelis`
--

CREATE TABLE `majelis` (
  `id_jadwal` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `nama_majelis` varchar(50) NOT NULL,
  `tanggal` varchar(50) NOT NULL,
  `jam_mulai` varchar(10) NOT NULL,
  `nama_alamat` varchar(50) NOT NULL,
  `lokasi` varchar(255) NOT NULL,
  `keterangan` text NOT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `latitude` varchar(255) NOT NULL,
  `longitude` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `majelis`
--

INSERT INTO `majelis` (`id_jadwal`, `user_id`, `foto`, `nama_majelis`, `tanggal`, `jam_mulai`, `nama_alamat`, `lokasi`, `keterangan`, `waktu`, `latitude`, `longitude`) VALUES
(92, 83, 'img3401.jpg', 'Majlis Rasul', '2020/04/29', '12:41', 'Tomang', 'Tomang, Kec. Grogol petamburan, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta, Indonesia', 'a', '2020-04-30 05:42:39', '-6.1747751', '106.7999513');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pesan`
--

CREATE TABLE `pesan` (
  `id_pesan` int(11) NOT NULL,
  `majelis_id` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `pesan` text NOT NULL,
  `waktuy` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `foto` varchar(50) DEFAULT NULL,
  `nama_user` varchar(50) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(255) NOT NULL,
  `level` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `foto`, `nama_user`, `username`, `password`, `level`) VALUES
(74, 'img1928.jpg', 'yudaMC', 'yudaa', '$2y$10$IWUZHDl64uq53BH7pGzUCO.rpxkbbhehTfDCUiLCtnzPWcAesr5dq', 'majelis'),
(75, '', 'ray', 'apray', '$2y$10$x3SelzazFrsHgfSkIjkLh.UIcEAXUZG46JFMFwpfq/LCv864CWSn.', 'jamaah'),
(76, '', 'hara goliat', 'hara', '$2y$10$wVqtrv2b7KI8lHDX7sa1RehuF4LF9xQPxZ3w3xIhIBPf8ttD8VIfa', 'majelis'),
(79, 'img312.jpg', 'Ali Thalib', 'ali123', '$2y$10$u0UXA3GVb7XRyAAfVlMvD.EhecAS6OeULoOZkWhMYJbg8qSfiVBNe', 'majelis'),
(80, 'img5279.jpg', 'Umar', 'umar', '$2y$10$8g1aS9CYrOGUQefKH1nu5OcHeLnUJLVMykQBe7lg5goqKLMBFIpaS', 'Jamaah'),
(83, 'img8339.jpg', 'Husein Alhaddad', 'husein', '$2y$10$lYj90Sa.XcX/8Gn72OV1.equ0n4ylfQxHvRUaTiC3ne/F1Txd/knq', 'Majelis'),
(84, NULL, 'Hasan Assegaf', 'hasan', '$2y$10$JKoZ1GDMUCW79tOujK2GQe957bS9KxWvAb/VgCfughg0PV.ToPJIG', 'Jamaah'),
(85, 'img8058.jpg', 'Ali alydrus', 'ali', '$2y$10$tus/s0Qv.GIZjZMhojbR0urLLrKlhF5zBccwWArEvPhNMi3m3EzCW', 'Majelis');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `majelis`
--
ALTER TABLE `majelis`
  ADD PRIMARY KEY (`id_jadwal`),
  ADD KEY `user_id` (`user_id`);

--
-- Indeks untuk tabel `pesan`
--
ALTER TABLE `pesan`
  ADD PRIMARY KEY (`id_pesan`),
  ADD KEY `majelis_id` (`majelis_id`,`userid`),
  ADD KEY `user_id` (`userid`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `majelis`
--
ALTER TABLE `majelis`
  MODIFY `id_jadwal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=93;

--
-- AUTO_INCREMENT untuk tabel `pesan`
--
ALTER TABLE `pesan`
  MODIFY `id_pesan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `majelis`
--
ALTER TABLE `majelis`
  ADD CONSTRAINT `majelis_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id_user`);

--
-- Ketidakleluasaan untuk tabel `pesan`
--
ALTER TABLE `pesan`
  ADD CONSTRAINT `pesan_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id_user`),
  ADD CONSTRAINT `pesan_ibfk_2` FOREIGN KEY (`majelis_id`) REFERENCES `majelis` (`id_jadwal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
