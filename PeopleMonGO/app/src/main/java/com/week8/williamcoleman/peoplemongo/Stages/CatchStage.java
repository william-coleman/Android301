package com.week8.williamcoleman.peoplemongo.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.week8.williamcoleman.peoplemongo.PeopleMonGO;
import com.week8.williamcoleman.peoplemongo.R;
import com.week8.williamcoleman.peoplemongo.Riggers.SlideRigger;

/**
 * Created by williamcoleman on 11/11/16.
 */

public class CatchStage extends IndexedStage {
    private final SlideRigger rigger;

    public CatchStage(Application context) {
        super(CatchStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public CatchStage() {
        this(PeopleMonGO.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.catch_view;
    }


    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}