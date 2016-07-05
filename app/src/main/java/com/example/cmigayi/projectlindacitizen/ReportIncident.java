package com.example.cmigayi.projectlindacitizen;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class ReportIncident extends AppCompatActivity implements View.OnClickListener{
    Spinner spinnerIncident;
    LinearLayout loadingLinearlayout;
    ScrollView scrollView;
    EditText etOffender,etVictim,etCrimePlace;
    TextView title;
    String [] incidents = {
            "Robbery","Bribery","Rape","Fraud"
    };
    RadioGroup radioGroup;
    RadioButton radioButton,known,unknown;
    int selectedID;
    Button proceedBtn,undoBtn;
    ImageView back,appIcon;
    LinearLayout linearLayout;
    LocalUserStorage localUserStorage;
    ServerRequest serverRequest;
    Citizen citizen;
    String crimeType,victim,offender,crimePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);
        overridePendingTransition(0, 0);

        localUserStorage = new LocalUserStorage(this);
        citizen = localUserStorage.getLoggedInCitizen();
        serverRequest = new ServerRequest();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        appIcon = (ImageView) toolbar.findViewById(R.id.appIcon);
        appIcon.setVisibility(View.GONE);
        back = (ImageView) toolbar.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        title = (TextView) toolbar.findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("Report Crime");
        linearLayout = (LinearLayout) toolbar.findViewById(R.id.linearlayout1);
        linearLayout.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        spinnerIncident = (Spinner) findViewById(R.id.spinnerIncident);
        etOffender = (EditText) findViewById(R.id.etOffender);
        etVictim = (EditText) findViewById(R.id.etVictim);
        etCrimePlace = (EditText) findViewById(R.id.etCrimePlace);
        loadingLinearlayout = (LinearLayout) findViewById(R.id.loadingLinearlayout);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        proceedBtn = (Button) findViewById(R.id.procceedBtn);
        proceedBtn.setEnabled(false);
        undoBtn = (Button) findViewById(R.id.undoBtn);
        undoBtn.setEnabled(false);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,
                incidents);
        spinnerIncident.setAdapter(arrayAdapter);

        etOffender.setOnClickListener(this);
        etVictim.setOnClickListener(this);
        undoBtn.setOnClickListener(this);
        proceedBtn.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.etOffender:
                verifyEditTextDialog("offender");
                break;
            case R.id.etVictim:
                verifyEditTextDialog("victim");
                break;
            case R.id.undoBtn:
                finish();
                startActivity(new Intent(this,ReportIncident.class));
                break;
            case R.id.procceedBtn:
                loadingLinearlayout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);

                victim = etVictim.getText().toString();
                offender = etOffender.getText().toString();
                crimePlace = etCrimePlace.getText().toString();
                crimeType = spinnerIncident.getSelectedItem().toString();
                reportCrime(victim,offender,crimePlace,crimeType);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void reportCrime(String victim,String offender,String place, final String crimeType){
        int citizenID = citizen.citizen_id;
        serverRequest.reportCrime(citizenID,victim,offender,place,crimeType, new UrlCallBack() {
            @Override
            public void done(ArrayList<HashMap<String, String>> arrayList) {
                if(arrayList == null){
                    Log.d("Reponse","null");
                }else{
                    if(arrayList.size() > 0){
                        Log.d("Reponse","success");
                        Intent intent = new Intent(getApplicationContext(), CrimeReportFollowUp.class);
                        intent.putExtra("arrayList",arrayList);
                        intent.putExtra("crimeType",crimeType);
                        startActivity(intent);
                    }else{
                        Log.d("Reponse","error");
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
    }

    public void verifyEditTextDialog(final String editText){
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Do you know the "+editText+"?");
        dialog.setContentView(R.layout.dialog_verify_edit_text);

        radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        known = (RadioButton) dialog.findViewById(R.id.known);
        unknown = (RadioButton) dialog.findViewById(R.id.unknown);

        if(editText.equals("offender")){
            unknown.setText("I don't know the offender");
            known.setText("I know the offender");
        }else{
            unknown.setText("I don't know the victim");
            known.setText("I know the victim");
        }

        Button doneBtn = (Button) dialog.findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedID = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) dialog.findViewById(selectedID);
                //Toast.makeText(getApplicationContext(), radioButton.getText().toString(), Toast.LENGTH_LONG).show();
                switch(editText){
                    case "offender":
                        if(radioButton.getText().toString().equals("I know the "+editText)){
                            etOffender.setFocusable(true);
                            etOffender.setKeyListener(etOffender.getKeyListener());
                            etOffender.setCursorVisible(true);
                            etOffender.setOnClickListener(null);
                        }else {
                            etOffender.setText(radioButton.getText().toString());
                            etOffender.setFocusable(false);
                        }
                        break;
                    case "victim":
                        if(radioButton.getText().toString().equals("I know the "+editText)){
                            etVictim.setFocusable(true);
                            etVictim.setKeyListener(etOffender.getKeyListener());
                            etVictim.setCursorVisible(true);
                            etVictim.setOnClickListener(null);
                        }else {
                            etVictim.setText(radioButton.getText().toString());
                            etVictim.setFocusable(false);
                        }
                        break;
                }

                if(etOffender.length()>0 && etVictim.length()>0){
                    proceedBtn.setEnabled(true);
                }

                radioButton.setChecked(false);
                undoBtn.setEnabled(true);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report_incident, menu);
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
