package com.susu.studentcity.models.ImageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public ImageLoaderTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        String url = urls.length > 0 ? urls[0] : null;
        Bitmap bitmap = null;

        if(url == null) return bitmap;

        try {
            InputStream inputStream = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        catch (Exception e) {
            Log.e("download image error", e.getMessage());
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap == null) return;

        imageView.setImageBitmap(bitmap);
    }
}
