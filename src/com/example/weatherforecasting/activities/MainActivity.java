package com.example.weatherforecasting.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherforecasting.R;
import com.example.weatherforecasting.models.DataModel;
import com.example.weatherforecasting.models.ForcastModel;
import com.example.weatherforecasting.models.TemperatureModel;
import com.example.weatherforecasting.models.WhetherModel;
import com.example.weatherforecasting.utilities.App;
import com.example.weatherforecasting.utilities.ConnectionDetector;
import com.example.weatherforecasting.utilities.UsersLocation;

public class MainActivity extends Activity implements LocationListener{


	private Button mButtonShowData = null;
	private EditText mEditTextCityNames = null ;
	private ListView mList = null ;
	private TextView mTextViewListTitle = null;
	private View mViewSep1 = null , mViewSep2 =null;
	private ArrayList<String> mCityItems  = new ArrayList<String>();
	private ArrayAdapter<String> mAdapter = null; 
	private Activity mActivity = null;
	private ArrayList<DataModel> mLocalData = new ArrayList<DataModel>();
	private ArrayList<ForcastModel> mLocalForCastData = new ArrayList<ForcastModel>() ;
	public static ArrayList<ForcastModel> sForcastModels = new ArrayList<ForcastModel>();

	private UsersLocation mLocation = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mActivity = MainActivity.this;

