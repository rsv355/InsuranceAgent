package com.app.insuranceagent;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Settings extends ActionBarActivity implements View.OnClickListener {

    ImageView imgBack, imgClose;
    Button rateUs, aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();

        imgClose.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        rateUs.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    private void init() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        rateUs = (Button) findViewById(R.id.rate);
        aboutUs = (Button) findViewById(R.id.about);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;

            case R.id.imgClose:
                finish();
                break;

            case R.id.rate:
                // Open Play store page
                break;

            case R.id.about:
                InfoDialog dialog = new InfoDialog(Settings.this);
                dialog.show();
                break;
        }
    }
}
