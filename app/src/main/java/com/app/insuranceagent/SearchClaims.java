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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.insuranceagent.model.SubClaim;
import com.app.insuranceagent.model.SubCompanies;
import com.app.insuranceagent.model.SubPolicy;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;


public class SearchClaims extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgBack, imgNew;
    ListView listView;
    ArrayList<String> policyObj;
    ArrayList<String> statusObj;
    ArrayList<SubClaim> claimObj;
    DBAdapter db;
    MaterialSpinner spPolicy, spStatus;
    CustomAdapter adp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_claims);
        db = new DBAdapter(SearchClaims.this);

        init();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1 = new Intent(SearchClaims.this, NewClaims.class);

                i1.putExtra("clmID", claimObj.get(i).clmID);
                i1.putExtra("clmAmount", claimObj.get(i).clmAmount);
                i1.putExtra("clmAmountPaid", claimObj.get(i).clmAmountPaid);
                i1.putExtra("clmDate", claimObj.get(i).clmDate);
                i1.putExtra("clmPolicy", claimObj.get(i).clmPolicy);
                i1.putExtra("clmFulfilDate", claimObj.get(i).clmFulfilDate);
                i1.putExtra("clmStatus", claimObj.get(i).clmStatus);
                i1.putExtra("clmNotes", claimObj.get(i).clmNotes);
                i1.putExtra("clmImage", claimObj.get(i).clmImage);
                Log.e("clmImage", claimObj.get(i).clmImage + "---");

                startActivity(i1);
            }
        });
    }

    private void init() {
        imgNew = (ImageView) findViewById(R.id.imgNew);
        listView = (ListView) findViewById(R.id.listView);
        spPolicy = (MaterialSpinner) findViewById(R.id.spPolicy);
        spStatus = (MaterialSpinner) findViewById(R.id.spStatus);

        statusObj = new ArrayList<>();
        statusObj.add("Fulfilled");
        statusObj.add("In Progress");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, statusObj);
        spStatus.setAdapter(statusAdapter);

        imgBack = (ImageView) findViewById(R.id.imgBack);


        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(SearchClaims.this, NewClaims.class);
                startActivity(i1);

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        processFetchPolicy();
        processFetchClaims();
    }

    private void FetchData(Cursor c) {
        policyObj.add(c.getString(4));
    }

    private void processFetchClaims() {
        claimObj = new ArrayList<>();

        try {
            db.open();
            Cursor c = db.getALLClaimsList();
            if (c.moveToFirst()) {
                do {
                    FetchData1(c);
                } while (c.moveToNext());
            }
            db.close();
            adp = new CustomAdapter(SearchClaims.this, claimObj);
            listView.setAdapter(adp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void FetchData1(Cursor c) {
        SubClaim subClaim = new SubClaim();
        subClaim.clmID = c.getInt(0);
        subClaim.clmPolicy = c.getString(1);
        subClaim.clmDate = c.getString(2);
        subClaim.clmAmount = c.getString(3);
        subClaim.clmAmountPaid = c.getString(4);
        subClaim.clmStatus = c.getString(5);
        subClaim.clmFulfilDate = c.getString(6);
        subClaim.clmNotes = c.getString(7);
        subClaim.clmImage = c.getBlob(8);

        claimObj.add(subClaim);

    }

    private void processFetchPolicy() {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class CustomAdapter extends BaseAdapter {
        LayoutInflater layoutInflator;
        private Context ctx;


        List<SubClaim> ValuesSearch;
        ArrayList<SubClaim> arraylist;

        public CustomAdapter(Context ctx, ArrayList<SubClaim> cmp) {
            this.ctx = ctx;

            this.ValuesSearch = cmp;

            arraylist = new ArrayList<SubClaim>();
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


            view = layoutInflator.inflate(R.layout.claim_row, parent, false);

            ImageView img = (ImageView) view.findViewById(R.id.img);
            TextView txtPolicy = (TextView) view.findViewById(R.id.txtName);
            TextView txtAmount = (TextView) view.findViewById(R.id.txtAmount);
            TextView txtStatus = (TextView) view.findViewById(R.id.txtStatus);
            TextView txdate = (TextView) view.findViewById(R.id.txdate);

            img.setImageResource(R.drawable.claims);

            int col = Color.parseColor("#2196F3");
            img.setColorFilter(col, PorterDuff.Mode.SRC_ATOP);

            txtPolicy.setText(ValuesSearch.get(position).clmPolicy);
            txtAmount.setText(ValuesSearch.get(position).clmAmount);
            txtStatus.setText(ValuesSearch.get(position).clmStatus);
            txdate.setText(ValuesSearch.get(position).clmDate);

            return view;
        }
    }

    //end of main class
}
