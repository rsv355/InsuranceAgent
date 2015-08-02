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

import com.app.insuranceagent.model.SubAppointments;
import com.app.insuranceagent.model.SubPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.ganfra.materialspinner.MaterialSpinner;


public class SearchPolicies extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgBack,imgNew;
    ListView listView;
    DBAdapter db;
    MaterialSpinner spStatus,spCmp,spAgent;
    EditText edSearchBox;
    ImageView imgSearch;
    CustomAdapter adp;
    ArrayList<SubPolicy> polObj;

    ArrayList<String> cmpData;
    ArrayList<String> agentData;
    ArrayList<String> statusData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_policies);
        db = new DBAdapter(SearchPolicies.this);

        statusData = new ArrayList<>();
        statusData.add("Active");
        statusData.add("Inactive");
        statusData.add("Canceled");

        init();
    }

    private void init(){
        spStatus= (MaterialSpinner) findViewById(R.id.spStatus);
        spCmp = (MaterialSpinner) findViewById(R.id.spCmp);
        spAgent = (MaterialSpinner) findViewById(R.id.spAgent);

        edSearchBox = (EditText) findViewById(R.id.edSearchBox);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgNew = (ImageView)findViewById(R.id.imgNew);
        listView= (ListView)findViewById(R.id.listView);

        imgBack = (ImageView)findViewById(R.id.imgBack);


        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(SearchPolicies.this,NewPolicies.class);
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


        fetchAgentSpinnerData();
        fetchCompaniesSpinnerData();


        ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, statusData);
        spStatus.setAdapter(clientAdapter);

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
            Log.e("### Exc1", e.toString());
        }
    }

    private void FetchDataAgent(Cursor c) {
        agentData.add(c.getString(0));
    }


    @Override
    protected void onResume() {
        super.onResume();
        processFetchPolicy();
    }





    void processFetchPolicy() {

        polObj = new ArrayList<>();


        try {
            db.open();
            Cursor c = db.getALLPolicyList();
            if (c.moveToFirst()) {
                do {
                    FetchData(c);
                } while (c.moveToNext());
            }
            db.close();

            adp = new CustomAdapter(SearchPolicies.this, polObj);

            listView.setAdapter(adp);
        } catch (Exception e) {
            Log.e("### Exc", e.toString());
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1 = new Intent(SearchPolicies.this, NewPolicies.class);

                i1.putExtra("polID", polObj.get(i).polID);
                i1.putExtra("clientName", polObj.get(i).clientName);
                i1.putExtra("agentName", polObj.get(i).agentName);
                i1.putExtra("cmpName", polObj.get(i).cmpName);
                i1.putExtra("polNo", polObj.get(i).polNo);
                i1.putExtra("polType", polObj.get(i).polType);

                i1.putExtra("polDetails", polObj.get(i).polDetails);
                i1.putExtra("efftDate", polObj.get(i).efftDate);
                i1.putExtra("expDate", polObj.get(i).expDate);
                i1.putExtra("termLength", polObj.get(i).termLength);
                i1.putExtra("insSummary", polObj.get(i).insSummary);


                i1.putExtra("premium", polObj.get(i).premium);
                i1.putExtra("downPayment", polObj.get(i).downPayment);
                i1.putExtra("salesCommision", polObj.get(i).salesCommision);
                i1.putExtra("deductible", polObj.get(i).deductible);
                i1.putExtra("payMethod", polObj.get(i).payMethod);
                i1.putExtra("payFreq", polObj.get(i).payFreq);
                i1.putExtra("status", polObj.get(i).status);

                i1.putExtra("charge", polObj.get(i).charge);
                i1.putExtra("credit", polObj.get(i).credit);
                i1.putExtra("balance", polObj.get(i).balance);

                startActivity(i1);
            }
        });


    }

    private void FetchData(Cursor c) {
        SubPolicy subpol = new SubPolicy();


        subpol.polID=c.getInt(0);
        subpol.clientName=c.getString(1);
        subpol.agentName=c.getString(2);
        subpol.cmpName=c.getString(3);

        subpol.polNo=c.getString(4);
        subpol.polType=c.getString(5);
        subpol.polDetails=c.getString(6);
        subpol.efftDate=c.getString(7);
        subpol.expDate=c.getString(8);

        subpol.termLength=c.getString(9);
        subpol.insSummary=c.getString(10);

        subpol.premium=c.getString(11);
        subpol.downPayment=c.getString(12);

        subpol.salesCommision=c.getString(13);
        subpol.deductible=c.getString(14);

        subpol.payMethod=c.getString(15);

        subpol.payFreq=c.getString(16);

        subpol.status=c.getString(17);

        subpol.charge=c.getString(18);
        subpol.credit=c.getString(19);
        subpol.balance=c.getString(20);

        polObj.add(subpol);


    }
    class CustomAdapter extends BaseAdapter {
        LayoutInflater layoutInflator;
        private Context ctx;


        List<SubPolicy> ValuesSearch;
        ArrayList<SubPolicy> arraylist;

        public CustomAdapter(Context ctx, ArrayList<SubPolicy> cmp) {
            this.ctx = ctx;

            this.ValuesSearch = cmp;

            arraylist = new ArrayList<SubPolicy>();
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


            txtclient.setText(ValuesSearch.get(position).polNo + " - " + ValuesSearch.get(position).clientName);
            txtagentname.setText(ValuesSearch.get(position).agentName);
            txdate.setText("Premium:"+ValuesSearch.get(position).premium);

            return view;
        }

        // Filter Class
        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());

            ValuesSearch.clear();
            if (charText.length() == 0) {
                ValuesSearch.addAll(arraylist);

            } else {
                for (SubPolicy obj : arraylist) {
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
