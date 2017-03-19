<?php
//$con = mysql_connect("localhost","root","student");
$con = mysql_connect("localhost","root","1234");

if (!$con){ die('Could not connect: ' . mysql_error());}


if (mysql_select_db('appliance_db', $con)) {

	$username = $_POST["username"];
	$appname = $_POST["appname"];
	$energy = $_POST["energy"];
	$operationtime = $_POST["optime"];
	$usrsttime = $_POST["usrstarttime"];
	$usrendtime = $_POST["usrendtime"];
	$constraint = $_POST["constrainttype"];
	$schdulesttime=$_POST["schstarttime"];
	$schduleendtime=$_POST["schendtime"];
	
	$q = mysql_query("SELECT EXISTS(SELECT * FROM appliance_data WHERE appliancename = '$appname' AND userid ='$username')");
	//$g =  mysql_query("SELECT EXISTS(SELECT * FROM userlist WHERE email = '$email')");
	
	if(mysql_result($q,0) == 1)
		print("false_appname");
	//else if (mysql_result($g,0) ==1)
	//	print("false_email");
	else {
		$result = mysql_query("INSERT INTO appliance_data (userid, appliancename, energy, operationtime, userstarttime, userendtime, hardsoftconst, scheduledstarttime, scheduledendtime,proposedstarttime,proposedendtime,proposedcost)
		VALUES ('$username','$appname','$energy','$operationtime', '$usrsttime','$usrendtime','$constraint','$schdulesttime','$schduleendtime','$schdulesttime','$schduleendtime','0.0')");
		if($result)
			print("true");
		else
			print("false");
	}
}	
mysql_close();
?>
