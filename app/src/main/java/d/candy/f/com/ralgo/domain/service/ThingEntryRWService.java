package d.candy.f.com.ralgo.domain.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;
import d.candy.f.com.ralgo.data_store.sql_database.ThingEntryContract;
import d.candy.f.com.ralgo.domain.RepositoryUser;
import d.candy.f.com.ralgo.domain.structure.Thing;
import d.candy.f.com.ralgo.infra.Repository;
import d.candy.f.com.ralgo.infra.data_package.SqlEntryPackage;
import d.candy.f.com.ralgo.infra.sqlite.SqliteQuery;
import d.candy.f.com.ralgo.infra.sqlite.SqliteWhere;

/**
 * Created by daichi on 8/16/17.
 */

public class ThingEntryRWService extends Service implements RepositoryUser {

    private Repository mRepository = null;

    public ThingEntryRWService() {}

    @Override
    boolean isReady() {
        return (mRepository != null);
    }

    @Override
    public void setRepository(@NonNull Repository repository) {
        mRepository = repository;
    }

    @Nullable
    public Thing readThingForEmbodierIdAndTable(long embodierId, @NonNull String table) {
        SqliteWhere.CondExpr embodierIdIs =
                new SqliteWhere.CondExpr(ThingEntryContract.COL_EMBODIER_ID).equalTo(embodierId);
        SqliteWhere.CondExpr tableNameIs =
                new SqliteWhere.CondExpr(ThingEntryContract.COL_TABLE_OF_EMBODIER).equalTo(table);
        SqliteWhere.LogicExpr where = new SqliteWhere.LogicExpr(embodierIdIs).and(tableNameIs);

        SqliteQuery query = new SqliteQuery();
        query.putTables(ThingEntryContract.TABLE_NAME);
        query.setSelection(where);

        ArrayList<SqlEntryPackage> results = mRepository.loadSqlEntries(query);

        if (results.size() == 0) {
            return null;
        } else if (results.size() == 1) {
            return createEntryPackageFromThing(results.get(0));
        }

        throw new IllegalStateException(
                "The Thing entry for the embodier ID(= "
                        + String.valueOf(embodierId) + ") is ambigous");
    }

    @NonNull
    public ArrayList<Thing> readAllThingsInTable(@NonNull String table) {
        EntryRWService entryRWService = new EntryRWService();
        entryRWService.setRepository(mRepository);
        ArrayList<SqlEntryPackage> sqlEntryPackages = entryRWService.readAllEntriesInTable(table);
        ArrayList<Thing> things = new ArrayList<>(sqlEntryPackages.size());

        for (SqlEntryPackage entryPackage : sqlEntryPackages) {
            things.add(createEntryPackageFromThing(entryPackage));
        }

        return things;
    }

    public long writeThing(@NonNull Thing thing) {
        if (thing.getThingId() != DbContract.NULL_ID) {
            return (updateThing(thing)) ? thing.getThingId() : DbContract.NULL_ID;
        } else {
            return saveThing(thing);
        }
    }

    private long saveThing(@NonNull Thing thing) {
        if (!isThingValid(thing, false)) {
            Log.d("mylog", "thing is invalid");
            return DbContract.NULL_ID;
        }

        SqlEntryPackage entryPackage = createThingFromEntryPackage(thing, false);
        return mRepository.saveSqlEntry(ThingEntryContract.TABLE_NAME, entryPackage);
    }

    private boolean updateThing(@NonNull Thing thing) {
        if (!isThingValid(thing, true)) {
            return false;
        }

        SqlEntryPackage toEntryPackage = createThingFromEntryPackage(thing, true);
        return mRepository.updateSqlEntry(
                ThingEntryContract.TABLE_NAME, toEntryPackage, ThingEntryContract.COL_ID);
    }

    /**
     * When add/remove any column of Thing table, edit this method
     */
    @NonNull
    private Thing createEntryPackageFromThing(@NonNull SqlEntryPackage entryPackage) {
        Thing thing = new Thing();
        thing.setThingId(entryPackage.getAsLongOrDefault(
                ThingEntryContract.COL_ID, DbContract.NULL_ID));

        thing.setThingEmbodierId(entryPackage.getAsLongOrDefault(
                ThingEntryContract.COL_EMBODIER_ID, DbContract.NULL_ID));

        thing.setTableOfEmbodier(entryPackage.getAsStringOrDefault(
                ThingEntryContract.COL_TABLE_OF_EMBODIER, Thing.DEFAULT_TABLE_OF_EMBODIER));

        return thing;
    }

    private SqlEntryPackage createThingFromEntryPackage(@NonNull Thing thing, boolean includeId) {
        SqlEntryPackage entryPackage = new SqlEntryPackage();
        if (includeId) {
            entryPackage.put(ThingEntryContract.COL_ID, thing.getThingId());
        }
        entryPackage.put(ThingEntryContract.COL_EMBODIER_ID, thing.getThingEmbodierId());
        entryPackage.put(ThingEntryContract.COL_TABLE_OF_EMBODIER, thing.getTableOfEmbodier());

        return entryPackage;
    }

    private boolean isThingValid(@NonNull Thing thing, boolean checkId) {
        if (checkId) {
            return ThingEntryContract.isThingValid(
                    thing.getThingId(), thing.getThingEmbodierId(), thing.getTableOfEmbodier());
        } else {
            return ThingEntryContract.isThingValid(
                    thing.getThingEmbodierId(), thing.getTableOfEmbodier());
        }
    }
}
