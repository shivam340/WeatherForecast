package com.example.weatherforecasting.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.weatherforecasting.R;
import com.example.weatherforecasting.utilities.App;
import com.example.weatherforecasting.utilities.ConnectionDetector;

public class MainActivity extends Activity {


	private Button mButtonShowData;
	private EditText mEditTextCityNames;
	private ListView mList;
	private ArrayList<String> mCityItems  = new ArrayList<String>();
	private ArrayAdapter<String> mAdapter; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUi();

	}// end of onCreate



	private void initUi() {

		mButtonShowData = (Button) findViewById(R.id.btn_main_show_data);
		mEditTextCityNames = (EditText) findViewById(R.id.edt_main_city_names);
		mList  = (ListView) findViewById(R.id.list_main_data);
		
		mButtonShowData.setOnClickListener(new HandleEventOnClick(R.id.btn_main_show_data));
		mList.setOnItemClickListener(new HandleEventOnItemClick(R.id.list_main_data));
		
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mCityItems);
			
		mList.setAdapter(mAdapter);
		
		
	}// end of initUi



	private class HandleEventOnClick implements View.OnClickListener {

		private int mId = 0;

		public HandleEventOnClick(int mId) {

			this.mId = mId;

		}// end of constructor

		@Override
		public void onClick(View view) {

			switch (mId) {
		
			case R.id.btn_main_show_data:

					if ( checkConditions() ) {
						
						// fetch weather data here
					
					}
					else {
						
						App.Log(App.D, "criteria does not satisfied" ,"unable to fetch weather information");
					}
						

				break;

			default:
				break;
			
			}// end of switch cases
		
		}// end of onClick

		
		private boolean checkConditions() {
			
			String data = mEditTextCityNames.getText().toString();
			
			if(data != null) {
			
				if(data.trim().length()>0) {
					
					String cityNames[] = data.split(",");
					App.Log(App.D,"city names are",""+data);
					
					if(cityNames!=null) {
					
						ConnectionDetector cd=new ConnectionDetector(getApplicationContext());
						boolean isInternetPresent = cd.isConnectedToInternet();

						if (isInternetPresent) {
							
							//everything is ok.
							return true;
						} 
						else {
							App.ShowAlertDialog(MainActivity.this, "No Internet Connection","You don't have internet connection.", false);
						}

					}  //end of if cityNames!=null
				
				} // end of if data.length > 0
				
				else {
					
					Toast.makeText(getApplicationContext(), "Please fill up data properly.", Toast.LENGTH_SHORT).show();
				}
			
			} //end of if data != null
			
			else {
				Toast.makeText(getApplicationContext(), "Please Enter at least one city name.", Toast.LENGTH_SHORT).show();
			}

			
			return false;
			
		}
		
		
	}  // end of class HandleEventOnClick

	
	
	private class HandleEventOnItemClick implements AdapterView.OnItemClickListener {

		private int mId = 0;

		public HandleEventOnItemClick(int mId) {

			this.mId = mId;

		}// end of constructor

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			
			switch (mId) {
			
			case R.id.list_main_data:
				
				
				break;

			default:
				break;
			}// end of switch case
			
			
		} // end of onItemClick()
	
		
	}  // end of class HandleOnItemClick
	
	
} // end of MainActivity