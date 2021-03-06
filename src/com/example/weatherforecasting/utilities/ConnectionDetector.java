package com.example.weatherforecasting.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/** helps to find out Internet's availability.
 * @author shivam
 */
public class ConnectionDetector {

	private Context context = null ;
	public static boolean wifiStatus=false; 

	public ConnectionDetector(Context context) {

		if(context !=null) {
			this.context = context;	
		}

	} 



	/** it checks the availability of Internet connection.
	 * @return boolean value
	 */
	public boolean isConnectedToInternet() {

		if(context != null) {

			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			// checks for packet data
			if (connectivity != null) {

				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) { 

					for (int i = 0; i < info.length; i++) {

						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}

					}// end of for
				}  

				// checks for WiFi
				if(isConnectedToWIFI()) {
					return true;
				}

			}// end of connectivity!=null
		}
		return false;
	}// end of isConnectedToInternet()


	/** return information about wifi's current status.
	 * @return boolean value.
	 */
	private boolean isConnectedToWIFI() {

		if(context != null) {

			ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			if (mWifi.isConnected()){

				App.Log(App.D, "Is device connected to  wifi"," "+true);
				return true;
			}

			App.Log(App.D,"Is device connected to  wifi"," "+false);
		}
		return false;
	} // end of isConnectedToWIFI()

} // end of class ConnectionDetector