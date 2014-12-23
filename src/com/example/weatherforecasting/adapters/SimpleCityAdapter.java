package com.example.weatherforecasting.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.weatherforecasting.R;
import com.example.weatherforecasting.models.DataModel;
import com.example.weatherforecasting.utilities.App;

public class SimpleCityAdapter  extends ArrayAdapter<DataModel> {

	private LayoutInflater mInflater;
	private int mResourceId;
	private ArrayList<DataModel> mDataModels;
		
	
	public SimpleCityAdapter(Context context, int resourceId, ArrayList<DataModel> dataModels) {
		
		super(context, resourceId, dataModels);
	
		this.mResourceId = resourceId;
		this.mDataModels = dataModels;
		App.Log(App.D, "in Adapter ", " data model size is "+dataModels.size());
		
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	}

	
	protected class ViewHolder {
		
		private TextView mDay, mHumidity, mPressure, mDescription;
	}
	
	
	@Override
	public View getView(int position , View convertView, ViewGroup parent) {
		
			ViewHolder mViewHolder ;
			if (convertView == null) {
				
				convertView = mInflater.inflate(mResourceId, null);
				mViewHolder = new ViewHolder();
				mViewHolder.mDay = (TextView) convertView.findViewById(R.id.txt_whether_day);
				mViewHolder.mHumidity = (TextView) convertView.findViewById(R.id.txt_whether_humidity);
				mViewHolder.mPressure=(TextView) convertView.findViewById(R.id.txt_whether_pressure);
				mViewHolder.mDescription=(TextView) convertView.findViewById(R.id.txt_whether_description);
				
			} 
			else {
				
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			
			
			mViewHolder.mDay.setText("day "+(position+1));
			mViewHolder.mPressure.setText("Pressure "+mDataModels.get(position).getPressure());
			mViewHolder.mHumidity.setText("Humidity "+mDataModels.get(position).getHumidity());
			mViewHolder.mDescription.setText(""+mDataModels.get(position).getWhetherModel().getDescription());
			
			App.Log(App.D,"day", ""+(position+1));
			App.Log(App.D,"humidity ", ""+mDataModels.get(position).getHumidity());
			App.Log(App.D,"pressure ", ""+mDataModels.get(position).getPressure());
			App.Log(App.D,"Details ", ""+mDataModels.get(position).getWhetherModel().getDescription());
					
			convertView.setTag(mViewHolder);

		return convertView;
				
	}
	
}