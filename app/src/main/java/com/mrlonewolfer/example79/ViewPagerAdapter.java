package com.mrlonewolfer.example79;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    ArrayList<String> tabArrayList=new ArrayList<>();
    ArrayList<Fragment> fragmentList=new ArrayList<>();

    public ViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);

    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return tabArrayList.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        fragmentList.add(fragment);
        tabArrayList.add(title);
    }
}
