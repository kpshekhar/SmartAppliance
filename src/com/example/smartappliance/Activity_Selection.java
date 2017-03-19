package com.example.smartappliance;


import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Selection extends Activity {

	private Bundle bundle;
	private Button manageAppliances;
	private Button viewschedule;
	private Button manageschedule;
	private Button manageaccount;
	private Button logout;
	private TextView dispUsername;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity__selection);
	
		String Username; //String to catch the value from the bundle
		
		//Define the variables
		manageAppliances= (Button)findViewById(R.id.mngApp);
		viewschedule= (Button)findViewById(R.id.viewScedule);
		manageschedule= (Button)findViewById(R.id.mngSchdle);
		manageaccount= (Button)findViewById(R.id.mngAccount);
		logout= (Button)findViewById(R.id.logout);
		dispUsername= (TextView)findViewById(R.id.UsrNameDisp);
		
		//Receive the sent bundle
		bundle = getIntent().getExtras();
		
		//Get the string value from the bundle
		Username =bundle.getString("username");
		
		dispUsername.setText(Username);  //Display the Username in the Text View
		
		//Setup Manage Appliances Button 
		manageAppliances.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Setting up intent activity and passing the Bundle
				Intent manageAppliance = new Intent(getBaseContext(),Manage_Appliances.class);
				manageAppliance.putExtras(bundle);
				startActivity(manageAppliance);
				
			}
		});
		
		
		//Setup View Schedule
		viewschedule.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Setting up intent activity and passing the Bundle
				//AS of now , no screens are configured, so configure the same first.
				Intent viewSchedule = new Intent(getBaseContext(),View_Schedule.class);
				viewSchedule.putExtras(bundle);
				Toast.makeText(getBaseContext(),"Viewing Current Schedule!",Toast.LENGTH_SHORT).show();
				startActivity(viewSchedule);
				
			}
		});
		
		
		//setup Manage Schedule
		manageschedule.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Setting up intent activity and passing the Bundle
				//AS of now , no screens are configured, so configure the same first.
				Intent manageSchedule = new Intent(getBaseContext(),Manage_Schedule.class);
				manageSchedule.putExtras(bundle);
				startActivity(manageSchedule);
				
			}
		});
		
		
		//Setup Manage Account
		manageaccount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Setting up intent activity and passing the Bundle
				//AS of now , no screens are configured, so configure the same first.
				Intent manageAccount = new Intent(getBaseContext(),Manage_Appliances.class);
				manageAccount.putExtras(bundle);
				startActivity(manageAccount);
				
			}
		});
		
		//Logout Button setup
		logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		
	}

	
}
