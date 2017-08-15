package d.candy.f.com.ralgo.infra.sqlite;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by daichi on 8/14/17.
 */

public class SqliteQuery {
    private static final int DEFAULT_LIMIT = -1;

    private boolean mDistinct;
    private ArrayList<String> mTables;
    private ArrayList<String> mColumns;
    private SqliteWhere.Expr mSelection;
    private int mLimit;

    public SqliteQuery() {
        mDistinct = false;
        mTables = new ArrayList<>();
        mColumns = new ArrayList<>();
        mSelection = null;
        mLimit = DEFAULT_LIMIT;
    }

    public boolean distinct() {
        return mDistinct;
    }

    public String tables() {
        return (mTables.size() != 0)
                ? TextUtils.join(" JOIN ", mTables)
                : null;
    }

    public String[] columns() {
        return (mColumns.size() != 0)
                ? mColumns.toArray(new String[]{})
                : null;
    }

    public String selection() {
        if (mSelection != null) {
            return mSelection.formalize();
        }
        return null;
    }

    public String[] selectionArgs() {
        // TODO; Coming soon...
        return null;
    }

    public String groupBy() {
        // TODO; Coming soon...
        return null;
    }

    public String having() {
        // TODO; Coming soon...
        return null;
    }

    public String orderBy() {
        // TODO; Coming soon...
        return null;
    }

    public String limit() {
        if (mLimit != DEFAULT_LIMIT) {
            return String.valueOf(mLimit);
        }
        return null;
    }

    public void putTables(final String... tables) {
        for (String table : tables) {
            mTables.add(table);
        }
    }

    public void setDistinct(boolean distinct) {
        mDistinct = distinct;
    }

    public void setTables(ArrayList<String> tables) {
        if (tables != null) {
            mTables = tables;
        }
    }

    public void putColumns(final String... columns) {
        for (String column : columns) {
            mColumns.add(column);
        }
    }

    public void setColumns(ArrayList<String> columns) {
        if (columns != null) {
            mColumns = columns;
        }
    }

    public void setColumns(final String[] columns) {
        if (columns != null) {
            mColumns = new ArrayList<>(Arrays.asList(columns));
        }
    }

    public void setSelection(SqliteWhere.Expr selection) {
        mSelection = selection;
    }

    public void clear() {
        mDistinct = false;
        mTables.clear();
        mColumns.clear();
        mSelection = null;
    }

    public int getLimit() {
        return mLimit;
    }

    public void setLimit(int limit) {
        mLimit = limit;
    }
}
