package com.example.cmigayi.projectlindacitizen;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by cmigayi on 18-Jan-16.
 */
public class IntroFragmentAbout extends Fragment implements View.OnClickListener{
    //Delare all variables
    TextView tvList;
    Button citizenBtn,orgBtn,policeBtn;

    String citizenInfo = "&#8226; Linda Citizen - Mobile application that is on the hand of the Kenyan" +
            " citizen. His personal information is in this device i.e bio data and current address. " +
            "He/she is expected to report incidences happening in his immediate environment. He/she can " +
            "also access security infomation from the same device.";

    String orgInfo = "&#8226; Linda Org, - Its a desktop " +
            "based system that is installed in most public places. It monitors activities of people accessing " +
            "the premises and incidences can be reported directly to the nearby police stations and HQ. " +
            "This module, has the following features: Camera (capture real time events), capture people images;" +
            " NFC (Access control), monitor people who access the premises; monitor the specific people, " +
            "the number of people and where they visit within the premise in a given time.";

    String policeInfo = "&#8226; Linda Police - This part is in two forms: a mobile device for each police \" +\n" +
            "            \"staff and a desktop station based system";

    String myHtmlString = "&#8226; Linda Citizen - Mobile application that is on the hand of the Kenyan" +
            " citizen. His personal information is in this device i.e bio data and current address. " +
            "He/she is expected to report incidences happening in his immediate environment. He/she can " +
            "also access security infomation from the same device.<br/>&#8226; Linda Org, - Its a desktop " +
            "based system that is installed in most public places. It monitors activities of people accessing " +
            "the premises and incidences can be reported directly to the nearby police stations and HQ. " +
            "This module, has the following features: Camera (capture real time events), capture people images;" +
            " NFC (Access control), monitor people who access the premises; monitor the specific people, " +
            "the number of people and where they visit within the premise in a given time." +
            "<br/>&#8226; Linda Police - This part is in two forms: a mobile device for each police " +
            "staff and a desktop station based system<br/>";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_about,container,false);
        citizenBtn = (Button) view.findViewById(R.id.citizenBtn);
        orgBtn = (Button) view.findViewById(R.id.orgBtn);
        policeBtn = (Button) view.findViewById(R.id.policeBtn);

        citizenBtn.setOnClickListener(this);
        orgBtn.setOnClickListener(this);
        policeBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.citizenBtn:
                displayModuleInfo("Linda Citizen",citizenInfo);
                break;
            case R.id.orgBtn:
                displayModuleInfo("Linda Org",orgInfo);
                break;
            case R.id.policeBtn:
                displayModuleInfo("Lind Police",policeInfo);
                break;
        }
    }

    public void displayModuleInfo(String title, String info){
        Dialog dialog = new Dialog(getContext());
        dialog.setTitle(title);
        dialog.setContentView(R.layout.dialog_intro_module_info);
        TextView moduleInfo = (TextView) dialog.findViewById(R.id.tvInfo);
        moduleInfo.setText(Html.fromHtml(info));
        dialog.setCancelable(true);
        dialog.show();
    }
}
