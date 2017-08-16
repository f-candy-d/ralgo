package d.candy.f.com.ralgo.infra.sqlite;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by daichi on 17/08/16.
 */

public interface SqliteDatabaseOpenHelper {

    SQLiteDatabase openWritableDatabase();
    SQLiteDatabase openReadableDatabase();
}
