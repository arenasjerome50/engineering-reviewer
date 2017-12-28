package com.philcst.www.engineeringreviewer.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philcst.www.engineeringreviewer.R;
import com.philcst.www.engineeringreviewer.data.Topic;
import com.philcst.www.engineeringreviewer.interfaces.OnItemClickListener;

import java.util.ArrayList;

/**
 * An RecyclerView Adapter the is use in TopicListFragment class
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {


    private ArrayList<Topic> mDataset;
    //private TopicItem selectedTopicItem;
    private OnItemClickListener mListener;

    public TopicAdapter(ArrayList<Topic> dataset, OnItemClickListener listener) {
        this.mDataset = dataset;
        this.mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView topicIcon;
        TextView title;
        TextView description;

        ViewHolder(View itemView) {
            super(itemView);
            topicIcon = (AppCompatImageView) itemView.findViewById(R.id.topic_icon);
            title = (TextView) itemView.findViewById(R.id.topic_title);
            description = (TextView) itemView.findViewById(R.id.topic_desc);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.topicIcon.setImageResource(mDataset.get(position).getImageId());
        holder.title.setText(mDataset.get(position).getTitle());
        holder.description.setText(mDataset.get(position).getDesc());

        //Attaching the listeners
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    //int getSelectedTopicIndex() { return mDataset.indexOf(selectedTopicItem); }

    //void setSelectedTopicIndex(int index) { this.selectedTopicItem = mDataset.get(index); }
}
