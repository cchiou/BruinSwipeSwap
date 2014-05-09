package com.example.bruinswipeswap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;

public class Home extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getIntent() != null && getIntent().getExtras() != null)
        {
            String m_name = getIntent().getExtras().getString("UPDATE_KEY_NAME");
            String m_number = getIntent().getExtras().getString("UPDATE_KEY_NUMBER");
            TextView tv_name = (TextView) findViewById(R.id.name_value);
            TextView tv_number = (TextView) findViewById(R.id.number_value);
            tv_name.setText(m_name);
            tv_number.setText(m_number);
        }

        setDetailsString();
        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    		// Inflate the menu items for use in the action bar
    	    MenuInflater inflater = getMenuInflater();
    	    inflater.inflate(R.menu.actions, menu);
    	    return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	final Context context = this;
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edit:
            	Intent intent = new Intent(context, EditUser.class);
                startActivity(intent);   
            case R.id.action_refresh:
                // add stuff
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void setDetailsString() {
    	String current_details = getString(R.string.current_details_1) + " " +
        		getString(R.string.number_offers) + " " + getString(R.string.current_details_2) +
        		" " + getString(R.string.number_requests) + " " + getString(R.string.current_details_3);
        
        TextView txt = new TextView(this);
        txt = (TextView) findViewById(R.id.textView5);
        txt.setText(current_details);
    }
    
	public void addListenerOnButton() {
		 
		final Context context = this;
		
		Button offer_swipe_button = (Button) findViewById(R.id.offer_swipes_button);
		offer_swipe_button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
			    Intent intent = new Intent(context, OfferSwipe.class);
                            startActivity(intent);  
 
			}
 
		});
		
		Button request_swipe_button = (Button) findViewById(R.id.request_swipes_button);
		request_swipe_button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
			    Intent intent = new Intent(context, RequestSwipe.class);
                            startActivity(intent);   
 
			}
 
		});
 
	}
}
