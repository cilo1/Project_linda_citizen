package com.example.cmigayi.projectlindacitizen;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cmigayi on 25-Mar-16.
 */
public class LocalUserStorage {
    SharedPreferences localStorage,toursLocalStorage;
    public static String SP_NAME = "Citizen_Details";

    public LocalUserStorage(Context context) {
        localStorage = context.getSharedPreferences(SP_NAME,0);
    }

    public void storeCitizenData(Citizen citizen){
        SharedPreferences.Editor editor = localStorage.edit();
        editor.putInt("citizen_id", citizen.citizen_id);
        editor.putString("fname", citizen.fname);
        editor.putString("lname", citizen.lname);
        editor.putString("surname", citizen.surname);
        editor.putString("phone",citizen.phone);
        editor.putString("profession", citizen.profession);
        editor.putString("nationality", citizen.nationality);
        editor.putString("home",citizen.home);
        editor.putString("work",citizen.work);
        editor.putString("email",citizen.email);
        editor.putString("nationalID",citizen.nationalID);
        editor.putString("password",citizen.password);
        editor.putString("dob", citizen.dob);

        editor.commit();
    }

    public void storeCitizenDataDetails(ArrayList<HashMap<String,String>> arrayList){
        SharedPreferences.Editor editor = localStorage.edit();
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap = arrayList.get(0);
        editor.putInt("citizen_id", Integer.parseInt(hashMap.get("citizen_id")));
        editor.putString("fname", hashMap.get("fname"));
        editor.putString("lname", hashMap.get("lname"));
        editor.putString("surname", hashMap.get("surname"));
        editor.putString("phone", hashMap.get("phone"));
        editor.putString("profession", hashMap.get("profession"));
        editor.putString("nationality", hashMap.get("nationality"));
        editor.putString("home", hashMap.get("home"));
        editor.putString("work", hashMap.get("work"));
        editor.putString("email",hashMap.get("email"));
        editor.putString("nationalID",hashMap.get("nationalID"));
        editor.putString("password",hashMap.get("password"));
        editor.putString("dob", hashMap.get("dob"));

        editor.commit();
    }

    public Citizen getLoggedInCitizen(){
        int citizenID = localStorage.getInt("citizen_id",-1);
        String fname = localStorage.getString("fname", "");
        String lname = localStorage.getString("lname", "");
        String surname = localStorage.getString("surname", "");
        String phone = localStorage.getString("phone", "");
        String profession = localStorage.getString("profession","");
        String nationality = localStorage.getString("nationality","");
        String home = localStorage.getString("home", "");
        String work = localStorage.getString("work","");
        String email = localStorage.getString("email","");
        String nationalID = localStorage.getString("nationalID","");
        String password = localStorage.getString("password","");
        String dob = localStorage.getString("dob","");

        Citizen citizen = new Citizen(citizenID,fname,lname,surname,phone,profession,nationality,home,
                work,email,nationalID,password,dob);
        return citizen;
    }

    public void setCitizenLogged(boolean logged){
        SharedPreferences.Editor editor = localStorage.edit();
        editor.putBoolean("citizenLoggedIn",logged);
        editor.commit();
    }

    public boolean getCitizenLogged(){
        if(localStorage.getBoolean("citizenLoggedIn",false) == true){
            return true;
        }else{
            return false;
        }
    }

    public void clearCitizenData(){
        SharedPreferences.Editor editor = localStorage.edit();
        editor.clear();
        editor.commit();
    }
}

