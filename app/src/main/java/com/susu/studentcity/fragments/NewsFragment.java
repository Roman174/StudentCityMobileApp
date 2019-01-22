package com.susu.studentcity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susu.studentcity.R;
import com.susu.studentcity.adapters.ListNewsAdapter;
import com.susu.studentcity.adapters.ListOfNewsAdapterCreator.ListNewsAdapterAsyncCreator;
import com.susu.studentcity.fragments.presenters.NewsFragmentPresenter;
import com.susu.studentcity.models.news.NewsModel;

import java.util.ArrayList;

public class NewsFragment extends RootFragment {

    private RecyclerView listNewsView;
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

        listNewsView = fragmentView.findViewById(R.id.list_news_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        listNewsView.setLayoutManager(layoutManager);
        presenter = new NewsFragmentPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(presenter.auth()) {
            presenter.sendRequest();
        }
        presenter.startTokenTracking();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stopTokenTracking();

        if(creatorAdapter != null)
            creatorAdapter.cancel();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onAuthResult(requestCode, resultCode, data);
    }

    ListNewsAdapterAsyncCreator creatorAdapter;
    public void showNews(ArrayList<NewsModel> news) {

        if(listNewsView.getAdapter() != null) {
            ListNewsAdapter adapter = (ListNewsAdapter) listNewsView.getAdapter();
            adapter.update(news);
            return;
        }

        creatorAdapter = new ListNewsAdapterAsyncCreator.Builder(getContext())
                .addNews(news)
                .addCallback(creatorCallback)
                .build();

        if(creatorAdapter != null)
            creatorAdapter.create();
    }

    ListNewsAdapterAsyncCreator.Callback creatorCallback = new ListNewsAdapterAsyncCreator.Callback() {
        @Override
        public void onComplete(ListNewsAdapter adapter) {
            listNewsView.setAdapter(adapter);
            hideProgress();
        }

        @Override
        public void onFail() {

        }
    };
}
