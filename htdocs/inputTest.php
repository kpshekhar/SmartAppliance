<?php
ini_set('max_execution_time', 300);

// Change directory to the input data file folder
//chdir("/home/hduser/LxC/data");

$infile = 'newtimestamp.txt';
//$outfile = 'hadoop-output.txt';
//unlink($outfile);

$hadoopin=$_POST['input'];
// Write the necessary contents
//file_put_contents($infile, "Check for android and hadoop Veola");
file_put_contents($infile, $hadoopin);

// Wait for the output file

//while (!file_exists($outfile)) 
	{
		sleep(2);
	}

//sleep(4);
echo file_get_contents($infile);
ob_flush();
flush();
?>
