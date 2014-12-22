package com.example.weatherforecasting.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.weatherforecasting.R;
import com.example.weatherforecasting.activities.CityWeatherActivity;
import com.example.weatherforecasting.activities.MainActivity;
import com.example.weatherforecasting.models.DataModel;
import com.example.weatherforecasting.models.TemperatureModel;
import com.example.weatherforecasting.models.WhetherModel;

/**
 * it provides pages (images) to fragment.
 * 
 * @author shivam
 */
public class CityOneDayActivityAdapter extends PagerAdapter  {
	
	private Activity mActivity = null;
	private ArrayList<DataModel> mDataModels = null;
	private TextView mTextTitle, mTextDay, mTextNight, mTextMorning, mTextEvening, mTextMin, mTextMax, mTextHumidity,
	mTextPressure ,mTextSpeed, mTextMainWhetherDescription, mTextDescription; 

	
	// constructor
    public CityOneDayActivityAdapter(Activity activity, ArrayList<DataModel> dataModel) {
        this.mActivity = activity;
        this.mDataModels = dataModel;
    }
	

    @Override
    public int getCount() {
        return this.mDataModels.size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) 
    {
        return view == ((ScrollView) object);
    }
     
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.day_details, container, false);
  
        	viewLayout = initUi(viewLayout, position);
        
        	
    		TemperatureModel temperatureModel = mDataModels.get(position).getTemperatureModel();
    		WhetherModel whetherModel = mDataModels.get(position).getWhetherModel();
    		
    		mTextDay.setText("Day  : "+temperatureModel.getDay());
    		mTextNight.setText("Night  : "+temperatureModel.getNight());
    		mTextEvening.setText("Evening  : "+temperatureModel.getEve());
    		mTextMorning.setText("Morning  : "+temperatureModel.getMorn());
    		mTextMin.setText("MIN  : "+temperatureModel.getMin());
    		mTextMax.setText("MAX  : "+temperatureModel.getMax());
    		
    		mTextMainWhetherDescription.setText("Main : "+whetherModel.getMain());
    		mTextDescription.setText("Description  : "+whetherModel.getDescription());
    		
    		mTextSpeed.setText("Speed  : "+mDataModels.get(position).getSpeed());
    		mTextHumidity.setText("Humidity  : "+mDataModels.get(position).getHumidity());
    		mTextPressure.setText("Pressure : "+mDataModels.get(position).getPressure());

        	
        	
        ((ViewPager) container).addView(viewLayout);
        
       
        return viewLayout;
    }
     
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ScrollView) object);
  
    }
    
    
    
	public View initUi(View view, int position) {
		
		mTextTitle = (TextView) view.findViewById(R.id.txt_day_title);
		mTextTitle.setText("day "+(position+1)+" Weather Details For "+MainActivity.sForcastModels.get(CityWeatherActivity.sPosition).getName());
		
		mTextDay = (TextView) view.findViewById(R.id.txt_day_daytemp);
		mTextNight = (TextView) view.findViewById(R.id.txt_day_nighttemp);
		mTextMorning = (TextView) view.findViewById(R.id.txt_day_morntemp);
		mTextEvening = (TextView) view.findViewById(R.id.txt_day_evetemp);
		mTextMin = (TextView) view.findViewById(R.id.txt_day_mintemp);
		mTextMax = (TextView) view.findViewById(R.id.txt_day_maxtemp);

		
		mTextHumidity = (TextView) view.findViewById(R.id.txt_day_humidity);
		mTextPressure = (TextView) view.findViewById(R.id.txt_day_pressure);
		mTextSpeed = (TextView) view.findViewById(R.id.txt_day_speed);

		mTextMainWhetherDescription = (TextView) view.findViewById(R.id.txt_day_wh_main);
		mTextDescription = (TextView) view.findViewById(R.id.txt_day_wh_des);
		
		return view;
	}
    
}