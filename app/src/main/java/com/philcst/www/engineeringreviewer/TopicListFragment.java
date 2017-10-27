package com.philcst.www.engineeringreviewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment for displaying Topics and Subtopics
 */
public class TopicListFragment extends Fragment {

    // This class holds an adapter for its own recylcer view
    private TopicAdapter mTopicAdapter;

    public TopicListFragment() {
        mTopicAdapter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_topic_list, container, false);

        RecyclerView mRecyclerView = (RecyclerView) layout.findViewById(R.id.main_topic_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        if (mTopicAdapter != null) {
            mRecyclerView.setAdapter(mTopicAdapter);
        }

        return layout;
    }

    @Override
    public void onDetach() {
        mTopicAdapter = null;
        super.onDetach();
    }

    // You can call this setter method to assign a TopicAdapter Object from the Parent Activity
    public void setTopicAdapter(TopicAdapter topicAdapter) {
        mTopicAdapter = topicAdapter;
    }
}
