package com.susu.studentcity.models.api.client.Hostels;

import com.susu.studentcity.models.database.Hostel;

class SuccessResult extends ResponseResult {
    private Hostel[] hostels;

    public SuccessResult(Hostel[] hostels) {
        this.hostels = hostels;
    }

    public Hostel[] getHostels() {
        return hostels;
    }
}
