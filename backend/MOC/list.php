<?php
	session_start();
	include("configuracao.php");
	
	if(!isset($_SESSION["loggedIn"])){
		die("<a href='startseite.php'> home </a>");
	}
?>
<html>
<head>
<title>Liste</title>
<link rel="stylesheet" type="text/css" href="meuestilo.css">
</head>
<body>

<h1> Benutzer suchen </h1>
<ul>
<form action='list.php'>
	<p>nach Benutzername suchen: <input type="text" name="benutzername"></p>
	<input type="submit" name="absenden" value="Suchen">
</form>
</ul>
<?php
	if(isset($_GET['benutzername'])){
		$u = $_GET['benutzername'];
		$u1 = "%$u%";
		$stmt = $conn->prepare("SELECT * FROM users WHERE username LIKE ? ");
		$stmt->bind_param('s', $u1);
		echo "<p>Alle Benuter mit $u:</p>";
	}else{
		$stmt = $conn->prepare('SELECT * FROM users');
		echo "<p>Alle Benutzer:</p>";
	}
	
	$stmt->execute();
	$result = $stmt->get_result();

	while ($row = $result->fetch_assoc()) {
		$userName =htmlentities($row['username']);
		$userId= htmlentities($row['user_id']);
		$userCreated = htmlentities($row['dateCreated']);
		$userLastLogin = htmlentities($row['lastLogin']);
		
		echo "<p> $userName ($userId) , Created: $userCreated, LastLogin: $userLastLogin</p>";
	}
?>
<ul>
<a href="startseite.php"> home </a>
</ul>
</body>
</html>