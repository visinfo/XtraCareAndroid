package com.service.xtracare;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.webkit.WebView;
 
public class SplashScreen extends Activity {

    DBUtil mydb = null;
    HttpResponse response = null;
    JSONObject user_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()   
        .detectDiskReads()   
        .detectDiskWrites()   
        .detectNetwork()   // or .detectAll() for all detectable problems   
        .penaltyLog()   
        .build());  
        mydb = new DBUtil(getApplicationContext());
        user_db= mydb.getUserData();
        
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
        	  try {
      			if(user_db != null && !user_db.get("userId").toString().equalsIgnoreCase("")){
      						JSONObject param = new JSONObject();
      						JSONObject serviceData = new JSONObject();
      	    				try {
      	    					serviceData.put("userId", user_db.get("userId").toString());
      	    					param.put("Service", 2);
      		    				param.put("Request", serviceData);
      		    				param.put("Context", getApplicationContext());
      		    				
      						} catch (JSONException e) {
      							// TODO Auto-generated catch block
      							e.printStackTrace();
      						}
      	    				new ConnectionAsyncTask().execute(param);	
      			 }else{
      				 Intent i = new Intent(SplashScreen.this, RegisterActivity.class);
      	             startActivity(i);
      	             finish();
      			 }
      		} catch (JSONException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      		}
          }
        }, 2000);
   	    
    }

   
}