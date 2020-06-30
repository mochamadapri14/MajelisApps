<?php 
require_once 'koneksi/koneksi.php';
	if($_SERVER['REQUEST_METHOD'] == 'POST')
	{	
		$majelis_id = $_POST['majelis_id'];
		$userid = $_POST['userid'];
        $pesan = $_POST['pesan'];
        date_default_timezone_set('Asia/Jakarta');
		$waktu = date('Y-m-d H:i:s');
		$query = "INSERT INTO pesan (majelis_id, userid, pesan,waktuy) 
		VALUES ('$majelis_id','$userid','$pesan','$waktu')";
		if($pesan != null){
		if(mysqli_query($konek, $query)){
			$response['kode'] = "1";
			$response['message'] = "Terkirim";
		}
	}
		else{
			$response['kode'] = "2";
			$response['message'] = "Komentar anda kosong";
		}
	}
	else{
        $response['kode'] = "101";
        $response['message'] = "Erorr!";
	}
	echo json_encode($response);	


?>