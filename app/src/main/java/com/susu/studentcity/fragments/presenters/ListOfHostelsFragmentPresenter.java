package com.susu.studentcity.fragments.presenters;

import com.susu.studentcity.R;
import com.susu.studentcity.fragments.ListOfHostelsFragment;
import com.susu.studentcity.models.api.client.Hostels.DownloadListOfHostelsCallback;
import com.susu.studentcity.models.api.client.Hostels.LoaderOfHostels;
import com.susu.studentcity.models.database.Hostel;

public class ListOfHostelsFragmentPresenter {
    private ListOfHostelsFragment fragment;
    private LoaderOfHostels loader;

    public ListOfHostelsFragmentPresenter(ListOfHostelsFragment fragment) {
        this.fragment = fragment;
    }

    public void downloadListOfHostels() {
        loader = new LoaderOfHostels();
        fragment.showProgress();
        loader.download(new DownloadListOfHostelsCallback() {
            @Override
            public void onSuccess(Hostel[] hostels) {
                fragment.hideProgress();
                fragment.showListHostel(hostels);
            }

            @Override
            public void onFailInternetConnection() {
                fragment.hideProgress();
                fragment.showMessage(fragment.getString(R.string.internet_connection_is_messed));
            }

            @Override
            public void onServerError() {
                fragment.hideProgress();
                fragment.showMessage(fragment.getString(R.string.error_downloading_data));
            }
        });
    }

    public void cancelDownloading() {
        loader.cancel();
    }
}
