package com.app.insuranceagent;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import fr.ganfra.materialspinner.MaterialSpinner;


public class NewClients extends ActionBarActivity {
    TextView txtTitle, txtSubHeading;
    ImageView imgClose, imgBack, imgSave, imgImportCell, imgImportHome, imgImportWork, imgImportEmergency, imgImportEmergencyPhone;
    ListView listView;
    MaterialSpinner spCType, spGender, spMStatus;
    String[] ClientType = {"Individual", "Company (fleet)"};
    String[] Gender = {"Male", "Female"};
    String[] MaritalStatus = {"single", "Married", "Domestic Patnership", "Divorced", "Widowed"};
    MaterialEditText edId, edName, edBirthday, edEmail, edAddress, edCellPhone, edHomePhone, edWorkPhone, edEmergencycontact, edEmergencyPhone, edNotes;
    DBAdapter db;
    String temp_edId, temp_edName, temp_edBirthday, temp_edEmail, temp_edAddress, temp_edCellPhone, temp_edHomePhone, temp_edWorkPhone, temp_edEmergencycontact, temp_edEmergencyPhone, temp_edNotes;
    int mainID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclients);

        db = new DBAdapter(NewClients.this);

        init();

        mainID = getIntent().getIntExtra("mainID", 1);
        temp_edId = getIntent().getStringExtra("clientID");
        temp_edName = getIntent().getStringExtra("clientName");
        temp_edBirthday = getIntent().getStringExtra("clientBdy");
        temp_edEmail = getIntent().getStringExtra("clientEmail");
        temp_edAddress = getIntent().getStringExtra("clientAdd");

        temp_edCellPhone = getIntent().getStringExtra("clientCellPhone");
        temp_edHomePhone = getIntent().getStringExtra("clientHomePhone");
        temp_edWorkPhone = getIntent().getStringExtra("clientWorkPhone");
        temp_edEmergencycontact = getIntent().getStringExtra("clientEMEContact");
        temp_edEmergencyPhone = getIntent().getStringExtra("clientEMEPhone");
        temp_edNotes = getIntent().getStringExtra("clientNotes");


        if (temp_edName == null || temp_edName.toString().trim().length() == 0) {

        } else {
            txtSubHeading.setText(temp_edName + " :: Edit");
            edId.setText(temp_edId);
            edName.setText(temp_edName);

            edBirthday.setText(temp_edBirthday);
            edEmail.setText(temp_edEmail);
            edAddress.setText(temp_edAddress);
            edCellPhone.setText(temp_edCellPhone);

            edHomePhone.setText(temp_edHomePhone);
            edWorkPhone.setText(temp_edWorkPhone);
            edEmergencycontact.setText(temp_edEmergencycontact);
            edEmergencyPhone.setText(temp_edEmergencyPhone);

            edNotes.setText(temp_edNotes);

        }
    }

    private void init() {
        txtSubHeading = (TextView) findViewById(R.id.txtSubHeading);

        imgSave = (ImageView) findViewById(R.id.imgSave);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgImportCell = (ImageView) findViewById(R.id.imgImportCell);
        imgImportHome = (ImageView) findViewById(R.id.imgImportHome);
        imgImportWork = (ImageView) findViewById(R.id.imgImportWork);
        imgImportEmergency = (ImageView) findViewById(R.id.imgImportEmergency);
        imgImportEmergencyPhone = (ImageView) findViewById(R.id.imgImportEmergencyPhone);


        spCType = (MaterialSpinner) findViewById(R.id.spCType);
        spGender = (MaterialSpinner) findViewById(R.id.spGender);
        spMStatus = (MaterialSpinner) findViewById(R.id.spMStatus);

        edId = (MaterialEditText) findViewById(R.id.edId);
        edName = (MaterialEditText) findViewById(R.id.edName);
        edBirthday = (MaterialEditText) findViewById(R.id.edBirthday);
        edEmail = (MaterialEditText) findViewById(R.id.edEmail);

        edAddress = (MaterialEditText) findViewById(R.id.edAddress);
        edCellPhone = (MaterialEditText) findViewById(R.id.edCellPhone);
        edHomePhone = (MaterialEditText) findViewById(R.id.edHomePhone);
        edWorkPhone = (MaterialEditText) findViewById(R.id.edWorkPhone);

        edEmergencycontact = (MaterialEditText) findViewById(R.id.edEmergencycontact);
        edEmergencyPhone = (MaterialEditText) findViewById(R.id.edEmergencyPhone);
        edNotes = (MaterialEditText) findViewById(R.id.edNotes);


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

        imgImportCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });

        imgImportHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 200);
            }
        });

        imgImportWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 300);
            }
        });

        imgImportEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 400);
            }
        });

        imgImportEmergencyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 500);
            }
        });

        ArrayAdapter<String> ClientTypeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ClientType);
        ClientTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> Genderadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Gender);
        Genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> MaritalStatusadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MaritalStatus);
        MaritalStatusadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCType.setAdapter(ClientTypeadapter);
        spGender.setAdapter(Genderadapter);
        spMStatus.setAdapter(MaritalStatusadapter);


        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edName.getText().toString().trim().length() == 0) {
                    Toast.makeText(NewClients.this, "Please enter client details !!!", Toast.LENGTH_SHORT).show();
                } else {

                    if (temp_edName == null || temp_edName.toString().trim().length() == 0) {
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    pickContact(data, edCellPhone);
                    break;

                case 200:
                    pickContact(data, edHomePhone);
                    break;

                case 300:
                    pickContact(data, edWorkPhone);
                    break;

                case 400:
                    pickContact(data, edEmergencycontact);
                    break;

                case 500:
                    pickContact(data, edEmergencyPhone);
                    break;
            }
        }
    }

    private void pickContact(Intent data, MaterialEditText editText) {
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
                    editText.setText(phone);
                }
            }

        }
    }

    private void processUpdate() {

        String cId = edId.getText().toString().trim();
        String Name = edName.getText().toString().trim();
        String client_type = ClientType[spCType.getSelectedItemPosition()];

        String client_birth = edBirthday.getText().toString().trim();
        String client_gender = Gender[spGender.getSelectedItemPosition()];
        String client_marital_stat = MaritalStatus[spMStatus.getSelectedItemPosition()];

        String client_email = edEmail.getText().toString().trim();
        String client_address = edAddress.getText().toString().trim();

        String client_cell_phone = edCellPhone.getText().toString().trim();
        String client_home_phone = edHomePhone.getText().toString().trim();
        String client_work_phone = edWorkPhone.getText().toString().trim();
        String client_eme_cont = edEmergencycontact.getText().toString().trim();
        String client_eme_phone = edEmergencyPhone.getText().toString().trim();
        String Notes = edNotes.getText().toString().trim();


        db.open();
        if (db.updateClients(mainID, cId, Name, client_type, client_birth, client_gender, client_marital_stat, client_email, client_address,
                client_cell_phone, client_home_phone, client_work_phone, client_eme_cont, client_eme_phone, Notes))
            Toast.makeText(NewClients.this, "Record Updated !!!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(NewClients.this, "Record Failed !!!", Toast.LENGTH_SHORT).show();
        db.close();

        finish();
    }


    private void processSave() {


        String cId = edId.getText().toString().trim();
        String Name = edName.getText().toString().trim();
        String client_type = ClientType[spCType.getSelectedItemPosition()];

        String client_birth = edBirthday.getText().toString().trim();
        String client_gender = Gender[spGender.getSelectedItemPosition()];
        String client_marital_stat = MaritalStatus[spMStatus.getSelectedItemPosition()];

        String client_email = edEmail.getText().toString().trim();
        String client_address = edAddress.getText().toString().trim();

        String client_cell_phone = edCellPhone.getText().toString().trim();
        String client_home_phone = edHomePhone.getText().toString().trim();
        String client_work_phone = edWorkPhone.getText().toString().trim();
        String client_eme_cont = edEmergencycontact.getText().toString().trim();
        String client_eme_phone = edEmergencyPhone.getText().toString().trim();
        String Notes = edNotes.getText().toString().trim();

        db.open();
        db.insertClients(cId, Name, client_type, client_birth, client_gender, client_marital_stat, client_email, client_address,
                client_cell_phone, client_home_phone, client_work_phone, client_eme_cont, client_eme_phone, Notes);

        Toast.makeText(NewClients.this, "New Record Saved !!!", Toast.LENGTH_SHORT).show();
        db.close();

        finish();
    }


    //end of main class
}
