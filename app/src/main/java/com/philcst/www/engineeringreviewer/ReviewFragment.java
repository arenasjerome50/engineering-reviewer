package com.philcst.www.engineeringreviewer;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philcst.www.engineeringreviewer.adapter.TopicPagerAdapter;
import com.philcst.www.engineeringreviewer.data.Topic;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    private ArrayList<Topic> topicItems;
    private OnTabLayoutFragmentInteraction interaction;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get main topics
        topicItems = Topic.loadTopicData(getResources());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        // create an adapter for showing the list of subtopics
        // note: I use ChildFragmentManager
        TopicPagerAdapter mTopicPagerAdapter = new TopicPagerAdapter(getChildFragmentManager());
        // add fragments
        for (Topic topic : topicItems) {
            mTopicPagerAdapter.addTopicFragments(
                    TopicListFragment.newInstance(topic.getSubTopicList()), topic.getName());
        }

        ViewPager topicViewPager = (ViewPager) rootView.findViewById(R.id.topic_view_pager);
        // set the adapter
        topicViewPager.setAdapter(mTopicPagerAdapter);

        // set the tabs
        //TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.topic_tabs);
        //tabLayout.setupWithViewPager(topicViewPager);
        interaction.setTopicTabViewPager(topicViewPager);
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
        void setTopicTabViewPager(ViewPager viewPager);
    }

}
