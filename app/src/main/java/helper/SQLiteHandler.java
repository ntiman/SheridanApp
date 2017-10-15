package helper;

/**
 * Created by 1 on 06.12.2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_STUDENT = "student";

    // Login Table Columns names
    private static final String KEY_STUID = "stuid";
    private static final String KEY_SCORE = "score";
    private static final String KEY_COMMENT = "comment";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
                + KEY_STUID + " INTEGER PRIMARY KEY UNIQUE," + KEY_SCORE + " TEXT,"
                + KEY_COMMENT + " TEXT" + ")";
        db.execSQL(CREATE_STUDENT_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        // Create tables again
        onCreate(db);
    }

    public void delete() {
        // Drop older table if existed
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        // Create tables again
        onCreate(db);
    }


    public void UpdateUser (int id, String score, String comment){
        SQLiteDatabase db = this.getReadableDatabase();
        String UPDATE_USER = "UPDATE " + TABLE_STUDENT+ " SET "+ KEY_SCORE+"='"+score+"', "+KEY_COMMENT+"='"+comment+"' WHERE "+KEY_STUID+"="+id+";";
        db.execSQL(UPDATE_USER);
        db.close();

    }

    /**
     * Storing user details in database
     */
    public void addUser(int stuid, String score, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUID, stuid); // Student ID
        values.put(KEY_SCORE, score); // Score
        values.put(KEY_COMMENT, comment); // Comment

        // Inserting Row
        long id = db.insert(TABLE_STUDENT, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);

    }


    /**
     * Returns Row Count in database
     */


    public int getCount(){
        String countQ = "SELECT * FROM " + TABLE_STUDENT;
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQ, null);
        if (cursor != null && !cursor.isClosed()){
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public ArrayList<Students> getListContents() {
        ArrayList<Students> students = new ArrayList<Students>();
        students.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_STUDENT, null);

            if (data != null) {
                data.moveToFirst();
                while(data.isAfterLast() == false) {
                    Students st = new Students();
                    st.setID(data.getInt(0));
                    st.setScore(data.getString(1).toString());
                    st.setComment(data.getString(2).toString());
                    students.add(st);
                    data.moveToNext();
                }
                db.close();
                data.close();
            }
            return students;
        }


    /**
     * Getting user data from database
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("stuid", cursor.getString(1));
            user.put("score", cursor.getString(2));
            user.put("comment", cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;

    }

}
