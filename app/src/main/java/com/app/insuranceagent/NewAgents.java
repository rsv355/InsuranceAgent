package com.app.insuranceagent;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;


public class NewAgents extends ActionBarActivity {
    TextView txtTitle, txtSubHeading;
    ImageView imgClose, imgBack, imgSave, imgImport;
    ListView listView;
    MaterialEditText edName, edPhone, edAddress, edNotes;
    String tempName, tempAddress, tempPhone, tempNotes;
    int tempId;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addagents);
        db = new DBAdapter(NewAgents.this);

        init();

        imgImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });


        tempId = getIntent().getIntExtra("agentID", 1);
        tempName = getIntent().getStringExtra("agentName");
        tempAddress = getIntent().getStringExtra("agentAddress");
        tempPhone = getIntent().getStringExtra("agentPhone");
        tempNotes = getIntent().getStringExtra("agentNotes");


        if (tempName == null || tempName.toString().trim().length() == 0) {

        } else {
            txtSubHeading.setText(tempName + " :: Edit");
            edName.setText(tempName);
            edPhone.setText(tempPhone);
            edAddress.setText(tempAddress);
            edNotes.setText(tempNotes);
        }
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
                            edPhone.setText(phone);
                        }
                    }

                }
            }
        }
    }

    private void init() {
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgImport = (ImageView) findViewById(R.id.imgImport);
        imgSave = (ImageView) findViewById(R.id.imgSave);

        edName = (MaterialEditText) findViewById(R.id.edName);
        edPhone = (MaterialEditText) findViewById(R.id.edPhone);
        edAddress = (MaterialEditText) findViewById(R.id.edAddress);
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
                if (edName.getText().toString().trim().length() == 0) {
                    Toast.makeText(NewAgents.this, "Please enter agent details !!!", Toast.LENGTH_SHORT).show();
                } else {

                    if (tempName == null || tempName.toString().trim().length() == 0) {
                        processSave();
                    } else {
                        processUpdate();
                    }

                }
            }
        });

    }

    private void processUpdate() {
        String Name = edName.getText().toString().trim();
        String Address = edAddress.getText().toString().trim();
        String Phone = edPhone.getText().toString().trim();
        String Notes = edNotes.getText().toString().trim();


        db.open();
        if (db.updateAgents(tempId, Name, Phone, Address, Notes))
            Toast.makeText(NewAgents.this, "Record Updated !!!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(NewAgents.this, "Record Failed !!!", Toast.LENGTH_SHORT).show();
        db.close();

        finish();
    }


    private void processSave() {
        String Name = edName.getText().toString().trim();
        String Address = edAddress.getText().toString().trim();
        String Phone = edPhone.getText().toString().trim();
        String Notes = edNotes.getText().toString().trim();

        db.open();
        db.insertAgents(Name, Phone, Address, Notes);
        Toast.makeText(NewAgents.this, "New Record Saved !!!", Toast.LENGTH_SHORT).show();
        db.close();

        finish();
    }

    //end of main class
}
