package com.example.cmigayi.projectlindacitizen;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cmigayi on 19-Jan-16.
 */
public class ServerRequest {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000*15;
    public static final String SERVER_ADDRESS = "http://10.0.2.2/projectLinda";
   // public static final String SERVER_ADDRESS = "http://localhost/projectLinda";
    //public static final String SERVER_ADDRESS = "http://www.goodsclearanceclub.com/LAM_final";

   public void loginUser(String natID, String pwd,UrlCallBack urlCallBack){
       new LoginUserAsync(natID,pwd,urlCallBack).execute();
   }

   public void getPolicePatrols(int citizenID,UrlCallBack urlCallBack){
       new GetPolicePatrolsAsync(citizenID,urlCallBack).execute();
   }

   public void reportCrime(int citizenID, String victim, String offender, String place,
                           String crimeType,UrlCallBack urlCallBack){
       new ReportCrimeAsync(citizenID, victim, offender, place, crimeType,urlCallBack).execute();
   }

   public void getCitizenMessages(int citizen_id,UrlCallBack urlCallBack){
       new GetCitizenMessagesAsync(citizen_id,urlCallBack).execute();
   }

   public void getTotalCitizenMessagesAsync(int citizen_id,UrlCallBack urlCallBack){
       new GetTotalCitizenMessagesAsync(citizen_id,urlCallBack).execute();
   }

        class LoginUserAsync extends AsyncTask<String, Void, Citizen>{
            String requestURL = SERVER_ADDRESS+"/citizen_login.php";
            String natID;
            String pwd;
            Citizen citizen;
            ArrayList<HashMap<String,String>> citizenArraylist;
            UrlCallBack urlCallBack;

