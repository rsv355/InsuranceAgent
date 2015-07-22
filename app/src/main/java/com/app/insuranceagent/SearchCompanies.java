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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchCompanies extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgBack,imgNew;
    ListView listView;
    DBAdapter db;
    ArrayList<Integer> cmpID ;
    ArrayList<String> cmp ;
    ArrayList<String> address ;
    ArrayList<String> url ;
    ArrayList<String> notes ;

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

        cmpID = new ArrayList<>();
        address= new ArrayList<>();
        url= new ArrayList<>();
        notes= new ArrayList<>();
        cmp = new ArrayList<>();

        db.open();
        Cursor c = db.getALLCompaniesList();
        if (c.moveToFirst())
        {
            do {
                FetchData(c);
            } while (c.moveToNext());
        }
        db.close();

        listView.setAdapter(new CustomAdapter(SearchCompanies.this, cmp));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1 = new Intent(SearchCompanies.this,NewCompany.class);

                i1.putExtra("cmpID", cmpID.get(i));
                i1.putExtra("cmpName", cmp.get(i));
                i1.putExtra("cmpAddress",address.get(i));
                i1.putExtra("cmpUrl",url.get(i));
                i1.putExtra("cmpNotes",notes.get(i));
                startActivity(i1);
            }
        });


    }

    private void FetchData(Cursor c)
    {
        cmpID.add(c.getInt(0));
        cmp.add(c.getString(1));
        address.add(c.getString(2));
        url.add(c.getString(3));
        notes.add(c.getString(4));

    }


    private void init(){
        imgNew = (ImageView)findViewById(R.id.imgNew);
        listView= (ListView)findViewById(R.id.listView);
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        imgBack = (ImageView)findViewById(R.id.imgBack);
        txtTitle.setText(getIntent().getStringExtra("title"));

        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(SearchCompanies.this,NewCompany.class);
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



    class CustomAdapter extends BaseAdapter {
        LayoutInflater layoutInflator;
        private Context ctx;
        ArrayList<String> values;

        public CustomAdapter(Context ctx,ArrayList<String> cmp){
            this.ctx = ctx;
            this.values = cmp;
        }


        @Override
        public int getCount() {
            return values.size();
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
            txtName.setText(values.get(position));

            return view;
        }


    }









    //end of main class
}
