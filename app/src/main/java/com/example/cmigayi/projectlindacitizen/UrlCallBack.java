package com.example.cmigayi.projectlindacitizen;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cmigayi on 19-Jan-16.
 */
interface UrlCallBack {
    public abstract void done(ArrayList<HashMap<String,String>> arrayList);
    public abstract void done(Citizen citizen);
    public abstract void done(String response);
}
