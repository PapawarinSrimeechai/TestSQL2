package com.example.miw.testsql2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class myDBclass extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mydatabase";
    // Table Name
    private static final String TABLE_NAME = "TableTravel";
    public static final String COL_TNAME = "Travel";
    public static final String COL_TDES = "TraveDes";
    public static final String COL_PICTURE = "picture";
    public myDBclass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        // Create Table Name
        db.execSQL("CREATE TABLE "+ TABLE_NAME
                +" (TravelID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TNAME + " TEXT, " + COL_TDES + " TEXT, "
                + COL_PICTURE + " TEXT);");

        Log.d("CREATE TABLE","Create Table Successfully.");
    }

    // Insert Data
    public long InsertData(String Travel, String TraveDes, String picture) {
        // TODO Auto-generated method stub

        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data

            /**
             *  for API 11 and above
             SQLiteStatement insertCmd;
             String strSQL = "INSERT INTO " + TABLE_GALLERY
             + "(GalleryID,Name,Path) VALUES (?,?,?)";

             insertCmd = db.compileStatement(strSQL);
             insertCmd.bindString(1, strGalleryID);
             insertCmd.bindString(2, strName);
             insertCmd.bindString(3, strPath);
             return insertCmd.executeInsert();
             */

            ContentValues val = new ContentValues();
            val.put(COL_TNAME,Travel);
            val.put(COL_TDES,TraveDes);
            val.put(COL_PICTURE,picture);

            long rows = db.insert(TABLE_NAME, null, val);

            db.close();
            return rows; // return rows inserted.

        } catch (Exception e) {
            return -1;
        }

    }
    public long insertData (String Travel, String TraveDes, String picture){
        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put(COL_TNAME,Travel);
            val.put(COL_TDES,TraveDes);
            val.put(COL_PICTURE,picture);
            long rows = db.insert(TABLE_NAME,null,val);
            db.close();
            return rows;
        }catch (Exception e){
            return -1;
        }
    }
    //select data admin
    public ArrayList<HashMap<String, String>> selectAllDataadmin() {
        try{
            ArrayList<HashMap<String , String >> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String , String> map;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); //read data

            String strSQL = "SELECT * FROM "+ TABLE_NAME;
            Cursor cursor = db.rawQuery(strSQL,null);

            if (cursor!=null){
                if (cursor.moveToFirst()){
                    do {
                        map = new HashMap<String , String>();
                        map.put("TravelID",cursor.getString(0));
                        map.put("Travel",cursor.getString(1));
                        map.put("TraveDes",cursor.getString(2));
                        map.put("picture",cursor.getString(3));
                        MyArrList.add(map);
                    }while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            return MyArrList;
        }catch (Exception e){
            return null;
        }
    }

    // Select All Data
    public String[][] SelectAllData() {
        // TODO Auto-generated method stub

        try {
            String arrData[][] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT  * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(strSQL, null);

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()][cursor.getColumnCount()];
                    /***
                     *  [x][0] = GalleryID
                     *  [x][1] = Name
                     *  [x][2] = Path
                     */
                    int i= 0;
                    do {
                        arrData[i][0] = cursor.getString(0); //TravelID
                        arrData[i][1] = cursor.getString(1); //Travel
                        arrData[i][2] = cursor.getString(2); //TraveDes
                        arrData[i][3] = cursor.getString(3); //picture
                        i++;
                    } while (cursor.moveToNext());

                }
            }
            cursor.close();
            db.close();
            return arrData;

        } catch (Exception e) {
            return null;
        }

    }

    //searchdata
    public ArrayList<HashMap<String,String>> SearchData(String keyword){
        try {

            ArrayList<HashMap<String,String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String,String> map;
            SQLiteDatabase db;
            db = this.getReadableDatabase();
            String strSQL = "SELECT  * FROM "+TABLE_NAME+ "  WHERE "
                    + COL_TNAME + " LIKE "+ "'%"+keyword+"%'";
            Cursor cursor = db.rawQuery(strSQL,null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        map = new HashMap<String,String>();
                        map.put("TravelID",cursor.getString(0));
                        map.put(COL_TNAME,cursor.getString(1));
                        map.put(COL_TDES,cursor.getString(2));
                        map.put(COL_PICTURE,cursor.getString(3));
                        MyArrList.add(map);
                    }while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            return MyArrList;
        }catch (Exception e){
            return null;
        }
    }
    //Delete data
    public long DeleteData (String TravelID){
        try{
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            long rows = db.delete(TABLE_NAME, "TravelID = ?"
                    , new String[] {String.valueOf(TravelID)});
            db.close();
            return rows;
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Re Create on method  onCreate
        onCreate(db);
    }

}
