package com.service.xtracare;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceBookingFragment extends Fragment implements OnItemSelectedListener{
	
	Spinner servicespinner, modelspinner, pickDropSpinner;
	EditText desc,mobile,email;
	HttpResponse response = null;
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		int position = getArguments().getInt("position");
		
		// List of rivers
		String[] options = getResources().getStringArray(R.array.options);
		DataObj dbdata = DataObj.getData();
		View v = inflater.inflate(R.layout.fragment_layout, container, false);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setHasOptionsMenu(true); 
		servicespinner = (Spinner) v.findViewById(R.id.ms_typeofservice);
		ArrayAdapter<String> serviceadapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dbdata.Service){
	            @Override
	            public boolean isEnabled(int position){
	                if(position == 0){
	                	
	                    return false;
	                }else{
	                    return true;
	                }
	            }
	            @Override
	            public View getDropDownView(int position, View convertView,
	                                        ViewGroup parent) {
	                View view = super.getDropDownView(position, convertView, parent);
	                TextView tv = (TextView) view;
	                if(position == 0){
	                    tv.setTextColor(Color.GRAY);
	                }else {
	                    tv.setTextColor(Color.BLACK);
	                }
	                return view;
	            }
		};
		
		serviceadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		servicespinner.setAdapter(serviceadapter);
		
		modelspinner = (Spinner) v.findViewById(R.id.ms_carmodel);
		ArrayAdapter<String> modeladapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dbdata.Model){
			 @Override
	            public boolean isEnabled(int position){
	                if(position == 0){
	                    return false;
	                }else{
	                    return true;
	                }
	            }
	            @Override
	            public View getDropDownView(int position, View convertView,
	                                        ViewGroup parent) {
	                View view = super.getDropDownView(position, convertView, parent);
	                TextView tv = (TextView) view;
	                if(position == 0){
	                    tv.setTextColor(Color.GRAY);
	                }else {
	                    tv.setTextColor(Color.BLACK);
	                }
	                return view;
	            }
		};
		modeladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		   modelspinner.setAdapter(modeladapter);
		   
		    pickDropSpinner = (Spinner) v.findViewById(R.id.ms_pickdrop);
			ArrayAdapter<String> pickDropadapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dbdata.PickDrop){
				 @Override
		            public boolean isEnabled(int position){
		                if(position == 0){
		                    return false;
		                }else{
		                    return true;
		                }
		            }
		            @Override
		            public View getDropDownView(int position, View convertView,
		                                        ViewGroup parent) {
		                View view = super.getDropDownView(position, convertView, parent);
		                TextView tv = (TextView) view;
		                if(position == 0){
		                    tv.setTextColor(Color.GRAY);
		                }else {
		                    tv.setTextColor(Color.BLACK);
		                }
		                return view;
		            }
			};
			pickDropadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			pickDropSpinner.setAdapter(pickDropadapter);
		   
			
		// Getting reference to the TextView of the Fragment
		desc = (EditText) v.findViewById(R.id.ms_desc);
		mobile = (EditText) v.findViewById(R.id.ms_mobile);
		email = (EditText) v.findViewById(R.id.ms_emailid);
		
		JSONObject user = dbdata.User;
		getActivity().getActionBar().setTitle(options[position]);
		
		return v;
	}
	public void onItemSelected(AdapterView<?> parent, View view,
            int pos, long id) {
		
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,inflater);
        
        menu.findItem(R.id.action_settings).setOnMenuItemClickListener(new OnMenuItemClickListener() {
    		@Override
    		public boolean onMenuItemClick(MenuItem item) {
    			if(!desc.getText().toString().equalsIgnoreCase("")
    					&& android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() && android.util.Patterns.PHONE.matcher(mobile.getText().toString()).matches()){
    				JSONObject serviceData = new JSONObject();
    				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    				String currentDateandTime = sdf.format(new Date());
    				DataObj data = DataObj.getData();
    				JSONObject param = new JSONObject();
    				try {
    					serviceData.put("carModel", modelspinner.getSelectedItem().toString());
						serviceData.put("serviceType", servicespinner.getSelectedItem().toString());
						serviceData.put("serviceDesc", desc.getText().toString());
	    				serviceData.put("mobileNbr", mobile.getText().toString());
	    				serviceData.put("serviceDate", currentDateandTime);
	    				serviceData.put("emailId", email.getText().toString());
	    				serviceData.put("isPickNDrop", pickDropSpinner.getSelectedItem().toString());
	    				serviceData.put("userId", data.User.get("userId").toString());
	    				param.put("Service", 3);
	    				param.put("Request", serviceData);
	    				param.put("Context", getActivity());
	    				
	    				
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				new ConnectionAsyncTask().execute(param);	
    			}else{
    				Toast.makeText(getActivity(), "Please provide proper information!", Toast.LENGTH_LONG).show();
    			}
    			return true;
    		}
    			
    	});
    }
}