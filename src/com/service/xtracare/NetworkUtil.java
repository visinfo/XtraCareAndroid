package com.service.xtracare;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

	 public static boolean isNetworkAvailable(Context context) {
		    ConnectivityManager connectivityManager  = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	public static boolean isServerAvailable(Context context) {
		    ConnectivityManager connectivityManager  = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
