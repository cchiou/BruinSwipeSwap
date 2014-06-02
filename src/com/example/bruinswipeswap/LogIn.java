package com.example.bruinswipeswap;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;


import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.ParseException;

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
 
		button = (Button) findViewById(R.id.login_button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				TextView tv_email = (TextView) findViewById(R.id.login_editText_email);
				TextView tv_password = (TextView) findViewById(R.id.login_editText_password);
				
				ParseUser.logInInBackground(tv_email.getText().toString(), tv_password.getText().toString(), new LogInCallback() {
					public void done (ParseUser user, ParseException e) {
						if (user != null) {
							
							ParseUser CurrentUser = ParseUser.getCurrentUser();
							if (CurrentUser.getBoolean("emailVerified") == true)
							{
								Intent intent = new Intent(context, Home.class);
	                            startActivity(intent);							
							}
							else
							{
			                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

			                    builder.setTitle("Email Not Verified").setMessage("Please verify your email and try again.");

			                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id){}
			                    });

			                    AlertDialog dialog = builder.create();

			                    dialog.show();								
							}
						}
						else {
		                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

		                    builder.setTitle("Log In Failed").setMessage("Check your username/password and try again.");

		                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id){}
		                    });

		                    AlertDialog dialog = builder.create();

		                    dialog.show();
						}
					}
				});
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
