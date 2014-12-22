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

public class SimpleCityAdapter  extends ArrayAdapter<DataModel> 
{

	private LayoutInflater inflater;
	private int resourceId;
	private ArrayList<DataModel> dataModels;
		
	
	public SimpleCityAdapter(Context context, int resourceId, ArrayList<DataModel> dataModels) {
		super(context, resourceId, dataModels);
	
		this.resourceId = resourceId;
		this.dataModels = dataModels;
		App.Log(App.D, "in Adapter ", " data model size is "+dataModels.size());
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	}

	
	protected class ViewHolder
	{
		private TextView day, humidity, pressure, description;
	}
	
	
	@Override
	public View getView(int position , View convertView, ViewGroup parent)
	{
	
			ViewHolder viewHolder ;
			if (convertView == null) 
			{
				convertView = inflater.inflate(resourceId, null);
				viewHolder = new ViewHolder();
				viewHolder.day = (TextView) convertView.findViewById(R.id.txt_whether_day);
				viewHolder.humidity = (TextView) convertView.findViewById(R.id.txt_whether_humidity);
				viewHolder.pressure=(TextView) convertView.findViewById(R.id.txt_whether_pressure);
				viewHolder.description=(TextView) convertView.findViewById(R.id.txt_whether_description);
				
			} 
			else 
			{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			
			viewHolder.day.setText("day "+(position+1));
			viewHolder.pressure.setText("Pressure "+dataModels.get(position).getPressure());
			viewHolder.humidity.setText("Humidity "+dataModels.get(position).getHumidity());
			viewHolder.description.setText(""+dataModels.get(position).getWhetherModel().getDescription());
			
			App.Log(App.D,"day", ""+(position+1));
			App.Log(App.D,"humidity ", ""+dataModels.get(position).getHumidity());
			App.Log(App.D,"pressure ", ""+dataModels.get(position).getPressure());
			App.Log(App.D,"Details ", ""+dataModels.get(position).getWhetherModel().getDescription());
					
			convertView.setTag(viewHolder);

		return convertView;
				
	}
	
}