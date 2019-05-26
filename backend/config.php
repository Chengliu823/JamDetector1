<?php
	$hostname = "localhost";
	$username = "admin2";
	$password = "123";
	$dbname = "bvu19sys5";

	// Create connection
	$conn = mysqli_connect ( $hostname, $username, $password, $dbname );

	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}
?>