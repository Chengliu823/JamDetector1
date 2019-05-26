<?php
	include("config.php");
	
	mysqli_autocommit($conn, TRUE);

	$t = $_POST["json"]; //string
	$ta = json_decode($t); //zu json umgewandet
	
	$u = $ta->{'username'};
	$p = $ta->{'password'};
	$sql_str = "INSERT INTO user (username, password) VALUES ('".$u."','".$p."')";
	// echo $sql_str;
	mysqli_query($conn, $sql_str);
	
	mysqli_commit($conn);
	mysqli_close($conn);
	
	echo '{"result":"true"}';
?>
