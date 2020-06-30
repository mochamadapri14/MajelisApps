<?php
	require_once 'koneksi/koneksi.php';
	function insert_foto(){
		$part = "./upload/";
		$filename = "img".rand(9,9999).".jpg";
		
		if($_FILES['foto']){
			$destfile = $part.$filename;
			
			if(move_uploaded_file($_FILES['foto']['tmp_name'],$destfile)){
				return $filename;
			}else{
				return false;
			}
		}else{
			$response['kode'] = "0";
			$response['pesan'] ="Request Error!";
		}
		return $filename;
	}
	if($_SERVER['REQUEST_METHOD'] == 'POST')
	{
		$user_id = $_POST['user_id'];
		$foto = insert_foto();
		
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
		if($_FILES['foto'] != null){
		
		$query = "INSERT INTO `majelis` (user_id,foto, nama_majelis, tanggal,
		 jam_mulai,nama_alamat, lokasi, keterangan,waktu,latitude,longitude) 
		VALUES ('$user_id','$foto','$nama_majelis',
		'$tanggal','$jam_mulai','$nama_alamat','$lokasi','$keterangan','$waktu','$latitude','$longitude')";
		if($foto !=null && $nama_majelis != null && $tanggal !=null && $jam_mulai != null && $nama_alamat !=null
		&& $lokasi != null && $keterangan != null && $latitude != null && $longitude != null ){
			if(mysqli_query($konek,$query)){
				$response['kode'] = "1";
				$response['pesan'] = "Jadwal berhasil dibuat";
			}	
		}
		else{
			$response['kode'] = "2";
			$response['pesan'] = "Jadwal belum lengkap";
		}
	}
	}else{
			$response['kode'] = "101";
			$response['pesan'] = "Erorr!";
	}
	echo json_encode($response);	
	?>