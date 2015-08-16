package com.app.insuranceagent;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
    ImageView imgClose, imgBack, imgSave, imgImport;
    ListView listView;
    MaterialEditText edCompanyName, edCompanyAddress, edWebPage, edNotes, edCompanyContact;
    String tempCmpName, tempCmpAddress, tempCmpUrl, tempCmpNotes, tempCmpContact;
    int tempCmpId;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcompanies);
        db = new DBAdapter(NewCompany.this);

        init();

        tempCmpId = getIntent().getIntExtra("cmpID", 1);
        tempCmpContact = getIntent().getStringExtra("cmpContact");
        tempCmpName = getIntent().getStringExtra("cmpName");
        tempCmpAddress = getIntent().getStringExtra("cmpAddress");
        tempCmpUrl = getIntent().getStringExtra("cmpUrl");
        tempCmpNotes = getIntent().getStringExtra("cmpNotes");


        if (tempCmpName == null || tempCmpName.toString().trim().length() == 0) {

        } else {
            txtSubHeading.setText(tempCmpName + " :: Edit");
            edCompanyName.setText(tempCmpName);
            edCompanyContact.setText(tempCmpContact);
            edCompanyAddress.setText(tempCmpAddress);
            edWebPage.setText(tempCmpUrl);
            edNotes.setText(tempCmpNotes);
        }

    }

    private void init() {
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        imgImport = (ImageView) findViewById(R.id.imgImport);

        edCompanyContact = (MaterialEditText) findViewById(R.id.edCompanyContact);
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

        imgImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 100);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Uri contact = data.getData();
                ContentResolver cr = getContentResolver();

                Cursor c = managedQuery(contact, null, null, null, null);
                //      c.moveToFirst();

                while (c.moveToNext()) {
                    String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

                    if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                        while (pCur.moveToNext()) {
                            String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            edCompanyContact.setText(phone);
                        }
                    }

                }
            }
        }
    }

    private void processUpdate() {
        String cmpName = edCompanyName.getText().toString().trim();
        String cmpAddress = edCompanyAddress.getText().toString().trim();
        String cmpContact = edCompanyContact.getText().toString();
        String cmpWebPage = edWebPage.getText().toString().trim();
        String cmpNotes = edNotes.getText().toString().trim();


        db.open();
        if (db.updateCompanies(tempCmpId, cmpName, cmpAddress, cmpContact, cmpWebPage, cmpNotes))
            Toast.makeText(NewCompany.this, "Record Updated !!!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(NewCompany.this, "Record Failed !!!", Toast.LENGTH_SHORT).show();
        db.close();

        finish();
    }


    private void processSave() {
        String cmpName = edCompanyName.getText().toString().trim();
        String cmpAddress = edCompanyAddress.getText().toString().trim();
        String cmpContact = edCompanyContact.getText().toString().trim();
        String cmpWebPage = edWebPage.getText().toString().trim();
        String cmpNotes = edNotes.getText().toString().trim();


        db.open();
        db.insertCompanies(cmpName, cmpAddress, cmpContact, cmpWebPage, cmpNotes);
        Toast.makeText(NewCompany.this, "New Record Saved !!!", Toast.LENGTH_SHORT).show();
        db.close();

        finish();
    }

    //end of main class
}
