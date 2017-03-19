<?php
$con = mysql_connect("localhost","root","1234");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance_db");

$usrname = $_POST["usrname"];




		//$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
		$q=mysql_query("SELECT * FROM `appliance_data` WHERE `userid`='$usrname'");
	

$handle = fopen('/home/hduser/tmp/newtimestamp'.'.txt','w+');


//mysql_fetch_assoc is the key here, don't use mysql_fetch_array as it creates double results
while($row = mysql_fetch_assoc($q)) {
  fputs($handle, join(',', $row)."\n");
}

fclose($handle);

mysql_close();


$outfile = '/home/hduser/tmp/hadoop-output.txt';
unlink($outfile);
sleep(2);

// Wait for the output file

while (!file_exists($outfile)) 
	{
		sleep(2);
	}

sleep(4);



$handle1 = fopen("/home/hduser/tmp/hadoop-output.txt", "r");
$conn1 = mysql_connect("localhost","root","1234"); 
//mysql_select_db("mydatabase",$conn);
mysql_select_db("appliance_db");
//while (!feof($handle)) // Loop til end of file.
while (($data1 = fgetcsv($handle1, ",")) !== FALSE)
{

$sql1 = "UPDATE appliance_db.appliance_data SET `proposedstarttime`='$data1[13]',`proposedendtime`='$data1[14]',`proposedcost`='$data1[15]' WHERE `appliancename`='$data1[2]' AND `userid`='$data1[0]'";  
mysql_query($sql1,$conn1) or die(mysql_error());
//if (mysql_query($sql))
//echo "$data[12] \n";

}
mysql_close();
?>


