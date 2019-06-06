<html>
<head>
<title>Test send track</title>
</head>

<body>

<h1>Test register user SERVER</h1>
<form name="eingabe" action="https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/register.php" method="POST"></p>
<p>Track Json: <input type="text" name="json"></p>
<input type="submit" name="send" value="Send">
</form>

<h1>Test register user LOCALHOST</h1>
<form name="eingabe" action="http://localhost/jamlocal/register.php" method="POST"></p>
<p>Track Json: <input type="text" name="json"></p>
<input type="submit" name="send" value="Send">
</form>

<h1>Test send track SERVER</h1>
<form name="eingabe" action="https://ieslamp.technikum-wien.at/bvu19sys5/jamlocal/checklogin.php" method="POST"></p>
<p>Track Json: <input type="text" name="json"></p>
<input type="submit" name="send" value="Send">
</form>

<h1>Test send track LOCALHOST</h1>
<form name="eingabe" action="http://loccalhost/jamlocal/checklogin.php" method="POST"></p>
<p>Track Json: <input type="text" name="json"></p>
<input type="submit" name="send" value="Send">
</form>

</body>
</html>