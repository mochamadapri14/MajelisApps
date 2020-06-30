<?php
require_once 'koneksi/koneksi.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
    $id_jadwal = $_POST['id_jadwal'];
    $query = "SELECT * FROM majelis, user WHERE id_jadwal = '$id_jadwal' 
    AND majelis.user_id = user.id_user";
    $result= mysqli_query($konek,$query);
    $array = array();
    while($row = mysqli_fetch_assoc($result)){
    
        $array[] = $row;
        }
        
    echo ($result) ? json_encode(array("kode" => "1", "resultJadwal"=>$array)):

	json_encode(array("kode" => "0","pesan" => "data tidak ditemukan"));
}

?>