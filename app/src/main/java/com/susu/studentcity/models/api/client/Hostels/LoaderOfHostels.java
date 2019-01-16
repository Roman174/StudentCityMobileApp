package com.susu.studentcity.models.api.client.Hostels;

public class LoaderOfHostels {
    private DownloadListOfHostelsTask task;

    public LoaderOfHostels() {}

    public void download(DownloadListOfHostelsCallback callback){
        this.task = new DownloadListOfHostelsTask(callback);
        this.task.execute();
    }

    public void cancel() {
        if(!task.isCancelled()) {
            task.cancel(true);
        }
    }
}
