package com.newproject.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView=findViewById(R.id.image1);
        ProgressBar progressBar=findViewById(R.id.loadingProgressBar);
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ConnectivityManager manager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                assert manager != null;
                if (manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED ||
                        manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED){


                }else{
                    Toast.makeText(SplashActivity.this, "no Internet connection", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFinish() {
                Prefmanager prefmanager = new Prefmanager(getApplicationContext());
                if (prefmanager.UserLogin()){
                  startActivity(new Intent(getApplicationContext(),MainActivity.class));
                  finish();
                 }else{
                    startActivity(new Intent(getApplicationContext(),Login_Activity.class));
                    finish();
                }
            }
        }.start();
    }
}
