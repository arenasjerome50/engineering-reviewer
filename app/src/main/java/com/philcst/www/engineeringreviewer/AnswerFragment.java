package com.philcst.www.engineeringreviewer;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.philcst.www.engineeringreviewer.view.MathView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnswerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnswerFragment extends Fragment implements View.OnClickListener{
    // the fragment initialization parameters.
    public static final String ARG_ANSWER = "answer";
    public static final String ARG_RESULT = "result";
    public static final String ARG_END = "end";

    private String answer;
    private boolean isCorrect;
    private boolean isEnd;

    private OnFragmentInteractionListener mListener;

    private TextView feedBackTextView;
    private MathView answerView;

    public AnswerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param answer The Correct Answer.
     * @param result If it is right or wrong.
     * @return A new instance of fragment AnswerFragment.
     */
    public static AnswerFragment newInstance(String answer, boolean result, boolean end) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ANSWER, answer);
        args.putBoolean(ARG_RESULT, result);
        args.putBoolean(ARG_END, end);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            answer = getArguments().getString(ARG_ANSWER);
            isCorrect = getArguments().getBoolean(ARG_RESULT);
            isEnd = getArguments().getBoolean(ARG_END);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        feedBackTextView = (TextView) view.findViewById(R.id.feedback_textview);
        answerView = (MathView) view.findViewById(R.id.correct_answer_view);
        Button nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);
        if (isEnd) {
            nextButton.setText("End");
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String htmlCode;
        if (isCorrect) {
            htmlCode = "<h2>You are correct!</h2>";
        } else {
            htmlCode = "<h2>You are wrong!</h2><br><p>The answer is:</p>";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            feedBackTextView.setText(Html.fromHtml(htmlCode,Html.FROM_HTML_MODE_COMPACT));
        } else {
            feedBackTextView.setText(Html.fromHtml(htmlCode));
        }

        answerView.setText(answer);
    }

    @Override
    public void onClick(View v) {
        mListener.onFragmentNextQuestion();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentNextQuestion();
    }
}
