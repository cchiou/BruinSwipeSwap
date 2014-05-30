package com.example.bruinswipeswap;

import com.parse.ParseException;
import com.parse.ParseObject;

public class Request extends Entry {

	public Request(ParseObject obj) throws ParseException {
		super(obj);
		this.description = buildDescription(obj);
		this.type = Type.REQUEST;
	}
	
	public String buildDescription(ParseObject obj) throws ParseException {
		String description = "Requesting ";
		description = description.concat(super.buildEntryDescription(obj));
		return description;
	}
}
