<?php
	require_once 'koneksi/koneksi.php';
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		$username = $_POST['username'];
		$password = $_POST['password'];
		$query = "SELECT id_user,foto, nama_user, username, password, level FROM user WHERE username = '$username' ";
		$exeQuery = mysqli_query($konek,$query);
	if (mysqli_num_rows($exeQuery) === 1){
		
		while($row = mysqli_fetch_assoc($exeQuery)){
			$array[] = $row;
		}
		if(password_verify($password,$array[0]['password'])){
			$response['kode'] = "1";
			$response['pesan'] = "Berhasil Login";
			$response['result'] = $array; 
		}else{
			$response['kode'] = "11";
			$response['pesan'] = "Password Salah!";
		}	
	}else{
		$response['kode'] = "2";
		$response['pesan'] = "Username tidak ada";
	}
	}else{
		$response['kode'] = "101";
		$response['pesan'] = "Request tidak valid";
		
	}
	echo json_encode($response);
?>