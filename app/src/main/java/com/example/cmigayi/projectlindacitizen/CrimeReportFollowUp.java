package com.example.cmigayi.projectlindacitizen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class CrimeReportFollowUp extends AppCompatActivity implements View.OnClickListener {
    ImageView back,appIcon;
    LinearLayout linearLayout;
    TextView title,textViewTop,textViewBottom;
    ListView listView;
    ArrayList<HashMap<String, String>> arrayList;
    String crimeType;
    LocalUserStorage localUserStorage;
    Citizen citizen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_report_follow_up);

        Intent intent = getIntent();
        arrayList = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("arrayList");
        crimeType = intent.getStringExtra("crimeType");

        localUserStorage = new LocalUserStorage(this);
        citizen = localUserStorage.getLoggedInCitizen();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        appIcon = (ImageView) toolbar.findViewById(R.id.appIcon);
        appIcon.setVisibility(View.GONE);
        back = (ImageView) toolbar.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        title = (TextView) toolbar.findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("Crime Report Followup");
        linearLayout = (LinearLayout) toolbar.findViewById(R.id.linearlayout1);
        linearLayout.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        textViewTop = (TextView) findViewById(R.id.textViewTop);
        textViewBottom = (TextView) findViewById(R.id.textViewBottom);
        listView = (ListView) findViewById(R.id.listView);

        textViewTop.setText(Html.fromHtml("Hi "+ citizen.fname+" "+citizen.surname +", your crime " +
                "report has been received, the following patrol officers will be at your location " +
                "briefly, kindly stay at your present location"));
        textViewBottom.setText(Html.fromHtml("You have reported the following crime: <b>"+crimeType+
                "</b>, You can cancel this report by clicking the 'False Alarm' button below"));

        CustomPatrolListview customPatrolListview = new CustomPatrolListview(this,arrayList);
        listView.setAdapter(customPatrolListview);

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                startActivity(new Intent(this,dashboard.class));
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crime_report_follow_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