		initUi();

	}// end of onCreate



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);

		switch(item.getItemId()) {

		case R.id.menu_current_city_weather:

			mLocation = new UsersLocation(MainActivity.this, getApplicationContext());
			App.Log(App.D, "Lattitude is ", ""+mLocation.getLatitude());
			App.Log(App.D, "Longitude is ", ""+mLocation.getLongitude());

			if(mLocation!=null )
			{
				if(mLocation.getLatitude()!=-1 && mLocation.getLongitude()!=-1)
				{
					String cityName = "Not Found";                 
					Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());        
					try 
					{  
						List<Address> addresses = gcd.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);  
						if (addresses.size() > 0) 
						{ 
							cityName = addresses.get(0).getLocality();  
							App.Log(App.D, "CityName is ",""+cityName); 
						}
					} catch (IOException e) 
					{                 
						e.printStackTrace();  
					}

					if(!cityName.equalsIgnoreCase("Not Found"))
					{
						mEditTextCityNames.setText(""+cityName);
					
						ConnectionDetector cd=new ConnectionDetector(getApplicationContext());
						boolean isInternetPresent = cd.isConnectedToInternet();

						if (isInternetPresent) {

							new AsyncFetchWeatherData().execute(""+cityName);

							return true;
						} 
						else {
							App.ShowAlertDialog(MainActivity.this, "No Internet Connection","You don't have internet connection.", false);
						}

						
						
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Unable to get location, please try later",Toast.LENGTH_SHORT).show();
				}	
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Unable to get location, please try later",Toast.LENGTH_SHORT).show();
			}


		}


		return true;
	}


	@Override
	public void onLocationChanged(Location location) {


		UsersLocation.sLocation = location;
		UsersLocation.sLatitude = location.getLatitude();
		UsersLocation.sLongitude = location.getLongitude();

		App.Log(App.D,"Location1 is ", ""+location.getLatitude());
		App.Log(App.D,"Location1 is ", ""+location.getLongitude());

		Toast.makeText(getApplicationContext(), "got location.", Toast.LENGTH_SHORT).show();

		mLocation.stopUsingGPS();

	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}



	private void initUi() {

		mButtonShowData = (Button) findViewById(R.id.btn_main_show_data);
		mEditTextCityNames = (EditText) findViewById(R.id.edt_main_city_names);
		mList  = (ListView) findViewById(R.id.list_main_data);
		mTextViewListTitle  = (TextView) findViewById(R.id.txt_main_list_title);
		mViewSep1  = findViewById(R.id.view_main_sep1);
		mViewSep2  = findViewById(R.id.view_main_sep2);

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
							new AsyncFetchWeatherData().execute(cityNames);

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

				Toast.makeText(getApplicationContext(), ""+sForcastModels.get(position).getName(), Toast.LENGTH_SHORT).show();




				Intent intent = new Intent(MainActivity.this, CityWeatherActivity.class);
				intent.putExtra("position",position);
				startActivity(intent);


				break;

			default:
				break;
			}// end of switch case


		} // end of onItemClick()


	}  // end of class HandleOnItemClick



	private class AsyncFetchWeatherData extends AsyncTask<String, Integer, String> {

		private ProgressDialog mProgressDialog = null;

		@Override
		protected void onPreExecute() {


			if(!sForcastModels.isEmpty())
			{
				sForcastModels.clear();
			}

			if(!mCityItems.isEmpty())
			{
				mCityItems.clear();
			}


			if(!mLocalData.isEmpty())
			{
				mLocalData.clear();
			}


			if(!mLocalForCastData.isEmpty())
			{
				mLocalForCastData.clear();
			}


			if(mActivity!=null)
			{
				mProgressDialog = new ProgressDialog(mActivity, ProgressDialog.THEME_HOLO_LIGHT);
				mProgressDialog.setMessage("Please wait ...loading a data");
				mProgressDialog.setCancelable(false);
				mProgressDialog.setCanceledOnTouchOutside(false);
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				mProgressDialog.show();
			}

		} // end of onPreExecute



		@Override
		public void onProgressUpdate(Integer... pa) {


			if(mProgressDialog!=null) {

				mProgressDialog.dismiss();
				mProgressDialog=null;
			}


			try {

				for(int i=0;i<mLocalForCastData.size();i++) {

					if(!sForcastModels.contains(mLocalForCastData.get(i))) {

						sForcastModels.add(mLocalForCastData.get(i));
						mCityItems.add(sForcastModels.get(i).getName());

						mTextViewListTitle.setVisibility(View.VISIBLE);
						mViewSep1.setVisibility(View.VISIBLE);
						mViewSep2.setVisibility(View.VISIBLE);

						if(mAdapter!=null) {

							mAdapter.notifyDataSetChanged();

						}
					}
				}


			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}

		}


		@Override
		protected String doInBackground(String... params) {


			ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext()); 

			for(int i=0;i<params.length;i++) {

				if(params[i].trim().length()>0 && connectionDetector.isConnectedToInternet()) {

					if(download(params[i].trim())) {

						publishProgress(1);

					}
					else {

						App.Log(App.D,"download data"," failed to publish ");
					}

				}
				else {

					App.Log(App.D,"invalid city name at position ", ""+(i+1));
				}

			}


			return "failed";
		} // end of doInBackground


		@Override
		protected void onPostExecute(String result) {

			if(mProgressDialog!=null) {

				mProgressDialog.dismiss();
				mProgressDialog=null;
			}


			for(int i=0;i<mLocalForCastData.size();i++) {

				if(!sForcastModels.contains(mLocalForCastData.get(i))) {

					sForcastModels.add(mLocalForCastData.get(i));
					mCityItems.add(sForcastModels.get(i).getName());

					if(mAdapter!=null) {

						mAdapter.notifyDataSetChanged();
					}
				}
			}

		}  // end of onPostExecute


		/** fetch weather data of given city
		 * @param city name
		 * @return true if data fetched successfully.
		 */
		public boolean download(String city) {

			// initialize http connection
			HttpClient client =new DefaultHttpClient();
			HttpGet get=new HttpGet(App.URL_FORECAST+""+city+"&cnt=14&APPID="+App.API_KEY);

			try {

				HttpResponse response = client.execute(get);
				StatusLine status=response.getStatusLine();

				int code=status.getStatusCode();
				if(code==200) { //ok

					// get data
					StringBuilder builder=new StringBuilder();
					HttpEntity entity=response.getEntity();
					InputStream is=entity.getContent();
					BufferedReader br=new BufferedReader(new InputStreamReader(is));
					String line = null ;

					while((line=br.readLine())!=null) {

						builder.append(line);
					}

					App.Log(App.D," Server response is     "," "+builder);

					String statusPic=builder.toString();
					builder=null;

					if(statusPic.isEmpty()) {
						return false;
					}
					else {

						/*  data in json format will be 
						 * 
						 * {"cod":"200","message":0.0061,
						 * "city":{"id":1259229,"name":"Pune","coord":{"lon":73.855347,"lat":18.519569}
						 * ,"country":"IN","population":0,"sys":{"population":0}},"cnt":14,
						 * "list":[{"dt":1418799600,"temp":{"day":294.99,"min":281.06,"max":294.99,"night":281.06,"eve":293.21,"morn":294.99}
						 * ,"pressure":951.1,"humidity":40,
						 * "weather":[{"id":800,"main":"Clear","description":"sky is clear","icon":"01d"}],"speed":4.36,"deg":77,"clouds":0},
						 * {"dt":1418886000,"temp":{"day":292,"min":276.19,"max":295.35,"night":281.5,"eve":293.38,"morn":276.19}
						 * ,"pressure":953.06,"humidity":39,
						 * "weather":[{"id":800,"main":"Clear","description":"sky is clear","icon":"02d"}],"speed":4.92,"deg":98,"clouds":8},
						 */

						JSONObject jsonData=new JSONObject(statusPic);

						if(jsonData.getString("cod").equals("200")) {

							JSONArray list = jsonData.getJSONArray("list");

							for(int i=0; i<list.length(); i++) {


								DataModel dataModel= insertDataInModel(city,list.getJSONObject(i));

								if(dataModel!=null) {
									mLocalData.add(dataModel);
								}
								else {
									App.Log(App.D, "Download ","No Data  ");
								}

								/*								App.Log(App.D,"list data is "+(i+1),list.getJSONObject(i).toString());
								App.Log(App.D,"whether data is  "+(i+1),list.getJSONObject(i).getJSONArray("weather").toString());
								App.Log(App.D,"temp data is  "+(i+1),list.getJSONObject(i).getJSONObject("temp").toString());
								App.Log(App.D,"temp data is  "+(i+1),list.getJSONObject(i).getJSONObject("temp").toString());
								App.Log(App.D,"temp data is  "+(i+1),list.getJSONObject(i).getJSONObject("temp").toString());
								 */
							}

							App.Log(App.D, " data model 2  size is ",""+mLocalData.size());

							if(!mLocalData.isEmpty()) {

								App.Log(App.D, " data model 2  size is ",""+mLocalData.size());
								ForcastModel forcastModel = new ForcastModel(city, mLocalData);
								mLocalForCastData.add(forcastModel);
								mLocalData.clear();
							}


							return true;

						}  // end of if json cod==200

						else {
							return false;
						}

					}  // end of return data ! = empty

				}   //  end of if http status code ==200

			}  // end of try

			catch (JSONException e) {

				App.Log(App.E, "Error while parsing json Data in download()", ""+e.getMessage());

			}

			catch (ClientProtocolException e) {

				App.Log(App.E, "Error while fetching  Data in download() clientProtcolException. ", ""+e.getMessage());

			} 
			catch (IOException e) {

				App.Log(App.E, "Error while fectching  Data in download() IOException. ", ""+e.getMessage());

			}

			return false;

		} // end of download()


		/**
		 *  takes json object and return DataModel for given city 
		 * @param String name
		 * @param JSONObject data
		 * @return object of DataModel
		 */
		private DataModel insertDataInModel(String Name, JSONObject data) {

			try {

				/*{"day":294.99,"min":281.06,"max":294.99,"night":281.06,"eve":293.21,"morn":294.99}
				 */

				App.Log(App.D, "list data is ",data.toString());

				App.Log(App.D, "whether data is  ",""+data.getJSONArray("weather").toString());
				App.Log(App.D, "temp data is  ",""+data.getJSONObject("temp"));
				/*App.log("temp data is  ",list.getJSONObject(i).getJSONObject("temp").toString());
				App.log("temp data is  ",list.getJSONObject(i).getJSONObject("temp").toString());
				 */
				JSONObject temp = data.getJSONObject("temp");
				TemperatureModel temperatureModel = new TemperatureModel(temp.getDouble("day"), temp.getDouble("night"),temp.getDouble("morn"), temp.getDouble("eve"),temp.getDouble("min"), temp.getDouble("max"));


				App.Log(App.D, "Temp  ",""+temperatureModel.getDay());
				App.Log(App.D, "Temp  ",""+temperatureModel.getNight());
				App.Log(App.D, "Temp  ",""+temperatureModel.getMorn());
				App.Log(App.D, "Temp  ",""+temperatureModel.getEve());
				App.Log(App.D, "Temp  ",""+temperatureModel.getMin());
				App.Log(App.D, "Temp  ",""+temperatureModel.getMax());



				/* [{"id":800,"main":"Clear","description":"sky is clear","icon":"01d"}]
				 */
				JSONArray whetherArray = data.getJSONArray("weather");
				JSONObject whetherObject = whetherArray.getJSONObject(0);

				WhetherModel whetherModel = new WhetherModel(whetherObject.getString("description"), whetherObject.getString("icon"), whetherObject.getString("id"), whetherObject.getString("main"));

				App.Log(App.D, "Wheather  ",""+whetherModel.getDescription());
				App.Log(App.D, "Wheather ",""+whetherModel.getMain());
				App.Log(App.D, "pressure ",""+data.getDouble("pressure"));
				App.Log(App.D, "speed ",""+data.getDouble("speed"));
				App.Log(App.D, "deg ",""+data.getDouble("deg"));
				App.Log(App.D, "clouds ",""+data.getDouble("clouds"));

				/*		12-17 16:59:53.815: D/list data is 1(22878): {"dt":1418799600,"temp":{"day":294.99,"min":281.06,"max":294.99,"night":281.06,"eve":293.21,"morn":294.99},"pressure":951.1,
				 * "humidity":40,"weather":[{"id":800,"main":"Clear","description":"sky is clear","icon":"01d"}],"speed":4.36,"deg":77,"clouds":0}
			12-17 16:59:53.815: D/whether data is  1(22878): [{"id":800,"main":"Clear","description":"sky is clear","icon":"01d"}]
				 */
				DataModel dataModel = new DataModel( temperatureModel, whetherModel, data.getDouble("humidity"), data.getDouble("pressure"),data.getDouble("speed"),data.getDouble("deg"),data.getDouble("clouds"));


				return dataModel;

			}

			catch (JSONException e) {

				App.Log(App.E, "Error while parsing json Data in insertDataInModel()", ""+e.getMessage());
			}



			return null;

		} // end of insertDataInModel()


	} // end of AsyncFetchWeatherData

} // end of MainActivity