package com.susu.studentcity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susu.studentcity.R;
import com.susu.studentcity.adapters.ListOfNewsAdapter;
import com.susu.studentcity.fragments.presenters.NewsFragmentPresenter;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

public class NewsFragment extends Fragment {

    private RecyclerView listOfNewsView;
    private NewsFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragmentView,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragmentView, savedInstanceState);

        listOfNewsView = fragmentView.findViewById(R.id.list_news_view);
        presenter = new NewsFragmentPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(presenter.auth())
            presenter.sendRequest();

        presenter.startTokenTracking();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stopTokenTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onAuthResult(requestCode, resultCode, data);
    }

    public void showNews(VKList<VKApiPost> walls) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        listOfNewsView.setLayoutManager(layoutManager);

        ListOfNewsAdapter adapter = new ListOfNewsAdapter(getContext(), walls, itemClickCallback);
        listOfNewsView.setAdapter(adapter);
    }

    ListOfNewsAdapter.ItemClickCallback itemClickCallback = new ListOfNewsAdapter.ItemClickCallback() {
        @Override
        public void onClick(VKApiPost wall) {

        }
    };
}
