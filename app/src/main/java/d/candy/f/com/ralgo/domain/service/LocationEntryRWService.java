package d.candy.f.com.ralgo.domain.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;
import d.candy.f.com.ralgo.data_store.sql_database.LocationEntryContract;
import d.candy.f.com.ralgo.domain.RepositoryUser;
import d.candy.f.com.ralgo.domain.structure.Location;
import d.candy.f.com.ralgo.infra.Repository;
import d.candy.f.com.ralgo.infra.data_package.SqlEntryPackage;

/**
 * Created by daichi on 8/17/17.
 */

public class LocationEntryRWService extends Service implements RepositoryUser {

    private Repository mRepository = null;

    public LocationEntryRWService() {}

    @Override
    boolean isReady() {
        return (mRepository != null);
    }

    @Override
    public void setRepository(@NonNull Repository repository) {
        mRepository = repository;
    }

    @Nullable
    public Location readLocationForId(long id) {
        onServiceStart();

        EntryRWService entryRWService = new EntryRWService();
        entryRWService.setRepository(mRepository);
        SqlEntryPackage result = entryRWService.readEntryForId(
                LocationEntryContract.TABLE_NAME, LocationEntryContract.COL_ID, id);

        return (result != null) ? createLocationFromEntryPackage(result) : null;
    }

    public long writeLocation(@NonNull Location location) {
        if (location.getId() != DbContract.NULL_ID) {
            return (updateLocation(location)) ? location.getId() : DbContract.NULL_ID;
        } else {
            return saveLocation(location);
        }
    }

    private boolean updateLocation(@NonNull Location location) {
        if (!isLocationValid(location, true)) {
            return false;
        }

        SqlEntryPackage entryPackage = createEntryPackageFromLocation(location, true);
        return mRepository.updateSqlEntry(
                LocationEntryContract.TABLE_NAME,
                entryPackage,
                LocationEntryContract.COL_NAME);
    }

    private long saveLocation(@NonNull Location location) {
        if (!isLocationValid(location, false)) {
            return DbContract.NULL_ID;
        }

        SqlEntryPackage entryPackage = createEntryPackageFromLocation(location, false);
        return mRepository.saveSqlEntry(LocationEntryContract.TABLE_NAME, entryPackage);
    }

    /**
     * When add/remove any columns, edit this method.
     */
    @NonNull
    private Location createLocationFromEntryPackage(@NonNull SqlEntryPackage entryPackage) {
        Location location = new Location();

        location.setId(entryPackage.getAsLongOrDefault(
                LocationEntryContract.COL_ID, DbContract.NULL_ID));

        location.setName(entryPackage.getAsStringOrDefault(
                LocationEntryContract.COL_NAME, Location.DEFAULT_NAME));

        location.setNote(entryPackage.getAsStringOrDefault(
                LocationEntryContract.COL_NOTE, Location.DEFAULT_NOTE));

        return location;
    }

    /**
     * When add/remove any columns, edit this method.
     */
    @NonNull
    private SqlEntryPackage createEntryPackageFromLocation(@NonNull Location location, boolean includeId) {
        SqlEntryPackage entryPackage = new SqlEntryPackage();
        if (includeId) {
            entryPackage.put(LocationEntryContract.COL_ID, location.getId());
        }
        entryPackage.put(LocationEntryContract.COL_NAME, location.getName());
        entryPackage.put(LocationEntryContract.COL_NOTE, location.getNote());

        return entryPackage;
    }

    private boolean isLocationValid(@NonNull Location location, boolean checkId) {
        if (checkId) {
            return LocationEntryContract.isLocationValid(location.getId(), location.getName());
        } else {
            return LocationEntryContract.isLocationValid(location.getName());
        }
    }
}
