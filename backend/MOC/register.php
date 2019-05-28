<BODY BGCOLOR="#ffff00">

<?php
	session_start();
	include("configuracao.php");
?>

<html>
<head>
<title>Registrieren</title>
<link rel="stylesheet" type="text/css" href="meuestilo.css">
</head>

<body>

<h1> Registrieren </h1>
<ul>
<form name="eingabe" action="reg_speichern.php" method="POST"></p>
<p>Benutzername: <input type="text" name="benutzername"></p>
<p>Passwort: <input type="password" name="passwort"></p>
<input type="submit" name="register" value="Register">
</form>
<a href="startseite.php"> Home </a>
</ul>

</body>
</html>