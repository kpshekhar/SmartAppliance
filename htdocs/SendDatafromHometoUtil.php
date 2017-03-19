<?php
$con = mysql_connect("localhost","root","1234");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance_db");

$usrname = $_POST["usrname"];




		//$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
		$q=mysql_query("SELECT * FROM `appliance_data` WHERE `userid`='$usrname'");
	

$handle = fopen('/home/hduser/tmp/UserCostQuery1'.'.txt','w+');


//mysql_fetch_assoc is the key here, don't use mysql_fetch_array as it creates double results
while($row = mysql_fetch_assoc($q)) {
  fputs($handle, join(',', $row)."\n");
}

fclose($handle);



echo file_get_contents($handle);
ob_flush();
flush();
?>


