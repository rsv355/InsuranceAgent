package com.app.insuranceagent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.ArrayList;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;


public class NewClaims extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgClose, imgBack, imgSave;
    MaterialSpinner spPolicy, spStatus;
    ArrayList<String> policyObj;
    ArrayList<String> statusObj;
    MaterialEditText edClaimDate, edFulfillDate, edAmount, edAmountPaid, edNotes;
    DBAdapter db;
    static final int DATE_PICKER_ID1 = 1111;
    static final int DATE_PICKER_ID2 = 2222;
    private int year;
    private int month;
    private int day;
    String tmpPolicy, tmpDate, tmpAmount, tmpAmountPaid, tmpStatus, tmpFulfiildate, tmpNotes;
    int tmpclmID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclaims);

        tmpclmID = getIntent().getIntExtra("clmID", 0);
        tmpAmount = getIntent().getStringExtra("clmAmount");
        tmpAmountPaid = getIntent().getStringExtra("clmAmountPaid");
        tmpPolicy = getIntent().getStringExtra("clmPolicy");
        tmpDate = getIntent().getStringExtra("clmDate");
        tmpStatus = getIntent().getStringExtra("clmStatus");
        tmpFulfiildate = getIntent().getStringExtra("clmFulfilDate");
        tmpNotes = getIntent().getStringExtra("clmNotes");

        final Calendar c = java.util.Calendar.getInstance();
        year = c.get(java.util.Calendar.YEAR);
        month = c.get(java.util.Calendar.MONTH);
        day = c.get(java.util.Calendar.DAY_OF_MONTH);

        db = new DBAdapter(NewClaims.this);
        init();

        if (tmpPolicy == null || tmpPolicy.length() == 0) {

        } else {
            edAmount.setText(tmpAmount);
            edAmountPaid.setText(tmpAmountPaid);
            edClaimDate.setText(tmpDate);
            edFulfillDate.setText(tmpFulfiildate);
            edNotes.setText(tmpNotes);
        }

        edClaimDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID1);
            }
        });

        edFulfillDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID2);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID1:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener1, year, month, day);
            case DATE_PICKER_ID2:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener2, year, month, day);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            edClaimDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));

        }
    };

    private DatePickerDialog.OnDateSetListener pickerListener2 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            edFulfillDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));

        }
    };

    private void init() {
        edNotes = (MaterialEditText) findViewById(R.id.edNotes);
        edAmount = (MaterialEditText) findViewById(R.id.edAmount);
        edAmountPaid = (MaterialEditText) findViewById(R.id.edAmountPaid);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        edClaimDate = (MaterialEditText) findViewById(R.id.edClaimDate);
        edFulfillDate = (MaterialEditText) findViewById(R.id.edFulfillDate);
        spPolicy = (MaterialSpinner) findViewById(R.id.spPolicy);
        spStatus = (MaterialSpinner) findViewById(R.id.spStatus);

        statusObj = new ArrayList<>();
        statusObj.add("Fulfilled");
        statusObj.add("In Progress");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, statusObj);
        spStatus.setAdapter(statusAdapter);
        spStatus.setSelection(getIndex(spStatus, tmpStatus));

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
            public void onClick(View v) {
                checkValidation();
            }
        });

        fetchPolicyData();

    }

    private void showToast(String s) {
        Toast.makeText(NewClaims.this, s, Toast.LENGTH_SHORT).show();
    }

    private void checkValidation() {
        if (spPolicy.getSelectedItem().toString().length() == 0 || spPolicy.getSelectedItem().toString().equals("Policy")) {
            showToast("Select Policy");
        } else if (edClaimDate.getText().toString().length() == 0) {
            showToast("Select Date");
        } else if (edAmount.getText().toString().length() == 0 || edAmount.getText().toString().equals("0.0")) {
            showToast("Enter Amount");
        } else if (spStatus.getSelectedItem().toString().length() == 0 || spStatus.getSelectedItem().toString().equals("Status")) {
            showToast("Select Status");
        } else if (spStatus.getSelectedItem().toString().equals("Fulfilled")) {
            if (edFulfillDate.getText().toString().length() == 0) {
                showToast("Select Fullfill date");
            } else {
                if (tmpPolicy == null || tmpPolicy.toString().length() == 0) {
                    processSave();
                } else {
                    processUpdate();
                }
            }
        } else if (tmpPolicy == null || tmpPolicy.toString().length() == 0) {
            processSave();
        } else {
            processUpdate();
        }
    }

    private void processUpdate() {
        setData();

        db.open();
        if (db.updateClaims(tmpclmID, tmpPolicy, tmpDate, tmpAmount, tmpAmountPaid, tmpStatus, tmpFulfiildate, tmpNotes))
            showToast("Record Update");
        else
            showToast("Record Update Failed");
        db.close();
        finish();
    }

    private void processSave() {

        setData();

        db.open();
        db.insertClaims(tmpPolicy, tmpDate, tmpAmount, tmpAmountPaid, tmpStatus, tmpFulfiildate, tmpNotes);
        showToast("Data Saved");
        db.close();
        finish();
    }

    private void setData() {
        tmpPolicy = spPolicy.getSelectedItem().toString().trim();
        tmpDate = edClaimDate.getText().toString().trim();
        tmpAmount = edAmount.getText().toString().trim();
        tmpAmountPaid = edAmountPaid.getText().toString().trim();
        tmpStatus = spStatus.getSelectedItem().toString().trim();
        tmpFulfiildate = edFulfillDate.getText().toString().trim();
        tmpNotes = edNotes.getText().toString().trim();
    }

    private void fetchPolicyData() {
        policyObj = new ArrayList<>();

        try {
            db.open();
            Cursor c = db.getALLPolicyList();
            if (c.moveToFirst()) {
                do {
                    FetchData(c);
                } while (c.moveToNext());
            }
            db.close();
            ArrayAdapter<String> policyAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, policyObj);
            spPolicy.setAdapter(policyAdapter);
            spPolicy.setSelection(getIndex(spPolicy, tmpPolicy));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getIndex(MaterialSpinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void FetchData(Cursor c) {
        policyObj.add(c.getString(4));
    }


    //end of main class
}
