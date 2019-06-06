<?php
/*
require('nusoap.php');
include("config.php");

function register1($username, $password) {
	//mysqli_autocommit($conn, TRUE);
	
	$sql_str = "INSERT INTO user (username, password) VALUES ('".$username."','".$password."')";
	mysqli_query($conn, $sql_str);
	
	//mysqli_commit($conn);
	//mysqli_close($conn);
	
	return 'true';
}

// Create the server instance
$server = new soap_server();

$ns = "https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/registers.php";

// Initialize WSDL support
$server->configureWSDL('register1', $ns,'','document');

// Register the method to expose
$server->register('register1',                	// method name
    array('username' => 'xsd:string',
		'password' => 'xsd:string'),  	// input parameters
    array('return' => 'xsd:string'),    	// output parameters
    $ns,         				// namespace
    "$ns. / . 'register1'",     		// soapaction
    'document',                         	// style
    'literal',                          	// use
    'saves user data on server'            	// documentation
);

// Use the request to (try to) invoke the service
$HTTP_RAW_POST_DATA = isset($HTTP_RAW_POST_DATA) ? $HTTP_RAW_POST_DATA : '';
$server->service($HTTP_RAW_POST_DATA);
*/
?>

<?php

require_once("nusoap.php");

//Create a new soap server
$server = new soap_server();

//Define our namespace
$namespace = "https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/registers.php";
$server->wsdl->schemaTargetNamespace = $namespace;

//Configure our WSDL (Webservice description language, wenn nach dem url ?wsdl beschreibt code)
$server->configureWSDL("register1");

//Register a method that has parameters and return types
$server->register(
        'register1',
        array('username' => 'xsd:string', 'password' => 'xsd:string'),
        array('return' => 'xsd:string'),
        $namespace,
        false,
        'rpc',
        'encoded',
        'saves user data on server');

function register1($username1, $password1) {
	include("config.php");

	mysqli_autocommit($conn, TRUE);
	
	$sql_str = "INSERT INTO user (username, password) VALUES ('".$username1."','".$password1."')";
	mysqli_query($conn, $sql_str);
	
	mysqli_commit($conn);
	mysqli_close($conn);
	
	return $sql_str;
}

// Get our posted data if the service is being consumed
// otherwise leave this data blank.
$POST_DATA = file_get_contents("php://input");

// pass our posted data (or nothing) to the soap service
$server->service($POST_DATA); //post = http request
exit();

?>
