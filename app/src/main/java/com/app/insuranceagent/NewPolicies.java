package com.app.insuranceagent;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class NewPolicies extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgClose,imgBack;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpolicy);

        init();
    }

    private void init(){
        imgClose = (ImageView)findViewById(R.id.imgClose);
        imgBack = (ImageView)findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    //end of main class
}
