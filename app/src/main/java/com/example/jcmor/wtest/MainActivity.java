package com.example.jcmor.wtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.jcmor.wtest.utils.SimpleFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private AHBottomNavigation  bottomNavigation;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewPager();
        setupBottomNavigationBar();
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.viewpager);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter (this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void setupBottomNavigationBar(){
        bottomNavigation = findViewById(R.id.bottom_navigation);
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.navigation);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, new int[]{R.color.colorAccent,R.color.colorAccent,R.color.colorAccent});

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#354355"));
        bottomNavigation.setAccentColor(Color.parseColor("#F3CD70"));
        bottomNavigation.setInactiveColor(Color.parseColor("#FFFFFF"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setBehaviorTranslationEnabled(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(!wasSelected){
                    viewPager.setCurrentItem(position);
                }
                return true;
            }
        });
    }
}
