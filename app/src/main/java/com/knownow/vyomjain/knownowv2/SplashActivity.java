package com.knownow.vyomjain.knownowv2;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Vyom Jain on 02-09-2017.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
