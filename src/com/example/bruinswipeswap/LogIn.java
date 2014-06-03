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

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.RequestPasswordResetCallback;

public class LogIn extends Activity {
 
	Button button;
	TextView view;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		addListenerOnLogInButton();
		addListenerOnHelpLink();
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

	public void addListenerOnHelpLink() {
		final Context context = this;
		
		view = (TextView) findViewById(R.id.login_textView_help);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {		
				AlertDialog.Builder m_builder = new AlertDialog.Builder(context);

				String options_array[] = {  "Register for an Account",
											"Forgot Password", };
        
				m_builder.setTitle("Login Helper")
					.setItems(options_array, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							switch(which)
							{
							case 0:
								Intent intent = new Intent(context, Register.class);
								startActivity(intent); 
								break;
							case 1:
								TextView tv_email_query = (TextView) findViewById(R.id.login_editText_email);
								final String s_email_query = tv_email_query.getText().toString();
        				
								ParseQuery<ParseUser> query = ParseUser.getQuery();
								query.whereEqualTo("email", s_email_query);
								query.getFirstInBackground(new GetCallback<ParseUser>() {
									public void done(ParseUser user, ParseException e) {
										if (e == null) {
											ParseUser.requestPasswordResetInBackground(s_email_query,
													new RequestPasswordResetCallback() {
												public void done(ParseException e) {
													if (e == null) {
														AlertDialog.Builder builder = new AlertDialog.Builder(context);

														builder.setTitle("Reset Password")
															.setMessage("An email has been sent with instructions on resetting your password.");
        			    			                
														builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
        			    			                	
															public void onClick(DialogInterface dialog, int id){}
														});

														AlertDialog dialog = builder.create();

														dialog.show();
													}
												}
											});
										} 
										else {
											AlertDialog.Builder builder = new AlertDialog.Builder(context);

											builder.setTitle("Email Not Found").setMessage("Check your email and try again.");
        			                
											builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog, int id){}
											});

											AlertDialog dialog = builder.create();

											dialog.show();
										}
									}
								});
								break;
							}
						}
					});
								
                AlertDialog dialog = m_builder.create();

                dialog.show();
            }
		});
	}
}
