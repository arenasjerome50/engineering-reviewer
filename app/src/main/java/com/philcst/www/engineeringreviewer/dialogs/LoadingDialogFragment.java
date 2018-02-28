package com.philcst.www.engineeringreviewer.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.philcst.www.engineeringreviewer.R;


public class LoadingDialogFragment extends DialogFragment {

    private String TAG = getClass().getSimpleName();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // custom layout, get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.dialog_fragment_loading, null);

        builder.setView(layout);
        return builder.create();
    }
}
