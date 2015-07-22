package com.app.insuranceagent;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;


public class NewCompany extends ActionBarActivity {
    TextView txtTitle, txtSubHeading;
    ImageView imgClose, imgBack, imgSave;
    ListView listView;
    MaterialEditText edCompanyName, edCompanyAddress, edWebPage, edNotes;
    String tempCmpName, tempCmpAddress, tempCmpUrl, tempCmpNotes;
    int tempCmpId;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcompanies);
        db = new DBAdapter(NewCompany.this);


        init();

        tempCmpId = getIntent().getIntExtra("cmpID", 1);
        tempCmpName = getIntent().getStringExtra("cmpName");
        tempCmpAddress = getIntent().getStringExtra("cmpAddress");
        tempCmpUrl = getIntent().getStringExtra("cmpUrl");
        tempCmpNotes = getIntent().getStringExtra("cmpNotes");


        if (tempCmpName == null || tempCmpName.toString().trim().length() == 0) {

        } else {
            txtSubHeading.setText(tempCmpName + " :: Edit");
            edCompanyName.setText(tempCmpName);
            edCompanyAddress.setText(tempCmpAddress);
            edWebPage.setText(tempCmpUrl);
            edNotes.setText(tempCmpNotes);
        }

    }

    private void init() {
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgSave = (ImageView) findViewById(R.id.imgSave);

        edCompanyName = (MaterialEditText) findViewById(R.id.edCompanyName);
        edCompanyAddress = (MaterialEditText) findViewById(R.id.edCompanyAddress);
        edWebPage = (MaterialEditText) findViewById(R.id.edWebPage);
        edNotes = (MaterialEditText) findViewById(R.id.edNotes);

        txtSubHeading = (TextView) findViewById(R.id.txtSubHeading);

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


        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edCompanyName.getText().toString().trim().length() == 0) {
                    Toast.makeText(NewCompany.this, "Please enter company !!!", Toast.LENGTH_SHORT).show();
                } else {

                    if (tempCmpName == null || tempCmpName.toString().trim().length() == 0) {
                        processSave();
                    } else {
                        processUpdate();
                    }

                }
            }
        });

    }

    private void processUpdate() {
        String cmpName = edCompanyName.getText().toString().trim();
        String cmpAddress = edCompanyAddress.getText().toString().trim();
        String cmpWebPage = edWebPage.getText().toString().trim();
        String cmpNotes = edNotes.getText().toString().trim();


        db.open();
        if (db.updateCompanies(tempCmpId, cmpName, cmpAddress, cmpWebPage, cmpNotes))
            Toast.makeText(NewCompany.this, "Record Updated !!!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(NewCompany.this, "Record Failed !!!", Toast.LENGTH_SHORT).show();
        db.close();

        finish();
    }


    private void processSave() {
        String cmpName = edCompanyName.getText().toString().trim();
        String cmpAddress = edCompanyAddress.getText().toString().trim();
        String cmpWebPage = edWebPage.getText().toString().trim();
        String cmpNotes = edNotes.getText().toString().trim();


        db.open();
        db.insertCompanies(cmpName, cmpAddress, cmpWebPage, cmpNotes);
        Toast.makeText(NewCompany.this, "New Record Saved !!!", Toast.LENGTH_SHORT).show();
        db.close();

        finish();
    }

    //end of main class
}
