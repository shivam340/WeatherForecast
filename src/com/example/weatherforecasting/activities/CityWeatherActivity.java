package com.example.weatherforecasting.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherforecasting.R;
import com.example.weatherforecasting.adapters.SimpleCityAdapter;
import com.example.weatherforecasting.models.DataModel;
import com.example.weatherforecasting.utilities.App;

/**
 *  Displays weather details for 14 days
 * @author shivam
 */
public class CityWeatherActivity  extends Activity {

	private TextView mTextCityName;
	private ListView mListDetails;
	public static DataModel sData;
	public static int sPosition=0, sInnerPosition=0;

	@Override
	public void onCreate(Bundle savedData) {

		super.onCreate(savedData);

		setContentView(R.layout.activity_weather_details);

		int position = getIntent().getIntExtra("position", -1);
		App.Log(App.D,"wheather city position  is", ""+position);


		if(position!=-1) {

			CityWeatherActivity.sPosition = position;
			getUi(position);
		}
		else {

			Toast.makeText(getApplicationContext(), "something went wrong",Toast.LENGTH_SHORT).show();
			CityWeatherActivity.this.finish();
		}
	}


	public void getUi(final int position) {

		mTextCityName = (TextView) findViewById(R.id.txt_city_name);
		mListDetails= (ListView) findViewById(R.id.list_city_whether);

		mTextCityName.setText(""+MainActivity.sForcastModels.get(position).getName());

		App.Log(App.D,"data model size is ", ""+MainActivity.sForcastModels.get(position).getDataModels().size());

		SimpleCityAdapter adapter = new SimpleCityAdapter(getApplicationContext(), R.layout.weather_details, MainActivity.sForcastModels.get(position).getDataModels());

		mListDetails.setAdapter(adapter);

		mListDetails.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int innnerPosition, long id) {

				CityWeatherActivity.sInnerPosition= innnerPosition;
				sData = MainActivity.sForcastModels.get(position).getDataModels().get(innnerPosition);

					Intent intent = new Intent(CityWeatherActivity.this, CityOneDayActivity.class);
					intent.putExtra("position", innnerPosition);
				
					startActivity(intent);
			}
		});

	}

}