package com.example.cmigayi.projectlindacitizen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;


public class ReadMessage extends AppCompatActivity implements View.OnClickListener  {
    ImageButton backHome;
    ImageView back,appIcon;
    LinearLayout linearLayout;
    TextView title;
    HashMap<String, String> hashMap;
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);

        Intent intent = getIntent();
        hashMap = new HashMap<String, String>();
        hashMap = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

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

        backHome = (ImageButton) findViewById(R.id.backHome);
        message = (EditText) findViewById(R.id.message);

        message.setText(hashMap.get("message"));

        back.setOnClickListener(this);
        backHome.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.backHome:
                startActivity(new Intent(this,dashboard.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_message, menu);
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
