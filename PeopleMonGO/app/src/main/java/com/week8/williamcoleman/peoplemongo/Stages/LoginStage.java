package com.week8.williamcoleman.peoplemongo.Stages;

import android.app.Application;

import com.week8.williamcoleman.peoplemongo.PeopleMonGO;
import com.week8.williamcoleman.peoplemongo.R;
import com.week8.williamcoleman.peoplemongo.Riggers.SlideRigger;

/**
 * Created by williamcoleman on 11/7/16.
 */

public class LoginStage extends IndexedStage {
    private final SlideRigger rigger;

    public LoginStage(Application context) {
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public LoginStage() {
        this(PeopleMonGO.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.login_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}

