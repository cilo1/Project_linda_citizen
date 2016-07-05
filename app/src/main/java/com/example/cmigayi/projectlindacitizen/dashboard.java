package com.example.cmigayi.projectlindacitizen;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;


public class dashboard extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    TextView messageBanner;
    EditText reportCrime_et;
    ListView patrolListView;
    String[] police = {"Srg. Wahome Michael","Srg. Kiru S.W","Srg. S.M Otieno","Insp. Mwala Mwathe"};
    String place;
    ImageButton flaggedBtn,reportBtn,messageBtn;
    GoogleApiClient googleApiClient;
    Location lastLocation;
    TextView location;
    LinearLayout linearLayout;
    Commons commons;
    boolean network;
    ServerRequest serverRequest;
    LocalUserStorage localUserStorage;
    Citizen citizen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        overridePendingTransition(0, 0);

        serverRequest = new ServerRequest();
        localUserStorage = new LocalUserStorage(this);
        citizen = localUserStorage.getLoggedInCitizen();

        if(googleApiClient == null){
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        commons = new Commons();
        commons.checkInternetConnetion(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        linearLayout = (LinearLayout)toolbar.findViewById(R.id.linearlayout1);
        location = (TextView) toolbar.findViewById(R.id.location);
        setSupportActionBar(toolbar);

        flaggedBtn = (ImageButton) findViewById(R.id.flaggedBtn);
        reportBtn = (ImageButton) findViewById(R.id.reportBtn);
        messageBtn = (ImageButton) findViewById(R.id.messageBtn);

        patrolListView = (ListView) findViewById(R.id.patrol_list);
        reportCrime_et = (EditText) findViewById(R.id.report_crime);

        messageBanner = (TextView) findViewById(R.id.messageBanner);

        serverRequest.getPolicePatrols(citizen.citizen_id,new UrlCallBack() {
            @Override
            public void done(final ArrayList<HashMap<String, String>> arrayList) {
                if(arrayList == null){
                    Log.d("Patrols","null");
                }else{
                    int total = arrayList.size();
                    Log.d("Total",Integer.toString(total));
                    CustomPatrolListview customPatrolListview =
                            new CustomPatrolListview(getApplicationContext(),arrayList);
                    patrolListView.setAdapter(customPatrolListview);
                    patrolListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap = arrayList.get(position);
                        }
                    });
                }
            }

            @Override
            public void done(Citizen citizen) {

            }

            @Override
            public void done(String response) {

            }
        });

        serverRequest.getTotalCitizenMessagesAsync(citizen.citizen_id, new UrlCallBack() {
            @Override
            public void done(ArrayList<HashMap<String, String>> arrayList) {

            }

            @Override
            public void done(Citizen citizen) {

            }

            @Override
            public void done(String response) {
                if(response == null){
                    Log.d("response","null");
                    messageBanner.setVisibility(View.GONE);
                }else{
                    if(response.equals("0")){
                        messageBanner.setVisibility(View.GONE);
                    }else{
                        messageBanner.setText(response);
                    }
                }
            }
        });

        flaggedBtn.setOnClickListener(this);
        reportBtn.setOnClickListener(this);
        messageBtn.setOnClickListener(this);
        reportCrime_et.setOnClickListener(this);
    }

    protected void onStart() {
        if(network == true){
            googleApiClient.connect();
        }else{
            Log.d("Networkstatus","False");
        }
        super.onStart();
    }

    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.flaggedBtn:
                startActivity(new Intent(this,Flagged.class));
                break;
            case R.id.reportBtn:
                startActivity(new Intent(this,ReportIncident.class));
                break;
            case R.id.messageBtn:
                startActivity(new Intent(this,Message.class));
                break;
            case R.id.report_crime:
                startActivity(new Intent(this,ReportIncident.class));
                break;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            //placeText.setText(lastLocation.getLatitude()+","+lastLocation.getLongitude());
            String placeLat = String.valueOf(lastLocation.getLatitude());
            String placeLong = String.valueOf(lastLocation.getLongitude());

            linearLayout.setVisibility(View.GONE);
            location.setVisibility(View.VISIBLE);
            location.setText(placeLat+","+placeLong);

            Log.d("GetLocation:", lastLocation.getLatitude() + "," + lastLocation.getLongitude());
        }else{
            Log.d("GetLocation:","Last location is null");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_vehicle_speed) {
            startActivity(new Intent(this,VehicleSpeed.class));
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_logout) {
            startActivity(new Intent(this,MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
