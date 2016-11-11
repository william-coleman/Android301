package com.week8.williamcoleman.peoplemongo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    @Bind(R.id.avatar)
    ImageView avatar;

    @Bind(R.id.upload_picture)
    Button uploadPicture;

    @Bind(R.id.edit_name)
    EditText editName;

    @Bind(R.id.name_button)
    Button nameBtn;


    public ProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        ButterKnife.bind(this);
    }
}
