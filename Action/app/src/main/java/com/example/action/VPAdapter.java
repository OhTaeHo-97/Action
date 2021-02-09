package com.example.action;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext=new ArrayList<String>();
    public VPAdapter( FragmentManager fm) {
        super(fm);
        items = new ArrayList<Fragment>();
        items.add(new ScriptFragment());
        items.add(new WithScriptFragment());

        itext.add("대본");
        itext.add("같이 연습한 대본");
    }


    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public CharSequence getPageTitle(int position){
        return itext.get(position);
    }
}
