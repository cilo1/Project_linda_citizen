package com.example.cmigayi.projectlindacitizen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends FragmentActivity {
    //Declaring all public variables
    //Number of pages swiped
    private static int pageNum = 5;
    //View pager
    ViewPager viewPager;
    //pager adapter
    PagerAdapter pagerAdapter;
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get view pager from xml layout
        viewPager = (ViewPager) findViewById(R.id.pager);
        //Initialize our custom fragment pager adapter and set it to viewpager
        pagerAdapter = new CustomFragmentsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    public class CustomFragmentsPagerAdapter extends FragmentStatePagerAdapter{
        //create a constructor that takes fragment manager as a parameter.
        public CustomFragmentsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /*
            This is where the pages will be selected and switched from one to another based on
            there positions. I have added an extra position to ease user navigation when they get to
            the last page and want to move back.
            */
            switch(position){
                case 0:
                    return new IntroFragmentAbout();
                case 1:
                    return new IntroFragmentLogin();
                default:
                    return new IntroFragmentAbout();
            }
        }

        @Override
        public int getCount() {
            return pageNum;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
