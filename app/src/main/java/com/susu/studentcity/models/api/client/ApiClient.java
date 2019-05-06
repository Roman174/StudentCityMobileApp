package com.susu.studentcity.models.api.client;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {
    private FragmentActivity activity;
    public static final String API_URL = "http://192.168.1.46/";

    private OkHttpClient client;
    private static final int TIMEOUT = 30;
    private static final String MEDIA_TYPE = "application/json";

    private static ApiClient singleton = null;

    private ApiClient(FragmentActivity activity) {
        client = new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
        this.activity = activity;
    }

    public static ApiClient getInstance(FragmentActivity activity) {
        if (singleton == null) {
            singleton = new ApiClient(activity);
            return singleton;
        } else return singleton;
    }

    public Response POSTRequest(String action, Object data) throws IOException {

        MediaType mediaType = MediaType.parse(MEDIA_TYPE);
        String jsonData = new Gson().toJson(data);

        RequestBody body = RequestBody
                .create(mediaType, jsonData);

        String url = String.format("%s%s", API_URL, action);

        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        return client.newCall(request).execute();
    }

    public Response getRequest(String action) throws IOException {

        String url = String.format("%s%s", API_URL, action);

        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        return client.newCall(request).execute();
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? false : networkInfo.isConnected();
    }


}