package com.philcst.www.engineeringreviewer;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philcst.www.engineeringreviewer.data.QuizMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizMenuFragment extends Fragment implements View.OnClickListener{

    CardView normalMode;
    CardView timedMode;
    CardView vitaliMode;


    public QuizMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quiz_menu, container, false);
        normalMode = (CardView) rootView.findViewById(R.id.card_normal_mode);
        timedMode = (CardView) rootView.findViewById(R.id.card_timed_mode);
        vitaliMode = (CardView) rootView.findViewById(R.id.card_vitali_mode);

        normalMode.setOnClickListener(this);
        timedMode.setOnClickListener(this);
        vitaliMode.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        QuizMode mode;
        switch (id) {
            case R.id.card_normal_mode:
                mode = QuizMode.NORMAL;
                break;
            case R.id.card_timed_mode:
                mode = QuizMode.TIMED;
                break;
            case R.id.card_vitali_mode:
                mode = QuizMode.VITALI_3;
                break;
            default:
                return;
        }
        DialogFragment quizOptionDialog = new QuizOptionsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("quiz_mode", mode);
        quizOptionDialog.setArguments(args);
        quizOptionDialog.show(getChildFragmentManager(), "QUIZ_MENU");
    }
}
