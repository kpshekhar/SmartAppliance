<?php
$con = mysql_connect("localhost","root","1234");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance_db");

	$username = $_POST["usrname"];
	$appname = $_POST["appname"];
	$cost = $_POST["cost"];



	//the following query loads all the tables in the particular database "world".
	//$q=mysql_query("SELECT DISTINCT TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA= 'community'");
	$q=mysql_query("UPDATE appliance_db.appliance_data SET `proposedcost`='$cost' WHERE `appliancename`='$appname' AND `userid`='$username'");
	

mysql_close();
?>
