<?php
	session_start();
	include("configuracao.php");
?>

<html>
<head>
<title>Login</title>
<link rel="stylesheet" type="text/css" href="meuestilo.css">
</head>
<body>



 <?php

	$user = $_POST["benutzername"];
	$password = $_POST["passwort"];

	
	$stmt = $conn->prepare('SELECT * FROM users WHERE Username = ? AND Passwort = ?');
	$stmt->bind_param('ss', $user, $password);
		
	$stmt->execute();
	$result = $stmt->get_result();
	
	if(strpos($user, ';') !== false || strpos($user, '<') !== false || strpos($user, '+') !== false){
		echo '<p>Sonderzeichen verboten</p>';
		die("<a href='login.php'> zurück </a>");
	} 
	
//if($result->num_rows  == 0) {
//		echo "<p>Benutzername oder Passwort falsch!</p>";
//		unset ($_SESSION["loggedIn"]);
//	}else{
//		echo "<p>Erfolgereich</p>";
//		$_SESSION["loggedIn"] = true;
//	}
?>

<body>

		<div class="principal">
			<div class="cabecalho">
				<div class="logo-cabecalho">
					<a href="./">Jam Detector</a>
				</div>
				
				<div class="pesquisa">
				<ul>
					
					<form name="eingabe" action="startseite.php" method="POST"></p>
					<input type="submit" name="logout" value="Logout">
					</form>
					
				</ul>
					
				</div>
			</div>
			<div class="menu">
			<ul>

			<li><a href="list.php"> Benutzer suchen </a></li>
			<li><a href="leaflet/karte.html"> Karte </a></li>
			<li><a href=""> alle Routen anzeigen </a></li>
			<li><a href=""> als admin anmelden </a></li>
			<li><a href=""> Statistik </a></li>
			<li><a href=""> Route suchen</a></li>
			</ul>

			</div>
			<div class="corpo">
				<div class="artigos">
					<div class="espacamento">
						<h1>Introduction</h1>
						<p>
							<img img align="center" width="300px" height="250px"src="logo.png" />
						</p>
						<p>
							Herzlichen Willkomen zum Jam Detector!
						</p>
						<p>
						Here können Sie die Stau Prognose von Ihre Route anschauen.
						</p>
			
					</div>
				</div>
				<div class="barra-lateral">
					<div class="espacamento">
						<h2>Über uns</h2>
						<p>Wir sind Studierende von Verkehr und Umwelt </p>
						
					</div>
				</div>
			</div>
			<div class="rodape">
				<h3>Kontakt</h3>
        <p id="Telephone-email">
            Frau Peres Ferreira: vu17b038@technikum-wien.at
            <br>
			Herr Liu: vu17b026@technikum-wien.at
			<br>
			Herr Wagner: vu17b017@technikum-wien.at
			<br>
        </p>

        <p id="Adresse">
		<h4>Adresse</h4>
            Fachhochschule Technikum Wien
			<br>
			Höchstädtplatz 6
            <br>
            1200 Wien
            <br>
        </p>

        <small id="copyright">
            &copy; WPL Technologie.
        </small>
			</div>
		</div>
	</body>
</body>
</html>


