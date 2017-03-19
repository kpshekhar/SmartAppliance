

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



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Startup extends Activity {

	static String userString;
	static String pwdString;
	//Modified by Pranay K. Added a const string at the beginning jus to ease changing the ip
	//Ip address static string;
			static String ipaddr="10.39.202.34";
			
		//Ip address for Util;
			static String ipaddrutil="10.39.198.87";
 
		private EditText EDTuserName;
		private EditText EDTpassWord;
		private Button login;
		private Button frgtPaswrd;
		private Button exit;
		private Button registerUsr;
		private Button resetText;
		 
		
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        
        EDTuserName= (EditText)findViewById(R.id.usrName);
		EDTpassWord= (EditText)findViewById(R.id.dispusername);
		resetText= (Button)findViewById(R.id.reset);
		login= (Button)findViewById(R.id.Login);
		frgtPaswrd= (Button)findViewById(R.id.frgtPassword);
		exit= (Button)findViewById(R.id.exit);
		registerUsr= (Button)findViewById(R.id.register);
    registerUsr();
    resetText();
    exitApp();
    forgotPassword();
    //loginUser();
    
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	StrictMode.setThreadPolicy(policy); 
	login= (Button)findViewById(R.id.Login);
	login.setOnClickListener(new View.OnClickListener(){
		
		@Override
		public void onClick(View v) {


			userString = EDTuserName.getText().toString();
			pwdString = EDTpassWord.getText().toString();
			
			/*//Just for Temporary checking purposes the username and password is hardcoded
			userString="Shekhar";
			pwdString="shekhar";
			EDTuserName.setText(userString);
			EDTpassWord.setText(pwdString);   
			Comment the above section during actual execution */
			
			
			if(userString.equals("") ||pwdString.equals("")){
				Toast.makeText(getBaseContext(),"Enter Username and Password!", Toast.LENGTH_SHORT).show();
			}
			else if(isNetworkAvailable()){
				Log.d("test","Network available");

				String passwd = getConnection(userString, pwdString);

				if(!((passwd.equals("null\n")) || (passwd.equals("")))){

					if(passwd.equals("true\n")){

						Toast.makeText(getBaseContext(),"Login Successfull!", Toast.LENGTH_SHORT).show();
						Bundle bundle = new Bundle();
						bundle.putString("username", userString);
						Intent userHomeActivity = new Intent(getBaseContext(),Activity_Selection.class);
						userHomeActivity.putExtras(bundle);
						startActivity(userHomeActivity);
					}
					else
						Toast.makeText(getBaseContext(),"Invalid Username/Password!", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(getBaseContext(),"Login Failed!", Toast.LENGTH_SHORT).show();
			}

			else{	
				Toast.makeText(getBaseContext(), "NO INTERNET", Toast.LENGTH_SHORT).show();
			}
		}
	});
    }
	
	// TODO Auto-generated method stub


	//private void loginUser() {}


	private void forgotPassword() {
		View.OnClickListener lsnfrgtpwd= new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		};
		frgtPaswrd.setOnClickListener(lsnfrgtpwd);
	
		// TODO Auto-generated method stub
		
	}


	private void exitApp() {
	
		View.OnClickListener lsnExit = new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		};
		exit.setOnClickListener(lsnExit);	
	}


	private void resetText() {
	
		View.OnClickListener rstTxt= new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				EDTuserName.setText("");
				EDTpassWord.setText("");
			}
		};
		resetText.setOnClickListener(rstTxt);
		// TODO Auto-generated method stub
		
	}


	private void registerUsr() {
		View.OnClickListener rgstusr= new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isNetworkAvailable()){
					Log.d("test","Network available");
					Intent regActivity = new Intent(getBaseContext(),Registration_User.class);
					startActivity(regActivity);
				}

				else{	
					Toast.makeText(Startup.this, "NO INTERNET", Toast.LENGTH_SHORT).show();
				}
			
				// TODO Auto-generated method stub
				
			}
		};
		registerUsr.setOnClickListener(rgstusr);
		// TODO Auto-generated method stub
		
	}
	
	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		EDTuserName.setText("");
		EDTpassWord.setText("");
	}
	
	
	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// if no network is available networkInfo will be null, otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	
	public String getConnection(String usr, String pwd){

		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("username",usr));
		nameValuePairs1.add(new BasicNameValuePair("password",pwd));

		//http postappSpinners
		try{
			HttpClient httpclient = new DefaultHttpClient();

			// have to change the ip here to correct ip
			HttpPost httppost = new HttpPost("http://"+ipaddr+"/login.php");
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
