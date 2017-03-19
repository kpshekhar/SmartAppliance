package com.example.smartappliance;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Run_Scheduler extends Activity {
	
	//Modified by Pranay K. Added a const string at the beginning jus to ease changing the ip
	//Ip address static string;
	static String ipaddr="10.39.202.34";
	
//Ip address for Util;
	static String ipaddrutil="10.39.198.87";

	
	private Bundle bundle;
	private Button btnUtilserver,btnHomeserver,btnMobile,btnback,btnCostVerify;
	private String data="", username_bundle;
	private String serialno,
	   userid;
	   String appliancename[]=new String[110];
	   String proposedCost[]=new String[110];
	 private  int count=0;
	 private int countfromhme=0;
	
	private String sendData;
	private String HADOOPOUT;
	private TextView textview1;
	String userName[]=new String[100];
	String AppName[]=new String[100];
	String Proposedstarttime[]=new String[100];
	String ProposedEndTime[]=new String[100];
	String ProposedCost[]=new String[100];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run__scheduler);
		
		bundle=getIntent().getExtras();
		userid=bundle.getString("username");
		btnUtilserver=(Button)findViewById(R.id.btnutilserver);
		btnHomeserver=(Button)findViewById(R.id.btnhmeserver);
		btnMobile=(Button)findViewById(R.id.btnmobile);
		btnback=(Button)findViewById(R.id.btnbackmanschd);
		btnCostVerify=(Button)findViewById(R.id.btnverifyhmservercost);
		sendData="";
		textview1=(TextView)findViewById(R.id.textView1);
		

		
		//1. Utility Server
		btnUtilserver.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Please wait for the schedule to generate", Toast.LENGTH_LONG).show();
				getConnection(userid,ipaddrutil);
				Toast.makeText(getBaseContext(), "Schedule Received From Utility Server, please go to previous screen to view the Schedule", Toast.LENGTH_SHORT).show();

				
			}
		});
		
		
		//2. HomeServer
		btnHomeserver.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Toast.makeText(getBaseContext(), "Please wait for the schedule to generate", Toast.LENGTH_LONG).show();
				getConnection(userid,ipaddr);
				
				Toast.makeText(getBaseContext(), "Schedule Received from Home Server, please go to previous screen to view the Schedule", Toast.LENGTH_LONG).show();
				
			}
		});
		
		
		//3. VerifyCost from Utility Server
		btnCostVerify.setOnClickListener(new View.OnClickListener() {
					
					@Override
				public void onClick(View v) {
						int Lengthjs = 0;
						String conResult = getDatafromHome("http://"+ipaddr+"/getScheduleUtil.php",userid);
						try{
							JSONArray jArray = new JSONArray(conResult);
							Lengthjs=jArray.length();
							//for(int i=0;i<jArray.length();i++){
							for(int i=0;i<jArray.length();i++){	
								JSONObject json_data = jArray.getJSONObject(i);

								userName[i] =json_data.getString("userid");
								AppName[i] = json_data.getString("appliancename");
								Proposedstarttime[i] =json_data.getString("proposedstarttime");
								ProposedEndTime[i] =json_data.getString("proposedendtime");
								ProposedCost[i] =json_data.getString("proposedcost");

							}
							Toast.makeText(Run_Scheduler.this, "Data Recive from Home",
	                                Toast.LENGTH_LONG).show();
						}

						catch(JSONException e){
							//Log.e("log_tag", "Error parsing data "+e.toString());
							Log.e("log_tag", "Error parsing data "+e.toString());
						}
						
						for(int i=0;i<Lengthjs;i++){				
						String res=getConnectiontoupdateUtil(userName[i],AppName[i],
								Proposedstarttime[i],ProposedEndTime[i],ProposedCost[i]);
						Toast.makeText(Run_Scheduler.this, "Sent to Util",
                                Toast.LENGTH_LONG).show();
						
						}
					
						
						String REsult=getConnectionfromUtil(userid);
					for(int i=0;i<count;i++)
					{
						String ress=getConnectionUpdateHome(userid, appliancename[i], proposedCost[i]);
					//	textview1.setText(REsult);
					}
					
					Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
					}
				});		
		
		
		
		
		//4. Mobile button
		btnMobile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				
			}
		});
		
		
		
		
		//5. Back Button
		btnback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
	}




