package com.example.bruinswipeswap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TimePicker;

public class OfferSwipe extends Activity {
	
	Button offer_button;
	Button cancel_button;
	int num_requests;
	int from_time;
	int until_time;
	boolean anytimeToday;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offerswipe);
        NumberPicker numberpicker = (NumberPicker)findViewById(R.id.numberPicker1);
        String[] nums = new String[21];
        for(int i=0; i<nums.length; i++)
               nums[i] = Integer.toString(i);

        numberpicker.setMinValue(0);
        numberpicker.setMaxValue(20);
        numberpicker.setWrapSelectorWheel(false);
        numberpicker.setDisplayedValues(nums);
        numberpicker.setValue(0);
        num_requests = numberpicker.getValue();
        addListenerOnButton();
    }
	
	public void addListenerOnButton() {
		 
final Context context = this;
		
		Button requestbutton = (Button) findViewById(R.id.offer_button);
		requestbutton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				TimePicker timepicker = (TimePicker) findViewById(R.id.timePicker1);
				int hour = timepicker.getCurrentHour();
				int minute = timepicker.getCurrentMinute();
				//time is four digits, first two are the hours (0-23), second two are the minutes (0-59)
				from_time = Integer.parseInt(Integer.toString(hour) + Integer.toString(minute));
				
				
				TimePicker timepicker2 = (TimePicker) findViewById(R.id.timePicker2);
				int hour2 = timepicker2.getCurrentHour();
				int minute2 = timepicker2.getCurrentMinute();
				until_time = Integer.parseInt(Integer.toString(hour2) + Integer.toString(minute2));
				
				CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox1);
				if(checkBox.isChecked()){
		        	anytimeToday = true;
		        }
				
				if(from_time > until_time && anytimeToday == false)
				{
					new AlertDialog.Builder(context).setTitle("Time Error")
				    .setMessage("Please select a valid time range.")
				    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with selecting times
				        }
				     })
				    .setIcon(android.R.drawable.ic_dialog_alert)
				     .show();
				}
				else{
					 
				        
				        
				        
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
