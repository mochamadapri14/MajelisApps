<?php
require_once 'koneksi/koneksi.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
$lokasi = $_POST['lokasi'];
$query = "SELECT * FROM majelis WHERE lokasi LIKE '%".$lokasi."%' ORDER BY tanggal desc";
$result= mysqli_query($konek,$query);
$array = array();
    while($row = mysqli_fetch_assoc($result)){

	$array[] = $row;
    }
    $count = mysqli_num_rows($result);
	echo ($result) ? json_encode(array("kode" => "1","jumlah"=>$count,"resultJadwal"=>$array)):

	json_encode(array("kode" => "0","pesan" => "data tidak ditemukan"));
}

?>