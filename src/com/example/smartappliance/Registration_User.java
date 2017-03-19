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
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Registration_User extends Activity {
	//Ip address static string;
	//Ip address static string;
			static String ipaddr="10.39.202.34";
			
		//Ip address for Util;
			static String ipaddrutil="10.39.198.87";
	
	//Declare variables to use globally
	
	private EditText edt_Username;
	private EditText edt_Password;
	private EditText edt_verifyPassword;
	private EditText edt_firstName;
	private EditText edt_lastName;
	private EditText edt_Emailid;
	private EditText edt_phonenum;
	private Button Back;
	private Button Register;
	private Button Reset;
	private CheckBox cBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_user);
		
		//Definition of the variables
		edt_Username=(EditText)findViewById(R.id.txtUsrname);
		edt_Password=(EditText)findViewById(R.id.edtappname);
		edt_verifyPassword=(EditText)findViewById(R.id.edtenergy);
		edt_firstName=(EditText)findViewById(R.id.edtoperationtime);
		edt_lastName=(EditText)findViewById(R.id.txtlastName);
		edt_Emailid=(EditText)findViewById(R.id.edtdeadlinetime);
		edt_phonenum=(EditText)findViewById(R.id.txtphneno);
		Back=(Button)findViewById(R.id.btnbackmanschd);
		Register= (Button)findViewById(R.id.btneditapp);
		Reset=(Button) findViewById(R.id.btnutilserver);
		cBox=(CheckBox)findViewById(R.id.checkBox1);
		resetfields();
		back2main();
		
		
		//Setting the REgister Button
		Register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				//getting current string values from the view
				String userName = edt_Username.getText().toString();
				String password = edt_Password.getText().toString();
				String vPassword = edt_verifyPassword.getText().toString();
				String first_Name = edt_firstName.getText().toString();
				String last_Name = edt_lastName.getText().toString();
				String mail = edt_Emailid.getText().toString();
				String phone_No = edt_phonenum.getText().toString();

				//boolean value to check the email id format
				boolean emailCheck = android.util.Patterns.EMAIL_ADDRESS.matcher(edt_Emailid.getText().toString()).matches();

				// all the validations are done in sequence
				if (userName.length() == 0){
					edt_Username.setError(getResources().getString(R.string.username_error_message));
				}
				else if (userName.length() < 6){
					edt_Username.setError(getResources().getString(R.string.minimum_length_error));
				}
				else if (password.length() == 0){
					edt_Password.setError(getResources().getString(R.string.password_error_message));
				}
				else if ((password.length() < 6)){
					edt_Password.setError(getResources().getString(R.string.minimum_length_error));
				}
				else if (vPassword.length() == 0){
					edt_verifyPassword.setError(getResources().getString(R.string.password_error_message));
				}
				else if (!password.equals(vPassword)){
					edt_verifyPassword.setError(getResources().getString(R.string.passwordmatch_error_message));
				}
				else if (first_Name.length() == 0){
					edt_firstName.setError(getResources().getString(R.string.fName_error_message));
				}
				else if (last_Name.length() == 0){
					edt_lastName.setError(getResources().getString(R.string.lName_error_message));
				}
				else if (mail.length() == 0){
					edt_Emailid.setError(getResources().getString(R.string.email_error_message));
				}
				else if (!emailCheck){
					edt_Emailid.setError(getResources().getString(R.string.emailpattern_error_message));
				}
				else if (phone_No.length() == 0){
					edt_phonenum.setError(getResources().getString(R.string.phone_error_message));
				}
				else if(!cBox.isChecked()){
					Toast.makeText(getBaseContext(),getResources().getString(R.string.agreement_error_message),Toast.LENGTH_SHORT).show();
				}
				//finally on successfull verification the appropriate message is displayed
				else {

					String status = getConnection(userName,password,first_Name, last_Name, mail, phone_No);
					if(status.equals("true\n")){
						Toast.makeText(getBaseContext(),"Registration Successfull!",Toast.LENGTH_SHORT).show();
						finish();
					}
					else if(status.equals("false_user\n")){
						Toast.makeText(getBaseContext(),"Username already exists. Please enter a different username!",Toast.LENGTH_SHORT).show();
					}
					else if(status.equals("false_email\n")){
						Toast.makeText(getBaseContext(),"Email already registered. Please enter different email!",Toast.LENGTH_SHORT).show();
					}
					else

						Toast.makeText(getBaseContext(),"Problem! Server returned invalid value",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void back2main() {

		//Add Listener activity
		View.OnClickListener lsnrback = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Kill Activity
				finish();
			}
		};
		Back.setOnClickListener(lsnrback);
	}

	private void resetfields() {
		View.OnClickListener lsnreset = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				edt_Username.setText("");
				edt_Password.setText("");
				edt_verifyPassword.setText("");
				edt_firstName.setText("");
				edt_lastName.setText("");
				edt_Emailid.setText("");
				edt_phonenum.setText("");
				cBox.setChecked(false);
			}
		};
		// Start Listener
		Reset.setOnClickListener(lsnreset);
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
	
	public String getConnection(String usr, String pwd, String fName, String lName,String mail, String phone){

		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("username",usr));
		nameValuePairs1.add(new BasicNameValuePair("password",pwd));
		nameValuePairs1.add(new BasicNameValuePair("firstName",fName));
		nameValuePairs1.add(new BasicNameValuePair("lastName",lName));
		nameValuePairs1.add(new BasicNameValuePair("email",mail));
		nameValuePairs1.add(new BasicNameValuePair("phone",phone));

		//http postappSpinners
		try{
			HttpClient httpclient = new DefaultHttpClient();

			// have to change the ip here to correct ip
			HttpPost httppost = new HttpPost("http://"+ipaddr+"/register.php");
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
