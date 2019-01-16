package com.susu.studentcity.models.api.client.Hostels;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.susu.studentcity.models.api.client.ApiClient;
import com.susu.studentcity.models.database.Hostel;

import java.io.IOException;

import okhttp3.Response;

class DownloadListOfHostelsTask extends AsyncTask<Void, Void, ResponseResult> {

    public static final String API_URL_ACTION = "api/hostels/list";

    private ApiClient client;
    private DownloadListOfHostelsCallback callback;

    public DownloadListOfHostelsTask(DownloadListOfHostelsCallback callback) {
        client = ApiClient.getInstance();
        this.callback = callback;
    }

    @Override
    protected ResponseResult doInBackground(Void... voids) {
        if(client.isOnline()) {
            try {
                Response response = client.getRequest(API_URL_ACTION);
                String jsonResponse = response.body().string();
                Hostel[] hostels = new Gson().fromJson(jsonResponse, Hostel[].class);

                return new ResponseResult(true, hostels);
            }
            catch (IOException exception) {
                return new ResponseResult(false, null);
            }
        }
        else {
            return new ResponseResult(false);
        }
    }

    @Override
    protected void onPostExecute(ResponseResult responseResult) {
        if(isCancelled()) return;

        if(responseResult.isOnline()) {
            if(responseResult.isSuccess()) {
                callback.onSuccess(responseResult.getHostels());
                return;
            }
            else {
                callback.onServerError();
            }
        }
        else callback.onFailInternetConnection();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
