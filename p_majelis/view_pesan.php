<?php
require_once 'koneksi/koneksi.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
    $majelis_id = $_POST['majelis_id'];

$query = "SELECT * FROM pesan,user WHERE majelis_id = '$majelis_id' AND pesan.userid = user.id_user
 ORDER BY waktuy desc";
$result= mysqli_query($konek,$query);
$array = array();
while($row = mysqli_fetch_assoc($result)){

	$array[] = $row;
	}
	echo ($result) ? json_encode(array("kode" => "1", "resultPesan"=>$array)):
	
    json_encode(array("kode" => "0","pesan" => "data tidak ditemukan"));
}
?>