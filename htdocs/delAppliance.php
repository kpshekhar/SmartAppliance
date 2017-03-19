<?php
$con = mysql_connect("localhost","root","1234");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance_db");

$usrname = $_POST["usrname"];
$appname = $_POST["appname"];



 

		//$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
		$q=mysql_query("DELETE FROM `appliance_db`.`appliance_data` WHERE userid='$usrname' and appliancename='$appname' ");
		while($e=mysql_fetch_assoc($q))
			$output[]=$e;	


print(json_encode($output));
mysql_close();
?>
