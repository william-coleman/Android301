package com.week8.williamcoleman.peoplemongo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.week8.williamcoleman.peoplemongo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by williamcoleman on 11/9/16.
 */

public class ProfileView extends LinearLayout {
    private Context context;

    @Bind(R.id.show_username_field)
    TextView showUsernameField;

    @Bind(R.id.show_email_field)
    TextView showEmailField;

    public ProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        ButterKnife.bind(this);
    }
}
