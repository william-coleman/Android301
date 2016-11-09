package com.week8.williamcoleman.peoplemongo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.week8.williamcoleman.peoplemongo.Components.Constants;
import com.week8.williamcoleman.peoplemongo.Models.Auth;
import com.week8.williamcoleman.peoplemongo.Network.RestClient;
import com.week8.williamcoleman.peoplemongo.PeopleMonGO;
import com.week8.williamcoleman.peoplemongo.R;
import com.week8.williamcoleman.peoplemongo.Stages.LoginStage;

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

public class RegisterView extends LinearLayout {
    private Context context;

    @Bind(R.id.username_field)
    EditText usernameField;

    @Bind(R.id.email_field)
    EditText emailField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.confirm_field)
    EditText confirmField;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        String api = Constants.API_KEY;
        String avatar = "";
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void register() {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(emailField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(confirmField.getWindowToken(), 0);

        String fullname = usernameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String confirm = confirmField.getText().toString();
        String apiKey = Constants.API_KEY;
        String avatar = "avatar";

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(context, R.string.fill_out_fields, Toast.LENGTH_LONG).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, R.string.provide_valid_email, Toast.LENGTH_LONG).show();
        } else if (!password.equals(confirm)) {
            Toast.makeText(context, R.string.passwords_dont_match, Toast.LENGTH_LONG).show();
        } else {
            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);

            Auth auth = new Auth(email, fullname, avatar, apiKey, password);
            RestClient restClient = new RestClient();
            restClient.getApiService().register(auth).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
//                        Void regUser = response.body();
//                        UserStore.getInstance().setToken(regUser.getToken());
//                        UserStore.getInstance().setTokenExpiration(regUser.getExpiration());

                        Flow flow = PeopleMonGO.getMainFlow();
                        History newHistory = History.single(new LoginStage());
                        flow.setHistory(newHistory, Flow.Direction.BACKWARD);
                        Toast.makeText(context, "Registration was successful", Toast.LENGTH_LONG).show();
                    } else {
                        resetView();
                        Toast.makeText(context, getContext().getString(R.string.registration_failed) + ": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    resetView();
                    Toast.makeText(context, R.string.registration_failed, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void resetView() {
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }
}

