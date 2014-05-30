package com.example.bruinswipeswap;

import java.text.SimpleDateFormat;

import com.parse.ParseException;
import com.parse.ParseObject;

enum Type {
	REQUEST, OFFER
}
public class Entry {
	public String id;
	public String description;
	public Type type;

	public Entry(ParseObject obj) {
        this.id = obj.getObjectId();
	}
	
	protected String buildEntryDescription(ParseObject obj) throws ParseException {
		System.out.println(obj.get("beginTime"));
		String description = obj.get("numSwipes").toString() + " swipe(s)";
		if( Boolean.parseBoolean(obj.get("anytimeToday").toString())) {
			description = description.concat(" today");
			return description;
		}
		else {
			description = description.concat(" at ");
			description = description.concat(formatDate(obj.get("beginTime").toString()));
		}
		
		if(!(obj.get("beginTime").toString()).equals(obj.get("endTime").toString())){
			description = description.concat(" - ");
			description = description.concat(formatDate(obj.get("endTime").toString()));
		}
        return description;		
	}
	
    private static String formatDate(String inputTimeStamp) throws ParseException {
        try {
			return new SimpleDateFormat("H:mm a").format(new SimpleDateFormat(
					"EEE MMM d H:mm:ss zzz yyyy").parse(inputTimeStamp)).toString();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    }
}
