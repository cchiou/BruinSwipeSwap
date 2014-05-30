package com.example.bruinswipeswap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterItem extends ArrayAdapter<Entry>{
	Context mContext;
	int layoutResourceId;
	Entry entries[] = null;
	
	public ArrayAdapterItem(Context mContext, int layoutResourceId, Entry[] entries) {
		super(mContext, layoutResourceId, entries);
		
		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.entries = entries;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// inflate the layout
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}
		
		// Entry based on position
		Entry entry = entries[position];
		
		// get the Textview and set the text and tag (ID) values
		TextView tv = (TextView) convertView.findViewById(R.id.home_entry_text);
		tv.setText(entry.description);
		tv.setTag(R.id.ENTRY_ID, entry.id);
		tv.setTag(R.id.ENTRY_TYPE, entry.type);

		return convertView;
	}
}
