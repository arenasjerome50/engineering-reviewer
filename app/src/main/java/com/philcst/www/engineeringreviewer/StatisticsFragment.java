package com.philcst.www.engineeringreviewer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philcst.www.engineeringreviewer.adapter.StatisticsPagerAdapter;
import com.philcst.www.engineeringreviewer.data.QuizMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    private OnTabLayoutFragmentInteraction interaction;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        // create an adapter for showing the list of subtopics
        // note: use ChildFragmentManager
        StatisticsPagerAdapter mStatisticsPagerAdapter = new StatisticsPagerAdapter(getChildFragmentManager());

        mStatisticsPagerAdapter.addFragments(ScoreListFragment.newInstance(QuizMode.NORMAL));
        mStatisticsPagerAdapter.addFragments(ScoreListFragment.newInstance(QuizMode.TIMED));
        mStatisticsPagerAdapter.addFragments(ScoreListFragment.newInstance(QuizMode.VITALI_3));

        ViewPager statViewPager = (ViewPager) rootView.findViewById(R.id.stats_viewpager);
        // set the adapter
        statViewPager.setAdapter(mStatisticsPagerAdapter);

        interaction.setStatsTabViewPager(statViewPager);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            interaction = (OnTabLayoutFragmentInteraction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnTabLayoutFragmentInteraction");
        }
    }

    public interface OnTabLayoutFragmentInteraction {
        void setStatsTabViewPager(ViewPager viewPager);
    }

}
