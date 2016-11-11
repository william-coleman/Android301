package com.week8.williamcoleman.peoplemongo.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.week8.williamcoleman.peoplemongo.Models.Users;
import com.week8.williamcoleman.peoplemongo.Network.RestClient;
import com.week8.williamcoleman.peoplemongo.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by williamcoleman on 11/11/16.
 */

public class CatchView extends RelativeLayout {
    private Context context;
    public ArrayList<Users> caughtpMon;
    private RestClient restClient;

    @Bind(R.id.imageView)
    ImageView usrAvatar;
    @Bind(R.id.id_textview)
    TextView userId;
    @Bind(R.id.name_textview)
    TextView usrName;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;


    public CatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        restClient = new RestClient();
    }
}