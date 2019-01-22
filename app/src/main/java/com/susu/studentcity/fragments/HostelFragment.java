package com.susu.studentcity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.susu.studentcity.R;
import com.susu.studentcity.fragments.presenters.HostelFragmentPresenter;
import com.susu.studentcity.models.ImageLoader.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class HostelFragment extends RootFragment {

    private ImageView hostelPhoto;

    private Button buttonCall;
    private Button buttonMap;

    private TextView hostelTitle;
    private TextView hostelAddress;
    private TextView hostelNumberFloors;
    private TextView hostelNumberStudents;
    private TextView hostelRating;

    private CircleImageView hostelManagerPhoto;
    private TextView hostelManagerName;

    private CircleImageView studentManagerPhoto;
    private TextView studentManagerName;

    private CircleImageView  cultureManagerPhoto;
    private TextView cultureManagerName;

    private CircleImageView sportManagerPhoto;
    private TextView sportManagerName;

    private ProgressBar progressBar;

    private HostelFragmentPresenter presenter;
    private ImageLoader imageLoader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hostel_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hostelPhoto = view.findViewById(R.id.hostelPhoto);

        buttonCall = view.findViewById(R.id.buttonCall);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.redirectToCall();
            }
        });

        buttonMap  = view.findViewById(R.id.buttonInMap);

        hostelTitle          = view.findViewById(R.id.hostelTitle);
        hostelAddress = view.findViewById(R.id.hostelAddress);
        hostelNumberFloors   = view.findViewById(R.id.hostelNumberFloors);
        hostelNumberStudents = view.findViewById(R.id.hostelNumberStudents);
        hostelRating         = view.findViewById(R.id.hostelRating);

        hostelManagerPhoto   = view.findViewById(R.id.hostelManagerPhoto);
        hostelManagerName    = view.findViewById(R.id.hostelManagerName);

        studentManagerPhoto  = view.findViewById(R.id.studentManagerPhoto);
        studentManagerName   = view.findViewById(R.id.studentManagerName);

        cultureManagerPhoto  = view.findViewById(R.id.cultureManagerPhoto);
        cultureManagerName   = view.findViewById(R.id.cultureManagerName);

        sportManagerPhoto    = view.findViewById(R.id.sportManagerPhoto);
        sportManagerName     = view.findViewById(R.id.sportManagerName);

        progressBar = getActivity().findViewById(R.id.progress_bar);

        presenter = new HostelFragmentPresenter(this);
        presenter.showHostel();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(imageLoader != null)
            imageLoader.cancel();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(imageLoader != null)
            imageLoader.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setText(TextView textView, String type, String text) {
        String resultText = String.format("%s: %s", type, text);
        textView.setText(resultText);
    }

    public void showHostelPhoto(String photo) {
        if(TextUtils.isEmpty(photo)) return;

        if(imageLoader == null) {
            imageLoader = new ImageLoader(hostelPhoto, photo);
            imageLoader.load();
        }
    }

    public void showHostelTitle(String title) {
        if(TextUtils.isEmpty(title)) return;
        hostelTitle.setText(title);
    }

    public void showAddress(String address) {
        String typeForAddress = getString(R.string.address);
        setText(hostelAddress, typeForAddress, address);
    }

    public void showNumberFloors(int number) {
        String typeForNumberFloors = getString(R.string.numberFloors);
        setText(hostelNumberFloors, typeForNumberFloors, String.valueOf(number));
    }

    public void showNumberStudents(int number) {
        String typeForNumberStudents = getString(R.string.numberStudent);
        setText(hostelNumberStudents, typeForNumberStudents, String.valueOf(number));
    }

    public void showRating(double rating) {
        String typeForRating = getString(R.string.rating);
        setText(hostelRating, typeForRating, String.valueOf(rating));
    }

    public void showHostelManager(String photo, String name) {

    }

    public void showStudentManager(String photo, String name) {

    }

    public void showCultureManager(String photo, String name) {

    }

    public void showSportManager(String photo, String name) {

    }
}
