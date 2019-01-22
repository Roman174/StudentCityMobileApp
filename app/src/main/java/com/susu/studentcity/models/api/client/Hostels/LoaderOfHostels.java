package com.susu.studentcity.models.api.client.Hostels;

import android.support.v4.app.FragmentActivity;

public class LoaderOfHostels {
    private DownloadListOfHostelsTask task;

    public LoaderOfHostels() {}

    public void download(FragmentActivity activity, DownloadListOfHostelsCallback callback){
        this.task = new DownloadListOfHostelsTask(activity, callback);
        this.task.execute();
    }

    public void cancel() {
        if(!task.isCancelled()) {
            task.cancel(true);
        }
    }
}
