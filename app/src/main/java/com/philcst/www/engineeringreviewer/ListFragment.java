package com.philcst.www.engineeringreviewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philcst.www.engineeringreviewer.adapter.BigListItemAdapter;


public class ListFragment extends Fragment{

    // This class holds an adapter for its own recylcer view
    private BigListItemAdapter adapter;
    public ListFragment() {
        adapter = null;
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

        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }

        return layout;
    }

    @Override
    public void onDetach() {
        adapter = null;
        super.onDetach();
    }

    // You can call this setter method to assign a TopicAdapter Object from the Parent Activity
    public void setAdapter(BigListItemAdapter adapter) {
        this.adapter = adapter;
    }
}
