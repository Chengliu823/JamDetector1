<?php
	$hostname = "localhost";
	$username = "Jennifer Carolinne";
	$password = "felicidade2";
	$dbname = "vorlesung3";

	// Create connection
	$conn = mysqli_connect ( $hostname, $username, $password, $dbname );

	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}
?>