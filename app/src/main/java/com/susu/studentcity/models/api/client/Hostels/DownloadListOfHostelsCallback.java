package com.susu.studentcity.models.api.client.Hostels;

import com.susu.studentcity.models.database.Hostel;

public interface DownloadListOfHostelsCallback {
    void onSuccess(Hostel[] hostels);
    void onFailInternetConnection();
    void onServerError();
}
