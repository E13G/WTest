package com.example.jcmor.wtest.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jcmor.wtest.exercise3.Ex3;
import com.example.jcmor.wtest.Ex4;
import com.example.jcmor.wtest.exercise1.Ex1;
import com.example.jcmor.wtest.exercise2.Ex2;

/*
 * Created by jcmor on 27/01/2018.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Ex1();
        } else if (position == 1){
            return new Ex2();
        } else if (position == 2) {
            return new Ex3();
        } else {
            return new Ex4();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
