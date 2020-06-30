<?php
require_once 'koneksi/koneksi.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
    $userid = $_POST['userid'];

    $query = "SELECT majelis_id FROM pesan,majelis WHERE pesan.userid = '$userid' 
    AND pesan.majelis_id = majelis.id_jadwal GROUP BY pesan.majelis_id ORDER BY waktuy desc";

    $result= mysqli_query($konek,$query);
    $array = array(); //2 data
    $arrayData = array(); //awal 0, panjang 0, trus insert 1, data 1, panjang 1, arraydata isi 2
    while($row = mysqli_fetch_assoc($result)){
        $array[] = $row;
    }

    for($i = 0 ; $i < sizeOf($array);$i++){
        $majelisid = $array[$i]['majelis_id'];

        $query2 = "SELECT * FROM pesan, majelis WHERE pesan.majelis_id = '$majelisid' AND 
        pesan.userid = '$userid' AND pesan.majelis_id = majelis.id_jadwal ORDER BY waktuy DESC";
        $result2= mysqli_query($konek,$query2);
        $arraySementara = array(); //1 kolom query ke 2
        while($row2 = mysqli_fetch_assoc($result2)){
            $arraySementara[] = $row2;
        }
        $arrayData[sizeOf($arrayData)] = $arraySementara[0];

    }
    $count = sizeOf($arrayData);
	echo ($result) ? json_encode(array("kode" => "1", "jumlah" =>$count,"resultPesan"=>$arrayData)):
    json_encode(array("kode" => "0","pesan" => "data tidak ditemukan"));
}
?>