package com.example.bruinswipeswap;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Home extends ActionBarActivity {
	private static Context context;
	FindMatch matcher;
	private String match_name;
	private String match_number;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //deletes offers/requests at the end of the day
        ParseQuery<ParseObject> requests = ParseQuery.getQuery("Requests");
		requests.findInBackground(new FindCallback<ParseObject>() {
		
	        @Override
	        public void done(List<ParseObject> objects, ParseException e) {
	            if (e == null) {
	            	Calendar c = Calendar.getInstance();
	            	if(objects.size()>0){
		            	Date today = (Date)objects.get(0).get("endTime");
		            	Calendar c2 = Calendar.getInstance();
		            	c2.setTime(today);
		            	Date d = c.getTime();
		            	int dayDiff = c.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
		            	if(dayDiff != 0){
			                for(int i =0; i < objects.size();i++)
			                	objects.get(i).deleteInBackground();
		                
		            	}
	            	}
	            	
	            } else {
	                // TODO: handle query failure
	            }

	        }
		}); 
		ParseQuery<ParseObject> offers = ParseQuery.getQuery("Offers");
		offers.findInBackground(new FindCallback<ParseObject>() {
		
	        @Override
	        public void done(List<ParseObject> objects, ParseException e) {
	            if (e == null) {
	            	Calendar c = Calendar.getInstance();
	            	if(objects.size()>0){
	            		Date today = (Date)objects.get(0).get("endTime");
		            	Calendar c2 = Calendar.getInstance();
		            	c2.setTime(today);
		            	Date d = c.getTime();
		            	int dayDiff = c.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
		            	if(dayDiff != 0){
			                for(int i =0; i < objects.size();i++)
			                	objects.get(i).deleteInBackground();
		                
		            	}
	            	}
	            } else {
	                // TODO: handle query failure
	            }

	        }
		}); 

        Home.context = this;
        setContentView(R.layout.activity_home);
        Button view_match = (Button) findViewById(R.id.view_match_button);
        view_match.setEnabled(false);
        view_match.setVisibility(View.INVISIBLE);
        
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
        	String m_name = currentUser.get("name").toString();
        	String m_number = currentUser.get("number").toString();
        	
        	m_number = String.format("(%s) %s-%s", m_number.substring(0, 3), m_number.substring(3, 6), 
        	          m_number.substring(6, 10));
        	
        	TextView tv_name = (TextView) findViewById(R.id.name_value);
        	TextView tv_number = (TextView) findViewById(R.id.number_value);
        	tv_name.setText(m_name);
        	tv_number.setText(m_number);
        	
            setDetailsString();
            addListenerOnButtons();
            populateList();
       }
       else { 
            // If not logged in, return to LogIn page
        	final Context context = this;
        	Intent intent = new Intent(context, LogIn.class);
        	startActivity(intent);
        } 
    }

    public static Context getContext() {
    	return context;
    }
    
    private void populateList() {
     	ParseQuery<ParseObject> requests = ParseQuery.getQuery("Requests");
		requests.whereEqualTo("userId", (String) ParseUser.getCurrentUser().getUsername());

		requests.findInBackground(new FindCallback<ParseObject>() {
		
	        @Override
	        public void done(List<ParseObject> objects, ParseException e) {
	            if (e == null) {
	            	Entry [] requestEntries = new Request[objects.size()];

	                int i = 0;
	                
	                for (ParseObject obj : objects) {
						try {
		                   requestEntries[i] = new Request(obj);
		                   i++;
		                }
						catch(ParseException e1) {
							// TODO: handle exception
						}
	                }
	                ArrayAdapterItem adapter = new ArrayAdapterItem(Home.getContext(), R.layout.home_list_item, requestEntries);
	            	
	            	ListView listView = (ListView) findViewById(R.id.home_requests_listview);
	            	listView.setAdapter(adapter);
	            	
	            } else {
	                // TODO: handle query failure
	            }

	        }
		}); 
		ParseQuery<ParseObject> offers = ParseQuery.getQuery("Offers");
		offers.whereEqualTo("userId", (String) ParseUser.getCurrentUser().getUsername());
		
		offers.findInBackground(new FindCallback<ParseObject>() {
			
	        @Override
	        public void done(List<ParseObject> objects, ParseException e) {
	            if (e == null) {
	            	Entry [] offerEntries = new Offer[objects.size()];

	                int i = 0;
	                
	                for (ParseObject obj : objects) {
						try {
		                   offerEntries[i] = new Offer(obj);
		                   i++;
		                }
						catch(ParseException e1) {
							// TODO: handle exception
						}
	                }
	                ArrayAdapterItem adapter = new ArrayAdapterItem(Home.getContext(), R.layout.home_list_item, offerEntries);
	            	
	            	ListView listView = (ListView) findViewById(R.id.home_offers_listview);
	            	listView.setAdapter(adapter);
	            	
	            } else {
	                // TODO: handle query failure
	            }

	        }
		});
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
    	        Bundle b = new Bundle();
                TextView tv_name = (TextView) findViewById(R.id.name_value);
                String s_name = tv_name.getText().toString();
                b.putString("UPDATE_KEY_NAME", s_name);
                	
                TextView tv_number = (TextView) findViewById(R.id.number_value);
                String s_number = tv_number.getText().toString();
                b.putString("UPDATE_KEY_NUMBER", s_number);

            	Intent intent = new Intent(context, EditUser.class);
            	intent.putExtras(b);
                startActivity(intent);
                
            case R.id.action_refresh:            	
            	matcher = new FindMatch();
            	boolean found_match = matcher.findMatchEitherWay();
            	
            	//Now if true then display the View Matches button
            	if(found_match)
            	{
                    Button view_match = (Button) findViewById(R.id.view_match_button);
                    view_match.setEnabled(true);
                    view_match.setVisibility(View.VISIBLE);
            	}
            	//Disable button
            	else
            	{
                    Button view_match = (Button) findViewById(R.id.view_match_button);
                    view_match.setEnabled(false);
                    view_match.setVisibility(View.INVISIBLE);
                    
                    match_name = "unknown";
                    match_number = "unknown";
            	}
            	
            	return true;
                
            case R.id.action_logout:
            	new AlertDialog.Builder(context).setTitle("Log out")
        	    .setMessage("Are you sure you want to exit?")
        	    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
        	        public void onClick(DialogInterface dialog, int which) { 
                    	ParseUser.logOut();
                    	System.exit(0);
        	        }
        	     }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	           }
        	     })
        	     .show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void setDetailsString() {
    	ParseQuery<ParseObject> requests = ParseQuery.getQuery("Requests");
		ParseQuery<ParseObject> offers = ParseQuery.getQuery("Offers");
    	
		try {
			int numRequests = requests.count();
			int numOffers = offers.count();
			
	    	String current_details = getString(R.string.current_details_1) + " " +
	        		numOffers + " " + getString(R.string.current_details_2) +
	        		" " + numRequests + " " + getString(R.string.current_details_3);
	        
	        TextView txt = new TextView(this);
	        txt = (TextView) findViewById(R.id.home_current_update);
	        txt.setTypeface(null, Typeface.ITALIC);
	        txt.setText(current_details);
		} catch (ParseException e) {
			// TODO: handle query failure
		}
    }
    
    public void deleteEntryOnClickHandler(final View v) {
    	final RelativeLayout parentRow = (RelativeLayout) v.getParent();
        TextView child = (TextView) parentRow.getChildAt(0);
    	final String objectId = child.getTag(R.id.ENTRY_ID).toString();
    	final Type type = (Type) child.getTag(R.id.ENTRY_TYPE);
    	
    	String msg = "";

    	if(type == Type.REQUEST ) {
    		msg = "Are you sure you want to delete this request?";
    	} else {
    		msg = "Are you sure you want to delete this offer?";
    	}
    	
    	new AlertDialog.Builder(context).setTitle("Delete entry")
	    .setMessage(msg)
	    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	parentRow.setVisibility(View.GONE);
	        	// TODO: update listview height
	        	
	        	if(type == Type.REQUEST)
	        		ParseObject.createWithoutData("Requests", objectId).deleteEventually();
	        	else
	        		ParseObject.createWithoutData("Offers", objectId).deleteEventually();
	        	setDetailsString();
	        	
	        }
	     }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	           }
	     })
	     .show();
    }
    
	public void addListenerOnButtons() {
		 
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
		
		Button view_match_button = (Button) findViewById(R.id.view_match_button);
		view_match_button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				
				if(matcher.getName() == null)
				{
					match_name = "unknown";
					match_number = "unknown";
				}
					
				match_name = matcher.getName();
				match_number = matcher.getNumber();
				
				//Get name and number of matched user and display
				AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Match Details").setMessage("Name: " + match_name + 
                											"\nNumber: " + match_number);

                builder.setNegativeButton("Thanks", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id){}
                });

                AlertDialog dialog = builder.create();

                dialog.show();				
			}
		});
	}
}
