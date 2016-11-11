package com.week8.williamcoleman.peoplemongo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;
import com.week8.williamcoleman.peoplemongo.Models.Auth;
import com.week8.williamcoleman.peoplemongo.Models.ImageEvent;
import com.week8.williamcoleman.peoplemongo.Network.RestClient;
import com.week8.williamcoleman.peoplemongo.Network.UserStore;
import com.week8.williamcoleman.peoplemongo.Stages.LoginStage;
import com.week8.williamcoleman.peoplemongo.Stages.ProfileStage;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.week8.williamcoleman.peoplemongo.PeopleMonGO.getMainFlow;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Flow flow;
    private ScreenplayDispatcher dispatcher;
    private static int RESULT_LOAD_IMG = 1;
    String encodedImage;
    private Context context;

    @Bind(R.id.container)
    RelativeLayout container;


    private Menu menu;
    public Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        flow = getMainFlow();
        dispatcher = new ScreenplayDispatcher(this, container);
        dispatcher.setUp(flow);

        if (UserStore.getInstance().getToken() == null || UserStore.getInstance().getTokenExpiration() == null) {
            History newHistory = History.single(new LoginStage());
            flow.setHistory(newHistory, Flow.Direction.REPLACE);
        }

        if (Build.VERSION.SDK_INT >= 23) {
            if (!(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            if (!(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_menu:
                Flow flow = PeopleMonGO.getMainFlow();
                History newHistory = flow.getHistory().buildUpon()
                        .push(new ProfileStage())
                        .build();
                flow.setHistory(newHistory, Flow.Direction.FORWARD);
                return true;
            case R.id.logout_item:
                UserStore.getInstance().setToken(null);
                flow = PeopleMonGO.getMainFlow();
                newHistory = flow.getHistory().buildUpon()
                        .push(new LoginStage())
                        .build();
                flow.setHistory(newHistory, Flow.Direction.REPLACE);
            default:
                return super.onOptionsItemSelected(item);
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

    public void getImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                        null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imageString = cursor.getString(columnIndex);
                cursor.close();


                Bitmap bitmap = BitmapFactory.decodeFile(imageString);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream); //bm is the bitmap object
                byte[] overByte = outputStream.toByteArray();
                encodedImage = Base64.encodeToString(overByte, Base64.DEFAULT);

                imagePost(encodedImage);

                EventBus.getDefault().post(new ImageEvent(imageString));
            } else {
                Toast.makeText(this, R.string.retrieving_image_failed, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.retrieving_image_failed, Toast.LENGTH_LONG).show();
        }
    }

    public void imagePost(String encodedImage) {
        Auth avatar = new Auth(null, encodedImage);
        RestClient restClient = new RestClient();
        restClient.getApiService().editProfile(avatar).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                } else {
                    Toast.makeText(context, getString(R.string.failure_uploading_image) + ":" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, R.string.failure_uploading_image, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void showMenuItem(boolean show) {
        if (menu != null) {
            menu.findItem(R.id.options_menu).setVisible(true);
        }
    }
}