            public LoginUserAsync(String natID, String pwd, UrlCallBack urlCallBack){
                this.natID = natID;
                this.pwd = pwd;
                citizenArraylist = new ArrayList<HashMap<String,String>>();
                this.urlCallBack = urlCallBack;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Citizen doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("natID",natID);
                data.put("password",pwd);

                URL url;
                String response = "";
                try {
                    url = new URL(requestURL);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(data));

                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode=conn.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        response = br.readLine();
                    }
                    else {
                        response="Error Registering";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if(jsonObject.length() == 0){
                        citizen = null;
                    }
                    JSONArray userContents = jsonObject.getJSONArray("contents");
                    Log.d("Array length:", Integer.toString(userContents.length()));

                    for(int i = 0; i<userContents.length();i++){

                        JSONObject c = userContents.getJSONObject(i);

                        String citizenID = c.getString("citizen_id");
                        String fname = c.getString("fname");
                        String lname = c.getString("lname");
                        String surname = c.getString("surname");
                        String phone = c.getString("phone");
                        String profession = c.getString("profession");
                        String nationality = c.getString("nationality");
                        String home = c.getString("home");
                        String work = c.getString("work");
                        String email = c.getString("email");
                        String nationalID = c.getString("nationalID");
                        String pwd = c.getString("password");
                        String dob = c.getString("dob");

                        Log.i("postedBy:", fname);

                        HashMap<String,String> hashMap = new HashMap<String,String>();
                        hashMap.put("citizen_id",citizenID);
                        hashMap.put("fname",fname);
                        hashMap.put("lname",lname);
                        hashMap.put("surname",surname);
                        hashMap.put("phone",phone);
                        hashMap.put("profession",profession);
                        hashMap.put("nationality",nationality);
                        hashMap.put("home",home);
                        hashMap.put("work",work);
                        hashMap.put("email",email);
                        hashMap.put("nationalID",nationalID);
                        hashMap.put("pwd",pwd);
                        hashMap.put("dob",dob);

                        citizenArraylist.add(hashMap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                citizen = new Citizen(citizenArraylist);

                return  citizen;
            }

            @Override
            protected void onPostExecute(Citizen c) {
                super.onPostExecute(c);
                urlCallBack.done(c);
                //Log.d("Result:",s);
                // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    class GetPolicePatrolsAsync extends AsyncTask<String, Void, ArrayList<HashMap<String,String>>>{
        String requestURL = SERVER_ADDRESS+"/policePatrol.php";
        ArrayList<HashMap<String,String>> citizenArraylist;
        UrlCallBack urlCallBack;
        int citizenID;

        public GetPolicePatrolsAsync(int citizenID,UrlCallBack urlCallBack){
            citizenArraylist = new ArrayList<HashMap<String,String>>();
            this.urlCallBack = urlCallBack;
            this.citizenID = citizenID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<HashMap<String,String>> doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String,String>();
            data.put("citizen_id",Integer.toString(citizenID));

            URL url;
            String response = "";
            try {
                url = new URL(requestURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(data));

                writer.flush();
                writer.close();
                os.close();
                int responseCode=conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    response = br.readLine();
                }
                else {
                    response="Error Registering";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                Log.d("response:", response);
                if(jsonObject.length() == 0){
                    citizenArraylist = null;
                }
                JSONArray userContents = jsonObject.getJSONArray("contents");
                Log.d("Array length:", Integer.toString(userContents.length()));

                for(int i = 0; i<userContents.length();i++){

                    JSONObject c = userContents.getJSONObject(i);

                    String policeID = c.getString("police_id");
                    String fname = c.getString("fname");
                    String lname = c.getString("lname");
                    String surname = c.getString("surname");
                    String officerID = c.getString("officerID");
                    String rank = c.getString("rank");
                    String distance = c.getString("distance");


                    Log.i("postedBy:", fname);

                    HashMap<String,String> hashMap = new HashMap<String,String>();
                    hashMap.put("citizen_id",policeID);
                    hashMap.put("fname",fname);
                    hashMap.put("lname",lname);
                    hashMap.put("surname",surname);
                    hashMap.put("officerID",officerID);
                    hashMap.put("rank",rank);
                    hashMap.put("distance",distance);

                    citizenArraylist.add(hashMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return citizenArraylist;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String,String>> arrayList) {
            urlCallBack.done(arrayList);
            super.onPostExecute(arrayList);
        }
    }

    public class ReportCrimeAsync extends AsyncTask<String,String, ArrayList<HashMap<String,String>>>{
        String requestURL = SERVER_ADDRESS+"/reportCrime.php";
        UrlCallBack urlCallBack;
        int citizenID;
        String victim, offender, crimeType,place;
        ArrayList<HashMap<String,String>> arrayList;

        public ReportCrimeAsync(int citizenID, String victim, String offender, String place,
                                String crimeType,UrlCallBack urlCallBack) {
            this.urlCallBack = urlCallBack;
            this.citizenID = citizenID;
            this.victim = victim;
            this.offender = offender;
            this.crimeType = crimeType;
            this.place = place;
            arrayList = new ArrayList<HashMap<String,String>>();
        }

        @Override
        protected ArrayList<HashMap<String,String>> doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String,String>();
            data.put("citizen_id",Integer.toString(citizenID));
            data.put("victim",victim);
            data.put("offender",offender);
            data.put("place",place);
            data.put("crimeType",crimeType);

            URL url;
            String response = "";
            try {
                url = new URL(requestURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(data));

                writer.flush();
                writer.close();
                os.close();
                int responseCode=conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    response = br.readLine();
                    Log.d("Response:",response);
                }
                else {
                    response="Error Registering";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                Log.d("response:", response);
                if(jsonObject.length() == 0){
                    arrayList = null;
                }
                JSONArray userContents = jsonObject.getJSONArray("contents");
                Log.d("Array length:", Integer.toString(userContents.length()));

                for(int i = 0; i<userContents.length();i++){

                    JSONObject c = userContents.getJSONObject(i);

                    String policeID = c.getString("police_id");
                    String fname = c.getString("fname");
                    String lname = c.getString("lname");
                    String surname = c.getString("surname");
                    String officerID = c.getString("officerID");
                    String rank = c.getString("rank");
                    String distance = c.getString("distance");


                    Log.i("postedBy:", fname);

                    HashMap<String,String> hashMap = new HashMap<String,String>();
                    hashMap.put("citizen_id",policeID);
                    hashMap.put("fname",fname);
                    hashMap.put("lname",lname);
                    hashMap.put("surname",surname);
                    hashMap.put("officerID",officerID);
                    hashMap.put("rank",rank);
                    hashMap.put("distance",distance);

                    arrayList.add(hashMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String,String>> arrayList) {
            urlCallBack.done(arrayList);
            super.onPostExecute(arrayList);
        }
    }

    class GetCitizenMessagesAsync extends AsyncTask<String, Void, ArrayList<HashMap<String,String>>>{
        String requestURL = SERVER_ADDRESS+"/citizenMessages.php";
        ArrayList<HashMap<String,String>> citizenArraylist;
        UrlCallBack urlCallBack;
        int citizen_id;

        public GetCitizenMessagesAsync(int citizen_id,UrlCallBack urlCallBack){
            citizenArraylist = new ArrayList<HashMap<String,String>>();
            this.urlCallBack = urlCallBack;
            this.citizen_id = citizen_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<HashMap<String,String>> doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String,String>();
            data.put("citizen_id",Integer.toString(citizen_id));

            URL url;
            String response = "";
            try {
                url = new URL(requestURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(data));

                writer.flush();
                writer.close();
                os.close();
                int responseCode=conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    response = br.readLine();
                }
                else {
                    response="Error Registering";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                Log.d("response:", response);
                if(jsonObject.length() == 0){
                    citizenArraylist = null;
                }
                JSONArray userContents = jsonObject.getJSONArray("contents");
                Log.d("Array length:", Integer.toString(userContents.length()));

                for(int i = 0; i<userContents.length();i++){

                    JSONObject c = userContents.getJSONObject(i);

                    String citizenmessageID = c.getString("citizenmessage_id");
                    String subject = c.getString("subject");
                    String message = c.getString("message");
                    String status = c.getString("status");
                    String datetime = c.getString("dateTime");


                    Log.i("Subject:", subject);

                    HashMap<String,String> hashMap = new HashMap<String,String>();
                    hashMap.put("citizenmessage_id",citizenmessageID);
                    hashMap.put("subject",subject);
                    hashMap.put("message",message);
                    hashMap.put("status",status);
                    hashMap.put("datetime",datetime);

                    citizenArraylist.add(hashMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return citizenArraylist;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String,String>> arrayList) {
            urlCallBack.done(arrayList);
            super.onPostExecute(arrayList);
        }
    }

    class GetTotalCitizenMessagesAsync extends AsyncTask<String, Void, String>{
        String requestURL = SERVER_ADDRESS+"/getTotalcitizenMessages.php";
        ArrayList<HashMap<String,String>> citizenArraylist;
        UrlCallBack urlCallBack;
        int citizen_id;

        public GetTotalCitizenMessagesAsync(int citizen_id,UrlCallBack urlCallBack){
            citizenArraylist = new ArrayList<HashMap<String,String>>();
            this.urlCallBack = urlCallBack;
            this.citizen_id = citizen_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String,String>();
            data.put("citizen_id",Integer.toString(citizen_id));

            URL url;
            String response = "";
            try {
                url = new URL(requestURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(data));

                writer.flush();
                writer.close();
                os.close();
                int responseCode=conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    response = br.readLine();
                }
                else {
                    response="Error Registering";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            urlCallBack.done(s);
            super.onPostExecute(s);
        }
    }
}
