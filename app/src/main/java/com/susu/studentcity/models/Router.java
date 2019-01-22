package com.susu.studentcity.models;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.susu.studentcity.R;

public class Router {

    private static Router singleton;

    private FragmentManager fragmentManager;
    private Activity activity;

    private Fragment currentFragment;

    private Router(Fragment fragment) {
        this.fragmentManager = fragment.getActivity().getSupportFragmentManager();
        this.activity = fragment.getActivity();
    }

    private Router(AppCompatActivity appCompatActivity) {
        this.activity = appCompatActivity;
        this.fragmentManager = appCompatActivity.getSupportFragmentManager();
    }

    public static Router getInstance(Fragment fragment) {
        if(singleton == null) {
            singleton = new Router(fragment);
            return singleton;
        }
        else return singleton;
    }

    public static Router getInstance(AppCompatActivity activity) {
        if(singleton == null) {
            singleton = new Router(activity);
            return singleton;
        }
        else return singleton;
    }

    public void startFragment(Fragment newFragment, Bundle args) {
        if(args != null)
            newFragment.setArguments(args);

        currentFragment = newFragment;

        fragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.fragment_container, newFragment)
                .commit();
    }

    public void showFragment(Fragment newFragment, Bundle args) {
        if(currentFragment.getClass() == newFragment.getClass()) return;

        currentFragment = newFragment;

        if(args != null)
            newFragment.setArguments(args);

        fragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit();
    }

    public void showFragmentGone(Fragment newFragment, Bundle args) {
        if(currentFragment.getClass() == newFragment.getClass()) return;

        currentFragment = newFragment;

        if(args != null)
            newFragment.setArguments(args);

        fragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, newFragment)
                .commit();
    }

    public void redirectToCallForward(String phoneNumber) {
        Intent intentCallForward = new Intent(Intent.ACTION_DIAL);
        Uri uriPhone = Uri.parse("tel:" + phoneNumber);
        intentCallForward.setData(uriPhone);
        activity.startActivity(intentCallForward);
    }
}
