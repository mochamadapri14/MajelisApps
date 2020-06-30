<?php
	error_reporting(0);
	require_once 'koneksi/koneksi.php';

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		
		$id_jadwal = $_POST['id_jadwal'];
		$fotolama = $_POST['fotolama']; //ini dari model berupa string
		$nama_majelis = $_POST['nama_majelis'];
		$tanggal = $_POST['tanggal'];
		$jam_mulai = $_POST['jam_mulai'];
		$nama_alamat = $_POST['nama_alamat'];
		$lokasi = $_POST['lokasi'];
		$keterangan = $_POST['keterangan'];
		$latitude = $_POST['latitude'];
		$longitude = $_POST['longitude'];
		date_default_timezone_set('Asia/Jakarta');
		$waktu = date('Y-m-d H:i:s');

		if($_FILES['foto'] == null){  //kalo dia ga isi foto
			$query = "UPDATE majelis SET foto='$fotolama', nama_majelis = '$nama_majelis', tanggal = '$tanggal',
			jam_mulai = '$jam_mulai',nama_alamat= '$nama_alamat', lokasi = '$lokasi' , keterangan = '$keterangan', 
			latitude = '$latitude' , longitude = '$longitude' ,waktu = '$waktu' WHERE id_jadwal = '$id_jadwal' ";
			if(mysqli_query($konek,$query)){
					$response['kode'] = "1";
					$response['pesan'] = "Jadwal berhasil diubah";
				}	
			
			else{
				$response['kode'] = "2";
				$response['pesan'] = "Jadwal gagal diubah";
			}
		}else{ //kalo isi

			if($fotolama != null){ //kalo ga ada foto buat diawal
				unlink("upload/".$fotolama); //apus foto
			}
			$part = "./upload/";
			$filename = "img".rand(9,9999).".jpg";
			if($_FILES['foto']){  //ini dari part
				$destfile = $part.$filename;
				if(move_uploaded_file($_FILES['foto']['tmp_name'],$destfile)){
					$query = "UPDATE majelis SET foto = '$filename', nama_majelis = '$nama_majelis', 
					tanggal = '$tanggal',jam_mulai = '$jam_mulai',nama_alamat= '$nama_alamat', 
					lokasi = '$lokasi' , keterangan = '$keterangan',latitude = '$latitude' , longitude = '$longitude' ,
					waktu = '$waktu' WHERE id_jadwal = '$id_jadwal' ";
					if(mysqli_query($konek,$query)){
						$response['kode'] = 1;
						$response['pesan'] = "Data berhasil diubah";
					}	
				
				else{
					$response['kode'] = 2;
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
	else{$response['kode'] = 101;
		$response['pesan'] = "Erorr!";
	}
	
	echo json_encode($response);	
	?>