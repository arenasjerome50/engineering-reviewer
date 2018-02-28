package com.philcst.www.engineeringreviewer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.philcst.www.engineeringreviewer.data.QuizMode;
import com.philcst.www.engineeringreviewer.data.Topic;

/**
 * Dialog for choosing number of questions and kind of topics will be given in the exam.
 */

public class QuizOptionsDialogFragment extends DialogFragment {

    private QuizMode mode;

    private String TAG = getClass().getSimpleName();

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get the passed quiz mode
        if (getArguments() != null) {
            mode = getArguments().getParcelable("quiz_mode");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // custom layout, get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layout = inflater.inflate(R.layout.dialog_fragment_quiz_options, null);

        final Spinner topicSpinner = (Spinner) layout.findViewById(R.id.topic_spinner);
        final Spinner numOfQuestionSpinner = (Spinner) layout.findViewById(R.id.num_of_questions_spinner);

        ArrayAdapter<CharSequence> topicSpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.quiz_option_topics, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> numOfQuesSpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.quiz_option_number_of_question, android.R.layout.simple_spinner_item);

        topicSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numOfQuesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        topicSpinner.setAdapter(topicSpinnerAdapter);
        numOfQuestionSpinner.setAdapter(numOfQuesSpinnerAdapter);


        // Inflate and set the layout for the dialog
        builder.setView(layout)
                .setTitle("Quiz Options (" + mode.getName() + ")")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int topicPosition = topicSpinner.getSelectedItemPosition();
                        int numberOfQuestions = Integer.parseInt(numOfQuestionSpinner.getSelectedItem().toString());

                        // creating an intent to go to quiz activity
                        Intent intent = new Intent(getContext(), QuizActivity.class);

                        // getting selected topic
                        Topic topic;
                        if (topicPosition < 2) {
                            topic = Topic.loadTopicData(getResources()).get(topicPosition);
                        } else {
                            topic = null;
                        }

                        // prepare the selected topic data
                        intent.putExtra("quiz_mode", (Parcelable) mode);
                        intent.putExtra("topic", topic);
                        intent.putExtra("number_of_questions", numberOfQuestions);

                        dismiss();
                        // start quiz activity
                        startActivity(intent);
                        //new StartQuizActivityTask(QuizOptionsDialogFragment.this).execute(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }


    /*private static class StartQuizActivityTask extends AsyncTask<Intent, Void, Void> {

        private LoadingDialogFragment loadingDialogFragment;

        // creating a weak reference to the parent class in order to access non-static fields and methods.
        private WeakReference<QuizOptionsDialogFragment> dialogFragmentWeakReference;

        public StartQuizActivityTask(QuizOptionsDialogFragment context) {
            loadingDialogFragment = new LoadingDialogFragment();
            dialogFragmentWeakReference = new WeakReference<>(context);
        }

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            QuizOptionsDialogFragment context = dialogFragmentWeakReference.get();
            loadingDialogFragment.setCancelable(false);
            loadingDialogFragment.show(context.getFragmentManager(), context.TAG);
        }

        @Override
        protected Void doInBackground(Intent... intents) {
            // get the intent
            Intent intent = intents[0];
            // get weak reference
            QuizOptionsDialogFragment context = dialogFragmentWeakReference.get();
            context.startActivity(intent);
            return null;
        }

        // Runs in UI after background threed is completed.
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadingDialogFragment.dismiss();
        }
    }*/
}
