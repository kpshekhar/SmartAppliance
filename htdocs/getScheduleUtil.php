<?php
$con = mysql_connect("localhost","root","1234");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance_db");

$usrname = $_POST["username"];


$sql1 = "UPDATE `appliance_db`.`appliance_data` SET scheduledstarttime = proposedstarttime, scheduledendtime = proposedendtime, cost = proposedcost WHERE `userid`='$usrname'";
	mysql_query($sql1,$con) or die(mysql_error());

		//$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
		$q=mysql_query("SELECT `userid`,`appliancename`,`proposedstarttime`,`proposedendtime`,`proposedcost`FROM `appliance_data` WHERE `userid`='$usrname' ");
		while($e=mysql_fetch_assoc($q))
			$output[]=$e;	


print(json_encode($output));
mysql_close();
?>
