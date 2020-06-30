<?php
	require_once 'koneksi/koneksi.php';
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		$id_jadwal = $_POST['id_jadwal'];
		$query_pesan = "DELETE FROM pesan WHERE majelis_id = '$id_jadwal'";
		$result = mysqli_query($konek,$query_pesan);
		$query = "DELETE FROM majelis WHERE id_jadwal = '$id_jadwal'";
		
		$exeQuery = mysqli_query($konek, $query);
		
		if($result){
			if($exeQuery){
				
				$response['kode'] = "1";
				$response['pesan'] = "Data dihapus";
			}
		}else{
			$response['kode'] = "2";
			$response['pesan'] = "Gagal dihapus";
		}
	}else{
			$response['kode'] = "101";
			$response['pesan'] = "Tidak valid / Eror";
	}
	echo json_encode($response);

?>