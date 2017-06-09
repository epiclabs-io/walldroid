package io.epiclabs.walldroid.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adrian on 14/05/17.
 */

abstract public class PluginDbAgent {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Services.db";

    private String createSql;
    private String deleteSql;


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public PluginDbAgent() {
    }
//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + ServiceDbHelper.TABLE_NAME + " (" +
//                    ServiceDbHelper._ID + " INTEGER PRIMARY KEY," +
//                    ServiceDbHelper.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
//                    ServiceDbHelper.COLUMN_NAME_SUBTITLE + TEXT_TYPE + " )";
//
//    private static final String SQL_DELETE_ENTRIES =
//            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

//    public ServiceDbHelper(Context context, SQLiteDatabase.CursorFactory factory, String createSql, String deleteSql) {
//        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
//        this.createSql = createSql;
//        this.deleteSql = deleteSql;
//    }

//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(createSql);
//    }
//
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // This database is only a cache for online data, so its upgrade policy is
//        // to simply to discard the data and start over
//        db.execSQL(deleteSql);
//        onCreate(db);
//    }
//
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        onUpgrade(db, oldVersion, newVersion);
//    }
}
