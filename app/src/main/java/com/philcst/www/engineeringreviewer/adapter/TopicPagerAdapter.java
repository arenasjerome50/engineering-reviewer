package com.philcst.www.engineeringreviewer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * A subclass of FragmentStatePagerAdapter for managing fragments that will
 * display sub topics of the Main selected topic.
 */

public class TopicPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mainTopicNames = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public TopicPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Our custom method that populates this Adapter with Fragments
    public void addTopicFragments(Fragment fragment, String title) {
        fragments.add(fragment);
        mainTopicNames.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mainTopicNames.get(position);
    }
}
