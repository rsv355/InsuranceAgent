package com.app.insuranceagent;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.insuranceagent.model.SubAgents;
import com.app.insuranceagent.model.SubAppointments;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.ganfra.materialspinner.MaterialSpinner;


public class SearchAppointments extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgBack, imgNew;
    ListView listView;
    DBAdapter db;
    MaterialSpinner spCType,spAgent;
    EditText edSearchBox;
    ImageView imgSearch;
    CustomAdapter adp;
    ArrayList<SubAppointments> appObj;

    ArrayList<String> clientData;
    ArrayList<String> agentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_appointments);
        db = new DBAdapter(SearchAppointments.this);
        init();
    }
    @Override
    protected void onResume() {
        super.onResume();
        processFetchAppointments();
    }

    void processFetchAppointments() {

        appObj = new ArrayList<>();


        try {
            db.open();
            Cursor c = db.getALLAppointmentsList();
            if (c.moveToFirst()) {
                do {
                    FetchData(c);
                } while (c.moveToNext());
            }
            db.close();

            adp = new CustomAdapter(SearchAppointments.this, appObj);

            listView.setAdapter(adp);
        } catch (Exception e) {
            Log.e("### Exc", e.toString());
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1 = new Intent(SearchAppointments.this, NewAgents.class);

                i1.putExtra("appID", appObj.get(i).appID);
                i1.putExtra("clientName", appObj.get(i).clientName);
                i1.putExtra("agentName", appObj.get(i).agentName);
                i1.putExtra("date", appObj.get(i).date);
                i1.putExtra("time", appObj.get(i).time);
                i1.putExtra("notes", appObj.get(i).notes);
                startActivity(i1);
            }
        });


    }

    private void FetchData(Cursor c) {
        SubAppointments subApp = new SubAppointments();
        subApp.appID = c.getInt(0);
        subApp.clientName = c.getString(1);
        subApp.agentName = c.getString(2);
        subApp.date = c.getString(3);
        subApp.time = c.getString(4);
        subApp.notes = c.getString(5);

        appObj.add(subApp);


    }

    private void init() {

        spCType = (MaterialSpinner) findViewById(R.id.spCType);
        spAgent = (MaterialSpinner) findViewById(R.id.spAgent);

        edSearchBox = (EditText) findViewById(R.id.edSearchBox);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);

        imgNew = (ImageView) findViewById(R.id.imgNew);
        listView = (ListView) findViewById(R.id.listView);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        imgBack = (ImageView) findViewById(R.id.imgBack);
       // txtTitle.setText(getIntent().getStringExtra("title"));

        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(SearchAppointments.this, NewAppointments.class);
                startActivity(i1);

            }
        });


        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adp.filter(edSearchBox.getText().toString().trim());
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
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
                    FetchclientData(c);
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

    private void FetchclientData(Cursor c) {
        clientData.add(c.getString(0));
    }

    class CustomAdapter extends BaseAdapter {
        LayoutInflater layoutInflator;
        private Context ctx;


        List<SubAppointments> ValuesSearch;
        ArrayList<SubAppointments> arraylist;

        public CustomAdapter(Context ctx, ArrayList<SubAppointments> cmp) {
            this.ctx = ctx;

            this.ValuesSearch = cmp;

            arraylist = new ArrayList<SubAppointments>();
            arraylist.addAll(ValuesSearch);
        }


        @Override
        public int getCount() {
            return ValuesSearch.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            layoutInflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = convertView;


            view = layoutInflator.inflate(R.layout.row_item_appoint, parent, false);

            ImageView img = (ImageView) view.findViewById(R.id.img);
            TextView txtclient = (TextView) view.findViewById(R.id.txtName);
            TextView txtagentname = (TextView) view.findViewById(R.id.txtPhone);
            TextView txdate = (TextView) view.findViewById(R.id.txdate);


            img.setImageResource(R.drawable.agents);

            int col = Color.parseColor("#2196F3");
            img.setColorFilter(col, PorterDuff.Mode.SRC_ATOP);


            txtclient.setText(ValuesSearch.get(position).clientName);
            txtagentname.setText(ValuesSearch.get(position).agentName);
            txdate.setText(ValuesSearch.get(position).date);

            return view;
        }

        // Filter Class
        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());

            ValuesSearch.clear();
            if (charText.length() == 0) {
                ValuesSearch.addAll(arraylist);

            } else {
                for (SubAppointments obj : arraylist) {
                    if (charText.length() != 0 && obj.agentName.toLowerCase(Locale.getDefault()).contains(charText)) {
                        ValuesSearch.add(obj);
                    }

                }
            }
            notifyDataSetChanged();
        }


    }

    //end of main class
}
