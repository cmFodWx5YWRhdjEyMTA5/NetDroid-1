package org.netdroid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.netdroid.fragments.AboutFragment;
import org.netdroid.fragments.HomeFragment;
import org.netdroid.fragments.NetworkFragment;
import org.netdroid.fragments.WirelessFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            return;
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE},1);
            }
            return;
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE)!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_WIFI_STATE},1);
            }
            return;
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
            }
            return;
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "Home");
        adapter.addFrag(new NetworkFragment(), "Net");
        adapter.addFrag(new WirelessFragment(), "WiFi");
        adapter.addFrag(new AboutFragment(), "About");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}