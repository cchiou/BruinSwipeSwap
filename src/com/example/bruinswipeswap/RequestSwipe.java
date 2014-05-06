package com.example.bruinswipeswap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

public class RequestSwipe extends Activity {
	Button request_button;
	Button cancel_button;
	int num_requests;
	int from_time;
	int until_time;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestswipe);
        addListenerOnButton();
    }
	//button function to request not yet implemented
	public void addListenerOnButton() {
		 
		final Context context = this;
 
	}
	//number of requests and time info not yet implemented
}
