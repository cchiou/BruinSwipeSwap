package com.example.bruinswipeswap;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TimePicker;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class RequestSwipe extends Activity {
	Button request_button;
	Button cancel_button;
	int num_requests;
	int from_time;
	int until_time;
	boolean anytimeToday;
	NumberPicker numberpicker;
	ParseObject p;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestswipe);
        numberpicker = (NumberPicker)findViewById(R.id.numberPicker1);
        String[] numbers = new String[10];
        for(int i=0; i<numbers.length; i++)
               numbers[i] = Integer.toString(i);

        
        numberpicker.setWrapSelectorWheel(false);
        numberpicker.setDisplayedValues(numbers);
        numberpicker.setValue(0);
        numberpicker.setMinValue(0);
        numberpicker.setMaxValue(9);
        num_requests = numberpicker.getValue();
        
        TimePicker timepicker1 = (TimePicker)findViewById(R.id.timePicker1);
        timepicker1.setCurrentMinute(0);
        timepicker1.setCurrentHour(12);
        
        TimePicker timepicker2 = (TimePicker)findViewById(R.id.timePicker2);
        timepicker2.setCurrentMinute(0);
        timepicker2.setCurrentHour(12);
        
        p = new ParseObject("Requests");
		
		
        
        addListenerOnButton();
        p.saveInBackground();
    }
	public void addListenerOnButton() {
		 
		final Context context = this;
		
		Button requestbutton = (Button) findViewById(R.id.request_button);
		requestbutton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				Calendar c = Calendar.getInstance();
				int currentHour = c.get(Calendar.HOUR);
				int currentMinute = c.get(Calendar.MINUTE);
				
				TimePicker timepicker = (TimePicker) findViewById(R.id.timePicker1);
				Calendar c1 = Calendar.getInstance();
				c1.set(Calendar.HOUR_OF_DAY, timepicker.getCurrentHour());
				c1.set(Calendar.MINUTE, timepicker.getCurrentMinute());
				Date time1 = c1.getTime();
				int hour = timepicker.getCurrentHour();
				int minute = timepicker.getCurrentMinute();
				from_time = Integer.parseInt(Integer.toString(hour) + Integer.toString(minute));
				
				
				TimePicker timepicker2 = (TimePicker) findViewById(R.id.timePicker2);
				Calendar c2 = Calendar.getInstance();
				c2.set(Calendar.HOUR_OF_DAY, timepicker2.getCurrentHour());
				c2.set(Calendar.MINUTE, timepicker2.getCurrentMinute());
				Date time2 = c2.getTime();
				int hour2 = timepicker2.getCurrentHour();
				int minute2 = timepicker2.getCurrentMinute();
				until_time = Integer.parseInt(Integer.toString(hour2) + Integer.toString(minute2));
				num_requests = numberpicker.getValue();
				CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox1);
				
				
				
				if(checkBox.isChecked()){
		        	anytimeToday = true;
		        }
				
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
				query.whereEqualTo("userId", (String) ParseUser.getCurrentUser().getUsername());
				
				int size=0;
				try {
					size=query.count();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(size >=3)
				{
					new AlertDialog.Builder(context).setTitle("Exceeded Three Maximum Requests")
				    .setMessage("Please delete previous request(s) to continue!")
				    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				             // continue with selecting times
				        }
				     })
				    .setIcon(android.R.drawable.ic_dialog_alert)
				     .show();
				}
				else if(num_requests == 0){
					new AlertDialog.Builder(context).setTitle("Invalid Number of Swipes")
				    .setMessage("Please enter number of swipes!")
				    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				             // continue with selecting times
				        }
				     })
				    .setIcon(android.R.drawable.ic_dialog_alert)
				     .show();
				}
				
				else if((hour > hour2 && anytimeToday ==false) || (hour == hour2 && minute > minute2) )
				{
					new AlertDialog.Builder(context).setTitle("Time Error")
				    .setMessage("Please select a valid time range!")
				    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with selecting times
				        }
				     })
				    .setIcon(android.R.drawable.ic_dialog_alert)
				     .show();
				}
				else{
				        num_requests = numberpicker.getValue();
				        p.put("numSwipes", num_requests); 
						p.put("beginTime", (Date)time1);
						p.put("endTime", (Date)time2);
						p.put("anytimeToday", anytimeToday);
						p.put("userId", (String) ParseUser.getCurrentUser().getUsername());
						p.saveInBackground();
						
				        Intent intent = new Intent(context, Home.class);
                            startActivity(intent);  
				}
			}
		});
		
		Button cancelbutton = (Button) findViewById(R.id.cancel_button);
		cancelbutton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
			    Intent intent = new Intent(context, Home.class);
                            startActivity(intent);   
 
			}
 
		});
 
	}
	
}
