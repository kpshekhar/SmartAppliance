package com.example.smartappliance;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Manage_Schedule extends ActionBarActivity {

	private Button btnViewSchedule,btnRunScheduler,btnCommit,btnBack,btnViewUtility,btnCommitUtilSch;
	private Bundle bundle;
	//Modified by Pranay K. Added a const string at the beginning jus to ease changing the ip
	//Ip address static string;
			static String ipaddr="10.39.202.34";
			
		//Ip address for Util;
			static String ipaddrutil="10.39.198.87";
	
	    String bundle_userName;
	    
		String userName[]=new String[100];
		String AppName[]=new String[100];
		String Proposedstarttime[]=new String[100];
		String ProposedEndTime[]=new String[100];
		String ProposedCost[]=new String[100];
		
		String userName_h[]=new String[100];
		String AppName_h[]=new String[100];
		String Proposedstarttime_h[]=new String[100];
		String ProposedEndTime_h[]=new String[100];
		String ProposedCost_h[]=new String[100];
		
	    
	    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage__schedule);
		
		bundle=getIntent().getExtras();
		bundle_userName=bundle.getString("username");
		btnViewSchedule=(Button)findViewById(R.id.btnutilserver);
		btnRunScheduler=(Button)findViewById(R.id.btnhmeserver);
		btnCommit=(Button)findViewById(R.id.btnmobile);
		btnBack=(Button)findViewById(R.id.btnbackmanschd);
		btnViewUtility=(Button)findViewById(R.id.viewutil);
		btnCommitUtilSch=(Button)findViewById(R.id.cmtutilserversch);
		
		//Setup back button
				btnViewSchedule.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent viewApp = new Intent(getBaseContext(),View_proposed_schedule.class);
						bundle.putString("Selection","1" );
						viewApp.putExtras(bundle);
						Toast.makeText(getBaseContext(),"Viewing Home Proposed Schedule!",Toast.LENGTH_SHORT).show();
						startActivity(viewApp);
					}
				});
		
				
				
				btnViewUtility.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent viewApp = new Intent(getBaseContext(),View_proposed_schedule.class);
						bundle.putString("Selection","0" );
						viewApp.putExtras(bundle);
						Toast.makeText(getBaseContext(),"Viewing Home Proposed Schedule!",Toast.LENGTH_SHORT).show();
						startActivity(viewApp);
					}
				});
				
				
		//Setup back button
		btnRunScheduler.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent runSchedule = new Intent(getBaseContext(),Run_Scheduler.class);
				runSchedule.putExtras(bundle);
				startActivity(runSchedule);
			}
		});
		
		
		//Setup Commmit button
				btnCommit.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {

						alertMessage();
						
					}
				});
		
				
				
				
				
		//Setup back button
				btnBack.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {

						finish();
					}
				});
		
/////////////////////////////////////////////////////////////////////////////////////////////////////
		//Setup up Commit Button for Utility Schedule commit
				btnCommitUtilSch.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int Lengthjs = 0;
						String conResult = getConnectionfromUtil ("http://"+ipaddrutil+"/getScheduleUtil.php",bundle_userName);
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
							Toast.makeText(Manage_Schedule.this, "Data Recive from Util",
	                                Toast.LENGTH_LONG).show();
						}

						catch(JSONException e){
							//Log.e("log_tag", "Error parsing data "+e.toString());
							Log.e("log_tag", "Error parsing data "+e.toString());
						}
						
						for(int i=0;i<Lengthjs;i++){				
						String res=getConnectiontoupdateHome(userName[i],AppName[i],
								Proposedstarttime[i],ProposedEndTime[i],ProposedCost[i]);
						Toast.makeText(Manage_Schedule.this, "Sent to Home",
                                Toast.LENGTH_LONG).show();
						
						}
					}
				});
				
				
	}
	    
	    
	    public void alertMessage() {
	         DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                      switch (which) {
	                      case DialogInterface.BUTTON_POSITIVE:
	                             // Yes button clicked
	                    	  String passwd = getConnection(bundle_userName);
	                    	  Toast.makeText(Manage_Schedule.this, "New Schedule Implemented",
	                                  Toast.LENGTH_LONG).show();

	  						int Lengthjs = 0;
	  						String conResult = getConnectionfromUtil ("http://"+ipaddr+"/getScheduleUtil.php",bundle_userName);
	  						try{
	  							JSONArray jArray = new JSONArray(conResult);
	  							Lengthjs=jArray.length();
	  							//for(int i=0;i<jArray.length();i++){
	  							for(int i=0;i<jArray.length();i++){	
	  								JSONObject json_data = jArray.getJSONObject(i);

	  								userName_h[i] =json_data.getString("userid");
	  								AppName_h[i] = json_data.getString("appliancename");
	  								Proposedstarttime_h[i] =json_data.getString("proposedstarttime");
	  								ProposedEndTime_h[i] =json_data.getString("proposedendtime");
	  								ProposedCost_h[i] =json_data.getString("proposedcost");

	  							}
	  							Toast.makeText(Manage_Schedule.this, "Data Recive from Home",
	  	                                Toast.LENGTH_LONG).show();
	  						}

	  						catch(JSONException e){
	  							//Log.e("log_tag", "Error parsing data "+e.toString());
	  							Log.e("log_tag", "Error parsing data "+e.toString());
	  						}
	  						
	  						for(int i=0;i<Lengthjs;i++){				
	  						String res=getConnectiontoupdateHome2(userName_h[i],AppName_h[i],
	  								Proposedstarttime_h[i],ProposedEndTime_h[i],ProposedCost_h[i]);
	  						Toast.makeText(Manage_Schedule.this, "Sent to Util",
	                                  Toast.LENGTH_LONG).show();
	  						
	  						}
	  					
	                    	  onResume();
	                    	  break;

	                      case DialogInterface.BUTTON_NEGATIVE:
	                             // No button clicked
	                             // do nothing
	                             
	                             break;
	                      }
	                }
	         };

	         AlertDialog.Builder builder = new AlertDialog.Builder(this);
	         builder.setMessage("Once Commited you will be subjected to new Costs. Are you sure you want to Commit?")
	                      .setPositiveButton("Yes", dialogClickListener)
	                      .setNegativeButton("No", dialogClickListener).show();
	  }		
	    
	    
	public String getConnection(String usr){

		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("username",usr));

		//http postappSpinners
		try{
			HttpClient httpclient = new DefaultHttpClient();

			// have to change the ip here to correct ip
			HttpPost httppost = new HttpPost("http://"+ipaddr+"/Commit.php");
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
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getConnectionfromUtil(String url, String usr){

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
	
	public String getConnectiontoupdateHome(
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
			HttpPost httppost = new HttpPost("http://"+ipaddr+"/updateSchedulefromUtil.php");
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
	
	
	public String getConnectiontoupdateHome2(
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
			HttpPost httppost = new HttpPost("http://"+ipaddrutil+"/updateSchedulefromUtil.php");
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

	

