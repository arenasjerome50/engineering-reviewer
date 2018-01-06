package com.philcst.www.engineeringreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.philcst.www.engineeringreviewer.adapter.BigListItemAdapter;
import com.philcst.www.engineeringreviewer.data.QuizMode;
import com.philcst.www.engineeringreviewer.data.Topic;
import com.philcst.www.engineeringreviewer.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class TopicListActivity extends AppCompatActivity implements OnItemClickListener {
    // TODO: Fix the state reset problem, apply the concept of using bundles for saving states like
    // what fragment is currently attached before rotating the screen layout?

    private ArrayList<Topic> topicItems;
    private QuizMode quizMode;
    private boolean isQuizTopicSelection = false;

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

        //load Data
        topicItems = Topic.loadTopicData(getResources());


        // if this activity receives an intent from QuizModeActivity class, it will become a topic
        // list selection for quiz, only main topics that the user can choose.
        Intent receivedIntent = getIntent();
        if (receivedIntent.hasExtra("quiz_mode")) {
            quizMode = receivedIntent.getParcelableExtra("quiz_mode");
            setTitle(quizMode.getName() + " - " + getResources().getString(R.string.title_activity_topic_list));
            isQuizTopicSelection = true;
            topicItems.add(new Topic(R.drawable.ic_random, "Random Topics",
                    "Take a Quiz to any topics", "random"));
        }

        // setting up the initial fragment
        ListFragment firstTopicList = new ListFragment();

        // Sets the Adapter,its dataset, and implements the callback required if the topic is selected.
        firstTopicList.setAdapter(new BigListItemAdapter(topicItems, this));

        // Add the initial fragment to the Frame.
        getSupportFragmentManager().beginTransaction().add(R.id.frame, firstTopicList).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    // I override this so the when im in the subtopic list, I can go back to the main topic list.
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
        // get the selected main topic
        final Topic mainTopic = topicItems.get(position);

        // if it is from QuizModeActivity with quiz_mode data, execute this
        if (isQuizTopicSelection) {
            // creating an intent to go to quiz activity
            Intent intent = new Intent(TopicListActivity.this, QuizActivity.class);

            // prepare the selected topic data
            intent.putExtra("quiz_mode", (Parcelable) quizMode);
            intent.putExtra("topic", mainTopic);

            // start quiz activity
            startActivity(intent);

            // finishing the quiz mode activity that left before
            sendBroadcast(new Intent("finish_quiz_mode_activity"));

            finish();
        } else { // ohhh it's just a reading intent
            // ready the fragment
            ListFragment subTopicList = new ListFragment();

            // setting an adapter for displaying the subtopics
            subTopicList.setAdapter(new BigListItemAdapter(mainTopic.getSubTopicList(),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            // creating an intent to go to reading activity
                            Intent intent = new Intent(TopicListActivity.this, ReadingActivity.class);
                            // passing a Topic object to the next activity
                            intent.putExtra("topic_data", mainTopic.getSubTopic(position));
                            startActivity(intent);
                        }
                    }));

            // swap the list fragments
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.frame, subTopicList)
                    .addToBackStack(null).commit();
        }

    }

}
