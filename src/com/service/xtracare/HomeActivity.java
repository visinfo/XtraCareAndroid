package com.service.xtracare;

import org.json.JSONException;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeActivity extends Fragment{
	TextView ownerInfo;
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int position = getArguments().getInt("position");
		String[] options = getResources().getStringArray(R.array.options);
		View v = inflater.inflate(R.layout.home_layout, container, false);
		getActivity().getActionBar().setTitle(options[position]);
		
		ownerInfo = (TextView) v.findViewById(R.id.ownerinfo);
		try {
			ownerInfo.setText(DataObj.getData().Owner.getString("email")+" | "+DataObj.getData().Owner.getString("contactNumber")
						+"\n"+DataObj.getData().Owner.getString("companyName")
						+"\n"+DataObj.getData().Owner.getString("address"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}
}