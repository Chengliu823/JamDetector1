<?php
	include("config.php");
?>

<html>
<head>
<title>track save</title>
</head>
<body>

<h1>track save</h1>

 <?php
	$t = $_POST["track"];
	$ta = json_decode($t);
	$taa = $ta->{'track'};
	
	foreach($taa AS $name) {
		$u = $name->{'username'};
		$lat = $name->{'lat'};
		$lon = $name->{'lon'};
		$dt =  $name->{'time'};
		//echo "$time";
		
		$stmt = $conn->prepare('INSERT INTO track(username, lat, lon, time) VALUES (?,?,?,?)');
		$stmt->bind_param('sdds',$u, $lat, $lon, $dt);
		$stmt->execute();
	}

?>
<a href="index.php"> home </a>

</body>
</html>