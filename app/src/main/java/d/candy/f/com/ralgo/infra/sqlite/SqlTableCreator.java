package d.candy.f.com.ralgo.infra.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by daichi on 8/15/17.
 */

public class SqlTableCreator {

    public static class Source {
        // key -> columnName | value -> {dataType, isNullable}
        @NonNull private Map<String, Pair<SqliteDataType, Boolean>> mPairMap;
        @NonNull private String mTableName;

        public Source(@NonNull String tableName) {
            this(tableName, -1);
        }

        public Source(@NonNull String tableName, int capacity) {
            if (capacity < 0) {
                mPairMap = new HashMap<>();
            } else {
                mPairMap = new HashMap<>(capacity);
            }
            mTableName = tableName;
        }

        public Source put(@NonNull String column, @NonNull SqliteDataType dataType, boolean isNullable) {
            mPairMap.put(column, new Pair<>(dataType, isNullable));
            return this;
        }

        public Pair<SqliteDataType, Boolean> get(@NonNull String column) {
            return mPairMap.get(column);
        }

        @NonNull
        public String getTableName() {
            return mTableName;
        }

        public Set<String> getAllColumns() {
            return mPairMap.keySet();
        }
    }

    public static boolean createFromSourse(@NonNull SQLiteDatabase database, @NonNull Source source) {
        if (!database.isOpen() || database.isReadOnly()) {
            return false;
        }

        final String comma_sep = ",";
        String sqlCreate = "CREATE TABLE " + source.getTableName() + " (";
        Set<String> columns = source.getAllColumns();
        String[] tokens = new String[columns.size()];
        Pair<SqliteDataType, Boolean> pair;
        int i = 0;

        for (String column : columns) {
            pair = source.get(column);
            // pair.second -> is a column nullable or not
            if (pair.second) {
                tokens[i] = column + " " + pair.first.toString();
            } else {
                tokens[i] = column + " " + pair.first.toString() + " NOT NULL";
            }
            ++i;
        }

        sqlCreate = sqlCreate.concat(TextUtils.join(comma_sep, tokens));
        sqlCreate = sqlCreate.concat(");");

        database.execSQL(sqlCreate);
        return true;
    }
}
