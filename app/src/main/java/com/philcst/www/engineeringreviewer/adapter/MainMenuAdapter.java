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


public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder>{

    private ArrayList<Topic> mDataset;
    private OnItemClickListener mListener;

    public MainMenuAdapter(ArrayList<Topic> mDataset, OnItemClickListener mListener) {
        this.mDataset = mDataset;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.menuTitle.setText(mDataset.get(position).getName());
        holder.menuIcon.setImageResource(mDataset.get(position).getIcon());
        holder.menuDescription.setText(mDataset.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuTitle;
        AppCompatImageView menuIcon;
        TextView menuDescription;

        ViewHolder(View itemView) {
            super(itemView);
            menuTitle = (TextView) itemView.findViewById(R.id.main_menu_title);
            menuIcon = (AppCompatImageView) itemView.findViewById(R.id.main_menu_icon);
            menuDescription = (TextView) itemView.findViewById(R.id.main_menu_description);
        }
    }
}
