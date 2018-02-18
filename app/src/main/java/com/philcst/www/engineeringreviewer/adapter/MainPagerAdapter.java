package com.philcst.www.engineeringreviewer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.philcst.www.engineeringreviewer.ExtrasFragment;
import com.philcst.www.engineeringreviewer.QuizMenuFragment;
import com.philcst.www.engineeringreviewer.ReviewFragment;
import com.philcst.www.engineeringreviewer.StatisticsFragment;

/**
 * Adapter for managing main screen fragments
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReviewFragment();
            case 1:
                return new QuizMenuFragment();
            case 2:
                return new ExtrasFragment();
            case 3:
                return new StatisticsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // we have 4 main menu fragments
        return 4;
    }
}
