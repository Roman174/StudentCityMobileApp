package com.susu.studentcity.fragments.presenters;

import android.os.Bundle;

import com.susu.studentcity.R;
import com.susu.studentcity.fragments.HostelFragment;
import com.susu.studentcity.models.database.Coordinates;
import com.susu.studentcity.models.database.Hostel;
import com.susu.studentcity.models.database.Post;
import com.susu.studentcity.models.database.Stuff;

public class HostelFragmentPresenter {
    private HostelFragment fragment;

    public HostelFragmentPresenter(HostelFragment fragment) {
        this.fragment = fragment;
    }

    public void showHostel() {
        Bundle args = fragment.getArguments();
        if(args == null) {
            fragment.showMessage(fragment.getString(R.string.error_downloading_data));
            return;
        }

        Hostel hostel = (Hostel) args.getSerializable(Hostel.SERIALIZABLE_KEY);

        if(hostel == null) {
            fragment.showMessage(fragment.getString(R.string.error_downloading_data));
            return;
        }

        fragment.showHostelTitle(hostel.getTitle());
        fragment.showHostelPhoto(hostel.getPhoto());
        fragment.showAddress(hostel.getAddress());
        fragment.showNumberFloors(hostel.getNumberFloors());
        fragment.showNumberStudents(hostel.getNumberStudents());
        fragment.showRating(hostel.getRating());

        Stuff hostelManager = hostel.getStuff(Post.HOSTEL_MANAGER_POST);
        if(hostelManager != null) {
            String fullName = String.format("%s %s %s", hostelManager.getSurname(),
                    hostelManager.getFirstname(), hostelManager.getPatronymic());

            fragment.showHostelManager(hostelManager.getPhoto(), fullName);
        }

        Stuff studentManager = hostel.getStuff(Post.STUDENT_MANAGER_POST);
        if(studentManager != null) {
            String fullName = String.format("%s %s %s", studentManager.getSurname(),
                    studentManager.getFirstname(), studentManager.getPatronymic());

            fragment.showStudentManager(studentManager.getPhoto(), fullName);
        }

        Stuff cultureManager = hostel.getStuff(Post.CULTURE_MANAGER_POST);
        if(cultureManager != null) {
            String fullName = String.format("%s %s %s", cultureManager.getSurname(),
                    cultureManager.getFirstname(), cultureManager.getPatronymic());

            fragment.showCultureManager(cultureManager.getPhoto(), fullName);
        }

        Stuff sportManager = hostel.getStuff(Post.SPORT_MANAGER_POST);
        if(sportManager != null) {

            String fullName = String.format("%s %s %s", sportManager.getSurname(),
                    sportManager.getFirstname(), sportManager.getPatronymic());

            fragment.showSportManager(sportManager.getPhoto(), fullName);
        }
    }

    public void redirectToCall(String phone) {

    }

    public void redirectToMap(Coordinates coordinates) {

    }
}
