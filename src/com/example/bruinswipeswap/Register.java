package com.example.bruinswipeswap;

import com.parse.ParseException;
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
            	
            	if (tv_password.getText().toString().equals(tv_password2.getText().toString())){
                	ParseUser new_user = new ParseUser();
            		new_user.setUsername(tv_email.getText().toString());
            		new_user.setPassword(tv_password.getText().toString());
            		new_user.setEmail(tv_email.getText().toString());
            		new_user.put("name", tv_name.getText().toString());
            		new_user.put("number", tv_number.getText().toString());
            		
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

                    builder.setTitle("Password Confirmation Failed");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){}
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();
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