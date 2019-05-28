<?php
include("configuracao.php");

header("Content-Type:application/json");

header("HTTP/1.1 200");

$stmt = $conn->prepare('SELECT * FROM events');

$stmt->execute();
$result = $stmt->get_result();

$list = array();

while ($row = $result->fetch_assoc()) {
	
	
	$longi = htmlentities($row['longitude']);
	$lat = htmlentities($row['latitude']);
	
	$cor = array(floatval($lat), floatval($longi));
	
	$geoobj["type"] = "Point"; //Objekt
	$geoobj['coordinates'] = $cor;
	
	$geofeat['type'] = 'Feature'; //Feature
	$geofeat['geometry'] = $geoobj;
	
	array_push ($list, $geofeat); //geofeat wird list hinzugefügt
}

$json_response = json_encode($list);
echo $json_response;
?>