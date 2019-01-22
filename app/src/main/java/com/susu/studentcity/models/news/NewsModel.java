package com.susu.studentcity.models.news;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

public class NewsModel {
    private String text;
    private String photo;

    public NewsModel(String text, String photo) {
        this.text = text;
        this.photo = photo;
    }

    public String getText() {
        return text;
    }

    public String getPhoto() {
        return photo;
    }

    public static void convert(VKList<VKApiPost> posts, ConvertCallback callback) {
        Task task = new Task(posts, callback);
        task.execute();
    }

    public interface ConvertCallback {
        void onConvert(ArrayList<NewsModel> news);
        void onFail();
    }

    private static class Task extends AsyncTask<Void, Void, ArrayList<NewsModel>> {

        private VKList<VKApiPost> posts;
        private ConvertCallback callback;

        Task(VKList<VKApiPost> posts, ConvertCallback callback) {
            this.posts = posts;
            this.callback = callback;
        }

        @Override
        protected ArrayList<NewsModel> doInBackground(Void... voids) {
            ArrayList<NewsModel> news = new ArrayList<>();
            for (VKApiPost post :
            posts) {
                if(isCancelled()) return null;

                if(TextUtils.isEmpty(post.text) || TextUtils.isEmpty(getPhoto(post)))
                    continue;

                String textPost = post.text;
                String photoPost = getPhoto(post);

                news.add(new NewsModel(textPost, photoPost));
            }

            return news;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsModel> news) {
            super.onPostExecute(news);
            if(news == null) return;

            if(posts != null && posts.size() != 0 && news.size() == 0)
                callback.onFail();
            else
                callback.onConvert(news);

        }

        private String getPhoto(VKApiPost post) {
            if(post.attachments.getCount() > 0) {
                for (VKAttachments.VKApiAttachment attachment :
                        post.attachments) {

                    if(attachment.getType().equals("photo")) {
                        VKApiPhoto photo = (VKApiPhoto) attachment;

                        if(photo != null) {
                            if(photo.photo_1280 != null)
                                return photo.photo_1280;
                            else if(photo.photo_604 != null)
                                return photo.photo_604;
                            else return null;
                        }
                    }
                }
            }
            return null;
        }
    }
}
