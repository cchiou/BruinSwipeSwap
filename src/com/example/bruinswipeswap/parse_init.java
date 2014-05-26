package com.example.bruinswipeswap;

import com.parse.Parse;
import android.app.Application;

public class parse_init extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "TknZnYwU5lvjuaiDftIATe6UMNpjsQD8EqiWPtwh", "OcxyFi2dFos1wGgezXh7Z2cdH0P5L4CUsffg2EK6");
	}
}
