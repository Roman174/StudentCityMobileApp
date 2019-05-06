package com.susu.studentcity.models.ImageLoader;

import android.widget.ImageView;

public class ImageLoader {
    private ImageLoaderTask task;
    private ImageView imageView;

    public ImageLoader(ImageView imageView) {
        this.imageView = imageView;
        task = new ImageLoaderTask(imageView);
    }

    public void load(String url) {
        task.execute(url);
    }

    public void cancel() {
        if(task != null || !task.isCancelled()) {
            task.cancel(true);
        }
    }
}
