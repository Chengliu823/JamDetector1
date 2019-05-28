<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">

		<link rel="stylesheet" type="text/css" href="meuestilo.css">

		<title>Jam Detector</title>
		
		
</head>
	</head>

	<body>

		<div class="principal">
			<div class="cabecalho">
				<div class="logo-cabecalho">
					<a href="./">Jam Detector</a>
				</div>
				
				<div class="pesquisa">
				<ul>
					<form name="eingabe" action="login.checkup.php" method="POST"></p>
					<p>Benutzername: <input type="text" name="benutzername"></p>
					<p>Passwort: <input type="password" name="passwort"></p>
					<input type="submit" name="register" value="Login">
					</form>
				</ul>
					
				</div>
			</div>
			<div class="menu">
			
			<?php
	session_start();
	
	if(!isset($_SESSION["loggedIn"]))?>
		<ul>
		<li><a href="register.php">Registrieren </a></li>
		<li><a href="leaflet/karte.html"> Karte </a></li>
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
</html>