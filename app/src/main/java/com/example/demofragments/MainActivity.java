package com.example.demofragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.demofragments.Adapters.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

// SOURCE :
// https://stackoverflow.com/questions/29915919/permission-denial-opening-provider-com-android-providers-contacts-contactsprovi
// https://www.youtube.com/watch?v=xuMuNNyL85Y&ab_channel=NekoCode
public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up fragments
        ViewPager viewPager = findViewById(R.id.activity_main_viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // Bind the Tab bar to the viewpager
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // Connect the TabLayout with the ViewPager.
        tabLayout.setupWithViewPager(viewPager);

    }
}