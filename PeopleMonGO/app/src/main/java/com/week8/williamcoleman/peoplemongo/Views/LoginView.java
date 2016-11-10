package com.week8.williamcoleman.peoplemongo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.week8.williamcoleman.peoplemongo.Components.Constants;
import com.week8.williamcoleman.peoplemongo.MainActivity;
import com.week8.williamcoleman.peoplemongo.Models.Auth;
import com.week8.williamcoleman.peoplemongo.Network.RestClient;
import com.week8.williamcoleman.peoplemongo.Network.UserStore;
import com.week8.williamcoleman.peoplemongo.PeopleMonGO;
import com.week8.williamcoleman.peoplemongo.R;
import com.week8.williamcoleman.peoplemongo.Stages.MapViewStage;
import com.week8.williamcoleman.peoplemongo.Stages.RegisterStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by williamcoleman on 11/7/16.
 */

public class LoginView extends LinearLayout {
    private Context context;

    @Bind(R.id.username_field)
    EditText usernameField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        ((MainActivity)context).showMenuItem(false);
    }

    @OnClick(R.id.register_button)
    public void showRegisterView() {
        Flow flow = PeopleMonGO.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new RegisterStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    @OnClick(R.id.login_button)
    public void login() {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);

        String email = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, R.string.please_provide_username_password, Toast.LENGTH_LONG).show();
        } else {
            loginButton.setEnabled(false);
            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);

            RestClient restClient = new RestClient();
            restClient.getApiService().login(Constants.GRANT_TYPE, email, password).enqueue(new Callback<Auth>() {
                @Override
                public void onResponse(Call<Auth> call, Response<Auth> response) {
                    if (response.isSuccessful()) {
                        Auth authAc = response.body();
                        UserStore.getInstance().setToken(authAc.getAccess_Token());
                        UserStore.getInstance().setTokenExpiration(authAc.getTokenExpiration());
                        Toast.makeText(context, getContext().getString(R.string.login_successful), Toast.LENGTH_LONG).show();

                        Flow flow = PeopleMonGO.getMainFlow();
                        History newHistory = History.single(new MapViewStage());
                        flow.setHistory(newHistory, Flow.Direction.BACKWARD);
                    } else {
                        resetView();
                        Toast.makeText(context, getContext().getString(R.string.login_failed) + ": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Auth> call, Throwable t) {
                    resetView();
                    Toast.makeText(context, R.string.login_failed, Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        }
    }

    private void resetView() {
        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }
}
