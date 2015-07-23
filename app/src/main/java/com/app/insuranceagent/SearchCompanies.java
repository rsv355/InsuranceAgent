package com.app.insuranceagent;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.insuranceagent.model.Companies;
import com.app.insuranceagent.model.SubCompanies;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchCompanies extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgBack,imgNew;
    ListView listView;
    DBAdapter db;

    EditText edSearchBox;
    ImageView imgSearch;
    CustomAdapter adp;
    ArrayList<SubCompanies> cmpObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_companies);
        db = new DBAdapter(SearchCompanies.this);

        init();

    }


    @Override
    protected void onResume() {
        super.onResume();
        processFetchCompanies();
    }

    void processFetchCompanies(){

        cmpObj = new ArrayList<>();

        db.open();
        Cursor c = db.getALLCompaniesList();
        if (c.moveToFirst())
        {
            do {
                FetchData(c);
            } while (c.moveToNext());
        }
        db.close();

        adp = new  CustomAdapter(SearchCompanies.this, cmpObj);

        listView.setAdapter(adp);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1 = new Intent(SearchCompanies.this,NewCompany.class);

                i1.putExtra("cmpID", cmpObj.get(i).cmpID);
                i1.putExtra("cmpName", cmpObj.get(i).cmpName);
                i1.putExtra("cmpAddress",cmpObj.get(i).cmpAddress);
                i1.putExtra("cmpUrl",cmpObj.get(i).cmpWebURL);
                i1.putExtra("cmpNotes",cmpObj.get(i).cmpNotes);
                startActivity(i1);
            }
        });


    }

    private void FetchData(Cursor c)
    {
        SubCompanies subCmp = new SubCompanies();
        subCmp.cmpID = c.getInt(0);
        subCmp.cmpName = c.getString(1);
        subCmp.cmpAddress = c.getString(2);
        subCmp.cmpWebURL = c.getString(3);
        subCmp.cmpNotes = c.getString(4);


        cmpObj.add(subCmp);


    }


    private void init(){
        edSearchBox = (EditText)findViewById(R.id.edSearchBox);
        imgSearch = (ImageView)findViewById(R.id.imgSearch);

        imgNew = (ImageView)findViewById(R.id.imgNew);
        listView= (ListView)findViewById(R.id.listView);
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        imgBack = (ImageView)findViewById(R.id.imgBack);
        txtTitle.setText(getIntent().getStringExtra("title"));

        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(SearchCompanies.this, NewCompany.class);
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


        List<SubCompanies> ValuesSearch;
        ArrayList<SubCompanies> arraylist;

        public CustomAdapter(Context ctx,ArrayList<SubCompanies> cmp){
            this.ctx = ctx;

            this.ValuesSearch = cmp;

            arraylist = new ArrayList<SubCompanies>();
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


            view = layoutInflator.inflate(R.layout.row_item_comapnies, parent, false);


            TextView txtName = (TextView)view.findViewById(R.id.txtName);
            txtName.setText(ValuesSearch.get(position).cmpName);

            return view;
        }

        // Filter Class
        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());

            ValuesSearch.clear();
            if (charText.length() == 0) {
                ValuesSearch.addAll(arraylist);

            } else {
                for ( SubCompanies obj: arraylist) {
                    if (charText.length() != 0 && obj.cmpName.toLowerCase(Locale.getDefault()).contains(charText)) {
                        ValuesSearch.add(obj);
                    }

                }
            }
            notifyDataSetChanged();
        }



    }









    //end of main class
}
