package com.service.xtracare;

import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DataObj {
	
	private static DataObj dbdata;
	public static JSONObject User;
	public static JSONObject Owner;
	public static String[] Model;
	public static String[] Service;
	public static String[] PickDrop = new String[3];
	public static String Login = "http://anandmotor.co.in:8080/login";
	public static String Register = "http://anandmotor.co.in:8080/register";
	public static String ServiceBooking = "http://anandmotor.co.in:8080/serviceBooking";
	public static int Connectiontimeout = 10000;
	
	
	public static DataObj getData(){
		if(dbdata == null){
			dbdata = new DataObj();
		}
		return dbdata;
	}
	public void setData(JSONObject User,JSONObject Owner,String[] Model,String[] Service){
		DataObj.User = User;
		DataObj.Owner = Owner;
		DataObj.Model = Model;
		DataObj.Service = Service;
		DataObj.PickDrop[0] = "Pick & Drop";
		DataObj.PickDrop[1] = "Yes";
		DataObj.PickDrop[2] = "No";
	}
}