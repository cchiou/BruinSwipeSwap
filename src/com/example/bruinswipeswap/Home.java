package com.example.bruinswipeswap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Home extends Activity {
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);            
        setContentView(R.layout.activity_home);
        setDetailsString();
        addListenerOnButton();
    }

    public void setDetailsString() {
    	String current_details = getString(R.string.current_details_1) + " " +
        		getString(R.string.number_offers) + " " + getString(R.string.current_details_2) +
        		" " + getString(R.string.number_requests) + " " + getString(R.string.current_details_3);
        
        TextView txt = new TextView(this);
        txt = (TextView) findViewById(R.id.textView5);
        txt.setText(current_details);
    }
    
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		ImageButton edit_info_button = (ImageButton) findViewById(R.id.edit_info_button);
		edit_info_button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
			    Intent intent = new Intent(context, EditUser.class);
                            startActivity(intent);   
 
			}
 
		});
		
		Button offer_swipe_button = (Button) findViewById(R.id.offer_swipes_button);
		offer_swipe_button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
			    Intent intent = new Intent(context, OfferSwipe.class);
                            startActivity(intent);   
 
			}
 
		});
		
		Button request_swipe_button = (Button) findViewById(R.id.request_swipes_button);
		request_swipe_button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
			    Intent intent = new Intent(context, RequestSwipe.class);
                            startActivity(intent);   
 
			}
 
		});
 
	}
}
