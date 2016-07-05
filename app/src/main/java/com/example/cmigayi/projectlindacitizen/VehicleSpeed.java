package com.example.cmigayi.projectlindacitizen;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class VehicleSpeed extends AppCompatActivity implements View.OnClickListener{
    LinearLayout linearLayout;
    ImageView back,appIcon;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_speed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        appIcon = (ImageView) toolbar.findViewById(R.id.appIcon);
        appIcon.setVisibility(View.GONE);
        back = (ImageView) toolbar.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        title = (TextView) toolbar.findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("Vehicle Speed");
        linearLayout = (LinearLayout) toolbar.findViewById(R.id.linearlayout1);
        linearLayout.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vehicle_speed, menu);
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
