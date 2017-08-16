package d.candy.f.com.ralgo.domain.service;

import android.support.annotation.NonNull;

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

    public SqlEntryPackage readEntryForId
            (@NonNull String tableName, @NonNull String idColumnName, long id) {

        onServiceStart();

        SqliteWhere.CondExpr idIs = new SqliteWhere.CondExpr(idColumnName).equalTo(id);
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

    @Override
    boolean isReady() {
        return (mRepository != null);
    }

    @Override
    public void setRepository(@NonNull Repository repository) {
        mRepository = repository;
    }
}
