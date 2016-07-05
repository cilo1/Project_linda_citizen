package com.example.cmigayi.projectlindacitizen;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cmigayi on 18-Jan-16.
 */
public class IntroFragmentLogin extends Fragment implements View.OnClickListener{

    Button loginBtn;
    String natID,pwd;
    EditText et_nat_id,et_pwd;
    LocalUserStorage localUserStorage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_login,container,false);

        localUserStorage = new LocalUserStorage(getContext());

        loginBtn = (Button) view.findViewById(R.id.loginBtn);
        et_nat_id = (EditText) view.findViewById(R.id.nat_id);
        et_pwd = (EditText) view.findViewById(R.id.pwd);
        loginBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.loginBtn:
                ServerRequest serverRequest = new ServerRequest();

                natID = et_nat_id.getText().toString();
                pwd = et_pwd.getText().toString();

                serverRequest.loginUser(natID,pwd,new UrlCallBack() {
                    @Override
                    public void done(ArrayList<HashMap<String, String>> arrayList) {

                    }

                    @Override
                    public void done(Citizen citizen) {
                        if(citizen == null){
                            Log.d("citizen","null");
                            showErrorMessage();
                        }else{
                            loggedInUser(citizen);
                        }
                    }

                    @Override
                    public void done(String response) {

                    }
                });
                break;
        }
    }

    public void loggedInUser(Citizen citizen){
        localUserStorage.storeCitizenDataDetails(citizen.citizenList);
        localUserStorage.setCitizenLogged(true);
        startActivity(new Intent(getActivity(),dashboard.class));
    }

    private void showErrorMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("You entered incorrect user details!");
        builder.setPositiveButton("Ok",null);
        builder.show();
    }
}
