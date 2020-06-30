<?php 
require_once 'koneksi/koneksi.php';
	if($_SERVER['REQUEST_METHOD'] == 'POST')
	{
		$nama_user = $_POST['nama_user'];
		$user = $_POST['username'];
		$username = strtolower($user);
		$password = password_hash($_POST['password'], PASSWORD_BCRYPT);
		$level = "Jamaah";
	
		$query2 = "SELECT username FROM user WHERE username='$username'";
		$result2 = mysqli_query($konek,$query2);
		$cek_username = mysqli_num_rows($result2);
		$query = "INSERT INTO user (nama_user, username, password, level) 
		VALUES ('$nama_user','$username','$password','$level')";
		if (strlen($nama_user) > 16){
			$response['kode'] = "6";
			$response['pesan'] = "Nama maksimal 16 karakter";
		}else if(strlen($nama_user) < 4){
			$response['kode'] = "5";
			$response['pesan'] = "Nama harus lebih dari 4 karakter";
		}else if(strlen($username) < 4){
			$response['kode'] = "4";
			$response['pesan'] = "Username harus lebih dari 4 karakter";
		}else if(strlen($username) > 8){
			$response['kode'] = "3";
			$response['pesan'] = "Username maksimal 8 karakter";
		}
		else if($cek_username>0){
			$response['kode'] = "2";
			$response['pesan'] = "Username sudah ada";
		}else{
			if($nama_user != null && $username != null && $password !=null){
				if(mysqli_query($konek,$query)){
					$response['kode'] = "1";
					$response['pesan'] = "Berhasil daftar";
				}	
			}
		}
	}
	else{
			$response['kode'] = "101";
			$response['pesan'] = "Erorr!";
	}
	echo json_encode($response);
?>