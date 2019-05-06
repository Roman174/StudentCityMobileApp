package com.susu.studentcity.fragments.presenters;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.susu.studentcity.R;
import com.susu.studentcity.fragments.NewsFragment;
import com.susu.studentcity.models.news.NewsModel;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

public class NewsFragmentPresenter {
    private NewsFragment fragment;

    private static final String DOMAIN_PARAMETER = "domain";
    private static final String DOMAIN           = "studgorod.susu";
    private static final int COUNT_WALL          = 100;

    public NewsFragmentPresenter(NewsFragment fragment) {
        this.fragment = fragment;
    }

    private VKAccessTokenTracker accessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(@Nullable VKAccessToken oldToken, @Nullable VKAccessToken newToken) {
            if(newToken == null) {
                VKSdk.logout();
                VKSdk.login(fragment.getActivity(), (String) null);
            }
        }
    };

    private VKCallback<VKAccessToken> vkAuthCallback = new VKCallback<VKAccessToken>() {
        @Override
        public void onResult(VKAccessToken res) {
            sendRequest();
        }

        @Override
        public void onError(VKError error) {

        }
    };

    private NewsModel.ConvertCallback convertCallback = new NewsModel.ConvertCallback() {
        @Override
        public void onConvert(ArrayList<NewsModel> news) {
            fragment.hideProgress();
            fragment.showNews(news);
        }

        @Override
        public void onFail() {

        }
    };

    public void sendRequest() {

        if(!fragment.checkInternetConnection()) {
            fragment.showMessage(fragment.getString(R.string.internet_connection_is_messed));
            return;
        }

        fragment.showProgress();

        VKRequest request = VKApi
                .wall()
                .get(VKParameters
                        .from(DOMAIN_PARAMETER, DOMAIN, VKApiConst.COUNT, COUNT_WALL,
                                VKApiConst.EXTENDED, 1));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList<VKApiPost> posts = (VKList<VKApiPost>) response.parsedModel;
                NewsModel.convert(posts, convertCallback);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                fragment.hideProgress();
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                fragment.hideProgress();
            }
        });
    }



    public boolean auth() {
        if(fragment.checkInternetConnection()) {
            if(!VKSdk.isLoggedIn()) {
                VKSdk.login(fragment.getActivity(), (String) null);
                return false;
            }

            return true;
        }
        else {
            fragment.showMessage(fragment.getString(R.string.internet_connection_is_messed));
            return false;
        }
    }
    public void onAuthResult(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, vkAuthCallback);
    }

    public void startTokenTracking() {
        if(!accessTokenTracker.isTracking())
            accessTokenTracker.startTracking();
    }

    public void stopTokenTracking() {
        if(accessTokenTracker.isTracking())
            accessTokenTracker.stopTracking();
    }
}
