package d.candy.f.com.ralgo.infra.entry_package;

import android.content.ContentValues;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.util.Set;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;
import d.candy.f.com.ralgo.utils.Quantizable;

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

    public SqlEntryPackage put(@NonNull String columnName, @NonNull Quantizable value) {
        mColumnData.put(columnName, value.quantize());
        return this;
    }

    public SqlEntryPackage putRecognizableObjectOrThrow(@NonNull String columnName, Object value) {
        if (value instanceof Integer) {
            put(columnName, (Integer) value);
        } else if (value instanceof Long) {
            put(columnName, (Long) value);
        } else if (value instanceof Float) {
            put(columnName, (Float) value);
        } else if (value instanceof Boolean) {
            put(columnName, (Boolean) value);
        } else if (value instanceof String) {
            put(columnName, (String) value);
        } else {
            throw new ClassCastException("Unrecognizable type of the object");
        }

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

    public <T> T getAsQuantizable(@NonNull String columnName, @NonNull Quantizable.Converter<T> converter) {
        final int quantity = mColumnData.getAsInteger(columnName);
        return converter.convertFromQuantity(quantity);
    }

    public Set<String> getColumnNames() {
        return mColumnData.keySet();
    }

    public boolean containsKey(@NonNull String key) {
        return mColumnData.containsKey(key);
    }
}
