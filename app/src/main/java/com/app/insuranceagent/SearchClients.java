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
import com.app.insuranceagent.model.SubClients;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchClients extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgBack,imgNew;
    ListView listView;
    DBAdapter db;

    EditText edSearchBox;
    ImageView imgSearch;
    CustomAdapter adp;
    ArrayList<SubClients> clientObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_clients);
        db = new DBAdapter(SearchClients.this);
        init();
    }

    private void init(){

        edSearchBox = (EditText)findViewById(R.id.edSearchBox);
        imgSearch= (ImageView)findViewById(R.id.imgSearch);
        imgNew = (ImageView)findViewById(R.id.imgNew);
        listView= (ListView)findViewById(R.id.listView);
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        imgBack = (ImageView)findViewById(R.id.imgBack);
        txtTitle.setText(getIntent().getStringExtra("title"));

        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(SearchClients.this, NewClients.class);
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

    void processFetchClients() {

        clientObj = new ArrayList<>();


        try {
            db.open();
            Cursor c = db.getALLClientsList();
            if (c.moveToFirst()) {
                do {
                    FetchData(c);
                } while (c.moveToNext());
            }
            db.close();

            adp = new CustomAdapter(SearchClients.this, clientObj);

            listView.setAdapter(adp);
        } catch (Exception e) {
            Log.e("### Exc", e.toString());
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1 = new Intent(SearchClients.this, NewClients.class);

                i1.putExtra("mainID", clientObj.get(i).id);
                i1.putExtra("clientID", clientObj.get(i).clientid);
                i1.putExtra("clientName", clientObj.get(i).client_name);
                i1.putExtra("clientBdy", clientObj.get(i).client_birth);
                i1.putExtra("clientEmail", clientObj.get(i).client_email);

                i1.putExtra("clientAdd", clientObj.get(i).client_address);
                i1.putExtra("clientCellPhone", clientObj.get(i).client_cell_phone);
                i1.putExtra("clientHomePhone", clientObj.get(i).client_home_phone);
                i1.putExtra("clientWorkPhone", clientObj.get(i).client_work_phone);

                i1.putExtra("clientEMEContact", clientObj.get(i).client_eme_cont);
                i1.putExtra("clientEMEPhone", clientObj.get(i).client_eme_phone);
                i1.putExtra("clientNotes", clientObj.get(i).client_notes);
                startActivity(i1);
            }
        });


    }

    private void FetchData(Cursor c) {
        SubClients subCmp = new SubClients();

        subCmp.id = c.getInt(0);
        subCmp.clientid = c.getString(1);
        subCmp.client_name = c.getString(2);
        subCmp.client_type = c.getString(3);
        subCmp.client_birth = c.getString(4);
        subCmp.client_gender = c.getString(5);
        subCmp.client_marital_stat = c.getString(6);

        subCmp.client_email = c.getString(7);
        subCmp.client_address = c.getString(8);

        subCmp.client_cell_phone = c.getString(9);
        subCmp.client_home_phone = c.getString(10);
        subCmp.client_eme_cont = c.getString(11);
        subCmp.client_eme_phone = c.getString(12);

        subCmp.client_eme_phone = c.getString(13);
        subCmp.client_notes = c.getString(14);


        clientObj.add(subCmp);


    }

    @Override
    protected void onResume() {
        super.onResume();
        processFetchClients();
    }

    class CustomAdapter extends BaseAdapter {
        LayoutInflater layoutInflator;
        private Context ctx;


        List<SubClients> ValuesSearch;
        ArrayList<SubClients> arraylist;

        public CustomAdapter(Context ctx, ArrayList<SubClients> cmp) {
            this.ctx = ctx;

            this.ValuesSearch = cmp;

            arraylist = new ArrayList<SubClients>();
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


            txtName.setText(ValuesSearch.get(position).client_name);

            if (ValuesSearch.get(position).client_cell_phone.length() != 0)
                txtPhone.setText(ValuesSearch.get(position).client_cell_phone);
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
                for (SubClients obj : arraylist) {
                    if (charText.length() != 0 && obj.client_name.toLowerCase(Locale.getDefault()).contains(charText)) {
                        ValuesSearch.add(obj);
                    }

                }
            }
            notifyDataSetChanged();
        }


    }

    //end of main class
}
