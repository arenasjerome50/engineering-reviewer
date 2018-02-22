package com.philcst.www.engineeringreviewer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;


public class AdjustFontDialogFragment extends DialogFragment {

    private int fontSize = 0;
    private OnDialogFragmentInteraction mListener;
    private final int interval = 2;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // custom layout, get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layout = inflater.inflate(R.layout.dialog_fragment_adjust_font, null);

        SeekBar seekBar= (SeekBar) layout.findViewById(R.id.resize_seekbar);
        seekBar.setProgress(mListener.getFontSize());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 if (progress >= 12 && (progress % 2 == 0)) {
                     fontSize = progress;
                     mListener.setFontSize(progress);
                 }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "Font Size is :" + fontSize, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(layout);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogFragmentInteraction) {
            mListener = (OnDialogFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDialogFragmentInteraction");
        }
    }

    public interface OnDialogFragmentInteraction {
        void setFontSize(int fontSize);
        int getFontSize();
    }
}
