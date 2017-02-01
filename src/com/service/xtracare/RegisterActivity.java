package com.service.xtracare;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class RegisterActivity extends Activity {
	EditText fname ;
	EditText email ;
    EditText mobile ;
    EditText lname ;
    Button mainView ;
    DBUtil mydb;
    HttpResponse response = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        fname = (EditText) findViewById(R.id.reg_firstname);
        email = (EditText) findViewById(R.id.reg_email);
        mobile = (EditText) findViewById(R.id.reg_mobile);
        lname = (EditText) findViewById(R.id.reg_lastname);
        mainView = (Button) findViewById(R.id.btnRegister);
        mydb = new DBUtil(getApplicationContext());
        // Listening to Login Screen link
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()   
        .detectDiskReads()   
        .detectDiskWrites()   
        .detectNetwork()   // or .detectAll() for all detectable problems   
        .penaltyLog()   
        .build());  
        mainView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
            	if(!fname.getText().toString().equalsIgnoreCase("") && !lname.getText().toString().equalsIgnoreCase("")
            				&& android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() && android.util.Patterns.PHONE.matcher(mobile.getText().toString()).matches() ){
            			JSONObject param = new JSONObject();
						JSONObject serviceData = new JSONObject();
	    				try {
	    					serviceData.put("fname", fname.getText().toString());
	            			serviceData.put("emailId", email.getText().toString());
	            			serviceData.put("mobileNumber", mobile.getText().toString());
	            			serviceData.put("lname", lname.getText().toString());
	    					param.put("Service", 1);
		    				param.put("Request", serviceData);
		    				param.put("Context", getApplicationContext());
		    			} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    				new ConnectionAsyncTask().execute(param);
	        	}else{
            		Toast.makeText(getApplicationContext(), "Please enter proper details!", Toast.LENGTH_SHORT).show();
            	}
                
            }

        });
        
        
    }
}