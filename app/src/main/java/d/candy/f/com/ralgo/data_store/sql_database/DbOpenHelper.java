package d.candy.f.com.ralgo.data_store.sql_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import d.candy.f.com.ralgo.infra.sqlite.SqliteTableUtils;

/**
 * Created by daichi on 8/15/17.
 *
 * This class depends on {@link DbContract} class.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    public DbOpenHelper(@NonNull Context context) {
        super(context, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        SqliteTableUtils.TableSource[] tableSourses = DbContract.getTableSourses();
        for (SqliteTableUtils.TableSource source : tableSourses) {
            SqliteTableUtils.createTable(sqLiteDatabase, source);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO: 8/15/17 #10 Implement
    }
}
