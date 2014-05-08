package com.example.bruinswipeswap;

import android.app.Activity;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class LogIn extends Activity {
 
	Button button;
	TextView view;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		addListenerOnLogInButton();
		addListenerOnRegisterLink();
		
	}
 
	public void addListenerOnLogInButton() {
		final Context context = this;
 
		button = (Button) findViewById(R.id.login_submit_button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, Home.class);
                            startActivity(intent);   
			}
		});
	}

	public void addListenerOnRegisterLink() {
		final Context context = this;
 
		view = (TextView) findViewById(R.id.login_textView_register);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, Home.class);
                            startActivity(intent);   
			}
		});
	}
}
