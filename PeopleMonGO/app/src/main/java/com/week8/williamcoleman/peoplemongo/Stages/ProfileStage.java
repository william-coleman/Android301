package com.week8.williamcoleman.peoplemongo.Stages;

import android.app.Application;

import com.week8.williamcoleman.peoplemongo.PeopleMonGO;
import com.week8.williamcoleman.peoplemongo.R;
import com.week8.williamcoleman.peoplemongo.Riggers.SlideRigger;

/**
 * Created by williamcoleman on 11/9/16.
 */

public class ProfileStage extends IndexedStage {
    private final SlideRigger rigger;

    public ProfileStage(Application context) {
        super(ProfileStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public ProfileStage() {
        this(PeopleMonGO.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.profile_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
