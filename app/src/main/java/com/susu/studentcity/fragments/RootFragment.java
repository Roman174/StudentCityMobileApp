package com.susu.studentcity.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.susu.studentcity.R;

public class RootFragment extends Fragment {
    protected ProgressBar progressBar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = getActivity().findViewById(R.id.progress_bar);
    }

    public boolean checkInternetConnection() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo == null ? false : networkInfo.isConnected();
    }

    public void showMessage(String message) {
        Snackbar.make(getView().getRootView(), message, Snackbar.LENGTH_LONG).show();
    }

    public void showProgress() {
        if(progressBar.getVisibility() != View.VISIBLE)
            progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        if(progressBar.getVisibility() != View.INVISIBLE)
            progressBar.setVisibility(View.INVISIBLE);
    }
}