public void getConnection(String usr, String ipaddress){

	InputStream inputStream = null;
	String result = "";
	ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
	nameValuePairs1.add(new BasicNameValuePair("usrname",usr));

	//http postappSpinners
	try{
		HttpClient httpclient = new DefaultHttpClient();

		// have to change the ip here to correct ip
		HttpPost httppost = new HttpPost("http://"+ipaddress+"/Integration.php");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
    	HADOOPOUT = EntityUtils.toString(entity);
	}
	catch(Exception e){
		Log.e("log_tag", "Error in http connection "+e.toString());
		Toast.makeText(getBaseContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
	}

	
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////
public String getConnectionfromUtil(String userName){

InputStream inputStream = null;
String result = "";
ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
nameValuePairs1.add(new BasicNameValuePair("usrname",userName));



//http postappSpinners
try{
HttpClient httpclient = new DefaultHttpClient();

// have to change the ip here to correct ip
HttpPost httppost = new HttpPost("http://"+ipaddrutil+"/HomeScheduleVerifyCost.php");
httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
HttpResponse response = httpclient.execute(httppost);
HttpEntity entity = response.getEntity();
inputStream = entity.getContent();
//HADOOPOUT = EntityUtils.toString(entity);
}
catch(Exception e){
Log.e("log_tag", "Error in http connection "+e.toString());
Toast.makeText(getBaseContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
return "";
}
//convert response to string
try{
BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
StringBuilder sb = new StringBuilder();
String line = null;
count=0;
while ((line = reader.readLine()) != null) {
	String[] splitted = line.split(",");
	 appliancename[count]=splitted[0];
	 proposedCost[count]=splitted[1];
	 count++;
	// System.out.println(appliancename[count]);

}
inputStream.close();
result=sb.toString();
}
catch(Exception e){
Log.e("log_tag", "Error converting result "+e.toString());
}
return result;

}

public String getConnectionUpdateHome(String userName, String Appname, String Cost){

InputStream inputStream = null;
String result = "";
ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
nameValuePairs1.add(new BasicNameValuePair("usrname",userName));
nameValuePairs1.add(new BasicNameValuePair("appname",Appname));
nameValuePairs1.add(new BasicNameValuePair("cost",Cost));


//http postappSpinners
try{
HttpClient httpclient = new DefaultHttpClient();

// have to change the ip here to correct iputil
HttpPost httppost = new HttpPost("http://"+ipaddr+"/UpdateCostFromUtiltoHome.php");
httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
HttpResponse response = httpclient.execute(httppost);
HttpEntity entity = response.getEntity();
inputStream = entity.getContent();
}
catch(Exception e){
Log.e("log_tag", "Error in http connection "+e.toString());
Toast.makeText(getBaseContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
return "";
}
//convert response to string
try{
BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
StringBuilder sb = new StringBuilder();
String line = null;
//count=0;
while ((line = reader.readLine()) != null) {
	sb.append(line + "\n");
}
inputStream.close();
result=sb.toString();
}
catch(Exception e){
Log.e("log_tag", "Error converting result "+e.toString());
}
return result;

}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
public String getDatafromHome(String url, String usr){

Bundle bundle = new Bundle();
Message msg = new Message();
InputStream inputStream = null;
String result = "";
ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
nameValuePairs1.add(new BasicNameValuePair("username",usr));


//http postappSpinners
try{
HttpClient httpclient = new DefaultHttpClient();

// have to change the ip here to your ip
HttpPost httppost = new HttpPost(url);
httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
HttpResponse response = httpclient.execute(httppost);
HttpEntity entity = response.getEntity();
inputStream = entity.getContent();
}
catch(Exception e){
Log.e("log_tag", "Error in http connection "+e.toString());
}
//convert response to string
try{
BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
StringBuilder sb = new StringBuilder();
String line = null;
while ((line = reader.readLine()) != null) {
sb.append(line + "\n");
}
inputStream.close();
result=sb.toString();
}
catch(Exception e){
Log.e("log_tag", "Error converting result "+e.toString());
}
return result;

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

public String getConnectiontoupdateUtil(
String userName,
String AppName,
String proposedstarttime,
String proposedendtime,
String proposedcost
){

InputStream inputStream = null;
String result = "";
ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
nameValuePairs1.add(new BasicNameValuePair("username",userName));
nameValuePairs1.add(new BasicNameValuePair("appname",AppName));
nameValuePairs1.add(new BasicNameValuePair("proposedstarttime",proposedstarttime));
nameValuePairs1.add(new BasicNameValuePair("proposedendtime",proposedendtime));
nameValuePairs1.add(new BasicNameValuePair("proposedcost",proposedcost));


//http postappSpinners
try{
HttpClient httpclient = new DefaultHttpClient();

// have to change the ip here to correct ip
HttpPost httppost = new HttpPost("http://"+ipaddrutil+"/updateSchedulefromHomeToUtil.php");
httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
HttpResponse response = httpclient.execute(httppost);
HttpEntity entity = response.getEntity();
inputStream = entity.getContent();
}
catch(Exception e){
Log.e("log_tag", "Error in http connection "+e.toString());
Toast.makeText(getBaseContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
return "";
}
//convert response to string
try{
BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
StringBuilder sb = new StringBuilder();
String line = null;
while ((line = reader.readLine()) != null) {
sb.append(line + "\n");
}
inputStream.close();
result=sb.toString();
}
catch(Exception e){
Log.e("log_tag", "Error converting result "+e.toString());
}
return result;

}
}
