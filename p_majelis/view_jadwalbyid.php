<?php
require_once 'koneksi/koneksi.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
$user_id = $_POST['user_id'];
$query = "SELECT * FROM majelis WHERE user_id = '$user_id' ORDER BY tanggal desc";
$result= mysqli_query($konek,$query);
$array = array();
    while($row = mysqli_fetch_assoc($result)){

	$array[] = $row;
	}
	echo ($result) ? json_encode(array("kode" => "1", "resultJadwal"=>$array)):

	json_encode(array("kode" => "0","pesan" => "data tidak ditemukan"));
}

?>