-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 14 Agu 2019 pada 11.24
-- Versi Server: 10.1.28-MariaDB
-- PHP Version: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projectkp`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `nama` varchar(80) NOT NULL,
  `telepon` varchar(15) NOT NULL,
  `gender` varchar(9) NOT NULL,
  `alamat` text NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `customer`
--

INSERT INTO `customer` (`customer_id`, `nama`, `telepon`, `gender`, `alamat`, `created`, `updated`) VALUES
(2, 'Reza Zulfan', '087847274085', 'Laki-laki', 'Jl.Anggrek 1 Karangdawa Margasari', '2019-07-30 12:45:59', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_belanjaan`
--

CREATE TABLE `detail_belanjaan` (
  `id` int(11) NOT NULL,
  `invoice` varchar(50) NOT NULL,
  `tgl_transaksi` varchar(70) NOT NULL,
  `kode_barang` varchar(50) NOT NULL,
  `harga` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `sub_total` int(11) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `detail_belanjaan`
--

INSERT INTO `detail_belanjaan` (`id`, `invoice`, `tgl_transaksi`, `kode_barang`, `harga`, `jumlah`, `sub_total`, `created`, `updated`) VALUES
(13, 'F1307', '08 Aug 2019', 'TB03', 30000, 1, 30000, '2019-08-07 11:21:09', NULL),
(14, 'F1308', '08 Aug 2019', 'TB03', 30000, 2, 60000, '2019-08-07 15:03:36', NULL),
(15, 'F1309', '08 Aug 2019', 'TA03', 25000, 2, 50000, '2019-08-07 17:03:07', NULL),
(16, 'F1310', '08 Aug 2019', 'TA03', 25000, 2, 50000, '2019-08-09 10:13:21', NULL),
(19, 'F1312', '09 Aug 2019', 'TA02', 20000, 3, 60000, '2019-08-09 16:27:20', NULL),
(20, 'F1312', '09 Aug 2019', 'TB01', 15000, 3, 45000, '2019-08-09 16:27:20', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `item`
--

CREATE TABLE `item` (
  `item_id` int(11) NOT NULL,
  `kode_barang` varchar(100) DEFAULT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `category_id` varchar(50) NOT NULL,
  `unit_id` varchar(50) NOT NULL,
  `harga` int(11) DEFAULT NULL,
  `stok` int(11) DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `item`
--

INSERT INTO `item` (`item_id`, `kode_barang`, `nama`, `category_id`, `unit_id`, `harga`, `stok`, `created`, `updated`) VALUES
(15, 'TB01', 'Biasa (1 Telor)', 'Martabak Telor (Bebek)', 'Porsi', 15000, 18, '2019-08-01 10:24:36', NULL),
(16, 'TB02', 'Special (2 Telor)', 'Martabak Telor (Bebek)', 'Porsi', 25000, 48, '2019-08-01 10:25:10', NULL),
(17, 'TB03', 'Istimewa (3 Telor)', 'Martabak Telor (Bebek)', 'Porsi', 30000, 10, '2019-08-01 10:25:35', NULL),
(18, 'TB04', 'Super (4 Telor)', 'Martabak Telor (Bebek)', 'Porsi', 35000, 15, '2019-08-01 10:26:18', NULL),
(19, 'TA01', 'Special (2 Telor)', 'Martabak Telor (Ayam)', 'Porsi', 15000, 12, '2019-08-01 10:27:13', NULL),
(20, 'TA02', 'Istimewa (3 Telor)', 'Martabak Telor (Ayam)', 'Porsi', 20000, 30, '2019-08-01 10:27:34', NULL),
(21, 'TA03', 'Super (4 Telor)', 'Martabak Telor (Ayam)', 'Porsi', 25000, 0, '2019-08-01 10:28:03', NULL),
(22, 'TA04', 'Jumbo (5 Telor)', 'Martabak Telor (Ayam)', 'Porsi', 30000, 0, '2019-08-01 10:28:27', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kategori`
--

CREATE TABLE `kategori` (
  `id_kategori` int(11) NOT NULL,
  `nama_kategori` varchar(50) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `nama_kategori`, `created`, `updated`) VALUES
(3, 'Martabak Telor (Bebek)', '2019-08-01 10:22:05', NULL),
(4, 'Martabak Telor (Ayam)', '2019-08-01 10:22:19', NULL),
(5, 'Martabak Manis', '2019-08-01 10:22:30', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `no_invoice`
--

CREATE TABLE `no_invoice` (
  `id` int(11) NOT NULL,
  `invoice` varchar(50) NOT NULL,
  `kasir` varchar(100) NOT NULL,
  `tanggal` varchar(50) NOT NULL,
  `customer` varchar(100) NOT NULL,
  `total_belanjaan` int(11) NOT NULL,
  `uang_bayar` int(11) NOT NULL,
  `potongan` int(11) NOT NULL DEFAULT '0',
  `kembalian` int(11) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `no_invoice`
--

INSERT INTO `no_invoice` (`id`, `invoice`, `kasir`, `tanggal`, `customer`, `total_belanjaan`, `uang_bayar`, `potongan`, `kembalian`, `created`, `updated`) VALUES
(21, 'F1307', 'Azmi', '07 Aug 2019', 'Umum', 80000, 100000, 0, 20000, '2019-08-07 11:21:09', NULL),
(22, 'F1308', 'Azmi', '07 Aug 2019', 'Reza Zulfan', 60000, 80000, 0, 20000, '2019-08-07 15:03:36', NULL),
(23, 'F1309', 'Azmi', '07 Aug 2019', 'Umum', 50000, 100000, 10, 55000, '2019-08-07 17:03:07', NULL),
(24, 'F1310', 'Azmi', '09 Aug 2019', 'Reza Zulfan', 50000, 100000, 0, 50000, '2019-08-09 10:13:21', NULL),
(25, 'F1311', 'Administrator', '09 Aug 2019', 'Umum', 60000, 100000, 0, 40000, '2019-08-09 16:25:42', NULL),
(26, 'F1312', 'Administrator', '09 Aug 2019', 'Umum', 105000, 110000, 0, 5000, '2019-08-09 16:27:20', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `stok_in`
--

CREATE TABLE `stok_in` (
  `id` int(11) NOT NULL,
  `tgl_masuk` varchar(100) NOT NULL,
  `kode_barang` varchar(100) NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `unit` varchar(50) NOT NULL,
  `stok_masuk` int(11) NOT NULL,
  `keterangan` text NOT NULL,
  `supplier` varchar(100) NOT NULL,
  `harga_beli` int(11) NOT NULL,
  `total_beli` int(11) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `stok_in`
--

INSERT INTO `stok_in` (`id`, `tgl_masuk`, `kode_barang`, `nama_barang`, `unit`, `stok_masuk`, `keterangan`, `supplier`, `harga_beli`, `total_beli`, `created`, `updated`) VALUES
(21, '01 August 2019', 'TB01', 'Biasa (1 Telor)', 'Porsi', 20, 'Bonus', 'Mulyadi', 12000, 240000, '2019-08-01 10:58:20', NULL),
(22, '01 August 2019', 'TB03', 'Istimewa (3 Telor)', 'Porsi', 10, '', 'Mulyadi', 10000, 100000, '2019-08-01 10:59:11', NULL),
(23, '01 August 2019', 'TA01', 'Special (2 Telor)', 'Porsi', 12, '', 'Mas Hosun', 10000, 120000, '2019-08-01 11:01:09', NULL),
(24, '01 August 2019', 'TA02', 'Istimewa (3 Telor)', 'Porsi', 30, '', 'Mas Hosun', 12000, 360000, '2019-08-01 11:01:44', NULL),
(25, '01 August 2019', 'TB02', 'Special (2 Telor)', 'Porsi', 40, '', 'Hartono', 12000, 480000, '2019-08-01 11:04:27', NULL),
(26, '01 August 2019', 'TB04', 'Super (4 Telor)', 'Porsi', 15, '', 'Mulyadi', 9000, 135000, '2019-08-01 11:04:53', NULL),
(27, '07 August 2019', 'TB02', 'Special (2 Telor)', 'Porsi', 10, 'Bonus', 'Mulyadi', 12000, 120000, '2019-08-07 16:39:40', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `stok_out`
--

CREATE TABLE `stok_out` (
  `id` int(11) NOT NULL,
  `tgl_hariini` varchar(50) NOT NULL,
  `kode_barang` varchar(100) NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `unit` varchar(50) NOT NULL,
  `stok_keluar` int(11) NOT NULL,
  `keterangan` text NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `stok_out`
--

INSERT INTO `stok_out` (`id`, `tgl_hariini`, `kode_barang`, `nama_barang`, `unit`, `stok_keluar`, `keterangan`, `created`, `updated`) VALUES
(1, '08 August 2019', 'TB01', 'Biasa (1 Telor)', 'Porsi', 1, 'Basi', '2019-08-08 09:14:31', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `supplier`
--

CREATE TABLE `supplier` (
  `supplier_id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `telepon` varchar(15) NOT NULL,
  `alamat` varchar(200) NOT NULL,
  `keterangan` text,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `supplier`
--

INSERT INTO `supplier` (`supplier_id`, `nama`, `telepon`, `alamat`, `keterangan`, `created`, `updated`) VALUES
(1, 'Hartono', '081293112772', 'Lebaksiu', 'Supplier Bahan Kopi', '2019-07-20 13:05:25', NULL),
(2, 'Mulyadi', '089271281462', 'Karang gondang', 'Supplier Kacang', '2019-07-20 13:05:25', NULL),
(3, 'Mas Hosun', '082327917293', 'Slawi', 'Bandar ale ale  dus dusan ', '2019-07-21 01:15:11', '2019-07-23 04:40:24');

-- --------------------------------------------------------

--
-- Struktur dari tabel `unit`
--

CREATE TABLE `unit` (
  `id_unit` int(11) NOT NULL,
  `nama_unit` varchar(50) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `unit`
--

INSERT INTO `unit` (`id_unit`, `nama_unit`, `created`, `updated`) VALUES
(2, 'Porsi', '2019-08-01 10:22:41', NULL),
(3, 'Cup', '2019-08-01 10:22:47', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nip` varchar(20) NOT NULL,
  `nama` varchar(80) NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `level` varchar(5) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `nip`, `nama`, `username`, `password`, `level`, `created`, `updated`) VALUES
(3, '921092', 'Administrator', 'YWRtaW4=', 'YWRtaW4=', 'Admin', '2019-07-29 13:00:12', NULL),
(5, '129819', 'Kasir', 'a2FzaXI=', 'a2FzaXI=', 'Kasir', '2019-08-01 10:30:35', NULL),
(7, '18201', 'Reza Zulfan Azmi', 'cmV6YWF6bWk=', 'cmV6YQ==', 'Admin', '2019-08-09 09:39:11', NULL),
(8, '198291', 'kojin', 'a29qaW5kYXk=', 'a29qaW4=', 'Kasir', '2019-08-09 13:55:55', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `detail_belanjaan`
--
ALTER TABLE `detail_belanjaan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `category_id` (`category_id`),
  ADD KEY `unit_id` (`unit_id`);

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `no_invoice`
--
ALTER TABLE `no_invoice`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stok_in`
--
ALTER TABLE `stok_in`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stok_out`
--
ALTER TABLE `stok_out`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supplier_id`);

--
-- Indexes for table `unit`
--
ALTER TABLE `unit`
  ADD PRIMARY KEY (`id_unit`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `detail_belanjaan`
--
ALTER TABLE `detail_belanjaan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `no_invoice`
--
ALTER TABLE `no_invoice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `stok_in`
--
ALTER TABLE `stok_in`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `stok_out`
--
ALTER TABLE `stok_out`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `supplier_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `unit`
--
ALTER TABLE `unit`
  MODIFY `id_unit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
