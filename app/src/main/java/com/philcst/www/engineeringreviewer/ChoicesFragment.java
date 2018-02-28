package com.philcst.www.engineeringreviewer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philcst.www.engineeringreviewer.view.MathView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link View.OnClickListener} interface
 * to handle interaction events.
 * Use the {@link ChoicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoicesFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters.
    public static final String ARG_CHOICE_A = "choice_a";
    public static final String ARG_CHOICE_B = "choice_b";
    public static final String ARG_CHOICE_C = "choice_c";
    public static final String ARG_CHOICE_D = "choice_d";
    public static final String ACTION_SET_NEW_CHOICES = "set_new_choices";

    // choices
    private MathView mathViewA, mathViewB, mathViewC, mathViewD;
    private String choiceA, choiceB, choiceC, choiceD;

    // for handling events
    private OnFragmentChoiceListener mListener;

    //private BroadcastReceiver receiver;

    public ChoicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param choiceA choice a.
     * @param choiceB choice b.
     * @param choiceC choice c.
     * @param choiceD choice d.
     * @return A new instance of fragment ChoicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChoicesFragment newInstance(String choiceA, String choiceB, String choiceC, String choiceD) {
        ChoicesFragment fragment = new ChoicesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CHOICE_A, choiceA);
        args.putString(ARG_CHOICE_B, choiceB);
        args.putString(ARG_CHOICE_C, choiceC);
        args.putString(ARG_CHOICE_D, choiceD);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            choiceA = getArguments().getString(ARG_CHOICE_A);
            choiceB = getArguments().getString(ARG_CHOICE_B);
            choiceC = getArguments().getString(ARG_CHOICE_C);
            choiceD = getArguments().getString(ARG_CHOICE_D);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_choices, container, false);
        // set choices views
        mathViewA = (MathView) layout.findViewById(R.id.choice_math_view_a);
        mathViewB = (MathView) layout.findViewById(R.id.choice_math_view_b);
        mathViewC = (MathView) layout.findViewById(R.id.choice_math_view_c);
        mathViewD = (MathView) layout.findViewById(R.id.choice_math_view_d);
        CardView cardA = (CardView) layout.findViewById(R.id.choice_card_view_a);
        CardView cardB = (CardView) layout.findViewById(R.id.choice_card_view_b);
        CardView cardC = (CardView) layout.findViewById(R.id.choice_card_view_c);
        CardView cardD = (CardView) layout.findViewById(R.id.choice_card_view_d);
        //nextButton = (Button) findViewById(R.id.next_button);

        mathViewA.setText(choiceA);
        mathViewB.setText(choiceB);
        mathViewC.setText(choiceC);
        mathViewD.setText(choiceD);

        cardA.setOnClickListener(this);
        cardB.setOnClickListener(this);
        cardC.setOnClickListener(this);
        cardD.setOnClickListener(this);

        return layout;
    }

    public void setChoices(String choiceA, String choiceB, String choiceC, String choiceD) {
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        mathViewA.setText(choiceA);
        mathViewB.setText(choiceB);
        mathViewC.setText(choiceC);
        mathViewD.setText(choiceD);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentChoiceListener) {
            mListener = (OnFragmentChoiceListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentChoiceListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //getActivity().unregisterReceiver(receiver);
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        mListener.onFragmentChoiceSelect(v);
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
    public interface OnFragmentChoiceListener {
        void onFragmentChoiceSelect(View v);
    }
}
