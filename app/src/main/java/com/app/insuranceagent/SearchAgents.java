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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.insuranceagent.model.SubAgents;
import com.app.insuranceagent.model.SubCompanies;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchAgents extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgBack, imgNew;
    ListView listView;
    DBAdapter db;

    EditText edSearchBox;
    ImageView imgSearch;
    CustomAdapter adp;
    ArrayList<SubAgents> agentObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_companies);
        db = new DBAdapter(SearchAgents.this);
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        processFetchAgents();
    }

    void processFetchAgents() {

        agentObj = new ArrayList<>();


        try {
            db.open();
            Cursor c = db.getALLAgentsList();
            if (c.moveToFirst()) {
                do {
                    FetchData(c);
                } while (c.moveToNext());
            }
            db.close();

            adp = new CustomAdapter(SearchAgents.this, agentObj);

            listView.setAdapter(adp);
        } catch (Exception e) {
            Log.e("### Exc", e.toString());
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1 = new Intent(SearchAgents.this, NewAgents.class);

                i1.putExtra("agentID", agentObj.get(i).agentID);
                i1.putExtra("agentName", agentObj.get(i).agentName);
                i1.putExtra("agentPhone", agentObj.get(i).agentPhone);
                i1.putExtra("agentAddress", agentObj.get(i).agentAddress);
                i1.putExtra("agentNotes", agentObj.get(i).agentNotes);
                startActivity(i1);
            }
        });


    }

    private void FetchData(Cursor c) {
        SubAgents subCmp = new SubAgents();
        subCmp.agentID = c.getInt(0);
        subCmp.agentName = c.getString(1);
        subCmp.agentPhone = c.getString(2);
        subCmp.agentAddress = c.getString(3);
        subCmp.agentNotes = c.getString(4);


        agentObj.add(subCmp);


    }

    private void init() {
        edSearchBox = (EditText) findViewById(R.id.edSearchBox);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);

        imgNew = (ImageView) findViewById(R.id.imgNew);
        listView = (ListView) findViewById(R.id.listView);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtTitle.setText(getIntent().getStringExtra("title"));

        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(SearchAgents.this, NewAgents.class);
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

    }


    class CustomAdapter extends BaseAdapter {
        LayoutInflater layoutInflator;
        private Context ctx;


        List<SubAgents> ValuesSearch;
        ArrayList<SubAgents> arraylist;

        public CustomAdapter(Context ctx, ArrayList<SubAgents> cmp) {
            this.ctx = ctx;

            this.ValuesSearch = cmp;

            arraylist = new ArrayList<SubAgents>();
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


            view = layoutInflator.inflate(R.layout.row_item_agents, parent, false);

            ImageView img = (ImageView) view.findViewById(R.id.img);
            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            TextView txtPhone = (TextView) view.findViewById(R.id.txtPhone);

            img.setImageResource(R.drawable.agents);

            int col = Color.parseColor("#2196F3");
            img.setColorFilter(col, PorterDuff.Mode.SRC_ATOP);


            txtName.setText(ValuesSearch.get(position).agentName);

            if (ValuesSearch.get(position).agentPhone.length() != 0)
                txtPhone.setText(ValuesSearch.get(position).agentPhone);
            else
                txtPhone.setText("Phone");

            return view;
        }

        // Filter Class
        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());

            ValuesSearch.clear();
            if (charText.length() == 0) {
                ValuesSearch.addAll(arraylist);

            } else {
                for (SubAgents obj : arraylist) {
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
