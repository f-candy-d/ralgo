package d.candy.f.com.ralgo.infra.data_package;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.util.Set;

import d.candy.f.com.ralgo.utils.Quantizable;
import d.candy.f.com.ralgo.utils.QuantizableHelper;

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

    public SqlEntryPackage(@NonNull ContentValues contentValues) {
        mColumnData = contentValues;
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

    public SqlEntryPackage putStorableObjectOrThrow(@NonNull String columnName, @NonNull Object value) {
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
            throw new ClassCastException(
                    "Unrecognizable class type of the object -> columnName:"
                            + columnName
                            + " class:"
                            + value.getClass().getName());
        }

        return this;
    }

    public int getAsIntOrDefault(@NonNull String columnName, int defaultValue) {
        if (mColumnData.containsKey(columnName)) {
            return mColumnData.getAsInteger(columnName);
        }
        return defaultValue;
    }

    public int getAsIntOrThrow(@NonNull String columnName) {
        if (mColumnData.containsKey(columnName)) {
            return mColumnData.getAsInteger(columnName);
        }
        throw new IllegalArgumentException("Column:" + columnName + " does not exists");
    }

    public long getAsLongOrDefault(@NonNull String columnName, long defaultValue) {
        if (mColumnData.containsKey(columnName)) {
            return mColumnData.getAsLong(columnName);
        }
        return defaultValue;
    }

    public long getAsLongOrThrow(@NonNull String columnName) {
        if (mColumnData.containsKey(columnName)) {
            return mColumnData.getAsLong(columnName);
        }
        throw new IllegalArgumentException("Column:" + columnName + " does not exists");
    }

    public float getAsFloatOrDefault(@NonNull String columnName, float defaultValue) {
        if (mColumnData.containsKey(columnName)) {
            return mColumnData.getAsFloat(columnName);
        }
        return defaultValue;
    }

    public float getAsFloatOrDefaultThrow(@NonNull String columnName) {
        if (mColumnData.containsKey(columnName)) {
            return mColumnData.getAsFloat(columnName);
        }
        throw new IllegalArgumentException("Column:" + columnName + " does not exists");
    }

    public boolean getAsBooleanOrDefault(@NonNull String columnName, boolean defaultValue) {
        if (mColumnData.containsKey(columnName)) {
            return mColumnData.getAsBoolean(columnName);
        }
        return defaultValue;
    }

    public boolean getAsBooleanOrThrow(@NonNull String columnName) {
        if (mColumnData.containsKey(columnName)) {
            return mColumnData.getAsBoolean(columnName);
        }
        throw new IllegalArgumentException("Column:" + columnName + " does not exists");
    }

    public String getAsStringOrDefault(@NonNull String columnName, String defaultValue) {
        if (mColumnData.containsKey(columnName)) {
            return mColumnData.getAsString(columnName);
        }
        return defaultValue;
    }

    public String getAsStringOrThrow(@NonNull String columnName) {
        if (mColumnData.containsKey(columnName)) {
            return mColumnData.getAsString(columnName);
        }
        throw new IllegalArgumentException("Column:" + columnName + " does not exists");
    }

    public <T> T getAsQuantizableOrDefault(@NonNull String columnName,
                                           T defaultValue,
                                           @NonNull Quantizable.Converter<T> converter) {

        if (mColumnData.containsKey(columnName)) {
            final int quantity = mColumnData.getAsInteger(columnName);
            return converter.convertFromQuantity(quantity);
        }
        return defaultValue;
    }

    public <T> T getAsQuantizableOrThrow
            (@NonNull String columnName, @NonNull Quantizable.Converter<T> converter) {

        if (mColumnData.containsKey(columnName)) {
            final int quantity = mColumnData.getAsInteger(columnName);
            return converter.convertFromQuantity(quantity);
        }
        throw new IllegalArgumentException("Column:" + columnName + " does not exists");
    }

    public <T extends Enum<T> & Quantizable> T getAsQuantizableOrDefault
            (@NonNull String columnName, T defaultValue, @NonNull Class<T> clazz) {

        if (mColumnData.containsKey(columnName)) {
            final int quantity = mColumnData.getAsInteger(columnName);
            return QuantizableHelper.convertFromEnumClass(clazz, quantity);
        }
        return defaultValue;
    }

    public <T extends Enum<T> & Quantizable> T getAsQuantizableOrThrow
            (@NonNull String columnName, @NonNull Class<T> clazz) {

        if (mColumnData.containsKey(columnName)) {
            final int quantity = mColumnData.getAsInteger(columnName);
            return QuantizableHelper.convertFromEnumClass(clazz, quantity);
        }
        throw new IllegalArgumentException("Column:" + columnName + " does not exists");
    }

    public Set<String> getColumnNames() {
        return mColumnData.keySet();
    }

    public boolean containsKey(@NonNull String key) {
        return mColumnData.containsKey(key);
    }

    @Override
    public String toString() {
        return "#SqlEntryPackage { " + mColumnData.toString() + " }";
    }

}
