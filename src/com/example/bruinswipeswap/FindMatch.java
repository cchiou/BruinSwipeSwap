package com.example.bruinswipeswap;

import java.util.Date;
import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;

public class FindMatch {

	private String match_name;
	private String match_number;
	private List<ParseObject> offers;
	private List<ParseObject> requests;
	
	//Constructor initialize found_match bool to false
	public FindMatch()
	{
		Log.d("TREVOR", "initialize");
	}
	
	public String getName()
	{
		return match_name;
	}
	
	public String getNumber()
	{
		return match_number;
	}
	
	public boolean findMatchEitherWay()
	{
		Log.d("TREVOR", "findMatchEitherWay");
		if(findOfferMatch())
			return true;
		else
			return findRequestMatch();
	}
	
	//One of the functions to run when refresh is called after initialization
	//Here we find requests by me and match them to other offers
	private boolean findOfferMatch() {
	
		//Query all objects in offers by users other than me
		ParseQuery<ParseObject> query_else = ParseQuery.getQuery("Offers");
		Log.d("TREVOR", "hey");
		query_else.whereNotEqualTo("userId", (String) ParseUser.getCurrentUser().getUsername());
		Log.d("TREVOR", "hey2");
		/*query_else.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> offer_list, ParseException e) {
		    	Log.d("TREVOR", "uhh good? bad?");
		        if (e == null) {
		            Log.d("Offers", "Retrieved " + offer_list.size() + " offers");
		            offers = offer_list;
		        } else {
		            Log.d("Offers", "Error: " + e.getMessage());
		        }
		        Log.d("TREVOR", "ok");
		    }
		});*/
		try {
			offers = query_else.find();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.d("TREVOR", "I have offers: " + offers.size());
		
		//Query all of my requests
		ParseQuery<ParseObject> query_mine = ParseQuery.getQuery("Requests");
		query_mine.whereEqualTo("userId", (String)ParseUser.getCurrentUser().getUsername());
		/*query_mine.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> request_list, ParseException e) {
		        if (e == null) {
		            Log.d("Requests", "Retrieved " + request_list.size() + " requests");
		            requests = request_list;
		        } else {
		            Log.d("Requests", "Error: " + e.getMessage());
		        }
		    }
		});*/
		
		try {
			requests = query_mine.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("TREVOR", "I have requests: " + requests.size());
		
		Log.d("TREVOR", "I am calling runMatcher!");
		//Run the matcher for the two lists
		return runMatcher(offers, requests, true);
	}
	
	//I am offering and looking to match with requests
	private boolean findRequestMatch() {
		
		//Query all objects in requests by users other than me
		ParseQuery<ParseObject> query_else = ParseQuery.getQuery("Requests");
		query_else.whereNotEqualTo("userId", (String) ParseUser.getCurrentUser().getUsername());
		/*query_else.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> request_list, ParseException e) {
		        if (e == null) {
		            Log.d("Requests", "Retrieved " + request_list.size() + " requests");
		            requests = request_list;
		        } else {
		            Log.d("Requests", "Error: " + e.getMessage());
		        }
		    }
		});*/
		try {
			requests = query_else.find();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.d("TREVOR", "I have requests: " + requests.size());
		
		//Query all of my offers
		ParseQuery<ParseObject> query_mine = ParseQuery.getQuery("Offers");
		query_mine.whereEqualTo("userId", (String)ParseUser.getCurrentUser().getUsername());
		/*query_mine.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> offer_list, ParseException e) {
		        if (e == null) {
		            Log.d("Offers", "Retrieved " + offer_list.size() + " offers");
		            offers = offer_list;
		        } else {
		            Log.d("Offers", "Error: " + e.getMessage());
		        }
		    }
		});*/
		
		try {
			offers = query_mine.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("TREVOR", "I have offers: " + offers.size());
		
		Log.d("TREVOR", "I am calling runMatcher");
		//Run the matcher for the two lists
		return runMatcher(requests, offers, false);
	}
	
	//Find matches between a list of my requests/offers and others offers/requests
	private boolean runMatcher(List<ParseObject> others_list, List<ParseObject> my_list, 
																boolean i_am_requesting)
	{
		
		//Iterate through my own requests/offers
		for(int i = 0; i < my_list.size(); i++)
		{
			//Obtain the important matching parameters from my objects
			int number_swipes = (Integer) my_list.get(i).getNumber("numSwipes");
			Date my_time_begin = my_list.get(i).getDate("beginTime");
			Date my_time_end = my_list.get(i).getDate("endTime");
			
			//Now iterate through the others offers/requests and
			//find any that match my parameters
			for(int j = 0; j < others_list.size(); j++)
			{
				//I am requesting swipes
				if(i_am_requesting)
				{
					//They are not offering enough swipes
					if(number_swipes > (Integer) others_list.get(i).getNumber("numSwipes"))
						continue;
					else
					{
						//In these two cases the times do not match up
						if(my_time_begin.after(others_list.get(i).getDate("endTime")) ||
							my_time_end.before(others_list.get(i).getDate("beginTime")))
								continue;
						else
						{
							//found a match, get info and return true
							obtainMatchedUserInfo(others_list.get(i));
							return true;
						}
					}
				}
				//I am offering swipes
				else
				{
					//They are requesting more swipes than I am offering
					if(number_swipes < (Integer) others_list.get(i).getNumber("numSwipes"))
						continue;
					else
					{
						//In these two cases the times do not match up
						if(my_time_begin.after(others_list.get(i).getDate("endTime")) ||
							my_time_end.before(others_list.get(i).getDate("beginTime")))
								continue;
						else
						{
							obtainMatchedUserInfo(others_list.get(i));
							return true;
						}
					}
				}
			}
		}
		
		Log.d("TREVOR", "found nothing");
		//found nothing
		return false;
	}
	
	private void obtainMatchedUserInfo(ParseObject matched)
	{
		String username = matched.getString("userId");
		Log.d("TREVOR", "userId " + username);
		ParseQuery query_user = ParseUser.getQuery();
		query_user.whereEqualTo("username", username);
		query_user.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> user, ParseException e) {
		        if (e == null) {
		            Log.d("Users", "Retrieved user " + user.get(0).getString("name"));
		            Log.d("Users", "Retrieved user " + user.get(0).getString("number"));
		            match_name = user.get(0).getString("name");
		            match_number = user.get(0).getString("number");
		        } else {
		            Log.d("Users", "Error: " + e.getMessage());
		        }
		    }
		});
		
		/*if(user_list == null)
			Log.d("TREVOR", "didn't work");
		Log.d("TREVOR", "name " + user_list.getString("name"));
		
		if(user_list != null)
		{
			Log.d("TREVOR", "good!");
			match_name = user_list.getString("name");
			match_number = user_list.getString("number");
		}*/
	}
}
