package com.susu.studentcity.fragments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
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

public class ListOfHostelsFragment extends RootFragment
        implements HostelListAdapter.onItemClickListener, LifecycleOwner {

    private LifecycleRegistry lifecycleRegistry;

    private RecyclerView listOfHostelsView;

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

        lifecycleRegistry = new LifecycleRegistry(this);
        listOfHostelsView = fragmentView.findViewById(R.id.list_of_hostel);
        presenter = new ListOfHostelsFragmentPresenter(this);
        router = new Router(this);

        setRetainInstance(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.cancelDownloading();
        hideProgress();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.cancelDownloading();
        hideProgress();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelDownloading();
        hideProgress();
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

    @Override
    public void onItemClick(Hostel hostel) {
        Bundle args = new Bundle();
        args.putSerializable(Hostel.SERIALIZABLE_KEY, hostel);
        router.showFragment(new HostelFragment(), args);
    }

    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
