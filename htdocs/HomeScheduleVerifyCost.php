<?php
$con = mysql_connect("localhost","root","1234");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance_db");

$usrname = $_POST["usrname"];




		//$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
		$q=mysql_query("SELECT * FROM `appliance_data` WHERE `userid`='$usrname'");
	

$handle = fopen('/home/hduser/tmp/UserCostQuery'.'.txt','w+');


//mysql_fetch_assoc is the key here, don't use mysql_fetch_array as it creates double results
while($row = mysql_fetch_assoc($q)) {
  fputs($handle, join(',', $row)."\n");
}

fclose($handle);



$r=mysql_query("SELECT * FROM `appliance_data` WHERE `userid`<>'$usrname'");

$handleall = fopen('/home/hduser/tmp/AllOtherUserData'.'.txt','w+');


//mysql_fetch_assoc is the key here, don't use mysql_fetch_array as it creates double results
while($row = mysql_fetch_assoc($r)) {
  fputs($handleall, join(',', $row)."\n");
}

fclose($handleall);

mysql_close();



$outfile = '/home/hduser/tmp/UpdatedCost.txt';
unlink($outfile);
sleep(2);

// Wait for the output file

while (!file_exists($outfile)) 
	{
		sleep(2);
	}

sleep(4);


echo file_get_contents($outfile);
ob_flush();
flush();
?>


