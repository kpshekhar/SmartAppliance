<?php
$con = mysql_connect("localhost","root","1234");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance_db");

$usrname = $_POST["usrname"];




		$q=mysql_query("SELECT * FROM appliance_data WHERE 'userid' = '$usrname'");
	//	$q=mysql_query("SELECT `appliancename`,  `scheduledstarttime`, `scheduledendtime`, `cost` FROM `appliance_data` WHERE `userid`='$usrname'ORDER BY scheduledstarttime ASC ");
		while($e=mysql_fetch_assoc($q))
			$output[]=$e;	

print(json_encode($output));
mysql_close();
?>
