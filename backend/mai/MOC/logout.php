<html>
<head>
<title>Logout</title>
<link rel="stylesheet" type="text/css" href="meuestilo.css">
</head>
<body>

<h1> Logout </h1> 

<?php
	session_start();
	unset ($_SESSION["loggedIn"]);
?>
<ul>
<a href="startseite.php"> Home </a>
</ul>
</body>
</html>