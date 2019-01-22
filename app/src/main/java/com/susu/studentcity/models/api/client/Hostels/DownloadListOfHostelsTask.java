package com.susu.studentcity.models.api.client.Hostels;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.susu.studentcity.models.api.client.ApiClient;
import com.susu.studentcity.models.database.Hostel;

import java.io.IOException;

import okhttp3.Response;

class DownloadListOfHostelsTask extends AsyncTask<Void, Void, ResponseResult> {

    public static final String API_URL_ACTION = "api/hostels/list";

    private ApiClient client;
    private DownloadListOfHostelsCallback callback;

    public DownloadListOfHostelsTask(FragmentActivity activity, DownloadListOfHostelsCallback callback) {
        client = ApiClient.getInstance(activity);
        this.callback = callback;
    }

    @Override
    protected ResponseResult doInBackground(Void... voids) {
        if(client.isOnline()) {
            try {
                Response response = client.getRequest(API_URL_ACTION);
                String jsonResponse = response.body().string();
                Hostel[] hostels = new Gson().fromJson(jsonResponse, Hostel[].class);

                return new SuccessResult(hostels);
            }
            catch (IOException exception) {
                return new ErrorResult();
            }
        }
        else {
            return new MissedInternetConnectionResult();
        }
    }

    @Override
    protected void onPostExecute(ResponseResult responseResult) {
        if(isCancelled()) return;

        if(SuccessResult.class == responseResult.getClass()) {
            SuccessResult result = (SuccessResult) responseResult;
            callback.onSuccess((result.getHostels()));
        } else if(ErrorResult.class == responseResult.getClass())
            callback.onServerError();
        else if(MissedInternetConnectionResult.class == responseResult.getClass())
            callback.onFailInternetConnection();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
