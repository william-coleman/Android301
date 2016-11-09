package com.week8.williamcoleman.peoplemongo;

import android.app.Application;

import com.week8.williamcoleman.peoplemongo.Stages.MapViewStage;

import flow.Flow;
import flow.History;

/**
 * Created by williamcoleman on 11/7/16.
 */

public class PeopleMonGO extends Application {
    private static PeopleMonGO application;
    public final Flow mainFlow = new Flow(History.single(new MapViewStage()));

    public static final String API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net:443/";


    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static PeopleMonGO getInstance() {
        return application;
    }

    public static Flow getMainFlow() {
        return getInstance().mainFlow;
    }
}