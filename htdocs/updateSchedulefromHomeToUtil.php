<?php
//$con = mysql_connect("localhost","root","student");
$con = mysql_connect("localhost","root","1234");

if (!$con){ die('Could not connect: ' . mysql_error());}


mysql_select_db('appliance_db');

	$username = $_POST["username"];
	$appname = $_POST["appname"];
	$schdulesttime=$_POST["proposedstarttime"];
	$schduleendtime=$_POST["proposedendtime"];
	$cost = $_POST["proposedcost"];

		$q=mysql_query("UPDATE appliance_db.appliance_data SET `sproposedstarttime`='$schdulesttime',`proposedendcost`='$schduleendtime',`proposedcost`='$cost' WHERE `appliancename`='$appname' AND `userid`='$username'");
		
mysql_close();
?>
