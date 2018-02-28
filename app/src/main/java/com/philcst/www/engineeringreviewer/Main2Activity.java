package com.philcst.www.engineeringreviewer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.philcst.www.engineeringreviewer.adapter.MainPagerAdapter;

public class Main2Activity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener,
        ReviewFragment.OnTabLayoutFragmentInteraction, StatisticsFragment.OnTabLayoutFragmentInteraction {

    private AHBottomNavigationViewPager mainViewPager;
    private TabLayout tabLayout;
    private TabLayout statsTabLayout;
    private boolean isFirstRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (savedInstanceState != null) {
            isFirstRun = savedInstanceState.getBoolean("is_first_run");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.topic_tabs);
        initUI();


        if (isFirstRun) {
            statsTabLayout = (TabLayout) findViewById(R.id.stats_tabs);
            statsTabLayout.setVisibility(View.GONE);
            mainViewPager.setCurrentItem(0);
            isFirstRun = false;
        }
    }

    // populates ui manipulation in this method
    private void initUI() {
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        // set bottom navigation colors
        bottomNavigation.setDefaultBackgroundResource(R.color.colorPrimary);
        bottomNavigation.setAccentColor(fetchColor(R.color.bottomNavAccent));
        bottomNavigation.setInactiveColor(fetchColor(R.color.bottomNavInactive));

        // always show titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        mainViewPager = (AHBottomNavigationViewPager) findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        // disable swipe functionality
        mainViewPager.setPagingEnabled(false);

        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_nav);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation);
        // set the listener
        bottomNavigation.setOnTabSelectedListener(this);
        bottomNavigation.setBehaviorTranslationEnabled(false);
    }

    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (!wasSelected) {
            if (position == 0) {
                tabLayout.setVisibility(View.VISIBLE);
                statsTabLayout.setVisibility(View.GONE);
            } else if (position == 3){
                tabLayout.setVisibility(View.GONE);
                statsTabLayout.setVisibility(View.VISIBLE);
            } else {
                tabLayout.setVisibility(View.GONE);
                statsTabLayout.setVisibility(View.GONE);
            }
            mainViewPager.setCurrentItem(position);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("is_first_run", isFirstRun);
        super.onSaveInstanceState(outState);
    }

    private void setVisibilityWithAnimation(final View view, final int visibility) {
        // get 48 dp in px
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                48,
                getResources().getDisplayMetrics());

        if (visibility == View.GONE) {
            view.animate()
                    .translationY(-px)
                    .setDuration(100)  // 1-sec animation!!
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(visibility);
                        }
                    });
        } else {
            view.animate()
                    .translationY(0)
                    .setDuration(100) // 1-sec animation!!
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            view.setVisibility(visibility);
                        }
                    });
        }
    }

    @Override
    public void setTopicTabViewPager(ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setStatsTabViewPager(ViewPager viewPager) {
        statsTabLayout.setupWithViewPager(viewPager);
    }
}
