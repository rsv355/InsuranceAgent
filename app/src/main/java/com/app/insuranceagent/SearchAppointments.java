package com.app.insuranceagent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class SearchAppointments extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgBack,imgNew;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_appointments);

        init();
    }

    private void init(){
        imgNew = (ImageView)findViewById(R.id.imgNew);
        listView= (ListView)findViewById(R.id.listView);

        imgBack = (ImageView)findViewById(R.id.imgBack);


        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(SearchAppointments.this,NewAppointments.class);
                startActivity(i1);

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //end of main class
}