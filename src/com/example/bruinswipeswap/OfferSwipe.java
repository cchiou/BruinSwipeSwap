package com.example.bruinswipeswap;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class OfferSwipe extends Activity {
	
	Button offer_button;
	Button cancel_button;
	int num_requests;
	int from_time;
	int until_time;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offerswipe);
        addListenerOnButton();
    }
	//button function not yet implemented
	public void addListenerOnButton() {
		 
		final Context context = this;
 
	}
	//number of requests and time info not yet recorded
}
