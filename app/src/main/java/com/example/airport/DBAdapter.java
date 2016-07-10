package com.example.airport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class DBAdapter {
    DBHelper dbHelper;

    public long insertUser(String id ,String title , String message){
        System.out.println("hereeeeeeeeeeeeeeeeee2");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBHelper.Col_User_id, id);
        contentValues.put(DBHelper.Col_User_title, title);
        contentValues.put(DBHelper.Col_User_message, message);
//        contentValues.put(DBHelper.Col_User_ideal_LunchTime, lunchTime);
//        contentValues.put(DBHelper.Col_User_ideal_dinnerTime, dinnerTime);
        System.out.println("hereeeeeeeeeeeeeeeeee3");
        long record= db.insert(DBHelper.USER_TABLE_NAME,null,contentValues);
        System.out.println("hereeeeeeeeeeeeeeeeee4");
        return record;
    }

    public Map getUser(Integer randomId)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        String random_id = randomId.toString();
        String whereClause = "id = " + random_id;

        System.out.println("id is this" + random_id);

        String[] columns={DBHelper.Col_User_id,DBHelper.Col_User_title,DBHelper.Col_User_message};
        Cursor cursor=db.query(DBHelper.USER_TABLE_NAME, columns, whereClause, null, null, null, null);

        StringBuffer buffer=new StringBuffer();
        Map<String, String> map = new HashMap<String, String>();
        while(cursor.moveToNext())
        {
            int index2 = cursor.getColumnIndex(DBHelper.Col_User_id);
            int index3 = cursor.getColumnIndex(DBHelper.Col_User_title);
            int index4 = cursor.getColumnIndex(DBHelper.Col_User_message);
//            int index5 = cursor.getColumnIndex(DBHelper.Col_User_ideal_LunchTime);
//            int index6 = cursor.getColumnIndex(DBHelper.Col_User_ideal_dinnerTime);


            String id = cursor.getString(index2);
            String title = cursor.getString(index3);
            String message = cursor.getString(index4);
//            String age = cursor.getString(index4);
//            String lunchTime = cursor.getString(index5);
//            String dinnerTime = cursor.getString(index6);
//            String id1= id.toString();

            map.put("id", id);
            map.put("title", title);
            map.put("message", message);
;
//            System.out.println(map.get("dog"));

//            buffer.append(name+" "+email+" "+age+" "+lunchTime+" "+dinnerTime+"\n");
        }
        return map;
    }













    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context);
    }

    static class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MyDBName.db";
        //private static final String MEDICINE_TABLE_NAME = "Medicines";
        private static final String USER_TABLE_NAME = "User";
        private static final String UID = "_id";
        private static final int DATABASE_VERSION = 6;
        //private static final String CONTACTS_COLUMN_ID = "id";
        private static final String Col_User_id = "id";
        private static final String Col_User_title = "title";
        private static final String Col_User_message = "message";
        /*private static final String Col_User_ideal_LunchTime = "ideal_lunchTime";
        private static final String Col_User_ideal_dinnerTime = "ideal_dinnerTime";
        private static final String Col_Medicine_medicineName = "medicineName";
        private static final String Col_Medicine_dosageType = "dosageType";
        private static final String Col_Medicine_dosageUnits = "dosageUnits";
        private static final String Col_Medicine_dosageTime = "dosageTime";
        private static final String Col_Medicine_medicineDuration = "medicineDuration";
        private static final String Col_Medicine_stock = "stock";
        private static final String Col_Medicine_storeName = "storeName";
        private static final String Col_Medicine_storeNumber = "storeNumber";
        */
        private static final String DROP_TABLE = " DROP TABLE IF EXISTS " + USER_TABLE_NAME;
/*
        private static final String create_medicines = "CREATE TABLE " + MEDICINE_TABLE_NAME + "(" +
                Col_Medicine_medicineName + " VARCHAR(255)," +
                Col_Medicine_dosageType + " VARCHAR(255)," +
                Col_Medicine_dosageUnits + " INTEGER ," +
                Col_Medicine_dosageTime + " VARCHAR(255)," +
                Col_Medicine_medicineDuration + " VARCHAR(255)," +
                Col_Medicine_stock + " INTEGER ," +
                Col_Medicine_storeName + " VARCHAR(255) ," +
                Col_Medicine_storeNumber + " VARCHAR(255));";
*/

        private static final String create_user = "CREATE TABLE " + USER_TABLE_NAME + "(" +
                Col_User_id + " VARCHAR(255)," +
                Col_User_title + " VARCHAR(255)," +
                Col_User_message + " VARCHAR(255));";
  //              Col_User_ideal_LunchTime + " VARCHAR(255)," +
    //            Col_User_ideal_dinnerTime + " VARCHAR(255));";


        private HashMap hp;
        private Context context;

        //SQLiteDatabase sqliteDatabase;
        public DBHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Toast.makeText(context, "DbHelper constructor called", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try {
                db.execSQL(DROP_TABLE);
                //db.execSQL(create_medicines);
                db.execSQL(create_user);
                Toast.makeText(context, "table created", Toast.LENGTH_LONG).show();
                Toast.makeText(context, "onCreate called", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, "e", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            //db.execSQL(DROP_TABLE);
            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_SHORT).show();
        }
    }
}
