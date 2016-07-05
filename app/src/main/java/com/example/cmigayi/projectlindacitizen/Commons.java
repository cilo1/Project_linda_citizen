package com.example.cmigayi.projectlindacitizen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by cmigayi on 26-Jan-16.
 */
public class Commons {
    String placeLocation;

    public Commons(){

    }

    public void setPlaceLocation(String placeLocation){
        this.placeLocation = placeLocation;
    }

    public String getPlaceLocation(){
        return this.placeLocation;
    }

    public boolean checkInternetConnetion(Context context){
        ConnectivityManager check = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (check.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                check.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED){

            return false;
        }else{

            return true;
        }
    }

    public boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Log.e("Network testing", "Available");
            return true;
        }else {
            Log.e("Network testing", "Not Available");
            return false;
        }
    }
}
