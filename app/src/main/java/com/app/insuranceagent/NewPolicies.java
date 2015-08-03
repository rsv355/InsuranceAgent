package com.app.insuranceagent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import fr.ganfra.materialspinner.MaterialSpinner;


public class NewPolicies extends ActionBarActivity {
    TextView txtTitle, txtSubHeading;
    ImageView imgClose, imgBack, imgSave;
    ListView listView;
    MaterialEditText edPolicyNumber, edProductD, edDate, expDate,edTerm,edInsSumm,edPrem,edDownP,edSales,edDeduct,edCharge,edCredit,edBal;

    MaterialSpinner spCType,spAgent,spCmp,spPType,spPayM,spPayFrq,spStatus;

    String tempclientName, tempagentName, tempcmpName,temppolNo,temppolType,temppolDetails,tempefftDate,
            tempexpDate,temptermLength,tempinsSummary,temppremium,tempdownPayment,tempsalesCommision,
            tempdeductible,temppayMethod,temppayFreq,tempstatus,tempcharge,tempcredit,tempbalance;

    int temppolID;
    ArrayList<String> policyType;
    ArrayList<String> paymentMethod;
    ArrayList<String> paymentFreq;
    ArrayList<String> status;
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID1 = 1111;
    static final int DATE_PICKER_ID2 = 2222;
    ArrayList<String> cmpData;
    ArrayList<String> clientData;
    ArrayList<String> agentData;


    DBAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpolicy);
        db = new DBAdapter(NewPolicies.this);
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);
        init();

        policyType = new ArrayList<>();
        policyType.add("Vehicle (auto)");
        policyType.add("Property");
        policyType.add("Home");
        policyType.add("Health");
        policyType.add("Dental");
        policyType.add("Life");
        policyType.add("International (Travel)");
        policyType.add("Other");


        paymentMethod = new ArrayList<>();
        paymentMethod.add("Cash/Check");
        paymentMethod.add("Debit Card/Credit Card");
        paymentMethod.add("Direct Debit/Standing Order");
        paymentMethod.add("In-House Payroll");
        paymentMethod.add("Other");

        paymentFreq = new ArrayList<>();
        paymentFreq.add("01-Unique/Yearly");
        paymentFreq.add("12-Monthly");
        paymentFreq.add("06-Three-Monthly/Quarterly");
        paymentFreq.add("02-Semi-Annually/Bi-Annually");
        paymentFreq.add("24-Semi-Monthly");

        status = new ArrayList<>();
        status.add("Active");
        status.add("Inactive");
        status.add("Canceled");

        ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, policyType);
        spPType.setAdapter(clientAdapter);




        temppolID = getIntent().getIntExtra("polID", 1);
        tempclientName = getIntent().getStringExtra("clientName");
        tempagentName = getIntent().getStringExtra("agentName");
        tempcmpName = getIntent().getStringExtra("cmpName");
        temppolNo = getIntent().getStringExtra("polNo");

        temppolType = getIntent().getStringExtra("polType");
        temppolDetails = getIntent().getStringExtra("polDetails");
        tempefftDate = getIntent().getStringExtra("efftDate");
        tempexpDate = getIntent().getStringExtra("expDate");
        temptermLength = getIntent().getStringExtra("termLength");

        tempinsSummary = getIntent().getStringExtra("insSummary");
        temppremium = getIntent().getStringExtra("premium");
        tempdownPayment = getIntent().getStringExtra("downPayment");
        tempsalesCommision = getIntent().getStringExtra("salesCommision");
        tempdeductible = getIntent().getStringExtra("deductible");
        temppayMethod = getIntent().getStringExtra("payMethod");

        temppayFreq = getIntent().getStringExtra("payFreq");
        tempstatus = getIntent().getStringExtra("status");
        tempcharge = getIntent().getStringExtra("charge");
        tempcredit = getIntent().getStringExtra("credit");
        tempbalance = getIntent().getStringExtra("balance");



        if (temppolNo == null || temppolNo.toString().trim().length() == 0) {
            spPType.setSelection(0);
        } else {
            txtSubHeading.setText(temppolNo + " :: Edit");

        }





        spPType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        edProductD.setText("Vehicle Type:\nMake/Model:\nMarket Price:\nPrimary use:\nYear:\nMileage:\nTransmission:\nColor:");
                        break;
                    case 1:
                        edProductD.setText("Construction Type:\nMarket Price:\nYear Built:\nLocation:\nConstruction Materials:\n# of Units:\n# of Stories:\nOccupancy:");
                        break;
                    case 2:
                        edProductD.setText("Construction Type:\nMarket Price:\nYear Built:\nLocation:\nConstruction Materials:\n# of Units:\n# of Stories:\nOccupancy:");
                        break;
                    case 3:
                        edProductD.setText("SSN:\nCoverage Desired:Single / Family\nMember coverage:\nBeneficiaries:\nPersonal Info:\nMedical Info:");
                        break;
                    case 4:
                        edProductD.setText("SSN:\nCoverage Desired:Single / Family\nMember coverage:\nBeneficiaries:\nPersonal Info:\nMedical Info:");
                        break;
                    case 5:
                        edProductD.setText("SSN:\nCoverage Desired:Single / Family\nMember coverage:\nBeneficiaries:\nPersonal Info:\nMedical Info:");
                        break;
                    case 6:
                        edProductD.setText("");
                        break;
                    case 7:
                        edProductD.setText("");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edDate.getRight() - edDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showDialog(DATE_PICKER_ID1);
                        return true;
                    }
                }
                return false;
            }
        });

        expDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (expDate.getRight() - expDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showDialog(DATE_PICKER_ID2);
                        return true;
                    }
                }
                return false;
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
                return new DatePickerDialog(this, pickerListener1, year, month,day);
            case DATE_PICKER_ID2:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener2, year, month,day);

        }
        return null;
    }

    //private method of your class
    private int getSimpleIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }


    private void init(){


        edPolicyNumber= (MaterialEditText)findViewById(R.id.edPolicyNumber);
        edProductD= (MaterialEditText)findViewById(R.id.edProductD);
        edDate= (MaterialEditText)findViewById(R.id.edDate);
        expDate= (MaterialEditText)findViewById(R.id.expDate);
        edTerm= (MaterialEditText)findViewById(R.id.edTerm);
        edInsSumm= (MaterialEditText)findViewById(R.id.edInsSumm);
        edPrem= (MaterialEditText)findViewById(R.id.edPrem);
        edDownP= (MaterialEditText)findViewById(R.id.edDownP);
        edSales= (MaterialEditText)findViewById(R.id.edSales);
        edDeduct= (MaterialEditText)findViewById(R.id.edDeduct);
        edCharge= (MaterialEditText)findViewById(R.id.edCharge);
        edCredit= (MaterialEditText)findViewById(R.id.edCredit);
        edBal= (MaterialEditText)findViewById(R.id.edBal);

        spCType= (MaterialSpinner)findViewById(R.id.spCType);
        spAgent= (MaterialSpinner)findViewById(R.id.spAgent);
        spCmp= (MaterialSpinner)findViewById(R.id.spCmp);
        spPType= (MaterialSpinner)findViewById(R.id.spPType);
        spPayM= (MaterialSpinner)findViewById(R.id.spPayM);
        spPayFrq= (MaterialSpinner)findViewById(R.id.spPayFrq);
        spStatus= (MaterialSpinner)findViewById(R.id.spStatus);

        imgClose = (ImageView)findViewById(R.id.imgClose);
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgSave = (ImageView)findViewById(R.id.imgSave);

        fetchClientSpinnerData();
        fetchAgentSpinnerData();
        fetchCompaniesSpinnerData();

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

    void fetchCompaniesSpinnerData(){

        cmpData =  new ArrayList<>();
        try {

            db.open();
            Cursor c = db.getAllCompaniesName();
            if (c.moveToFirst()) {
                do {
                    FetchCmp(c);
                } while (c.moveToNext());
            }
            db.close();

            ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, cmpData);
            spCmp.setAdapter(clientAdapter);

        } catch (Exception e) {
            Log.e("### Exc1", e.toString());
        }
    }

    private void FetchCmp(Cursor c) {
        cmpData.add(c.getString(0));
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
                    FetchClientData(c);
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

    private void FetchClientData(Cursor c) {
        clientData.add(c.getString(0));


    }
    private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            edDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));

            StringBuilder sDate = new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day);

            LocalDate startDate = new LocalDate ();          //Birth date
            LocalDate endDate = new LocalDate(sDate.toString().trim());                    //Today's date
            Period period = new Period(startDate, endDate, PeriodType.months());

            Log.e("############## endDate",""+endDate.toString());

            Log.e("############## dif",""+period.getMonths());
        }
    };

    private DatePickerDialog.OnDateSetListener pickerListener2 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            expDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));

            LocalDate startDate = new LocalDate (edDate.getText().toString());          //Birth date
            LocalDate endDate = new LocalDate(expDate.getText().toString());                    //Today's date
            Period period = new Period(startDate, endDate, PeriodType.months());



            Log.e("############## dif",""+period);
        }
    };
    //end of main class
}
