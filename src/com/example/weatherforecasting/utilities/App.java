package com.example.weatherforecasting.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;


import com.example.weatherforecasting.R;


/**
 *  Handles all global data members and functions, like showing log, dialog. 
 * @author shivam
 */
public class App 
{

	/**indicates whether or not application  is running in debugger mode.*/
	private static boolean sDEBUG = true;

	/**indicates log cat level.*/
	public static final int I=0, D=1, E=2, W=3, V=4;

	/**URL for end point to access weather data*/
	public static final String URL_WEATHER = "http://api.openweathermap.org/data/2.5/weather?q=";
	/**URL for end point to access weather forecast data*/
	public static final String URL_FORECAST = "http://api.openweathermap.org/data/2.5/forecast/daily?q="; 

	/**key to access data.*/
	public static final String API_KEY = "bf6729781b09c4ff7f4de85dc7d318d8";	



	/**
	 * it will print log only if application is running in debugger mode.
	 * @param title
	 * @param message
	 */
	public static void Log(int level, String title, String message) {

		if(sDEBUG) {

			switch (level) 
			{
			case I:
				Log.i(""+title,""+message);
				break;

			case D:
				Log.d(""+title,""+message);
				break;

			case E:
				Log.e(""+title,""+message);
				break;

			case W:
				Log.w(""+title,""+message);
				break;

			default:
				Log.v(""+title,""+message);
				break;
			}

		}

	} // end of Log()


	
	/**
	 *  display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * @param status - success/failure (used to set icon)
	 * */
	@SuppressWarnings({ "deprecation" })
	public static void ShowAlertDialog(Context context, String title, String message, boolean status) {

		final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(""+title);
		alertDialog.setMessage(""+message);

		if(status) {

			alertDialog.setIcon(R.drawable.ic_launcher); 

		}
		else {
			alertDialog.setIcon(R.drawable.fail);

		}


		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
			}
		});


		try {

			if(context!=null) {

				// Showing Alert Message
				alertDialog.show();
			}
		}
		catch(NullPointerException ex) {

			App.Log(E, "Error while showing alert dialog",""+ex.getMessage());

		}// end of try/catch

	}// end of showAlertDialog() 
}