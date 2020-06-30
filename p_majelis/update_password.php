<?php
	require_once 'koneksi/koneksi.php';

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		
	$id_user = $_POST['id_user'];
	$password = $password = password_hash($_POST['password'], PASSWORD_BCRYPT);
    $query = "UPDATE user SET password = '$password' WHERE id_user = '$id_user' ";
    
        if($password !=null){
            if(mysqli_query($konek,$query)){
                $response['kode'] = "1";
                $response['pesan'] = "Password diubah";
            
        }else{
            $response['kode'] = "2";
                $response['pesan'] = "Update Gagal";
        }
    }          
}
	else{$response['kode'] = "101";
		$response['pesan'] = "Erorr!";
	}
	echo json_encode($response);	
	?>