package d.candy.f.com.ralgo.infra;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import d.candy.f.com.ralgo.infra.data_package.ConfigValuePackage;
import d.candy.f.com.ralgo.infra.data_package.SqlEntryPackage;
import d.candy.f.com.ralgo.infra.sqlite.SqliteDatabaseOpenHelper;
import d.candy.f.com.ralgo.infra.sqlite.SqliteQuery;
import d.candy.f.com.ralgo.infra.sqlite.SqliteWhere;

/**
 * Created by daichi on 17/08/14.
 */

public class SqliteAndSharedPrefRepository implements Repository {

    @NonNull final private Context mContext;
    @NonNull final private SqliteDatabaseOpenHelper mDatabaseOpenHelper;

    public SqliteAndSharedPrefRepository
            (@NonNull Context context, @NonNull SqliteDatabaseOpenHelper databaseOpenHelper) {

        mContext = context;
        mDatabaseOpenHelper = databaseOpenHelper;
    }

    /**
     * region; Save data to SharedPreferences
     */

    @Override
    public void saveConfigValue(@NonNull String key, int value) {
        SharedPreferences preferences = getPreferences();
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    @Override
    public void saveConfigValue(@NonNull String key, long value) {
        SharedPreferences preferences = getPreferences();
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    @Override
    public void saveConfigValue(@NonNull String key, float value) {
        SharedPreferences preferences = getPreferences();
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    @Override
    public void saveConfigValue(@NonNull String key, String value) {
        SharedPreferences preferences = getPreferences();
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public void saveConfigValue(@NonNull String key, boolean value) {
        SharedPreferences preferences = getPreferences();
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    @Override
    public void saveConfigValues(@NonNull ConfigValuePackage entryPackage) {
        SharedPreferences preferences = getPreferences();
        final SharedPreferences.Editor editor = preferences.edit();

        // boolean
        Set<String> keySet = entryPackage.keySetForBooleanValues();
        if (keySet != null) {
            for (String key : keySet) {
                editor.putBoolean(key, entryPackage.getBoolean(key));
            }
        }

        // long
        // boolean
        keySet = entryPackage.keySetForLongValues();
        if (keySet != null) {
            for (String key : keySet) {
                editor.putLong(key, entryPackage.getLong(key));
            }
        }

        // int
        keySet = entryPackage.keySetForIntValues();
        if (keySet != null) {
            for (String key : keySet) {
                editor.putInt(key, entryPackage.getInt(key));
            }
        }

        // float
        keySet = entryPackage.keySetForFloatValues();
        if (keySet != null) {
            for (String key : keySet) {
                editor.putFloat(key, entryPackage.getFloat(key));
            }
        }

        // String
        keySet = entryPackage.keySetForStringValues();
        if (keySet != null) {
            for (String key : keySet) {
                editor.putString(key, entryPackage.getString(key));
            }
        }

        editor.apply();
    }

    /**
     * region; Load config data from SharedPreferences
     */

    @Override
    public int loadConfigValueAsInt(@NonNull String key) {
        return loadConfigValueAsIntOrDefault(key, RepositoryDefaults.DEFAULT_INT);
    }

    @Override
    public int loadConfigValueAsIntOrDefault(@NonNull String key, int def) {
        SharedPreferences preferences = getPreferences();
        return preferences.getInt(key, def);
    }

    @Override
    public long loadConfigValueAsLong(@NonNull String key) {
        return loadConfigValueAsLongOrDefault(key, RepositoryDefaults.DEFAULT_LONG);
    }

    @Override
    public long loadConfigValueAsLongOrDefault(@NonNull String key, long def) {
        SharedPreferences preferences = getPreferences();
        return preferences.getLong(key, def);
    }

    @Override
    public float loadConfigValueAsFloat(@NonNull String key) {
        return loadConfigValueAsFloatOrDefault(key, RepositoryDefaults.DEFAULT_FLOAT);
    }

    @Override
    public float loadConfigValueAsFloatOrDefault(@NonNull String key, float def) {
        SharedPreferences preferences = getPreferences();
        return preferences.getFloat(key, def);
    }

    @Override
    public String loadConfigValueAsString(@NonNull String key) {
        return loadConfigValueAsStringOrDefault(key, RepositoryDefaults.DEFAULT_STRING);
    }

    @Override
    public String loadConfigValueAsStringOrDefault(@NonNull String key, String def) {
        SharedPreferences preferences = getPreferences();
        return preferences.getString(key, def);
    }

    @Override
    public boolean loadConfigValueAsBoolean(@NonNull String key) {
        return loadConfigValueAsBooleanOrDefault(key, RepositoryDefaults.DEFAULT_BOOLEAN);
    }

    @Override
    public boolean loadConfigValueAsBooleanOrDefault(@NonNull String key, boolean def) {
        SharedPreferences preferences = getPreferences();
        return preferences.getBoolean(key, def);
    }

    @Override
    public void loadConfigValuesForPresetKeys(@NonNull ConfigValuePackage presetPackage) {
        SharedPreferences preferences = getPreferences();

        // boolean
        Set<String> keySet = presetPackage.keySetForBooleanValues();
        if (keySet != null) {
            for (String key : keySet) {
                presetPackage.put(key,
                        preferences.getBoolean(key, presetPackage.getBoolean(key)));
            }
        }

        // long
        keySet = presetPackage.keySetForLongValues();
        if (keySet != null) {
            for (String key : keySet) {
                presetPackage.put(key,
                        preferences.getLong(key, presetPackage.getLong(key)));
            }
        }

        // int
        keySet = presetPackage.keySetForIntValues();
        if (keySet != null) {
            for (String key : keySet) {
                presetPackage.put(key,
                        preferences.getInt(key, presetPackage.getInt(key)));
            }
        }

        // float
        keySet = presetPackage.keySetForFloatValues();
        if (keySet != null) {
            for (String key : keySet) {
                presetPackage.put(key,
                        preferences.getFloat(key, presetPackage.getFloat(key)));
            }
        }

        // String
        keySet = presetPackage.keySetForStringValues();
        if (keySet != null) {
            for (String key : keySet) {
                presetPackage.put(key,
                        preferences.getString(key, presetPackage.getString(key)));
            }
        }
    }

    /**
     * region; Save data to SQLite
     */

    @Override
    public long saveSqlEntry(@NonNull String tableName, @NonNull SqlEntryPackage entryPackage) {
        SQLiteDatabase sqLiteDatabase = mDatabaseOpenHelper.openWritableDatabase();
        final long id = sqLiteDatabase.insert(tableName, null, entryPackage.toContentValues());
        sqLiteDatabase.close();

        // SQLiteDatabase#insert() method returns the row ID of the newly inserted row, or -1 if an error occurred.
        // See document -> https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html#insert(java.lang.String, java.lang.String, android.content.ContentValues)
        return (id != -1) ? id : SQL_ENTRY_NULL_ID;
    }

    @Override
    public long[] saveSqlEntries(@NonNull String tableName, @NonNull Collection<SqlEntryPackage> entryPackages) {
        final long[] ids = new long[entryPackages.size()];
        SQLiteDatabase sqLiteDatabase = mDatabaseOpenHelper.openWritableDatabase();
        int index = 0;

        sqLiteDatabase.beginTransaction();
        try {
            for (SqlEntryPackage entry : entryPackages) {
                final long id = sqLiteDatabase.insert(tableName, null, entry.toContentValues());
                // SQLiteDatabase#insert() method returns the row ID of the newly inserted row, or -1 if an error occurred.
                // See document -> https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html#insert(java.lang.String, java.lang.String, android.content.ContentValues)
                ids[index] = (id != -1) ? id : SQL_ENTRY_NULL_ID;
                ++index;
            }
            sqLiteDatabase.setTransactionSuccessful();

        } finally {
            sqLiteDatabase.endTransaction();
        }
        sqLiteDatabase.close();

        return ids;
    }

    /**
     * region; Load data from SQLite
     */

    @NonNull
    @Override
    public ArrayList<SqlEntryPackage> loadSqlEntries(@NonNull SqliteQuery query) {
        ArrayList<SqlEntryPackage> results = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = mDatabaseOpenHelper.openReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                query.distinct(),
                query.tables(),
                query.columns(),
                query.selection(),
                query.selectionArgs(),
                query.groupBy(),
                query.having(),
                query.orderBy(),
                query.limit());

        boolean isEOF = cursor.moveToFirst();
        ContentValues contentValues;
        SqlEntryPackage entryPackage;
        while (isEOF) {
            contentValues = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(cursor, contentValues);
            entryPackage = new SqlEntryPackage(contentValues);
            results.add(entryPackage);
            isEOF = cursor.moveToNext();
        }

        cursor.close();
        sqLiteDatabase.close();

        return results;
    }

    /**
     * region; Update data in SQLite
     */

    @Override
    public boolean updateSqlEntry(@NonNull String tableName, @NonNull SqlEntryPackage entryPackage, @NonNull String idColumnName) {
        final long id;
        if (!entryPackage.containsKey(idColumnName) || (id = entryPackage.getAsLongOrThrow(idColumnName)) == SQL_ENTRY_NULL_ID) {
            return false;
        }

        SqliteWhere.CondExpr idIs = new SqliteWhere.CondExpr(idColumnName).equalTo(id);
        SQLiteDatabase sqLiteDatabase = mDatabaseOpenHelper.openWritableDatabase();

        final int affected = sqLiteDatabase.update(tableName, entryPackage.toContentValues(), idIs.formalize(), null);
        sqLiteDatabase.close();

        return (affected != 0);
    }

    @Override
    public int updateSqlEntriesIfMatch(@NonNull String tableName, @NonNull SqlEntryPackage entryPackage, @NonNull SqliteWhere.Expr condition) {
        SQLiteDatabase sqLiteDatabase = mDatabaseOpenHelper.openWritableDatabase();
        final int affected = sqLiteDatabase.update(tableName, entryPackage.toContentValues(), condition.formalize(), null);
        sqLiteDatabase.close();

        return affected;
    }

    /**
     * region; Delete data in SQLite
     */

    @Override
    public boolean deleteSqlEntry(@NonNull String tableName, @NonNull String idColumnName, long id) {
        SqliteWhere.CondExpr idIs = new SqliteWhere.CondExpr(idColumnName).equalTo(id);
        SQLiteDatabase sqLiteDatabase = mDatabaseOpenHelper.openWritableDatabase();

        final int affected = sqLiteDatabase.delete(tableName, idIs.formalize(), null);
        sqLiteDatabase.close();

        return (affected != 0);
    }

    @Override
    public int deleteSqlEntriesIfMatch(@NonNull String tableName, @NonNull SqliteWhere.Expr condition) {
        SQLiteDatabase sqLiteDatabase = mDatabaseOpenHelper.openWritableDatabase();
        final int affected = sqLiteDatabase.delete(tableName, condition.formalize(), null);
        sqLiteDatabase.close();

        return affected;
    }

    /**
     * Helper methods
     */

    private SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
    }

}
