package com.philcst.www.engineeringreviewer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philcst.www.engineeringreviewer.adapter.BigListItemAdapter;
import com.philcst.www.engineeringreviewer.data.Topic;
import com.philcst.www.engineeringreviewer.interfaces.OnItemClickListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicListFragment extends Fragment implements OnItemClickListener {

    public static final String ARG_TOPICS = "ARG_TOPICS";
    private ArrayList<Topic> topics;

    public TopicListFragment() {
    }

    public static TopicListFragment newInstance(ArrayList<Topic> topics) {
        Bundle args = new Bundle();
        // put all the topics to be display
        args.putParcelableArrayList(ARG_TOPICS, topics);

        TopicListFragment fragment = new TopicListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args != null) {
            topics = args.getParcelableArrayList(ARG_TOPICS);
        }
        if(savedInstanceState != null) {
            topics = savedInstanceState.getParcelableArrayList(ARG_TOPICS);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topic_list, container, false);

        RecyclerView subTopicListView = (RecyclerView) rootView.findViewById(R.id.main_topic_recyclerview);
        subTopicListView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        /*DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        subTopicListView.addItemDecoration(dividerItemDecoration);*/
        subTopicListView.setLayoutManager(layoutManager);
        subTopicListView.setAdapter(new BigListItemAdapter(topics, this));
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save the state
        outState.putParcelableArrayList(ARG_TOPICS, topics);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(int position) {
        // creating an intent to go to reading activity
        Intent intent = new Intent(getContext(), ReadingActivity.class);
        // passing a Topic object to the next activity
        intent.putExtra("topic_data", topics.get(position));
        startActivity(intent);
    }
}
