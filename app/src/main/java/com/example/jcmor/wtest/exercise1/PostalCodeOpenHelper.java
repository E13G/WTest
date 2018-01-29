package com.example.jcmor.wtest.exercise1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * Created by jcmor on 27/01/2018.
 *
 * Class to make CRUD actions to the SQLite Database
 */

public class PostalCodeOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = PostalCodeOpenHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 8;
    private static final String POSTALCODE_LIST_TABLE = "postalcode_entries";
    private static final String DATABASE_NAME = "postcodelist";

    private static final String KEY_ID = "_id";
    static final String KEY_POSTAL_CODE = "postal_code";

    private static final String POSTCODE_TABLE_CREATE =
            "CREATE TABLE " + POSTALCODE_LIST_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_POSTAL_CODE + " TEXT );";

    private SQLiteDatabase writableDB;
    private SQLiteDatabase readableDB;

    PostalCodeOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(POSTCODE_TABLE_CREATE);
        //fillDatabaseWithData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + POSTALCODE_LIST_TABLE);
        onCreate(db);
    }

     PostalCode query(int position){

        String query = "SELECT * FROM " + POSTALCODE_LIST_TABLE +
                " ORDER BY " + KEY_POSTAL_CODE + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;

        PostalCode entry = new PostalCode();

        try{
            if (readableDB == null) {
                readableDB = getReadableDatabase();
            }
            cursor = readableDB.rawQuery(query, null);
            cursor.moveToFirst();

            entry.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setPostalCode(cursor.getString(cursor.getColumnIndex(KEY_POSTAL_CODE)));


        }catch (Exception e){
            Log.d(TAG, "QUERY EXCEPTION! " + e);
        }  {
            cursor.close();

        }
        return entry;
    }

    Cursor search(String searchString) {
        String[] columns = new String[]{KEY_POSTAL_CODE};
        String where =  KEY_POSTAL_CODE + " LIKE ?";
        searchString = "%" + searchString + "%";
        String[] whereArgs = new String[]{searchString};

        Cursor cursor = null;
        try {
            if (readableDB == null) {
                readableDB = getReadableDatabase();
            }
            cursor = readableDB.query(POSTALCODE_LIST_TABLE, columns, where, whereArgs, null, null, null);
        } catch (Exception e) {
            Log.d(TAG, "SEARCH EXCEPTION! " + e);
        }
        return cursor;
    }

     long count() {
        if (readableDB == null) {readableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(readableDB, POSTALCODE_LIST_TABLE);
    }

    void insert( String word) {

        ContentValues values = new ContentValues();
        values.put(KEY_POSTAL_CODE, word);

        if (writableDB == null) {
            writableDB = getWritableDatabase();
        }
         writableDB.insert(POSTALCODE_LIST_TABLE, null, values);
    }
}
