package com.service.xtracare;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;	
	String mTitle="";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 getWindow().setSoftInputMode(
	                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		mTitle = (String) getTitle();		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		mDrawerToggle = new ActionBarDrawerToggle(	this, 
													mDrawerLayout, 
													R.drawable.ic_drawer, 
													R.string.drawer_open,
													R.string.drawer_close){
			
			/** Called when drawer is closed */
            public void onDrawerClosed(View view) {
            	InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            	getActionBar().setTitle(mTitle);
            	invalidateOptionsMenu();
                
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
            	InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
			
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getBaseContext(), 
					R.layout.drawer_list_item  , 
					getResources().getStringArray(R.array.options) 
				);
		mDrawerList.setAdapter(adapter);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		HomeActivity rFragment = new HomeActivity();
		Bundle data = new Bundle();
		data.putInt("position", 0);
		rFragment.setArguments(data);
		FragmentManager fragmentManager  = getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.content_frame, rFragment);
		ft.commit();
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent,
							View view,
							int position,
							long id) {			
				String[] options = getResources().getStringArray(R.array.options);
				mTitle = options[position];				
				
				if(mTitle.equalsIgnoreCase("Service Booking")){
					ServiceBookingFragment rFragment = new ServiceBookingFragment();
					Bundle data = new Bundle();
					data.putInt("position", position);
					rFragment.setArguments(data);
					FragmentManager fragmentManager  = getFragmentManager();
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.content_frame, rFragment);
					ft.commit();
				}else if(mTitle.equalsIgnoreCase("Feedback/Query")){
					FeedbackActivity rFragment = new FeedbackActivity();
					Bundle data = new Bundle();
					data.putInt("position", position);
					rFragment.setArguments(data);
					FragmentManager fragmentManager  = getFragmentManager();
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.content_frame, rFragment);
					ft.commit();
				}else {
					HomeActivity rFragment = new HomeActivity();
					Bundle data = new Bundle();
					data.putInt("position", position);
					rFragment.setArguments(data);
					FragmentManager fragmentManager  = getFragmentManager();
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.content_frame, rFragment);
					ft.commit();
				}
				
				
				// Closing the drawer
				mDrawerLayout.closeDrawer(mDrawerList);				
				
			}
		});	
	}
	 @Override
	    public void onBackPressed() {
	      	         finish();
	    }
	
	 @Override
	 protected void onPostCreate(Bundle savedInstanceState) {
		 super.onPostCreate(savedInstanceState);	     
	     mDrawerToggle.syncState();	
	 }
	
	/** Handling the touch event of app icon */
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {     
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	
	
}
