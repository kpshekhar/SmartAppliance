package com.example.smartappliance;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class View_proposed_schedule extends Activity {
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
int selectionoption;

public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_view_proposed_schedule);
	
	bundle=getIntent().getExtras();
	username_bundle=bundle.getString("username");
	selectionoption=Integer.parseInt(bundle.getString("Selection"));

	tl = (TableLayout) findViewById(R.id.maintable);

	final getDataFromDbforScheduler getdb = new getDataFromDbforScheduler();
	new Thread(new Runnable() {
		public void run() {
			data = getdb.getDataFromDB(username_bundle,selectionoption);
			System.out.println(data);
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					ArrayList<DATA> users = parseJSON(data);
					addData(users);	
					
					
				}
			});
			
		}
	}).start();
}

public ArrayList<DATA> parseJSON(String result) {
	ArrayList<DATA> users = new ArrayList<DATA>();
	try {
		JSONArray jArray = new JSONArray(result);
		//totCost=0;
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject json_data = jArray.getJSONObject(i);
			DATA user = new DATA();
			user.setAppliancename(json_data.getString("appliancename"));
			user.setProposedstarttime(json_data.getString("proposedstarttime"));
			user.setProposedendtime(json_data.getString("proposedendtime"));
			user.setCost(json_data.getString("proposedcost"));
			
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
	Cost.setText("Cost ($)       ");
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
public void addData(ArrayList<DATA> users) {

	addHeader();
	int j=0;
	for (Iterator i = users.iterator(); i.hasNext();) {
j++;
		DATA p = (DATA) i.next();

		/** Create a TableRow dynamically **/
		tr = new TableRow(this);

		/** Creating a TextView to add to the row **/
		label = new TextView(this);
		label.setText(p.getAppliancename());
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
		sttime.setText(p.getProposedstarttime());
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
		edtime.setText(p.getProposedendtime());
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
