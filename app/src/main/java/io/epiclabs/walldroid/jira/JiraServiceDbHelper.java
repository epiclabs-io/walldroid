package io.epiclabs.walldroid.jira;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import io.epiclabs.walldroid.core.ServiceDbHelper;

/**
 * Created by adrian on 14/05/17.
 */

public class JiraServiceDbHelper extends ServiceDbHelper {
    public static final String TABLE_NAME = "jira_service";

//    private String username;
//    private String password;
//    private String wallboardId;
//    private Integer period;
//    private String effect;
//    private Boolean random;

    public static final String CREATE_SQL =
            "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY," +
                "alias TEXT," +
                "host TEXT," +
                "username TEXT," +
                "password TEXT," +
                "wallboardId TEXT," +
                "period NUMBER," +
                "effect TEXT," +
                "random BOOLEAN)";

    public static final String DELETE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public JiraServiceDbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, factory, CREATE_SQL, DELETE_SQL);
    }
}
