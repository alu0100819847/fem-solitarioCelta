package es.upm.miw.SolitarioCelta.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static es.upm.miw.SolitarioCelta.models.ResultContract.tablaResults;

public class RepositorioResults extends SQLiteOpenHelper {

    public static final String NOMBRE_FICHERO = tablaResults.Table_Name + ".db";

    public static final int DATABASE_VERSION = 1;

    public final int NUMBER_OF_ENTRIES= 3;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + tablaResults.Table_Name + " (" +
                    tablaResults.COL_NAME_ID + " INTEGER PRIMARY KEY," +
                    tablaResults.COL_NAME_NAME + " TEXT," +
                    tablaResults.COL_NAME_DATE + " DATE," +
                    tablaResults.COL_NAME_PIECE + " INTEGER )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + tablaResults.Table_Name;

    public RepositorioResults(Context context) {
        super(context, NOMBRE_FICHERO, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long add(String name, int pices){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tablaResults.COL_NAME_NAME, name);
        values.put(tablaResults.COL_NAME_DATE, new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss: ",
                Locale.getDefault())
                .format(new Date()));
        values.put(tablaResults.COL_NAME_PIECE, pices);

        long newRowId = db.insert(tablaResults.Table_Name, null, values);
        return newRowId;
    }

    public ArrayList<String> readString(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                tablaResults.COL_NAME_ID,
                tablaResults.COL_NAME_NAME,
                tablaResults.COL_NAME_DATE,
                tablaResults.COL_NAME_PIECE
        };

        String sortOrder = tablaResults.COL_NAME_PIECE + " ASC";

        Cursor cursor = db.query(
                tablaResults.Table_Name,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        ArrayList items = new ArrayList<>();
        int count = 0;
        while(cursor.moveToNext() && count < this.NUMBER_OF_ENTRIES) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(tablaResults.COL_NAME_NAME));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(tablaResults.COL_NAME_DATE));
            int pieces = cursor.getInt(cursor.getColumnIndexOrThrow(tablaResults.COL_NAME_PIECE));
            items.add( name + " | " + date + " | " + pieces );
            count++;

        }
        cursor.close();
        return items;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+ tablaResults.Table_Name);
    }

}