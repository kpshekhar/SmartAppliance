<?php
 
    mysql_connect("localhost","root","1234"); // host, <span class="IL_AD" id="IL_AD2">username</span>, password...
    mysql_select_db("testdb"); // db name...
      
    $q=mysql_query("SELECT * FROM users");
    while($row=mysql_fetch_assoc($q))
            $json_output[]=$row;
      
    print(json_encode($json_output));
      
    mysql_close();
     
?>
