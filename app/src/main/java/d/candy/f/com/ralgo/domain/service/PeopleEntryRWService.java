package d.candy.f.com.ralgo.domain.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;
import d.candy.f.com.ralgo.data_store.sql_database.PeopleEntryContract;
import d.candy.f.com.ralgo.domain.RepositoryUser;
import d.candy.f.com.ralgo.domain.structure.People;
import d.candy.f.com.ralgo.infra.Repository;
import d.candy.f.com.ralgo.infra.data_package.SqlEntryPackage;

/**
 * Created by daichi on 8/17/17.
 */

public class PeopleEntryRWService extends Service implements RepositoryUser {

    private Repository mRepository = null;

    public PeopleEntryRWService() {}

    @Override
    boolean isReady() {
        return (mRepository != null);
    }

    @Override
    public void setRepository(@NonNull Repository repository) {
        mRepository = repository;
    }

    @Nullable
    public People readPeopleForId(long id) {
        onServiceStart();

        EntryRWService entryRWService = new EntryRWService();
        entryRWService.setRepository(mRepository);
        SqlEntryPackage result = entryRWService.readEntryForId(
                PeopleEntryContract.TABLE_NAME, PeopleEntryContract.COL_ID, id);

        return (result != null) ? createPeopleFromEntryPackage(result) : null;
    }

    public long writePeople(@NonNull People people) {
        if (people.getId() != DbContract.NULL_ID) {
            return (updatePeople(people)) ? people.getId() : DbContract.NULL_ID;
        } else {
            return savePeople(people);
        }
    }

    private long savePeople(@NonNull People people) {
        if (!isPeopleValid(people, false)) {
            return DbContract.NULL_ID;
        }

        SqlEntryPackage entryPackage = createEntryPackageFromPeople(people, false);
        return mRepository.saveSqlEntry(PeopleEntryContract.TABLE_NAME, entryPackage);
    }

    private boolean updatePeople(@NonNull People people) {
        if (!isPeopleValid(people, true)) {
            return false;
        }

        SqlEntryPackage entryPackage = createEntryPackageFromPeople(people, true);
        return mRepository.updateSqlEntry(
                PeopleEntryContract.TABLE_NAME,
                entryPackage,
                PeopleEntryContract.COL_ID);
    }

    @NonNull
    private People createPeopleFromEntryPackage(@NonNull SqlEntryPackage entryPackage) {
        People people = new People();

        people.setId(entryPackage.getAsLongOrDefault(
                PeopleEntryContract.COL_ID, DbContract.NULL_ID));

        people.setName(entryPackage.getAsStringOrDefault(
                PeopleEntryContract.COL_NAME, People.DEFAULT_NAME));

        people.setMail(entryPackage.getAsStringOrDefault(
                PeopleEntryContract.COL_MAIL, People.DEFAULT_MAIL));

        people.setTel(entryPackage.getAsStringOrDefault(
                PeopleEntryContract.COL_TEL, People.DEFAULT_TEL));

        people.setNote(entryPackage.getAsStringOrDefault(
                PeopleEntryContract.COL_NOTE, People.DEFAULT_NOTE));

        return people;
    }

    @NonNull
    private SqlEntryPackage createEntryPackageFromPeople(@NonNull People people, boolean includeId) {
        SqlEntryPackage entryPackage = new SqlEntryPackage();
        if (includeId) {
            entryPackage.put(PeopleEntryContract.COL_ID, people.getId());
        }

        entryPackage.put(PeopleEntryContract.COL_NAME, people.getName());
        entryPackage.put(PeopleEntryContract.COL_NOTE, people.getNote());
        entryPackage.put(PeopleEntryContract.COL_MAIL, people.getMail());
        entryPackage.put(PeopleEntryContract.COL_TEL, people.getTel());

        return entryPackage;
    }

    private boolean isPeopleValid(@NonNull People people, boolean checkId) {
        if (checkId) {
            return PeopleEntryContract.isPeopleValid(people.getId(), people.getName());
        } else {
            return PeopleEntryContract.isPeopleValid(people.getName());
        }
    }
}
