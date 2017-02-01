package com.service.xtracare;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends Fragment{
	
	EditText query ;
	TextView ownerinfo;
	Button submit ;
	TextView ownerInfo,ownerCompany,ownerAddress;
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		// Retrieving the currently selected item number
		int position = getArguments().getInt("position");
		
		// List of rivers
		String[] options = getResources().getStringArray(R.array.options);
		
		// Creating view correspoding to the fragment
		View v = inflater.inflate(R.layout.contact_layout, container, false);
		
		// Getting reference to the TextView of the Fragment
		query = (EditText) v.findViewById(R.id.cs_query);
		ownerInfo = (TextView) v.findViewById(R.id.ownerinfo);
		try {
			ownerInfo.setText(DataObj.getData().Owner.getString("email") +" | "+DataObj.getData().Owner.getString("contactNumber")
					+"\n"+DataObj.getData().Owner.getString("companyName")
					+"\n"+DataObj.getData().Owner.getString("address"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		submit = (Button) v.findViewById(R.id.cs_submit);//	 
		submit.setOnClickListener(new View.OnClickListener() {
   	 
        public void onClick(View v) {
			if(!query.getText().toString().equalsIgnoreCase("") ){
				final DataObj dbdata = DataObj.getData();
				
				try {
					
					CharSequence option[] = new CharSequence[] {"SMS", "Email"};
	
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setTitle("Feedback/Query: ");
					builder.setItems(option, new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					        try{
						    	switch(which){
						    		case 0:
						    			SmsManager sms = SmsManager.getDefault();
						    			String text = dbdata.Owner.get("companyName").toString()+"| Feedback/Query \n "+query.getText().toString()+"\nBy: "+dbdata.User.get("name")+"("+dbdata.User.get("email")+")";
					    			    sms.sendTextMessage(dbdata.Owner.get("contactNumber").toString(), null,text, null, null);//mobile of owner
					    				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
					    				sendIntent.putExtra("address"  , dbdata.Owner.get("contactNumber").toString());
					    				sendIntent.putExtra("sms_body", text); 
					    				sendIntent.setType("vnd.android-dir/mms-sms");
					    				startActivity(sendIntent);
					    				break;
						    		case 1:
						    			Intent emailIntent = new Intent(Intent.ACTION_SEND);
						    			String textEmail = "Hi! \n\n"+query.getText().toString()+"\n\nBy: "+dbdata.User.get("name")+"("+dbdata.User.get("mobile")+")";
				    					String[] TO = {dbdata.Owner.get("email").toString()};
		    		    			      emailIntent.setData(Uri.parse("mailto:"));
		    		    			      emailIntent.setType("text/plain");
		    		    			      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
		    		    			      emailIntent.putExtra(Intent.EXTRA_SUBJECT, dbdata.Owner.get("companyName").toString()+" || Feedback/Query");
		    		    			      emailIntent.putExtra(Intent.EXTRA_TEXT, textEmail);
		    		    			      
		    		    			      try {
		    		    			         startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		    		    			      } catch (android.content.ActivityNotFoundException ex) {
		    		    			         Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
		    		    			      }
		    		    			    break;
					    			default:
					    				Toast.makeText(getActivity(), "No option selected!", Toast.LENGTH_SHORT).show();
					    				break;
						    			
						    	}
					        }catch(Exception ex){
					        	ex.printStackTrace();
					        }
					    }
					});
					builder.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	}else{
        		Toast.makeText(getActivity(), "Please provide proper information!", Toast.LENGTH_SHORT).show();
        	}
		}

    });
    	
//		// Setting currently selected river name in the TextView
//		tv.setText(options[position]);	
		// Updating the action bar title
		getActivity().getActionBar().setTitle(options[position]);
		
		return v;
	}
	 
	  
}