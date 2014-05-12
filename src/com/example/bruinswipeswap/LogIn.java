package com.example.bruinswipeswap;

import android.app.Activity;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import com.parse.Parse;

public class LogIn extends Activity {
 
	Button button;
	TextView view;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		addListenerOnLogInButton();
		addListenerOnRegisterLink();
		
		Parse.initialize(this, "TknZnYwU5lvjuaiDftIATe6UMNpjsQD8EqiWPtwh", "OcxyFi2dFos1wGgezXh7Z2cdH0P5L4CUsffg2EK6");
	}
 
	public void addListenerOnLogInButton() {
		final Context context = this;
 
		button = (Button) findViewById(R.id.login_button);
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
			    Intent intent = new Intent(context, Register.class);
                            startActivity(intent);   
			}
		});
	}
}
