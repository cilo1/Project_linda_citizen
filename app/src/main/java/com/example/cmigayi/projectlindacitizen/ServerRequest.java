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
