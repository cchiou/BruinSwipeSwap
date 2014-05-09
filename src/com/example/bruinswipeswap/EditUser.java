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

public class EditUser extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edituser);
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
                b.putString("UPDATE_KEY_NUMBER", s_number);

                Intent intent = new Intent(context, Home.class);
                intent.putExtras(b);
                startActivity(intent);

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
