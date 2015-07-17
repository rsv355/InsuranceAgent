package com.app.insuranceagent;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import fr.ganfra.materialspinner.MaterialSpinner;


public class NewClients extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgClose;
    ListView listView;
    MaterialSpinner spCType,spGender,spMStatus;
    String[] ClientType = {"Individual","Company (fleet)"};
    String[] Gender = {"Male","Female"};
    String[] MaritalStatus = {"single","Married","Domestic Patnership","Divorced","Widowed"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclients);

        init();
    }

    private void init(){
        imgClose = (ImageView)findViewById(R.id.imgClose);
        spCType = (MaterialSpinner)findViewById(R.id.spCType);
        spGender = (MaterialSpinner)findViewById(R.id.spGender);
        spMStatus = (MaterialSpinner)findViewById(R.id.spMStatus);

        ArrayAdapter<String> ClientTypeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ClientType);
        ClientTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> Genderadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Gender);
        Genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> MaritalStatusadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MaritalStatus);
        MaritalStatusadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCType.setAdapter(ClientTypeadapter);
        spGender.setAdapter(Genderadapter);
        spMStatus.setAdapter(MaritalStatusadapter);



        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //end of main class
}
