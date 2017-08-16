package d.candy.f.com.ralgo.domain.service;

import android.database.sqlite.SQLiteQuery;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import d.candy.f.com.ralgo.domain.RepositoryUser;
import d.candy.f.com.ralgo.infra.Repository;
import d.candy.f.com.ralgo.infra.data_package.SqlEntryPackage;
import d.candy.f.com.ralgo.infra.sqlite.SqliteQuery;
import d.candy.f.com.ralgo.infra.sqlite.SqliteWhere;

/**
 * Created by daichi on 8/16/17.
 */

public class EntryRWService extends Service implements RepositoryUser {

    private Repository mRepository = null;

    public EntryRWService() {}

    @Override
    boolean isReady() {
        return (mRepository != null);
    }

    @Override
    public void setRepository(@NonNull Repository repository) {
        mRepository = repository;
    }

    @Nullable
    public SqlEntryPackage readEntryForId
            (@NonNull String tableName, @NonNull String idColumn, long id) {

        onServiceStart();

        SqliteWhere.CondExpr idIs = new SqliteWhere.CondExpr(idColumn).equalTo(id);
        SqliteQuery query = new SqliteQuery();
        query.putTables(tableName);
        query.setSelection(idIs);
        query.setLimit(1);

        ArrayList<SqlEntryPackage> results = mRepository.loadSqlEntries(query);
        if (results.size() != 0) {
            return results.get(0);
        }

        return null;
    }

    @NonNull
    public ArrayList<SqlEntryPackage> readEntriesWithOneCondition
            (@NonNull String tableName,
             @NonNull String columnForCondition,
             @NonNull SqliteWhere.CondExpr.CondOp operator,
             @NonNull Object value) {

        SqliteWhere.CondExpr condition =
                new SqliteWhere.CondExpr(columnForCondition).join(value, operator);
        SqliteQuery query = new SqliteQuery();
        query.putTables(tableName);
        query.setSelection(condition);

        return mRepository.loadSqlEntries(query);
    }

    @NonNull
    public ArrayList<SqlEntryPackage> readAllEntriesInTable(@NonNull String table) {

        SqliteQuery query = new SqliteQuery();
        query.putTables(table);
        return mRepository.loadSqlEntries(query);
    }
}
