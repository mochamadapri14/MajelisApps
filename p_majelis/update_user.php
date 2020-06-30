<?php
error_reporting(0);
	require_once 'koneksi/koneksi.php';

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		
    $id_user = $_POST['id_user'];
    $fotolama = $_POST['fotolama'];
    $nama_user = $_POST['nama_user'];
    $username = $_POST['username'];
	
	if (strlen($nama_user) > 16){
		$response['kode'] = "6";
		$response['pesan'] = "Nama maksimal 16 karakter";
	}else if(strlen($nama_user) < 4){
		$response['kode'] = "5";
		$response['pesan'] = "Nama harus lebih dari 4 karakter";
	}
	else{
    
		if($_FILES['foto'] == null){ 
			
			 //kalo dia ga isi foto
            $query = "UPDATE user SET foto='$fotolama',nama_user = '$nama_user', username= '$username'
			 WHERE id_user = '$id_user' ";
		

             
            if(mysqli_query($konek,$query)){
                $query1 = "SELECT foto, nama_user, username, password, level FROM user WHERE id_user = '$id_user' ";
                $exeQuery = mysqli_query($konek,$query1);
                while($row = mysqli_fetch_assoc($exeQuery)){
                    $array[] = $row;
                }
                
					$response['kode'] = "1";
                    $response['pesan'] = "Profil berhasil diubah";
                    $response['result'] = $array;
				}	
			
			else{
				$response['kode'] = "11";
				$response['pesan'] = "Data gagal diubah";
			}
	
		}else{ //kalo isi
			

			if($fotolama != null){ //kalo ga ada foto buat diawal
				unlink("uploadUser/".$fotolama); //apus foto
			}

			
			$part = "./uploadUser/";
			$filename = "img".rand(9,9999).".jpg";
			if($_FILES['foto']){  //ini dari part
				$destfile = $part.$filename;
				if(move_uploaded_file($_FILES['foto']['tmp_name'],$destfile)){
                    $query = "UPDATE user SET foto='$filename',nama_user = '$nama_user', username= '$username'
                    WHERE id_user = '$id_user' ";
                    if(mysqli_query($konek,$query)){
                        $query1 = "SELECT foto, nama_user, username, password, level FROM user WHERE id_user = '$id_user' ";
                $exeQuery = mysqli_query($konek,$query1);
                while($row = mysqli_fetch_assoc($exeQuery)){
                    $array[] = $row;
                }
						$response['kode'] = "1";
                        $response['pesan'] = "Profil berhasil diubah";
                        $response['result'] = $array;
					}	
				
				else{
					$response['kode'] = "2";
					$response['pesan'] = "Data gagal diubah";
				}
				}else{
					$response['kode'] = "0";
					$response['pesan'] ="Request Error!";
				}
			}else{
				$response['kode'] = "0";
				$response['pesan'] ="Request Error!";
			}
		}
	}
    }
	else{$response['kode'] = "101";
		$response['pesan'] = "Erorr!";
	}
	
	echo json_encode($response);	
	?>