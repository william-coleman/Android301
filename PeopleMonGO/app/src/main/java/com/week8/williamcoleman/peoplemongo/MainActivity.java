package com.week8.williamcoleman.peoplemongo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;
import com.week8.williamcoleman.peoplemongo.Network.UserStore;
import com.week8.williamcoleman.peoplemongo.Stages.LoginStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Flow flow;
    private ScreenplayDispatcher dispatcher;

    @Bind(R.id.container)
    RelativeLayout container;

    public Bundle savedInstanceState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        flow = PeopleMonGO.getMainFlow();
        dispatcher = new ScreenplayDispatcher(this, container);
        dispatcher.setUp(flow);

        if (UserStore.getInstance().getToken() == null || UserStore.getInstance().getTokenExpiration() == null) {
            History newHistory = History.single(new LoginStage());
            flow.setHistory(newHistory, Flow.Direction.REPLACE);
        }
    }

    @Override
    public void onBackPressed() {
        if (!flow.goBack()) {
            flow.removeDispatcher(dispatcher);
            flow.setHistory(History.single(new LoginStage()), Flow.Direction.BACKWARD);
            super.onBackPressed();
        }
    }
}

