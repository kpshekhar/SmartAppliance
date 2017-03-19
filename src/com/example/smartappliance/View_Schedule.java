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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class View_Schedule extends Activity {
	//Ip address static string;
			static String ipaddr="10.39.202.34";
			
		//Ip address for Util;
			static String ipaddrutil="10.39.198.87";
	
	private Bundle bundle; //Declare a bundle variable
	private String username_bundle;
	String data = "";
	TableLayout tl;
	TableRow tr;
	TextView label;
	String TotalCost="";
	float totCost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view__schedule);
		
		bundle=getIntent().getExtras();
		username_bundle=bundle.getString("username");

		tl = (TableLayout) findViewById(R.id.maintable);

		final GetDataFromDB getdb = new GetDataFromDB();
		new Thread(new Runnable() {
			public void run() {
				data = getdb.getDataFromDB(username_bundle);
				System.out.println(data);
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						ArrayList<Users> users = parseJSON(data);
						addData(users);	
						
						
					}
				});
				
			}
		}).start();
	}

	public ArrayList<Users> parseJSON(String result) {
		ArrayList<Users> users = new ArrayList<Users>();
		try {
			JSONArray jArray = new JSONArray(result);
			//totCost=0;
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				Users user = new Users();
				user.setApplianceName(json_data.getString("appliancename"));
				user.setStartTime(json_data.getString("scheduledstarttime"));
				user.setEndTime(json_data.getString("scheduledendtime"));
				user.setCost(json_data.getString("cost"));
				
				users.add(user);
				
				
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());  
		}
		return users;
	}

	void addHeader(){
		/** Create a TableRow dynamically **/
		tr = new TableRow(this);

		/** Creating a TextView to add to the row **/
		label = new TextView(this);
		label.setText("Appliance Name");
		label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		label.setPadding(5, 5, 5, 5);
		label.setBackgroundColor(Color.YELLOW);
		LinearLayout Ll = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(label,params);
		tr.addView((View)Ll); // Adding textView to tablerow.

		/** Creating Qty Button **/
		TextView sttime = new TextView(this);
		sttime.setText("Start time");
		sttime.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		sttime.setPadding(5, 5, 5, 5);
		sttime.setBackgroundColor(Color.YELLOW);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(sttime,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		
		/** Creating Qty Button **/
		TextView edtime = new TextView(this);
		edtime.setText("End time         ");
		edtime.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		edtime.setPadding(5, 5, 5, 5);
		edtime.setBackgroundColor(Color.YELLOW);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(edtime,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		
		/** Creating Qty Button **/
		TextView Cost = new TextView(this);
		Cost.setText("Cost ($)     ");
		Cost.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		Cost.setPadding(5, 5, 5, 5);
		Cost.setBackgroundColor(Color.YELLOW);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(Cost,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		

		 // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void addData(ArrayList<Users> users) {

		addHeader();
		int j=0;
		for (Iterator i = users.iterator(); i.hasNext();) {
j++;
			Users p = (Users) i.next();

			/** Create a TableRow dynamically **/
			tr = new TableRow(this);

			/** Creating a TextView to add to the row **/
			label = new TextView(this);
			label.setText(p.getApplianceName());
			label.setId(j);
			label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			label.setPadding(5, 5, 5, 5);
			label.setBackgroundColor(Color.WHITE);
			LinearLayout Ll = new LinearLayout(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(label,params);
			tr.addView((View)Ll); // Adding textView to tablerow.

			/** Creating Qty Button **/
			TextView sttime = new TextView(this);
			sttime.setText(p.getStartTime());
			sttime.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			sttime.setPadding(5, 5, 5, 5);
			sttime.setBackgroundColor(Color.WHITE);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(sttime,params);
			tr.addView((View)Ll); // Adding textview to tablerow.
			
			
			
			/** Creating Qty Button **/
			TextView edtime = new TextView(this);
			edtime.setText(p.getEndTime());
			edtime.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			edtime.setPadding(5, 5, 5, 5);
			edtime.setBackgroundColor(Color.WHITE);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(edtime,params);
			tr.addView((View)Ll); // Adding textview to tablerow.
			
			/** Creating Qty Button **/
			TextView cost = new TextView(this);
			cost.setText(p.getCost());
			totCost=totCost+Float.parseFloat(p.getCost());
			cost.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			cost.setPadding(5, 5, 5, 5);
			cost.setBackgroundColor(Color.WHITE);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(cost,params);
			tr.addView((View)Ll); // Adding textview to tablerow.
			

			 // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
		}
		
		tr = new TableRow(this);
		/** Creating a TextView to add to the row **/
		label = new TextView(this);
		label.setText("Total Cost        ");
		label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		label.setPadding(5, 5, 5, 5);
		label.setBackgroundColor(Color.LTGRAY);
		LinearLayout Ll = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 2, 2, 2);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(label,params);
		tr.addView((View)Ll); // Adding textView to tablerow.
		
		/** Creating Qty Button **/
		TextView Cost = new TextView(this);
		Cost.setText("$"+String.valueOf(totCost));
		Cost.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		Cost.setPadding(5, 5, 5, 5);
		Cost.setBackgroundColor(Color.LTGRAY);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 2, 2, 2);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(Cost,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		

		 // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
	}
}