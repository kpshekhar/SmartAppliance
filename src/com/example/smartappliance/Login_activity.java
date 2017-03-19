package com.example.smartappliance;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login_activity extends Activity {
	
	static String userNameStr;  //String for Username
	static String passwordStr;	//String for password
	//Modified by Pranay K. Added a const string at the beginning jus to ease changing the ip
		static String ipaddr="10.39.200.200"; 
		// End of modification by Pranay K
		
	final EditText userName= (EditText)findViewById(R.id.usrName);
	final EditText passWord= (EditText)findViewById(R.id.dispusername);	
	Button registerUsr= (Button)findViewById(R.id.register);
	Button reset= (Button)findViewById(R.id.reset);
	Button login= (Button)findViewById(R.id.Login);
	Button frgtPaswrd= (Button)findViewById(R.id.frgtPassword);
	Button exit= (Button)findViewById(R.id.exit);
		
	
 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
    
    registerUsr();
    resetText();
    exitApp();
    forgotPassword();
    loginUser();
    
    //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	//StrictMode.setThreadPolicy(policy); 
    
    
    }


	private void loginUser() {

		
	}


	private void forgotPassword() {

		
	}


	private void exitApp() {
		View.OnClickListener lsnExit = new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		};
		//exit.setOnClickListener(lsnExit);	
	}


	private void resetText() {
		View.OnClickListener rstTxt= new View.OnClickListener() {

			public void onClick(View v) {

				userName.setText("");
				passWord.setText("");
			}
		};
		//reset.setOnClickListener(rstTxt);

		
	}


	private void registerUsr() {
		
		
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
    
}
