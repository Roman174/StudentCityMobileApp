package com.susu.studentcity.adapters.ListOfNewsAdapterCreator;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.susu.studentcity.adapters.ListNewsAdapter;
import com.susu.studentcity.models.news.NewsModel;

import java.util.ArrayList;

public class ListNewsAdapterAsyncCreator  {

    private Context context;
    private ArrayList<NewsModel> news;
    private Callback callback;
    private ListNewsAdapter.ItemClickListener itemClickListener;
    private Task task;

    private ListNewsAdapterAsyncCreator(Context context) {
        this.context = context;
    }

    public void cancel() {
        if(task.isCancelled())
            task.cancel(true);
    }

    public void create() {
        task = new Task(context);
        task.execute();
    }

    private class Task extends AsyncTask<Void, Void, ListNewsAdapter> {

        private Context context;

        private Task(Context context) {
            this.context = context;
        }

        @Override
        protected ListNewsAdapter doInBackground(Void... voids) {
            if(context == null) return null;

            try {
                ListNewsAdapter adapter = new ListNewsAdapter.Builder(context)
                        .addItemClickListener(itemClickListener)
                        .addNews(news)
                        .build();

                return adapter;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ListNewsAdapter adapter) {
            super.onPostExecute(adapter);

            if(isCancelled() || callback == null) return;

            if(adapter != null)
                callback.onComplete(adapter);
            else
                callback.onFail();
        }
    }

    public interface Callback {
        void onComplete(ListNewsAdapter adapter);
        void onFail();
    }

    public static class Builder {

        private Context context;
        private ArrayList<NewsModel> news;
        private Callback callback;
        private ListNewsAdapter.ItemClickListener itemClickListener;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder addNews(ArrayList<NewsModel> news) {
            this.news = news;
            return this;
        }

        public Builder addCallback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public Builder addItemClickListener(ListNewsAdapter.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }

        public ListNewsAdapterAsyncCreator build() {
            if(context == null) return null;

            ListNewsAdapterAsyncCreator creator = new ListNewsAdapterAsyncCreator(context);

            if(news != null) {
                creator.news = news;
            }

            if(callback != null)
                creator.callback = callback;

            if(itemClickListener != null)
                creator.itemClickListener = itemClickListener;

            return creator;
        }
    }
}
