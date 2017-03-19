
<?php
//$con = mysql_connect("localhost","root","student");
$con = mysql_connect("localhost","root","1234");

if (!$con){ die('Could not connect: ' . mysql_error());}


mysql_select_db("appliance_db");

	$username = $_POST["username"];
	
	
		$sql1 = "UPDATE `appliance_db`.`appliance_data` SET scheduledstarttime = proposedstarttime, scheduledendtime = proposedendtime, cost = proposedcost WHERE `userid`='$username'";
	mysql_query($sql1,$con) or die(mysql_error());	
	
mysql_close();
?>





