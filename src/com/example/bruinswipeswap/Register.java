package com.example.bruinswipeswap;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Register extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        Button update_button = (Button) findViewById(R.id.register_button);
        update_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
            	TextView tv_name, tv_number, tv_email, tv_password, tv_password2;
            	tv_name = (TextView) findViewById(R.id.register_editText_name);
            	tv_number = (TextView) findViewById(R.id.register_editText_number);
            	tv_email = (TextView) findViewById(R.id.register_editText_email);
            	tv_password = (TextView) findViewById(R.id.register_editText_password);
            	tv_password2 = (TextView) findViewById(R.id.register_editText_confirm_password);
            	
            	final String s_name, s_number, s_email, s_password, s_password2;
            	s_name = tv_name.getText().toString();
            	s_number = tv_number.getText().toString();
            	s_email = tv_email.getText().toString();
            	s_password = tv_password.getText().toString();
            	s_password2 = tv_password2.getText().toString();
            	
            	final String regex_number = "^[0-9]{10}$";

        		// Check if there is a name
        		if (s_name.isEmpty())
        		{
        			AlertDialog.Builder builder = new AlertDialog.Builder(context);
					
        			builder.setTitle("No Name").setMessage("Please enter a Name");
					
					builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					
						public void onClick(DialogInterface dialog, int id){}
					});
			
					AlertDialog dialog = builder.create();
			
					dialog.show();        			
        		}
    				// Check if phone number if proper format
        		else if (!s_number.matches(regex_number))
        		{
        			AlertDialog.Builder builder = new AlertDialog.Builder(context);
        			
        			builder.setTitle("Invalid Phone Number").setMessage("XXXXXXXXXX Format Only");
        			
        			builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
				
        				public void onClick(DialogInterface dialog, int id){}
        			});
		
        			AlertDialog dialog = builder.create();
		
        			dialog.show();
        		}
        			// Check if UCLA email
        		else if (!(s_email.endsWith("@ucla.edu") || s_email.endsWith("@g.ucla.edu")))
        		{
        			AlertDialog.Builder builder = new AlertDialog.Builder(context);
				
        			builder.setTitle("Invalid Email Address").setMessage("Please enter a correct UCLA Email address");
				
        			builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
				
        				public void onClick(DialogInterface dialog, int id){}
        			});
		
        			AlertDialog dialog = builder.create();
		
        			dialog.show();
        		}
        		else
        		{
        			ParseQuery<ParseUser> query = ParseUser.getQuery();
        			query.whereEqualTo("username", s_email);
        			query.findInBackground(new FindCallback<ParseUser>() {
        				public void done (List<ParseUser> objects, ParseException e) {
        					if (e == null)
        					{
        						AlertDialog.Builder builder = new AlertDialog.Builder(context);
        						
        						builder.setTitle("Account Already Exists")
        							.setMessage("This email already exists in our database.");
        					
        						builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
        					
        							public void onClick(DialogInterface dialog, int id){}
        						});
        			
        						AlertDialog dialog = builder.create();
        			
        						dialog.show();  
        					}
        					else
        					{	
        							// Check if password is confirmed correctly
                    			if (s_password.equals(s_password2) && !s_password.isEmpty()){ 
                    				ParseUser new_user = new ParseUser();
                    				new_user.setUsername(s_email);
                    				new_user.setPassword(s_password);
                    				new_user.setEmail(s_email);
                    				new_user.put("name", s_name);
                    				new_user.put("number", s_number);
                        		            		
                    				new_user.signUpInBackground(new SignUpCallback() {
                    					@Override
                    					public void done(ParseException e){
                    						if (e == null)
                    						{
                    							Intent intent = new Intent(context, Home.class);
                    							startActivity(intent);
                    						}
                    						else
                    						{
                    							AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    							
                    							builder.setTitle("Parse Input Failed");
                       						
                    							builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                       						
                    								public void onClick(DialogInterface dialog, int id){}
                    							});
                        				
                    							AlertDialog dialog = builder.create();
                        				
                    							dialog.show();
                    						}
                    					}
                    				});
                    			}
                    			else
                    			{
                    				AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    				builder.setTitle("Password Confirmation Failed").setMessage("Check your password and try again.");

                    				builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    					public void onClick(DialogInterface dialog, int id){}
                    				});

                    				AlertDialog dialog = builder.create();

                    				dialog.show();
                    			}
                    		}
            			}
            		});
            	}
            }
        });

        Button cancel_button = (Button) findViewById(R.id.register_cancel_button);
        cancel_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, LogIn.class);
                startActivity(intent);

            }

        });



    }


}