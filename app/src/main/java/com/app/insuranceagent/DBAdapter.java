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


    public Cursor getALLCompaniesList() throws SQLException
    {
        String selectQuery = "SELECT * FROM companies";
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