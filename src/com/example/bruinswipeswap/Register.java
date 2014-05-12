package com.example.bruinswipeswap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Register extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        // TODO: Later have this button update the info
        Button update_button = (Button) findViewById(R.id.register_button);
        update_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Home.class);
                startActivity(intent);

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