package com.service.xtracare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ConnectionAsyncTask extends AsyncTask<JSONObject, Integer, Double>{
	HttpResponse response = null;
	Context context;
	int service = 0;
	ProgressBar pb;
	@Override
	protected Double doInBackground(JSONObject... params) {
		
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, DataObj.Connectiontimeout);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpPost postRequest = null;
		try {
			
			service = (Integer) params[0].get("Service");
			context = (Context) params[0].get("Context");
			
				if(!NetworkUtil.isNetworkAvailable(context)){
					Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_LONG).show();
				}else{
					switch(service){
						case 1: //register
							postRequest = new HttpPost(DataObj.Register);
							 break;
						case 2: //login
							 postRequest = new HttpPost(DataObj.Login);
							 break;
						case 3: //service
							postRequest = new HttpPost(DataObj.ServiceBooking);
							 break;
						default:
							break;
					}
					
						postRequest.setEntity(new StringEntity(params[0].get("Request").toString()));
						postRequest.setHeader("Content-Type", "application/json");
						response = httpClient.execute(postRequest);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClientProtocolException ce) {
				ce.printStackTrace();
			}catch(JSONException ex){
				ex.printStackTrace();
			}catch(Exception exc){
				exc.printStackTrace();	
			}
		
		return null;
	} 
	protected void onPostExecute(Double result){
		try {
			if(response != null){
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String json = reader.readLine();
				JSONObject token = new JSONObject(json);
				switch(service){
				case 1: //register
					 onRegister(token);
					 break;
				case 2: //login
					onLogin(token);
					 break;
				case 3: //service
					onServiceBooking(token);
					 break;
				default:
					break;
				}
			}else{
				 Toast.makeText(context, "Server error", Toast.LENGTH_LONG).show();
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void onRegister(JSONObject token) {
		// TODO Auto-generated method stub
		try {
			if(token.get("responseCode").equals("100")){ // to be removed
				DBUtil mydb = new DBUtil(context);
		   	    mydb.insertUser(token.get("name").toString(),token.get("userId").toString());
		   	    JSONObject param = new JSONObject();
				JSONObject serviceData = new JSONObject();
					serviceData.put("userId", token.get("userId").toString());
					param.put("Service",2);
					param.put("Request", serviceData);
					param.put("Context", context);
					
				
				new ConnectionAsyncTask().execute(param);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void onLogin(JSONObject token) {
		// TODO Auto-generated method stub
		try {
			if(token.get("responseCode").equals("100")){ // to be removed
					 DataObj dbdata = DataObj.getData();
					 JSONObject user = new JSONObject();
					 user.put("name", token.get("name").toString());
					 user.put("userId", token.get("userId").toString());
					 user.put("email",  token.get("emailId").toString());
					 user.put("mobile" ,token.get("mobileNumber").toString());
					 JSONArray js_Service = (JSONArray)token.get("serviceType");
					 String[] service = new String[js_Service.length()];
					 service[0] = "Types of Service";
					 for(int i = 1, count = js_Service.length(); i<= count; i++)
					 {
					     try {
					    	 service[i] = js_Service.getString(i);
					     }
					     catch (JSONException e) {
					         e.printStackTrace();
					     }
					 }
					 JSONArray js_Model = (JSONArray)token.get("carModels");
					 String[] carmodel = new String[js_Model.length()];
					 carmodel[0] = "Car Model";
					 for(int i = 1, count = js_Model.length(); i<= count; i++)
					 {
					     try {
					    	 carmodel[i] = js_Model.getString(i);
					     }
					     catch (JSONException e) {
					         e.printStackTrace();
					     }
					 }
					 
					 dbdata.setData(user,new JSONObject(token.get("ownerDetails").toString()),carmodel,service);
	            	 Intent i = new Intent(context, MainActivity.class);
	            	 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            	 context.startActivity(i);
//	            	 ((Activity) context).finish();
	                 
			}else{
				 Intent i = new Intent(context, RegisterActivity.class);
				 context.startActivity(i);
				 Toast.makeText(context, token.get("responseMessage").toString(), Toast.LENGTH_LONG).show();
					
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	
	}
	private void onServiceBooking(JSONObject token) {
		// TODO Auto-generated method stub	
		try {
			if(token.get("responseCode").toString().equalsIgnoreCase("100") && !token.get("bookignId").toString().equalsIgnoreCase("")){
				new AlertDialog.Builder(context)
			    .setTitle("Booking Id")
			    .setMessage(token.get("bookignId").toString())
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_info)
			    .show();
			}
			Toast.makeText(context, token.get("responseMessage").toString(), Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void onPreExecute(){

	}
	protected void onProgressUpdate(Integer... progress){
		
	}
}