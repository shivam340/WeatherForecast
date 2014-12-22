package com.example.weatherforecasting.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.example.weatherforecasting.R;


/**
 *  Main Class to handle all global data members and functions. 
 * @author shivam
 */
public class App 
{
	
	/**
	 * indicates whether or not application  is running in debugger mode. 
	 */
	private static boolean DEBUG = true;
	
	/**
	 *  URL for end point
	 */
	protected static final String URL_WHETHER = "http://api.openweathermap.org/data/2.5/weather?q=";
	protected static final String URL_FORECAST = "http://api.openweathermap.org/data/2.5/forecast/daily?q="; 
	
	
	/**
	 * key to access data.
	 */
	protected static final String API_KEY = "bf6729781b09c4ff7f4de85dc7d318d8";	
	
	
	
	
	
	/**
	 * it will print log only if application is running in debugger mode.
	 * @param title
	 * @param message
	 */
	public static void log(String title, String message)
	{
		if(DEBUG)
		{
			Log.d(""+title, ""+message);   // ""+  to avoid null pointer exception
		}
	}
	
	
	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * @param status - success/failure (used to set icon)
	 * */
	@SuppressWarnings({ "deprecation" })
	public static void showAlertDialog(Context context, String title, String message) 
	{
		final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setIcon(R.drawable.fail);
		
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});

		
		try
		{
			if(context!=null)
			{
				// Showing Alert Message
				alertDialog.show();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}