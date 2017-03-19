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
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_Appliance extends Activity {
	
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
		private Button Update;
		private Button Back;
	
		private Bundle bundle;  //Define a bundle to send and recieve.
			
			private String username_bundle;
			private String appname_bundle;
			
			

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_appliance);
		
		//Declare the variables
		edt_appName=(EditText)findViewById(R.id.edtappname);
		edt_energy= (EditText)findViewById(R.id.edtenergy);
		edt_operationTime= (EditText)findViewById(R.id.edtoperationtime);
		edt_startTime= (EditText)findViewById(R.id.edtstartTime);
		edt_endTime=(EditText)findViewById(R.id.edtdeadlinetime);
		edt_constraint=(EditText)findViewById(R.id.edtconstraint);
		txt_Username=(TextView)findViewById(R.id.txtdispusrname);
		
		Update= (Button)findViewById(R.id.btnutilserver);
		Back= (Button)findViewById(R.id.btnmobile);
		
		//Catch values from User
		bundle = getIntent().getExtras();
		username_bundle = bundle.getString("username");
		appname_bundle=bundle.getString("appname");
		
		
		String conResult = getConnection ("http://"+ipaddr+"/editAppliance.php","retrieve",username_bundle,appname_bundle,"","","","","");
		try{
			JSONArray jArray = new JSONArray(conResult);

			//for(int i=0;i<jArray.length();i++){
			for(int i=0;i<jArray.length();i++){	
				JSONObject json_data = jArray.getJSONObject(i);

				String userName =json_data.getString("userid");
				String AppName = json_data.getString("appliancename");
				String Energy =json_data.getString("energy");
				String OperationTime =json_data.getString("operationtime");
				String UsrStartTime =json_data.getString("userstarttime");
				String UsrEndTime =json_data.getString("userendtime");
				String constraintHardSoft =json_data.getString("hardsoftconst");
				
				

				txt_Username.setText(userName);
				
				edt_appName.setText(AppName);
				edt_energy.setText(Energy);
				edt_operationTime.setText(OperationTime);
				edt_startTime.setText(UsrStartTime);
				edt_endTime.setText(UsrEndTime);
				edt_constraint.setText(constraintHardSoft);

			}
		}

		catch(JSONException e){
			//Log.e("log_tag", "Error parsing data "+e.toString());
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		edt_appName.setFocusableInTouchMode(false);
		
		
		// Setup the  Button
				Update.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//getting current string values from the view
						String userName = username_bundle;
						String AppName = edt_appName.getText().toString();
						String Energy = edt_energy.getText().toString();
						String OperationTime = edt_operationTime.getText().toString();
						String UsrStartTime = edt_startTime.getText().toString();
						String UsrEndTime = edt_endTime.getText().toString();
						String constraintHardSoft  = edt_constraint.getText().toString();
						
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
						
						else if ((Integer.parseInt(UsrEndTime))<=(Integer.parseInt(UsrStartTime))){
							edt_endTime.setError(getResources().getString(R.string.start_time_greater_error));
						}
						//Time format check error
						else if (!timeformatchecker(UsrEndTime)){
							edt_endTime.setError(getResources().getString(R.string.time_format_error));
						}
						//
						else if (constraintHardSoft.length() == 0){
							edt_constraint.setError(getResources().getString(R.string.constraint_empty_error));
						}
						else if (!((constraintHardSoft.equals("HARD"))||(constraintHardSoft.equals("SOFT")))){
							edt_constraint.setError(getResources().getString(R.string.constraint_entry_error));
						}
						
						//finally on successfull verification the appropriate message is displayed
						else {
							Toast.makeText(getBaseContext(),"Validation Passed!",Toast.LENGTH_SHORT).show();
							getConnection("http://"+ipaddr+"/editAppliance.php","update",userName,AppName,Energy,OperationTime,UsrStartTime,UsrEndTime,constraintHardSoft);
							
							Toast.makeText(getBaseContext(),"Appliance Details Updated!",Toast.LENGTH_SHORT).show();
							getConnection("http://"+ipaddrutil+"/editAppliance.php","update",userName,AppName,Energy,OperationTime,UsrStartTime,UsrEndTime,constraintHardSoft);
							finish();
						}
						
					}
				});
		
		
		//Set the Back Button
		Back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				finish();
			}
		});

	}

	public String getConnection(String url, String request, 
			String userName,
			String AppName,
			String Energy,
			String OperationTime,
			String UsrStartTime,
			String UsrEndTime,
			String constraintHardSoft){

		Bundle bundle = new Bundle();
		Message msg = new Message();
		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("request",request));
		nameValuePairs1.add(new BasicNameValuePair("username",userName));
		nameValuePairs1.add(new BasicNameValuePair("appname",AppName));
		nameValuePairs1.add(new BasicNameValuePair("energy",Energy));
		nameValuePairs1.add(new BasicNameValuePair("optime",OperationTime));
		nameValuePairs1.add(new BasicNameValuePair("usrstarttime",UsrStartTime));
		nameValuePairs1.add(new BasicNameValuePair("usrendtime",UsrEndTime));
		nameValuePairs1.add(new BasicNameValuePair("constrainttype",constraintHardSoft));
		

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
