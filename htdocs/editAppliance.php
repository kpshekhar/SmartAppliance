<?php
$con = mysql_connect("localhost","root","1234");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance_db");

	$username = $_POST["username"];
	$appname = $_POST["appname"];
	$energy = $_POST["energy"];
	$operationtime = $_POST["optime"];
	$usrsttime = $_POST["usrstarttime"];
	$usrendtime = $_POST["usrendtime"];
	$constraint = $_POST["constrainttype"];


if ( $_POST["request"] == "retrieve" )	 
{
		//$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
		$q=mysql_query("SELECT `userid`, `appliancename`, `energy`, `operationtime`, `userstarttime`, `userendtime`, `hardsoftconst` FROM `appliance_data` WHERE `appliancename`='$appname' and `userid`='$username' ");
		while($e=mysql_fetch_assoc($q))
			$output[]=$e;	
}

else if( $_POST["request"] == "update" )
{

	//the following query loads all the tables in the particular database "world".
	//$q=mysql_query("SELECT DISTINCT TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA= 'community'");
	$q=mysql_query("UPDATE appliance_db.appliance_data SET `energy`='$energy',`operationtime`='$operationtime',`userstarttime`='$usrsttime',`userendtime`='$usrendtime',`hardsoftconst`='$constraint' WHERE `appliancename`='$appname' AND `userid`='$username'");
	
	while($e=mysql_fetch_assoc($q))
			$output[]=$e;
}

print(json_encode($output));
mysql_close();
?>
