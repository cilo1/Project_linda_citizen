package com.example.cmigayi.projectlindacitizen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class Message extends AppCompatActivity implements View.OnClickListener {
    ImageView back,appIcon;
    LinearLayout linearLayout;
    TextView title;
    ListView listView;
    ServerRequest serverRequest;
    LocalUserStorage localUserStorage;
    Citizen citizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        serverRequest = new ServerRequest();
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
        title.setText("Messages:");
        linearLayout = (LinearLayout) toolbar.findViewById(R.id.linearlayout1);
        linearLayout.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        serverRequest.getCitizenMessages(citizen.citizen_id,new UrlCallBack() {
            @Override
            public void done(final ArrayList<HashMap<String, String>> arrayList) {
                if(arrayList == null){
                    Log.d("Messages","null");
                }else{
                    if(arrayList.size() > 0){
                        CustomMessageListview customMessageListview =
                                new CustomMessageListview(getApplicationContext(),arrayList);
                        listView.setAdapter(customMessageListview);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map = arrayList.get(position);
                                Intent intent = new Intent(getApplicationContext(),ReadMessage.class);
                                intent.putExtra("hashmap",map);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }

            @Override
            public void done(Citizen citizen) {

            }

            @Override
            public void done(String response) {

            }
        });

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                startActivity(new Intent(this,dashboard.class));
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
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
