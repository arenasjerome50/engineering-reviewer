package com.philcst.www.engineeringreviewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philcst.www.engineeringreviewer.adapter.ScoreListAdapter;
import com.philcst.www.engineeringreviewer.data.DatabaseAccess;
import com.philcst.www.engineeringreviewer.data.QuizMode;
import com.philcst.www.engineeringreviewer.data.ScoreEntry;

import java.util.ArrayList;


public class ScoreListFragment extends Fragment {

    QuizMode mode;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ScoreListFragment() {
    }

    public static ScoreListFragment newInstance(QuizMode mode) {
        ScoreListFragment fragment = new ScoreListFragment();
        Bundle args = new Bundle();
        args.putParcelable("mode", mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getting arguments that passed
        if (getArguments() != null) {
            mode = getArguments().getParcelable("mode");
        }

        // restoring saved state
        if (savedInstanceState != null) {
            mode = savedInstanceState.getParcelable("mode");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_list, container, false);

        RecyclerView scoreListView = (RecyclerView) view.findViewById(R.id.score_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        scoreListView.addItemDecoration(dividerItemDecoration);
        scoreListView.setLayoutManager(layoutManager);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        ArrayList<ScoreEntry> entries = databaseAccess.getScores(mode);
        scoreListView.setAdapter(new ScoreListAdapter(entries));

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("mode", mode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
