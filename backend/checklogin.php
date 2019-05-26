<?php
	include("config.php");
	
	$u = $_POST["user"]; //string
	$ua = json_decode($u); //zu json umgewandet
	$user = $ua->{'username'};
	$password = $ua->{'password'};
		
	$sql = "SELECT username FROM user WHERE username = '".$user."' AND password = '".$password."'";
	
	$result = $conn->query($sql);
	
	if ($result->num_rows > 0) {
		echo '{"result":"true"}';
	} else {
		echo '{"result":"false"}';
	}
?>