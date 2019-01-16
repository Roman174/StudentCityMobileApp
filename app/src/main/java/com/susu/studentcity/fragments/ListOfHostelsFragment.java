package com.susu.studentcity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.susu.studentcity.R;
import com.susu.studentcity.adapters.HostelListAdapter;
import com.susu.studentcity.fragments.presenters.ListOfHostelsFragmentPresenter;
import com.susu.studentcity.models.Router;
import com.susu.studentcity.models.database.Hostel;

public class ListOfHostelsFragment extends Fragment
        implements HostelListAdapter.onItemClickListener {

    private RecyclerView listOfHostelsView;
    private ProgressBar progressBar;

    private ListOfHostelsFragmentPresenter presenter;

    private Router router;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_of_hostels_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View fragmentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragmentView, savedInstanceState);

        listOfHostelsView = fragmentView.findViewById(R.id.list_of_hostel);

        progressBar = getActivity().findViewById(R.id.progress_bar);

        presenter = new ListOfHostelsFragmentPresenter(this);
        presenter.downloadListOfHostels();

        router = new Router(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.cancelDownloading();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.cancelDownloading();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.downloadListOfHostels();
    }

    public void showListHostel(Hostel[] hostels) {
        HostelListAdapter adapter = new HostelListAdapter(getContext(), hostels, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false);
        listOfHostelsView.setLayoutManager(linearLayoutManager);
        listOfHostelsView.setAdapter(adapter);
    }


    public void showMessage(String msg) {
        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(Hostel hostel) {
        Bundle args = new Bundle();
        args.putSerializable(Hostel.SERIALIZABLE_KEY, hostel);

        router.showFragment(new HostelFragment(), args);
    }
}
