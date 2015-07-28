package com.app.insuranceagent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Krishna on 21-07-2015.
 */

public class DBAdapter { public static final String KEY_ROWID = "_id";
    /* public static final String KEY_NAME = "playername";
     public static final String KEY_SCORE = "score";*/
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "MyDB1";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_COMPANIES =
            "create table COMPANIES (cmp_id integer primary key autoincrement,cmp_name text,cmp_address text,cmp_weburl text,cmp_notes text);";


    private static final String DATABASE_AGENTS =
            "create table AGENTS (agent_id integer primary key autoincrement,agent_name text,agent_phone text,agent_address text,agent_notes text);";


    private static final String DATABASE_CLIENTS =
            "create table CLIENTS (client_id integer primary key autoincrement," +
                    "id text," +  //1
                    "client_name text," + //2
                    "client_type text," +//3
                    "client_birth text," +//4
                    "client_gender text," +//5
                    "client_marital_stat text," +//6
                    "client_email text," +//7
                    "client_address text," +//8
                    "client_cell_phone text," +//9
                    "client_home_phone text," +//10
                    "client_work_phone text," +//11
                    "client_eme_cont text," +//12
                    "client_eme_phone text," +//13
                    "client_notes text);";//14


    private static final String DATABASE_APPOINTMENTS =
            "create table APPOINTMENTS (app_id integer primary key autoincrement," +
                    "client_name text," +
                    "agent_name text," +
                    "date text," +
                    "time text," +
                    "app_notes text);";//14


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_COMPANIES);
                db.execSQL(DATABASE_AGENTS);
                db.execSQL(DATABASE_CLIENTS);
                db.execSQL(DATABASE_APPOINTMENTS);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS COMPANIES");
            db.execSQL("DROP TABLE IF EXISTS AGENTS");
            db.execSQL("DROP TABLE IF EXISTS CLIENTS");
            db.execSQL("DROP TABLE IF EXISTS APPOINTMENTS");

            onCreate(db);
        }
    }


    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }



    public boolean updateCompanies(long cmp_id,String name,String add,String url,String notes)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("cmp_name", name);//1
        initialValues.put("cmp_address", add);//2
        initialValues.put("cmp_weburl", url);//3
        initialValues.put("cmp_notes", notes);//3
        return db.update("companies", initialValues, "cmp_id = " + cmp_id, null) > 0;
    }


    public boolean updateAgents(long agent_id,String name,String phone,String add,String notes)
    {
        Log.e("agent_id",""+agent_id);
        Log.e("name",name);
        Log.e("phone",phone);
        Log.e("add",add);
        Log.e("notes",notes);

        ContentValues initialValues = new ContentValues();

        initialValues.put("agent_name", name);//1
        initialValues.put("agent_phone", phone);//2
        initialValues.put("agent_address", add);//3
        initialValues.put("agent_notes", notes);//4
        return db.update("AGENTS", initialValues, "agent_id = " + agent_id, null) > 0;
    }

    public boolean updateClients(long client_id,String id,String client_name,String client_type,String client_birth,
                              String client_gender,String client_marital_stat,String client_email,String client_address,
                              String client_cell_phone,String client_home_phone,String client_work_phone,String client_eme_cont,String client_eme_phone,String client_notes)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("id", id);//1
        initialValues.put("client_name", client_name);//2
        initialValues.put("client_type", client_type);//3
        initialValues.put("client_birth", client_birth);//3

        initialValues.put("client_gender", client_gender);//1
        initialValues.put("client_marital_stat", client_marital_stat);//2
        initialValues.put("client_email", client_email);//3
        initialValues.put("client_address", client_address);//3

        initialValues.put("client_cell_phone", client_cell_phone);//1
        initialValues.put("client_home_phone", client_home_phone);//2
        initialValues.put("client_work_phone", client_work_phone);//3
        initialValues.put("client_eme_cont", client_eme_cont);//3
        initialValues.put("client_eme_phone", client_eme_phone);//3
        initialValues.put("client_notes", client_notes);//3


        Log.e("insert in CLIENTS ", "ok");

        return db.update("CLIENTS", initialValues, "client_id = " + client_id, null) > 0;
    }


    public long insertClients(String id,String client_name,String client_type,String client_birth,
                              String client_gender,String client_marital_stat,String client_email,String client_address,
                              String client_cell_phone,String client_home_phone,String client_work_phone,String client_eme_cont,String client_eme_phone,String client_notes)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("id", id);//1
        initialValues.put("client_name", client_name);//2
        initialValues.put("client_type", client_type);//3
        initialValues.put("client_birth", client_birth);//3

        initialValues.put("client_gender", client_gender);//1
        initialValues.put("client_marital_stat", client_marital_stat);//2
        initialValues.put("client_email", client_email);//3
        initialValues.put("client_address", client_address);//3

        initialValues.put("client_cell_phone", client_cell_phone);//1
        initialValues.put("client_home_phone", client_home_phone);//2
        initialValues.put("client_work_phone", client_work_phone);//3
        initialValues.put("client_eme_cont", client_eme_cont);//3
        initialValues.put("client_eme_phone", client_eme_phone);//3
        initialValues.put("client_notes", client_notes);//3


        Log.e("insert in CLIENTS ", "ok");

        return db.insert("CLIENTS", null, initialValues);
    }

    public long insertAgents(String name,String phone,String add,String notes)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("agent_name", name);//1
        initialValues.put("agent_phone", phone);//2
        initialValues.put("agent_address", add);//3
        initialValues.put("agent_notes", notes);//3

        Log.e("insert in AGENTS ", "ok");

        return db.insert("AGENTS", null, initialValues);
    }

    public long insertCompanies(String name,String add,String url,String notes)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("cmp_name", name);//1
        initialValues.put("cmp_address", add);//2
        initialValues.put("cmp_weburl", url);//3
        initialValues.put("cmp_notes", notes);//3

        Log.e("insert in companies ", "ok");

        return db.insert("COMPANIES", null, initialValues);
    }


    public long insertAppointments(String cname,String aname,String date,String time,String notes)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("client_name", cname);//1
        initialValues.put("agent_name", aname);//2
        initialValues.put("date", date);//3
        initialValues.put("time", time);//3
        initialValues.put("app_notes", notes);//3

        Log.e("insert in APPOINTMENTS ", "ok");

        return db.insert("APPOINTMENTS", null, initialValues);
    }

    public boolean updateAppointments(long app_id,String cname,String aname,String date,String time,String notes)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("client_name", cname);//1
        initialValues.put("agent_name", aname);//2
        initialValues.put("date", date);//3
        initialValues.put("time", time);//3
        initialValues.put("app_notes", notes);//3
        return db.update("APPOINTMENTS", initialValues, "app_id = " + app_id, null) > 0;
    }



    public Cursor getALLAppointmentsList() throws SQLException
    {
        String selectQuery = "SELECT * FROM APPOINTMENTS";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor getALLCompaniesList() throws SQLException
    {
        String selectQuery = "SELECT * FROM companies";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor getALLAgentsList() throws SQLException
    {
        String selectQuery = "SELECT * FROM AGENTS";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor getALLClientsList() throws SQLException
    {
        String selectQuery = "SELECT * FROM CLIENTS";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }






    //---deletes a particular contact---
    public void deleteRecord( )
    {
        Log.e("record ddelted ","ok");
        db.execSQL("delete  from DATABASE_TABLE");
        //  return db.delete(DATABASE_TABLE,null );
    }



    //---retrieves a particular contact---
    public Cursor getRecord(String rowId) throws SQLException
    {
        String selectQuery = "SELECT * FROM D_Word_ENG_HIN WHERE UPPER(WORD) ="+"\""+rowId.toString().trim().toUpperCase()+"\"";
        Cursor cursor = db.rawQuery(selectQuery, null);


        /*Cursor mCursor =db.query(true, DATABASE_TABLE, new String[] {
                        KEY_NAME, KEY_SCORE}, KEY_NAME + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }*/
        return cursor;
    }

    public Cursor getSimilarRecord(String rowId) throws SQLException
    {
        String selectQuery = "SELECT * FROM D_Word_ENG_HIN WHERE WORD IN (Select GROUP_NAME from Group_table where UPPER(GROUP_NAME))= "+"\""+rowId.toString().trim().toUpperCase()+"\"";
        Cursor cursor = db.rawQuery(selectQuery, null);


        return cursor;
    }




    public Cursor getCategory(String CatgId) throws SQLException
    {
        String selectQuery = "SELECT * FROM D_Word_ENG_HIN WHERE CATG_ID ="+"\""+CatgId.toString().trim()+"\"";
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }



}