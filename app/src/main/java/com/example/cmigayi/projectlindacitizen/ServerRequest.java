package com.example.cmigayi.projectlindapolicepatrol;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
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
   //public static final String SERVER_ADDRESS = "http://10.0.2.2/projectLinda";
   public static final String SERVER_ADDRESS = "http://www.goodsclearanceclub.com/projectLinda";

   public void loginPolice(String regNo, String pwd,UrlCallBack urlCallBack){
       new LoginPoliceAsync(regNo,pwd,urlCallBack).execute();
   }

   public void getCrimeReport(int policeID,UrlCallBack urlCallBack){
       new GetCrimeReportAsync(policeID,urlCallBack).execute();
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

   public void getCurrentPoliceLocation(int police_id,double latitude, double longitude, UrlCallBack urlCallBack){
       new GetCurrentPoliceLocationAsync(police_id,latitude,longitude,urlCallBack).execute();
   }

        class LoginPoliceAsync extends AsyncTask<String, Void, Police>{
            String requestURL = SERVER_ADDRESS+"/police_login.php";
            String regNo;
            String pwd;
            Police police;
            ArrayList<HashMap<String,String>> policeArraylist;
            UrlCallBack urlCallBack;

            public LoginPoliceAsync(String regNo, String pwd, UrlCallBack urlCallBack){
                this.regNo = regNo;
                this.pwd = pwd;
                policeArraylist = new ArrayList<HashMap<String,String>>();
                this.urlCallBack = urlCallBack;
            }

            @Override
            protected Police doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("regNo","435");
                data.put("password","123");

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
                        Log.d("response1:",response);
                    }
                    else {
                        response="Error Registering";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject = null;
                try {
                    Log.d("response1:",response);
                    jsonObject = new JSONObject(response);
                    if(jsonObject.length() == 0){
                        police = null;
                    }
                    JSONArray userContents = jsonObject.getJSONArray("contents");
                    Log.d("Array length:", Integer.toString(userContents.length()));

                    for(int i = 0; i<userContents.length();i++){

                        JSONObject c = userContents.getJSONObject(i);

                        String policeID = c.getString("police_id");
                        String fname = c.getString("fname");
                        String lname = c.getString("lname");
                        String surname = c.getString("surname");
                        String phone = c.getString("phone");
                        String officerID = c.getString("officerID");
                        String dob = c.getString("dob");
                        String rank = c.getString("rank");
                        String dateTime = c.getString("dateTime");

                        Log.i("postedBy:", fname);

                        HashMap<String,String> hashMap = new HashMap<String,String>();
                        hashMap.put("police_id",policeID);
                        hashMap.put("fname",fname);
                        hashMap.put("lname",lname);
                        hashMap.put("surname",surname);
                        hashMap.put("phone",phone);
                        hashMap.put("officerID",officerID);
                        hashMap.put("dob",dob);
                        hashMap.put("rank",rank);
                        hashMap.put("dateTime",dateTime);

                        policeArraylist.add(hashMap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                police = new Police(policeArraylist);

                return  police;
            }

            @Override
            protected void onPostExecute(Police p) {
                super.onPostExecute(p);
                urlCallBack.done(p);
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

    class GetCrimeReportAsync extends AsyncTask<String, Void, ArrayList<HashMap<String,String>>>{
        String requestURL = SERVER_ADDRESS+"/getCrimeReport.php";
        ArrayList<HashMap<String,String>> policeArraylist;
        UrlCallBack urlCallBack;
        int policeID;

        public GetCrimeReportAsync(int policeID,UrlCallBack urlCallBack){
            policeArraylist = new ArrayList<HashMap<String,String>>();
            this.urlCallBack = urlCallBack;
            this.policeID = policeID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<HashMap<String,String>> doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String,String>();
            data.put("police_id",Integer.toString(policeID));

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
                Log.d("response:", response);
                jsonObject = new JSONObject(response);
                if(jsonObject.length() == 0){
                    policeArraylist = null;
                }
                JSONArray userContents = jsonObject.getJSONArray("contents");
                Log.d("Array length:", Integer.toString(userContents.length()));

                for(int i = 0; i<userContents.length();i++){

                    JSONObject c = userContents.getJSONObject(i);

                    String crimeID = c.getString("crime_id");
                    String crime = c.getString("crime");
                    String fname = c.getString("fname");
                    String lname = c.getString("lname");
                    String surname = c.getString("surname");
                    String natID = c.getString("natID");
                    String phone = c.getString("phone");
                    String offender = c.getString("offender");
                    String victim = c.getString("victim");
                    String place = c.getString("place");
                    String status = c.getString("status");
                    String dateTime = c.getString("dateTime");


                    Log.i("postedBy:", crime);

                    HashMap<String,String> hashMap = new HashMap<String,String>();
                    hashMap.put("crime_id",crimeID);
                    hashMap.put("crime",crime);
                    hashMap.put("fname",fname);
                    hashMap.put("lname",lname);
                    hashMap.put("surname",surname);
                    hashMap.put("natID",natID);
                    hashMap.put("phone",phone);
                    hashMap.put("offender",offender);
                    hashMap.put("victim",victim);
                    hashMap.put("place",place);
                    hashMap.put("status",status);
                    hashMap.put("dateTime",dateTime);

                    policeArraylist.add(hashMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return policeArraylist;
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

    class GetCurrentPoliceLocationAsync extends AsyncTask<String, Void, String>{
        String requestURL = SERVER_ADDRESS+"/reverseGeocoding.php";
        UrlCallBack urlCallBack;
        int police_id;
        double latitude,longitude;

        public GetCurrentPoliceLocationAsync(int police_id,double latitude,double longitude,UrlCallBack urlCallBack){
            this.urlCallBack = urlCallBack;
            this.police_id = police_id;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String,String>();
            data.put("police_id",Integer.toString(police_id));
            data.put("latitude", String.valueOf(latitude));
            data.put("longitude", String.valueOf(longitude));

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
                    response="Location not registered";
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
