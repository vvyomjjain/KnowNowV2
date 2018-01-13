package com.knownow.vyomjain.knownowv2;

import android.app.Fragment;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public Fragment selectedFragment;
    public static BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_action_bar);


        selectedFragment = HomeFragment.newInstance();

        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            selectedFragment = HomeFragment.newInstance();
        }else{
            bottomNavigationView.getMenu().getItem(3).setChecked(true);
            selectedFragment = SavedFragment.newInstance();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.action_sources:
                                selectedFragment = SourceFragment.newInstance();
                                break;

                            case R.id.action_weather:
                                selectedFragment = WeatherFragment.newInstance();
                                break;

                            case R.id.action_saved:
                                selectedFragment = SavedFragment.newInstance();
                                break;

                            case R.id.action_settings:
                                selectedFragment = SettingsFragment.newInstance();
                                break;
                        }

                        setFragmentInMain(selectedFragment);
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        setFragmentInMain(selectedFragment);
    }

    public void setFragmentInMain(Fragment newFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, newFragment);
        transaction.commit();
    }
}
