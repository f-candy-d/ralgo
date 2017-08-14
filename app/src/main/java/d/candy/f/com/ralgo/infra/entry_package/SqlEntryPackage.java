package d.candy.f.com.ralgo.infra.entry_package;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.util.Set;

/**
 * Created by daichi on 8/14/17.
 *
 * Data structure that representing a row of the table database
 */

public class SqlEntryPackage {

    @NonNull private ContentValues mColumnData;

    public SqlEntryPackage() {
        mColumnData = new ContentValues();
    }

    public ContentValues toContentValues() {
        return mColumnData;
    }

    public SqlEntryPackage put(@NonNull String columnName, int value) {
        mColumnData.put(columnName, value);
        return this;
    }

    public SqlEntryPackage put(@NonNull String columnName, long value) {
        mColumnData.put(columnName, value);
        return this;
    }

    public SqlEntryPackage put(@NonNull String columnName, float value) {
        mColumnData.put(columnName, value);
        return this;
    }

    public SqlEntryPackage put(@NonNull String columnName, boolean value) {
        mColumnData.put(columnName, value);
        return this;
    }

    public SqlEntryPackage put(@NonNull String columnName, String value) {
        mColumnData.put(columnName, value);
        return this;
    }

    public int getAsInt(@NonNull String columnName) {
        return mColumnData.getAsInteger(columnName);
    }

    public long getAsLong(@NonNull String columnName) {
        return mColumnData.getAsLong(columnName);
    }

    public float getAsFloat(@NonNull String columnName) {
        return mColumnData.getAsFloat(columnName);
    }

    public boolean getAsBoolean(@NonNull String columnName) {
        return mColumnData.getAsBoolean(columnName);
    }

    public String getAsString(@NonNull String columnName) {
        return mColumnData.getAsString(columnName);
    }

    public Set<String> getColumnNames() {
        return mColumnData.keySet();
    }
}
