<?php
	include("config.php");
	
	mysqli_autocommit($conn, TRUE);

	$t = $_POST["track"]; //string
	$ta = json_decode($t); //zu json umgewandet
	$taa = $ta->{'track'}; //liste wird ausgelesen
	
	//die einzelnen objekte der liste werden ausgelsen und in die datenbank vom server geschriben
	foreach($taa AS $name) { //in name wird das ganze objekt gespeichert
		$u = $name->{'username'};
		$lat = $name->{'lat'};
		$lon = $name->{'lon'};
		$dt =  $name->{'time'};
		$sql_str = "INSERT INTO track(username, lat, lon, time) VALUES ('".$u."',".$lat.",".$lon.",'".$dt."')";
		mysqli_query($conn, sql_str);
	}
	
	mysqli_commit($conn);
	mysqli_close($conn);
	
	
	echo '{"result":"true"}';
?>
