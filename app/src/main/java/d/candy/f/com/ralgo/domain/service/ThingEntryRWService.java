package d.candy.f.com.ralgo.domain.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;
import d.candy.f.com.ralgo.data_store.sql_database.ThingEntryContract;
import d.candy.f.com.ralgo.domain.RepositoryUser;
import d.candy.f.com.ralgo.domain.structure.Thing;
import d.candy.f.com.ralgo.infra.Repository;
import d.candy.f.com.ralgo.infra.data_package.SqlEntryPackage;
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
    public Thing readThingForEmbodierId(long embodierId) {
        EntryRWService entryRWService = new EntryRWService();
        entryRWService.setRepository(mRepository);
        ArrayList<SqlEntryPackage> results = entryRWService.readEntriesWithOneCondition(
                ThingEntryContract.TABLE_NAME,
                ThingEntryContract.COL_EMBODIER_ID,
                SqliteWhere.CondExpr.CondOp.EQ,
                embodierId);

        if (results.size() == 0) {
            return null;
        } else if (results.size() == 1) {
            return convertEntryPackageToThing(results.get(0));
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
            things.add(convertEntryPackageToThing(entryPackage));
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
        if (!thingIsValid(thing, false)) {
            return DbContract.NULL_ID;
        }

        SqlEntryPackage entryPackage = convertThingToEntryPackage(thing, false);
        return mRepository.saveSqlEntry(ThingEntryContract.TABLE_NAME, entryPackage);
    }

    private boolean updateThing(@NonNull Thing thing) {
        return false;
    }

    /**
     * When add/remove any column of Thing table, edit this method
     */
    private Thing convertEntryPackageToThing(@NonNull SqlEntryPackage entryPackage) {
        Thing thing = new Thing();
        thing.setThingId(entryPackage.getAsLong(ThingEntryContract.COL_ID));
        thing.setEmbodierId(entryPackage.getAsLong(ThingEntryContract.COL_EMBODIER_ID));
        thing.setTableOfEmbodier(entryPackage.getAsString(ThingEntryContract.COL_TABLE_OF_EMBODIER));

        return thing;
    }

    private SqlEntryPackage convertThingToEntryPackage(@NonNull Thing thing, boolean includeId) {
        return null;
    }

    private boolean thingIsValid(@NonNull Thing thing, boolean checkId) {
        return false;
    }
}
