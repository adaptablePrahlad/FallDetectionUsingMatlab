package com.example.fallalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_BLOODGROUP = "user_bloodgroup";
    private static final String COLUMN_USER_CONTACT = "user_contact";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_BLOODGROUP + " TEXT," + COLUMN_USER_CONTACT + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;



    public DatabaseHelper(@Nullable Context context) {
        super(context, "FallDetection", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);
    }

    /**
    * This method is to create user record
     *
             * @param user
     */
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_BLOODGROUP, user.getBloodGroup());
        values.put(COLUMN_USER_CONTACT, user.getContact());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        long result = db.insert(TABLE_USER, null, values);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor loginuser(String semail, String spassword)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from user where user_email='" + semail + "' and user_password='" + spassword + "'";
        Cursor result = db.rawQuery(query,null);
        return result;
    }

    public Boolean updateuserdata(String email, String name, String password, String bloodgroup, String contact)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name", name);
        contentValues.put("user_password",password);
        contentValues.put("user_bloodgroup", bloodgroup);
        contentValues.put("user_contact",contact);
        Cursor cursor = DB.rawQuery("Select * from user where user_email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            long result = DB.update("user", contentValues, "user_email=?", new String[]{email});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


}
