package com.philcst.www.engineeringreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.philcst.www.engineeringreviewer.data.TopicData;
import com.philcst.www.engineeringreviewer.data.TopicItem;

import java.util.ArrayList;

public class TopicListActivity extends AppCompatActivity implements TopicAdapter.OnItemClickListener {
    // TODO: Fix the state reset problem, apply the concept of using bundles for saving states like
    // what fragment is currently attached before rotating the screen layout?

    // Containers for querying topics
    // soon, I will reimplement this that the Topics object will have a subtopics members for easier
    // access of data.
    private ArrayList<TopicItem> mTopicItems = new ArrayList<>();
    private ArrayList<TopicItem> mGeasSubTopicItems = new ArrayList<>();
    private ArrayList<TopicItem> mEnggMathSubTopicItems = new ArrayList<>();

    // Main Topic Indexes
    final static int GEAS_SUB_TOPICS = 0;
    final static int ENGG_MATH_TOPICS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up the Up button at the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // set up the data
        loadData();

        // setting up the initial fragment
        TopicListFragment firstTopicList = new TopicListFragment();

        // Sets the Adapter,its dataset, and implements the callback required if the topic is selected.
        firstTopicList.setTopicAdapter(new TopicAdapter(mTopicItems, this));

        // Add the initial fragment to the Frame.
        getSupportFragmentManager().beginTransaction().add(R.id.frame, firstTopicList).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    // Implements the callback which listening at the first added fragment
    @Override
    public void onItemClick(int position) {
        // ready the fragment
        TopicListFragment subTopicList = new TopicListFragment();

        // this block determines what subtopic dataset to be used and implements again the callback for each suptopics
        // P.S. my code is messy, I hate nested blocks!!! can someone suggest other way to do this.
        switch (position) {
            case GEAS_SUB_TOPICS:
                // Sets the Adapter,its dataset, and implements the callback required if the topic is selected.
                subTopicList.setTopicAdapter(new TopicAdapter(mGeasSubTopicItems, new TopicAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // TODO: implement to show the pdf for this subtopic
                    }
                }));
                break;
            case ENGG_MATH_TOPICS:
                // Sets the Adapter,its dataset, and implements the callback required if the topic is selected.
                subTopicList.setTopicAdapter(new TopicAdapter(mEnggMathSubTopicItems, new TopicAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(TopicListActivity.this, ReadingActivity.class);
                        Bundle args = new Bundle();
                        args.putString("title", TopicData.subTopics[ENGG_MATH_TOPICS][position]);
                        args.putString("content", TopicData.subTopicContents[ENGG_MATH_TOPICS][position]);
                        intent.putExtras(args);
                        startActivity(intent);
                    }
                }));
                break;
        }

        // Note: Im using PDF assets here and soon ill encode it to HTML to reduce the size of the app

        // swap the fragments
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.frame, subTopicList)
                .addToBackStack(null).commit();
    }


    // function for assigning the data to these containers members, mTopicItems, mGeasSubTopicItems
    // and mEnggMathSubTopicItems
    // TODO: we need to rethink how the data is access that is not hard coded to a class instead you
    // can query data to the database.
    private void loadData() {
        for (int x = 0; x < TopicData.titles.length; x++) {
            mTopicItems.add(x, new TopicItem(TopicData.images[x],
                    TopicData.titles[x],
                    TopicData.desc[x]
            ));
        }

        // for GeAS subtopics
        for (int x = 0; x < TopicData.subTopics[GEAS_SUB_TOPICS].length; x++) {
            mGeasSubTopicItems.add(x, new TopicItem(TopicData.subTopicImages[GEAS_SUB_TOPICS][x],
                    TopicData.subTopics[GEAS_SUB_TOPICS][x],
                    TopicData.subTopicdesc[GEAS_SUB_TOPICS][x]
            ));
        }

        // for Engg Math subtopics
        for (int x = 0; x < TopicData.subTopics[ENGG_MATH_TOPICS].length; x++) {
            mEnggMathSubTopicItems.add(x, new TopicItem(TopicData.subTopicImages[ENGG_MATH_TOPICS][x],
                    TopicData.subTopics[ENGG_MATH_TOPICS][x],
                    TopicData.subTopicdesc[ENGG_MATH_TOPICS][x]
            ));
        }
    }

}
