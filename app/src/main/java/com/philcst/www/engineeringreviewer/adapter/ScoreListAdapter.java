package com.philcst.www.engineeringreviewer.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philcst.www.engineeringreviewer.R;
import com.philcst.www.engineeringreviewer.data.ScoreEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ViewHolder>{

    private ArrayList<ScoreEntry> mDataSet;

    public ScoreListAdapter(ArrayList<ScoreEntry> dataset) {
        this.mDataSet = dataset;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateColumn;
        TextView topicColumn;
        TextView scoreColumn;

        ViewHolder(View itemView) {
            super(itemView);
            dateColumn = (TextView) itemView.findViewById(R.id.date_column);
            topicColumn = (TextView) itemView.findViewById(R.id.topic_column);
            scoreColumn = (TextView) itemView.findViewById(R.id.score_column);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mDataSet != null) {
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            holder.dateColumn.setText(df.format(mDataSet.get(position).getDate()));
            holder.topicColumn.setText(mDataSet.get(position).getTopic());
            holder.scoreColumn.setText(Integer.toString(mDataSet.get(position).getScore()));
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    //int getSelectedTopicIndex() { return mDataset.indexOf(selectedTopicItem); }

    //void setSelectedTopicIndex(int index) { this.selectedTopicItem = mDataset.get(index); }
}