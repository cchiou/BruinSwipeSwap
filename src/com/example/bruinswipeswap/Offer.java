package com.example.bruinswipeswap;

import com.parse.ParseException;
import com.parse.ParseObject;

public class Offer extends Entry {
	public Offer(ParseObject obj) throws ParseException {
		super(obj);
		this.description = buildDescription(obj);
		this.type = Type.OFFER;
	}
	
	public String buildDescription(ParseObject obj) throws ParseException {
		String description = "Offering ";
		description = description.concat(super.buildEntryDescription(obj));
		return description;
	}
}
