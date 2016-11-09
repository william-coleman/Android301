package com.week8.williamcoleman.peoplemongo.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.week8.williamcoleman.peoplemongo.PeopleMonGO;
import com.week8.williamcoleman.peoplemongo.R;
import com.week8.williamcoleman.peoplemongo.Riggers.SlideRigger;

/**
 * Created by williamcoleman on 11/7/16.
 */

public class MapViewStage extends IndexedStage {
    private final SlideRigger rigger;

    public MapViewStage(Application context) {
        super(MapViewStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public MapViewStage() {
        this(PeopleMonGO.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.gog_map_view;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}

