package com.susu.studentcity.models.ImageLoader;

import android.widget.ImageView;

public class ImageLoader {
    private ImageLoaderTask task;
    private ImageView imageView;
    private String url;

    public ImageLoader(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
        task = new ImageLoaderTask(imageView);
    }

    public void load() {
        task.execute(url);
    }

    public void cancel() {
        if(task != null || !task.isCancelled()) {
            task.cancel(true);
        }
    }
}
