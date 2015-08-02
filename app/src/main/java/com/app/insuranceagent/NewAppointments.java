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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.insuranceagent.model.SubAppointments;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;


public class NewAppointments extends ActionBarActivity {
    TextView txtTitle,txtSubTitle;
    ImageView imgClose,imgBack,imgSave;
    ListView listView;
    DBAdapter db;
    MaterialSpinner spCType,spAgent;
    EditText edDate,edTime,edNotes;
    int tempId;
    String tempName, tempclientName, tempagentName, tempdate,temptime,notes;
    ArrayList<String> clientData;
    ArrayList<String> agentData;
    static final int TIME_DIALOG_ID = 2222;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    static final int DATE_PICKER_ID = 1111;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addappointments);

        db = new DBAdapter(NewAppointments.this);



        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        init();

        tempId = getIntent().getIntExtra("appID", -1);
        tempclientName = getIntent().getStringExtra("clientName");
        tempagentName = getIntent().getStringExtra("agentName");
        tempdate = getIntent().getStringExtra("date");
        temptime = getIntent().getStringExtra("time");
        notes = getIntent().getStringExtra("notes");

        if (tempId == -1) {

        } else {
            txtSubTitle.setText("Edit");
            spCType.setSelection(getSimpleIndex(spCType,tempclientName));
            spAgent.setSelection(getSimpleIndex(spAgent,tempagentName));
            edDate.setText(tempdate);
            edTime.setText(temptime);
            edNotes.setText(notes);
        }
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

        spCType = (MaterialSpinner) findViewById(R.id.spCType);
        spAgent = (MaterialSpinner) findViewById(R.id.spAgent);
        txtSubTitle  = (TextView)findViewById(R.id.txtSubTitle);
        edDate = (MaterialEditText) findViewById(R.id.edDate);
        edTime = (MaterialEditText) findViewById(R.id.edTime);
        edNotes = (MaterialEditText) findViewById(R.id.edNotes);
        imgClose = (ImageView)findViewById(R.id.imgClose);
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgSave= (ImageView)findViewById(R.id.imgSave);

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

                if (spCType.getSelectedItemPosition()==0) {
                    Toast.makeText(NewAppointments.this, "Please select client !!!", Toast.LENGTH_SHORT).show();
                } else {

                    if (tempId == -1) {
                        processSave();
                    } else {
                        processUpdate();
                    }

                }
            }
        });


        fetchClientSpinnerData();
        fetchAgentSpinnerData();

        edDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edDate.getRight() - edDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showDialog(DATE_PICKER_ID);
                        return true;
                    }
                }
                return false;
            }
        });





        edTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edTime.getRight() - edTime.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showDialog(TIME_DIALOG_ID);
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void processUpdate() {

        String clientName = clientData.get(spCType.getSelectedItemPosition()-1);
        String agentName = agentData.get(spAgent.getSelectedItemPosition()-1);
        String date = edDate.getText().toString().trim();
        String time = edTime.getText().toString().trim();
        String Notes = edNotes.getText().toString().trim();


        db.open();
        if (db.updateAppointments(tempId, clientName, agentName, date,time, Notes))
            Toast.makeText(NewAppointments.this, "Record Updated !!!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(NewAppointments.this, "Record Failed !!!", Toast.LENGTH_SHORT).show();
        db.close();

        finish();
    }


    private void processSave() {
        String clientName = clientData.get(spCType.getSelectedItemPosition()-1);
        String agentName = agentData.get(spAgent.getSelectedItemPosition()-1);
        String date = edDate.getText().toString().trim();
        String time = edTime.getText().toString().trim();
        String Notes = edNotes.getText().toString().trim();

        db.open();
        db.insertAppointments(clientName, agentName, date,time, Notes);
        Toast.makeText(NewAppointments.this, "New Record Saved !!!", Toast.LENGTH_SHORT).show();
        db.close();

        finish();
    }












    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);

            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        false);


                 }
        return null;
    }
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour   = hourOfDay;
            minute = minutes;

            updateTime(hour,minute);

        }

    };

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        edTime.setText(aTime);
    }
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            edDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year)
                    .append(" "));

        }
    };
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
