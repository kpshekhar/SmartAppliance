<?php
$con = mysql_connect("localhost","root","1234");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance_db");

$usrname = $_POST["usrname"];




 

		//$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
		$q=mysql_query("SELECT `appliancename` FROM `appliance_data` WHERE `userid`='$usrname' ");
		while($e=mysql_fetch_assoc($q))
			$output[]=$e;	


print(json_encode($output));
mysql_close();
?>
