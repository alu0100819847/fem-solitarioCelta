package es.upm.miw.SolitarioCelta.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static es.upm.miw.SolitarioCelta.models.ResultContract.tablaResults;

public class RepositorioResults extends SQLiteOpenHelper {
    public static final String NOMBRE_FICHERO = tablaResults.Table_Name + ".db";
    public static final int DATABASE_VERSION = 1;

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
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
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

}