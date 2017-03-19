package com.example.smartappliance;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Manage_Appliances extends Activity {
	
	//Modified by Pranay K. Added a const string at the beginning jus to ease changing the ip
	//Ip address static string;
			static String ipaddr="10.39.202.34";
			
		//Ip address for Util;
			static String ipaddrutil="10.39.198.87";
			
			String[] Appliances= new String[100];
			private String bundle_username;
  //Variables Declaration
			private Button addAppliance;
			private Button editAppliance;
			private Button deleteAppliance;
			private Button viewAppliance;
			private Button goBack;
			private Spinner applianceList;
			private int index;
			private Bundle bundle;
			static ArrayList<String> ApplianceList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_appliances);
		
		addAppliance=(Button)findViewById(R.id.addAppliance);
		editAppliance= (Button)findViewById(R.id.editAppliance);
		deleteAppliance=(Button)findViewById(R.id.delAppliance);
		viewAppliance=(Button)findViewById(R.id.viewAppliance);
		goBack=(Button)findViewById(R.id.Back);
		applianceList=(Spinner)findViewById(R.id.appPicker);
		
		//Receive the sent bundle
		bundle = getIntent().getExtras();
		bundle_username=bundle.getString("username");
		
		//Add Appliance Button
		addAppliance.setOnClickListener(new View.OnClickListener() {
		
		//Clear all appliances
			
			@Override
			public void onClick(View v) {
				//Pass the bundle intent to a new actvity
				Intent AddAppliance = new Intent(getBaseContext(),Add_appliances.class);
				AddAppliance.putExtras(bundle);
				startActivity(AddAppliance);
				
			}
		});
		
		
		//Back Button
		goBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Kill Task 
				finish();
				
			}
		});
		
		
		//Edit Button
		editAppliance.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent EditAppliance = new Intent(getBaseContext(),Edit_Appliance.class);
				bundle.putString("appname",ApplianceList.get(index) );
				EditAppliance.putExtras(bundle);
				startActivity(EditAppliance);
				
			}
		});
		
		
		viewAppliance.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent ViewAppliance = new Intent(getBaseContext(),View_Appliance.class);
				bundle.putString("appname", ApplianceList.get(index));
				ViewAppliance.putExtras(bundle);
				startActivity(ViewAppliance);
				
			}
		});
		
		
		
		String result = getConnection("http://"+ipaddr+"/getAppliance.php",bundle_username,"");
		ApplianceList.clear();
		
		//parse json data
		try{
			JSONArray jArray = new JSONArray(result);
			for(int i=0;i<jArray.length();i++){
				JSONObject json_data = jArray.getJSONObject(i);

				// LIST Of appliances will be loaded into spin
				ApplianceList.add(json_data.getString("appliancename"));
			}
		}
		catch(JSONException e){
			//Log.e("log_tag", "Error parsing data "+e.toString());
			Log.e("log_tag", "Error parsing data "+e.toString());
		}


			//spinner 
		//	ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Appliances);
			ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ApplianceList);
			applianceList.setPrompt("Select an appliance");
			applianceList.setAdapter(adapter);
			
			applianceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0,
						View arg1, int index, long arg3) {
					index=arg0.getSelectedItemPosition();
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
			
	//Checking if there is any data to pick		
			if (ApplianceList.isEmpty())
			{
				editAppliance.setEnabled(false);
				deleteAppliance.setEnabled(false);
				viewAppliance.setEnabled(false);
				
			}
			else
			{
				editAppliance.setEnabled(true);
				deleteAppliance.setEnabled(true);
				viewAppliance.setEnabled(true);
			}
				
	
			//Delete Button Functionality
			deleteAppliance.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {

					alertMessage();
				}
			});
			
			
	}
	
	 public void alertMessage() {
         DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                      switch (which) {
                      case DialogInterface.BUTTON_POSITIVE:
                             // Yes button clicked
                    	  String result = getConnection("http://"+ipaddr+"/delAppliance.php",bundle_username,ApplianceList.get(index));
                    	  Toast.makeText(Manage_Appliances.this, "Deleted Appliance",
                                  Toast.LENGTH_LONG).show();
                    	  String result1 = getConnection("http://"+ipaddrutil+"/delAppliance.php",bundle_username,ApplianceList.get(index));
                    	  Toast.makeText(Manage_Appliances.this, "Deleted Appliance",
                                  Toast.LENGTH_LONG).show();
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
         builder.setMessage("Are you sure you want to delete Appliance "+ApplianceList.get(index)+"?")
                      .setPositiveButton("Yes", dialogClickListener)
                      .setNegativeButton("No", dialogClickListener).show();
  }



	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {

		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {

		super.onResume();
		String result = getConnection("http://"+ipaddr+"/getAppliance.php",bundle_username,"");
		ApplianceList.clear();
		
		//parse json data
		try{
			JSONArray jArray = new JSONArray(result);
			for(int i=0;i<jArray.length();i++){
				JSONObject json_data = jArray.getJSONObject(i);

				// LIST Of appliances will be loaded into spin
				ApplianceList.add(json_data.getString("appliancename"));
			}
		}
		catch(JSONException e){
			//Log.e("log_tag", "Error parsing data "+e.toString());
			Log.e("log_tag", "Error parsing data "+e.toString());
		}


			//spinner 
		//	ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Appliances);
			ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ApplianceList);
			applianceList.setPrompt("Select an appliance");
			applianceList.setAdapter(adapter);
			
			applianceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0,
						View arg1, int domainPos, long arg3) {
					index=arg0.getSelectedItemPosition();
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
			
			
			//Checking if there is any data to pick		
			if (ApplianceList.isEmpty())
			{
				editAppliance.setEnabled(false);
				deleteAppliance.setEnabled(false);
				viewAppliance.setEnabled(false);
				
			}
			else
			{
				editAppliance.setEnabled(true);
				deleteAppliance.setEnabled(true);
				viewAppliance.setEnabled(true);
			}
		
	}

	public String getConnection(String url, String usr,String Appname){

		Bundle bundle = new Bundle();
		Message msg = new Message();
		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("usrname",usr));
		nameValuePairs1.add(new BasicNameValuePair("appname",Appname));
		

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
