package com.philcst.www.engineeringreviewer.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philcst.www.engineeringreviewer.R;
import com.philcst.www.engineeringreviewer.data.QuizMode;
import com.philcst.www.engineeringreviewer.interfaces.OnItemClickListener;

import java.util.List;


public class QuizModeAdapter extends RecyclerView.Adapter<QuizModeAdapter.ViewHolder> {

    // for listening Recylcer Item taps
    private List<QuizMode> mModes;
    private OnItemClickListener mListener;

    public QuizModeAdapter(List<QuizMode> modes, OnItemClickListener listener) {
        this.mModes = modes;
        this.mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imageView;
        TextView nameTextView;
        TextView descTextView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.topic_icon);
            nameTextView = (TextView) itemView.findViewById(R.id.topic_title);
            descTextView = (TextView) itemView.findViewById(R.id.topic_desc);
        }
    }

    @Override
    public QuizModeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(QuizModeAdapter.ViewHolder holder, final int position) {
        holder.imageView.setImageResource(mModes.get(position).getIcon());
        holder.nameTextView.setText(mModes.get(position).getName());
        holder.descTextView.setText(mModes.get(position).getDesc());

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
        return mModes.size();
    }

    //int getSelectedTopicIndex() { return mDataset.indexOf(selectedTopicItem); }

    //void setSelectedTopicIndex(int index) { this.selectedTopicItem = mDataset.get(index); }
}
