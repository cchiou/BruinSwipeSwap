/**
 * Created by Joon on 5/4/2014.
 */

package com.example.bruinswipeswap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.parse.ParseUser;

public class EditUser extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edituser);
        
        if (getIntent() != null && getIntent().getExtras() != null) {
        	 String m_name = getIntent().getExtras().getString("UPDATE_KEY_NAME");
        	 String m_number = getIntent().getExtras().getString("UPDATE_KEY_NUMBER");
        	 TextView tv_name = (TextView) findViewById(R.id.edituser_editText_name);
        	 TextView tv_number = (TextView) findViewById(R.id.edituser_editText_number);
        	 tv_name.setText(m_name);
        	 tv_number.setText(m_number);
         }
                
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        Button update_button = (Button) findViewById(R.id.edituser_update_button);
        update_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Implements Edit
                Bundle b = new Bundle();
                TextView tv_name = (TextView) findViewById(R.id.edituser_editText_name);
                String s_name = tv_name.getText().toString();
                b.putString("UPDATE_KEY_NAME", s_name);

                TextView tv_number = (TextView) findViewById(R.id.edituser_editText_number);
                String s_number = tv_number.getText().toString();

                String regex_number = "^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";
                if (!s_number.matches(regex_number)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Invalid Phone Number").setMessage("XXXXXXXXXX, 1-XXX-XXX-XXXX, or XXX-XXX-XXXX Format Only");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){}
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();
                } else {
                    b.putString("UPDATE_KEY_NUMBER", s_number);

                    ParseUser currentUser = ParseUser.getCurrentUser();                	
                	if (currentUser != null){
                		currentUser.put("name", s_name);
                		currentUser.put("number", s_number);
                		currentUser.saveInBackground();
                	}
                    
                    Intent intent = new Intent(context, Home.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

        Button cancel_button = (Button) findViewById(R.id.edituser_cancel_button);
        cancel_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, Home.class);
                startActivity(intent);
            }
        });
    }
}
