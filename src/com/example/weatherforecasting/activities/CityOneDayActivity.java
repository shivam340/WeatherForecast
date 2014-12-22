package com.example.weatherforecasting.activities;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.weatherforecasting.R;
import com.example.weatherforecasting.adapters.CityOneDayActivityAdapter;
import com.example.weatherforecasting.utilities.App;

public class CityOneDayActivity extends Activity  {

	CityOneDayActivityAdapter mAdapter;
	ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) 	{
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_city_activity);

		
		int position=getIntent().getIntExtra("position",-1);
		
		if(position != -1) {
	
		mAdapter = new CityOneDayActivityAdapter(CityOneDayActivity.this, MainActivity.sForcastModels.get(CityWeatherActivity.sPosition).getDataModels());
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setCurrentItem(position);
		
		}
		else {
			App.Log(App.D, "position  is ","-1");
		}
	}

}