package com.service.xtracare;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUtil extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MOTOR.db";


    public DBUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS USER(username VARCHAR,userId VARCHAR);");
	}
	 public boolean insertUser (String username,String userId) {
		 boolean result = false;
		 SQLiteDatabase db = this.getWritableDatabase();
		 db.delete("USER", "",  new String[] {}); 
			  ContentValues contentValues = new ContentValues();
		      contentValues.put("username", username);
		      contentValues.put("userId", userId);
		      long re = db.insert("USER", null, contentValues);
		      if(re>0){
		    	  result = true;
		      }
	     return result;
	      
	  }
	
	 public JSONObject getUserData() {
		 Cursor resultSet = getReadableDatabase().rawQuery("Select * from USER",null);
		 JSONObject dbDetail = null;
		 	if(resultSet != null && resultSet.moveToFirst())
	        {
		    	String username = resultSet.getString(0);
		    	String userId = resultSet.getString(1);
		    	dbDetail = new JSONObject();
		    	try {
					dbDetail.put("username", username);
					dbDetail.put("userId", userId);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    	
	    	return dbDetail;
	  }
}