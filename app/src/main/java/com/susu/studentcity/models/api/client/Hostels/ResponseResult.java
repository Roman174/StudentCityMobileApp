package com.susu.studentcity.models.api.client.Hostels;

import com.susu.studentcity.models.database.Hostel;

class ResponseResult {
    private boolean isOnline;
    private boolean isSuccess;
    private Hostel[] hostels;

    public ResponseResult(boolean isOnline) {
        this.isOnline = isOnline;
        this.isSuccess = false;
        this.hostels = null;
    }

    public ResponseResult(boolean isSuccess, Hostel[] hostels) {
        this.isSuccess = isSuccess;
        this.hostels = hostels;
        this.isOnline = true;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public Hostel[] getHostels() {
        return hostels;
    }
}
