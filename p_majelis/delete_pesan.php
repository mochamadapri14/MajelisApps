
<?php
    error_reporting(0);
	require_once 'koneksi/koneksi.php';
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $id_pesan = $_POST['id_pesan'];
		$query_pesan = "DELETE FROM pesan WHERE id_pesan = '$id_pesan'";
        $result = mysqli_query($konek,$query_pesan);
    
            if($result){
                  
                $response['kode'] = "1";
                $response['message'] = "Pesan anda dihapus";
               
                }
         else{
                $response['kode'] = "2";
                $response['message'] = "Tidak memiliki hak menghapus";
            
            }	
    }
	        else{
			$response['kode'] = "101";
			$response['message'] = "Tidak valid / Eror";
	}
	echo json_encode($response);

?>