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

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Add_appliances extends Activity {
	
	//Ip address static string;
		static String ipaddr="10.39.202.34";
		
	//Ip address for Util;
		static String ipaddrutil="10.39.198.87";
	
	//Declare the Variables
	private EditText edt_appName;
	private EditText edt_energy;
	private EditText edt_operationTime;
	private EditText edt_startTime;
	private EditText edt_endTime;
	private EditText edt_constraint;
	private TextView txt_Username;
	private Button Add;
	private Button Back;
	private Button Reset;
	private Bundle bundle;  //Define a bundle to send and recieve.
	private String Username_receivd; //Define the string to receive the Username

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_appliances);
		
		
		edt_appName=(EditText)findViewById(R.id.edtappname);
		edt_energy= (EditText)findViewById(R.id.edtenergy);
		edt_operationTime= (EditText)findViewById(R.id.edtoperationtime);
		edt_startTime= (EditText)findViewById(R.id.edtstartTime);
		edt_endTime=(EditText)findViewById(R.id.edtdeadlinetime);
		edt_constraint=(EditText)findViewById(R.id.edtconstraint);
		txt_Username=(TextView)findViewById(R.id.txtdispusrname);
		Add=(Button)findViewById(R.id.btnutilserver);
		Back=(Button)findViewById(R.id.btnmobile);
		Reset=(Button)findViewById(R.id.btneditapp);
		
		//Get the bundle from the intent
		bundle=getIntent().getExtras();
		Username_receivd=bundle.getString("username");
		
		//Set the username to display in the textview
		txt_Username.setText(Username_receivd);
		
		// Setup the Add Button
		Add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//getting current string values from the view
				String userName = Username_receivd;
				String AppName = edt_appName.getText().toString();
				String Energy = edt_energy.getText().toString();
				String OperationTime = edt_operationTime.getText().toString();
				String UsrStartTime = edt_startTime.getText().toString();
				String UsrEndTime = edt_endTime.getText().toString();
				String constraintHardSoft  = edt_constraint.getText().toString();
				String ScheduledStartTime = edt_startTime.getText().toString();
				String ScheduledStopTime = edt_endTime.getText().toString();
				edt_appName.setError(null);
				edt_energy.setError(null);
				edt_operationTime.setError(null);
				edt_startTime.setError(null);
				edt_endTime.setError(null);
				edt_constraint.setError(null);
				
				// all the validations are done in sequence
				if (AppName.length() == 0){
					edt_appName.setError(getResources().getString(R.string.appliance_name_error));
				}
				else if (Energy.length() == 0){
					edt_energy.setError(getResources().getString(R.string.energy_error));
				}
				else if (OperationTime.length() == 0){
					edt_operationTime.setError(getResources().getString(R.string.operation_time_error));
				}
				else if ((UsrStartTime.length() == 0)){
					edt_startTime.setError(getResources().getString(R.string.usr_start_time_error));
				}
				else if ((Integer.parseInt(UsrStartTime))>2400){
					edt_startTime.setError(getResources().getString(R.string.time_bounds_error));
				}
				else if ((Integer.parseInt(UsrStartTime))%5 != 0){
					edt_startTime.setError(getResources().getString(R.string.time_interval_error));
				}
				//Time format check error
				else if (!timeformatchecker(UsrStartTime)){
					edt_startTime.setError(getResources().getString(R.string.time_format_error));
				}
				//
				else if ((UsrEndTime.length() == 0)){
					edt_endTime.setError(getResources().getString(R.string.usr_end_time_error));
				}
				else if ((Integer.parseInt(UsrEndTime))>2400){
					edt_endTime.setError(getResources().getString(R.string.time_bounds_error));
				}
				else if ((Integer.parseInt(UsrEndTime))%5 != 0){
					edt_endTime.setError(getResources().getString(R.string.time_interval_error));
				}
				//Time format check error
				else if (!timeformatchecker(UsrEndTime)){
					edt_endTime.setError(getResources().getString(R.string.time_format_error));
				}
				//
				
				else if ((Integer.parseInt(UsrEndTime))<=(Integer.parseInt(UsrStartTime))){
					edt_endTime.setError(getResources().getString(R.string.start_time_greater_error));
				}
				//else if ((Integer.parseInt(UsrEndTime)-(Integer.parseInt(UsrStartTime))){
				//	edt_endTime.setError(getResources().getString(R.string.start_time_greater_error));
				//}
				else if (constraintHardSoft.length() == 0){
					edt_constraint.setError(getResources().getString(R.string.constraint_empty_error));
				}
				else if (!((constraintHardSoft.equals("HARD"))||(constraintHardSoft.equals("SOFT")))){
					edt_constraint.setError(getResources().getString(R.string.constraint_entry_error));
				}
				
				//finally on successfull verification the appropriate message is displayed
				else {
					Toast.makeText(getBaseContext(),"Validation Passed!",Toast.LENGTH_SHORT).show();
					String status = getConnection(ipaddr,userName,AppName,Energy,OperationTime,UsrStartTime,UsrEndTime,constraintHardSoft,ScheduledStartTime,ScheduledStopTime);
					if(status.equals("true\n")){
						Toast.makeText(getBaseContext(),"Appliance Added Successfull!",Toast.LENGTH_SHORT).show();
						String utilstatus = getConnection(ipaddrutil,userName,AppName,Energy,OperationTime,UsrStartTime,UsrEndTime,constraintHardSoft,ScheduledStartTime,ScheduledStopTime);
						if(utilstatus.equals("true\n")){
							Toast.makeText(getBaseContext(),"Utility Database updated!",Toast.LENGTH_SHORT).show();
						finish();
						}
						else

							Toast.makeText(getBaseContext(),"Problem! Server returned invalid value",Toast.LENGTH_SHORT).show();
						}
					else if(status.equals("false_appname\n")){
						Toast.makeText(getBaseContext(),"Appliance already exists!",Toast.LENGTH_SHORT).show();
					}
					else

						Toast.makeText(getBaseContext(),"Problem! Server returned invalid value",Toast.LENGTH_SHORT).show();
				
				
				
				}
				
			}
		});
		
		
		//Setup reset button
		Reset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//reset all the Edit Text Boxes
				edt_appName.setText("");
				edt_energy.setText("");
				edt_operationTime.setText(""); 
				edt_startTime.setText("");
				edt_endTime.setText("");
				edt_constraint.setText("");
				
				
			}
		});
		
		//setup the Back button
		Back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Go back to the previous screen
				finish();
			}
		});
		
	}
	
	
	public String getConnection(String ippadress,
								String userName,
								String AppName,
								String Energy,
								String OperationTime,
								String UsrStartTime,
								String UsrEndTime,
								String constraintHardSoft,
								String ScheduledStartTime,
								String ScheduledStopTime){

		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("username",userName));
		nameValuePairs1.add(new BasicNameValuePair("appname",AppName));
		nameValuePairs1.add(new BasicNameValuePair("energy",Energy));
		nameValuePairs1.add(new BasicNameValuePair("optime",OperationTime));
		nameValuePairs1.add(new BasicNameValuePair("usrstarttime",UsrStartTime));
		nameValuePairs1.add(new BasicNameValuePair("usrendtime",UsrEndTime));
		nameValuePairs1.add(new BasicNameValuePair("constrainttype",constraintHardSoft));
		nameValuePairs1.add(new BasicNameValuePair("schstarttime",ScheduledStartTime));
		nameValuePairs1.add(new BasicNameValuePair("schendtime",ScheduledStopTime));

		//http postappSpinners
		try{
			HttpClient httpclient = new DefaultHttpClient();

			// have to change the ip here to correct ip
			HttpPost httppost = new HttpPost("http://"+ippadress+"/addappliance.php");
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
	
	public boolean timeformatchecker(String time)
	{
		int quo, //quotient
		remain,  //remainder
		entrdTime; //Entered Time
		//1. Parse the string into integer
		entrdTime = Integer.parseInt(time);
		quo=entrdTime/100;
		remain=entrdTime % 100;
		
		if (remain>55) return false;
		else return true;
	}
	
	
}
