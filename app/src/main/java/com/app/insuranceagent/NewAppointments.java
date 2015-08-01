package com.app.insuranceagent;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.insuranceagent.model.SubAppointments;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;


public class NewAppointments extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgClose,imgBack;
    ListView listView;
    DBAdapter db;
    MaterialSpinner spCType,spAgent;
    EditText edDate,edTime,edNotes;
    int tempId;
    String tempName, tempclientName, tempagentName, tempdate,temptime,notes;
    ArrayList<String> clientData;
    ArrayList<String> agentData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addappointments);
        db = new DBAdapter(NewAppointments.this);

        init();



        tempId = getIntent().getIntExtra("appID", 1);
        tempclientName = getIntent().getStringExtra("clientName");
        tempagentName = getIntent().getStringExtra("agentName");
        tempdate = getIntent().getStringExtra("date");
        temptime = getIntent().getStringExtra("time");
        notes = getIntent().getStringExtra("notes");

        if (tempName == null || tempName.toString().trim().length() == 0) {

        } else {

            edDate.setText(tempdate);
            edTime.setText(temptime);
            edNotes.setText(notes);
        }
    }

    private void init(){

        spCType = (MaterialSpinner) findViewById(R.id.spCType);
        spAgent = (MaterialSpinner) findViewById(R.id.spAgent);

        edDate = (MaterialEditText) findViewById(R.id.edDate);
        edTime = (MaterialEditText) findViewById(R.id.edTime);
        edNotes = (MaterialEditText) findViewById(R.id.edNotes);
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


        fetchClientSpinnerData();
        fetchAgentSpinnerData();
    }

    void fetchAgentSpinnerData(){

        agentData =  new ArrayList<>();
        try {

            db.open();
            Cursor c = db.getAllAgentName();
            if (c.moveToFirst()) {
                do {
                    FetchDataAgent(c);
                } while (c.moveToNext());
            }
            db.close();

            ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, agentData);
            spAgent.setAdapter(clientAdapter);

        } catch (Exception e) {
            Log.e("### Exc", e.toString());
        }
    }

    private void FetchDataAgent(Cursor c) {
        agentData.add(c.getString(0));


    }

    void fetchClientSpinnerData(){

         clientData =  new ArrayList<>();
        try {

            db.open();
            Cursor c = db.getAllClientName();
            if (c.moveToFirst()) {
                do {
                    FetchData(c);
                } while (c.moveToNext());
            }
            db.close();

            ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, clientData);
            spCType.setAdapter(clientAdapter);

        } catch (Exception e) {
            Log.e("### Exc", e.toString());
        }
    }

    private void FetchData(Cursor c) {
        clientData.add(c.getString(0));


    }

    //end of main class
}
