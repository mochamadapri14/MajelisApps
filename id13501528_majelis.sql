-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Waktu pembuatan: 01 Bulan Mei 2020 pada 05.13
-- Versi server: 10.3.16-MariaDB
-- Versi PHP: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id13501528_majelis`
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
(95, 83, 'img5627.jpg', 'Majelis nurmus', '2020/04/30', '16:25', 'Kecamatan Senen', 'Kec. Senen, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta, Indonesia', 'a', '2020-04-30 23:38:15', '-6.193456200000001', '106.8502879'),
(108, 83, 'img59.jpg', 'Majelis Rasulullah SAW', '2020/05/01', '23:41', 'Masjid Istiqlal', 'Masjid Istiqlal, Ps. Baru, Kecamatan Sawah Besar, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta, Indonesia', 'a', '2020-05-01 11:47:11', '-6.169993600000001', '106.8309278');

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

--
-- Dumping data untuk tabel `pesan`
--

INSERT INTO `pesan` (`id_pesan`, `majelis_id`, `userid`, `pesan`, `waktuy`) VALUES
(114, 108, 84, 'tes', '2020-05-01 12:02:14'),
(115, 108, 84, 'Tes', '2020-05-01 12:03:16');

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
(83, 'img6962.jpg', 'Husein Alhaddad', 'husein', '$2y$10$lYj90Sa.XcX/8Gn72OV1.equ0n4ylfQxHvRUaTiC3ne/F1Txd/knq', 'Majelis'),
(84, NULL, 'Hasan Assegaf', 'hasan', '$2y$10$JKoZ1GDMUCW79tOujK2GQe957bS9KxWvAb/VgCfughg0PV.ToPJIG', 'Jamaah'),
(85, 'img8058.jpg', 'Ali alydrus', 'ali', '$2y$10$tus/s0Qv.GIZjZMhojbR0urLLrKlhF5zBccwWArEvPhNMi3m3EzCW', 'Majelis'),
(90, 'img3215.jpg', 'Mochamad Apri', 'apry1', '$2y$10$V5h2surIFhEF6T2FQqodb.O.JUpv24Eqnsacn860iSQsrBv2QUxOW', 'Jamaah'),
(91, NULL, 'Annisa Dije', 'dije123', '$2y$10$8yflPk.M2epKLtfbeiRgQe0fYCabgWaw7b5F.c4E2CW7GGjLNkLm.', 'Majelis'),
(92, NULL, 'Annisa Dije', 'dije1234', '$2y$10$5xxWtNBYflZWV4P8rp4RAOveD9wabrQvIy4Ic8zjzcZR2dbctVC/K', 'Jamaah'),
(93, NULL, 'yudaaaaaa', 'yuda', '$2y$10$/1ersfHXDSS7Qcq02U50JeIQLcIYskehdNKb3Myoi2lyx1tySX18O', 'Majelis'),
(94, NULL, 'Goldy', 'goldywy', '$2y$10$DGYzTDmPlRIu0ZelXUpsHe6CR1V/P0dyPxbkiagL3p5fFHgVo1LEm', 'Jamaah'),
(95, '', 'Goldy', 'goldywy', '$2y$10$gRvOt3HaxtZkLmrf2/RixeWTi9afjwo5utHDIGNnKZGczM3BrRLfy', 'Majelis'),
(96, NULL, 'Goldy', 'goldywyy', '$2y$10$FqX3x6FqZ3SqKKt7FjO4DOFA.1xZvDiaVI0SC5iss68.qzcIInmMi', 'Jamaah'),
(97, '', 'Goldy W', 'goldywyy', '$2y$10$nGrQoHlniXmX24aBi2YNoexyOgdR/LfdMtffrz3F2BAYaPihJBtFK', 'Majelis');

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
  MODIFY `id_jadwal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=112;

--
-- AUTO_INCREMENT untuk tabel `pesan`
--
ALTER TABLE `pesan`
  MODIFY `id_pesan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;

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
