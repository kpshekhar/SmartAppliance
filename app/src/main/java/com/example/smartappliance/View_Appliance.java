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
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class View_Appliance extends Activity {

	//Modified by Pranay K. Added a const string at the beginning jus to ease changing the ip
	//Ip address static string;
			static String ipaddr="10.39.202.34";
			
		//Ip address for Util;
			static String ipaddrutil="10.39.198.87";
			
	private Bundle bundle;  //Declare a bundle variable
	
	//Declaring variables
	private TextView txtDispUsername;
	private TextView txtDispAppName;
	private TextView txtDispEnergy;
	private TextView txtDispOpTime;
	private TextView txtDispUsrStartTime;
	private TextView txtDispUsrEndTime;
	private TextView txtDispConstraint;
	private Button Edit;

	private Button Back;
	
	private String username_bundle;
	private String appname_bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_appliance);
		txtDispUsername=(TextView)findViewById(R.id.txtdispusrname);
		txtDispAppName=(TextView)findViewById(R.id.txtdispappname);
		txtDispEnergy=(TextView)findViewById(R.id.txtdispenergy);
		txtDispOpTime=(TextView)findViewById(R.id.txtdispopttime);
		txtDispUsrStartTime=(TextView)findViewById(R.id.txtdispstrttime);
		txtDispUsrEndTime=(TextView)findViewById(R.id.txtdispendtime);
		txtDispConstraint=(TextView)findViewById(R.id.txtdispconstraint);
		Edit= (Button)findViewById(R.id.btneditapp);
		Back= (Button)findViewById(R.id.btnmobile);
		
		//Catch values from User
		bundle = getIntent().getExtras();
		username_bundle = bundle.getString("username");
		appname_bundle=bundle.getString("appname");
		
		
		String conResult = getConnection ("http://"+ipaddr+"/viewAppliance.php","retrieve",username_bundle,appname_bundle);
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
				
				

				txtDispUsername.setText(userName);
				txtDispAppName.setText(AppName);
				txtDispEnergy.setText(Energy);
				txtDispOpTime.setText(OperationTime);
				txtDispUsrStartTime.setText(UsrStartTime);
				txtDispUsrEndTime.setText(UsrEndTime);
				txtDispConstraint.setText(constraintHardSoft);

			}
		}

		catch(JSONException e){
			//Log.e("log_tag", "Error parsing data "+e.toString());
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		
		//Edit Button 
		Edit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent EditAppliance = new Intent(getBaseContext(),Edit_Appliance.class);
				//bundle.putString("appname","Washing" );
				EditAppliance.putExtras(bundle);
				startActivity(EditAppliance);
				
			}
		});
		
		
		//Back Button 
				Back.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						finish();
						
					}
				});
		

	}

	
	public String getConnection(String url, String request, String usr, String appname){

		Bundle bundle = new Bundle();
		Message msg = new Message();
		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("request",request));
		nameValuePairs1.add(new BasicNameValuePair("usrname",usr));
		nameValuePairs1.add(new BasicNameValuePair("appname",appname));
		

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

	
}
