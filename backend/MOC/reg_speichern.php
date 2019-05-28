<?php
	session_start();
	include("configuracao.php");
?>

<html>
<head>
<title>Registrierung</title>
<link rel="stylesheet" type="text/css" href="meuestilo.css">
</head>
<body>

<h1> Registrieren </h1>

 <?php
	$user = $_POST["benutzername"];
	$password = $_POST['passwort'];
	
	if(strpos($user, ';') !== false || strpos($user, '<') !== false || strpos($user, '+') !== false){
		echo '<p>Sonderzeichen verboten</p>';
		die("<a href='register.php'> Zurück </a>");
	}
 
	echo "<p>Benutzername: " . $_POST["benutzername"] . "</p>"
	. "<p>Passwort: " . $_POST['passwort'] . "</p>";
	
	$stmt = $conn->prepare('INSERT INTO `users`(`Username`, `Passwort`) VALUES (?,?)');
	$stmt->bind_param('ss', $user, $password);
	
	if($stmt->execute()){
		echo "<p> Registrierung erfolgreich </p>";
	}else{
		echo"<p> Fehler </p>";	
	}	
	
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
<ul>  

</ul>
</body>
</html>