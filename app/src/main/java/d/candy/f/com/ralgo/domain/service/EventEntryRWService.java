package d.candy.f.com.ralgo.domain.service;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;
import d.candy.f.com.ralgo.data_store.sql_database.EventEntryContract;
import d.candy.f.com.ralgo.domain.RepositoryUser;
import d.candy.f.com.ralgo.domain.structure.Event;
import d.candy.f.com.ralgo.infra.Repository;
import d.candy.f.com.ralgo.infra.data_package.SqlEntryPackage;
import d.candy.f.com.ralgo.infra.sqlite.SqliteQuery;
import d.candy.f.com.ralgo.infra.sqlite.SqliteWhere;
import d.candy.f.com.ralgo.utils.Day;

/**
 * Created by daichi on 17/08/16.
 */

public class EventEntryRWService extends Service implements RepositoryUser {

    private Repository mRepository = null;

    public EventEntryRWService() {}

    @Override
    boolean isReady() {
        return (mRepository != null);
    }

    @Override
    public void setRepository(@NonNull Repository repository) {
        mRepository = repository;
    }

    @NonNull
    public ArrayList<Event> readEventsOnDate(@NonNull Calendar calendar) {
        return readEventsOnDate(calendar.getTimeInMillis());
    }

    @NonNull
    public ArrayList<Event> readEventsOnDate(long timeInMillis) {
        onServiceStart();

        Day day = new Day(timeInMillis);
        long startOfDay = day.getStartOfDay().getTimeInMillis();
        long startOfNextDay = day.getStartOfNextDay().getTimeInMillis();

        // Where clause
        SqliteWhere.BetweenExpr isStartOnDate =
                new SqliteWhere.BetweenExpr(EventEntryContract.COL_START_DATE)
                        .setRange(startOfDay, startOfNextDay)
                        .setRangeBoundaries(false, true);
        SqliteWhere.BetweenExpr isEndOnDate =
                new SqliteWhere.BetweenExpr(EventEntryContract.COL_END_DATE)
                        .setRange(startOfDay, startOfNextDay)
                        .setRangeBoundaries(false, true);

        isStartOnDate.setInBrancket(true);
        isEndOnDate.setInBrancket(true);
        SqliteWhere.LogicExpr startAndEndOnDate = new SqliteWhere.LogicExpr(isStartOnDate).and(isEndOnDate);

        SqliteQuery query = new SqliteQuery();
        query.putTables(EventEntryContract.TABLE_NAME);
        query.setSelection(startAndEndOnDate);

        // Find Event entries
        ArrayList<SqlEntryPackage> results = mRepository.loadSqlEntries(query);

        ArrayList<Event> events = new ArrayList<>(results.size());
        Event event;
        for (SqlEntryPackage entryPackage : results) {
            event = createEventFromEntryPackage(entryPackage);
            events.add(event);
        }

        return events;
    }

    /**
     * Return the row id of the inserted/updated Event entry, or DbContract.NULL_ID if an error occured
     */
    public long writeEvent(@NonNull Event event) {
        if (event.getId() != DbContract.NULL_ID) {
            return (updateEvent(event)) ? event.getId() : DbContract.NULL_ID;
        } else {
            return saveEvent(event);
        }
    }

    /**
     * Return the row id of the inserted Event entry, or DbContract.NULL_ID if an error occured
     */
    private long saveEvent(@NonNull Event event) {
        if (!eventIsValid(event, false)) {
            return DbContract.NULL_ID;
        }

        SqlEntryPackage entryPackage = createEntryPackageFromEvent(event, false);
        return mRepository.saveSqlEntry(EventEntryContract.TABLE_NAME, entryPackage);
    }

    /**
     * Return true if no error occured, false otherwise
     */
    private boolean updateEvent(@NonNull Event event) {
        if (!eventIsValid(event, true)) {
            return false;
        }

        SqlEntryPackage entryPackage = createEntryPackageFromEvent(event, true);
        return mRepository.updateSqlEntry(
                EventEntryContract.TABLE_NAME, entryPackage, EventEntryContract.COL_ID);
    }

    /**
     * When add/remove any columns, edit this method.
     */
    private Event createEventFromEntryPackage(@NonNull SqlEntryPackage entryPackage) {
        Event event = new Event();

        event.setId(entryPackage.getAsLongOrDefault(
                EventEntryContract.COL_ID, DbContract.NULL_ID));

        event.setStartDatetime(entryPackage.getAsLongOrDefault(
                EventEntryContract.COL_START_DATE, Event.DEFAULT_START_DATE));

        event.setEndDatetime(entryPackage.getAsLongOrDefault(
                EventEntryContract.COL_END_DATE, Event.DEFAULT_END_DATE));

        event.setContentThingId(entryPackage.getAsLongOrDefault(
                EventEntryContract.COL_CONTENT_THING_ID, DbContract.NULL_ID));

        event.setRepetition(entryPackage.getAsQuantizableOrDefault(
                EventEntryContract.COL_REPETITION, Event.DEFAULT_REPETITION, Event.Repetition.class));

        event.setNote(entryPackage.getAsStringOrDefault(
                EventEntryContract.COL_NOTE, Event.DEFAULT_NOTE));

        return event;
    }

    /**
     * When add/remove any columns, edit this method.
     */
    private SqlEntryPackage createEntryPackageFromEvent(@NonNull Event event, boolean includeId) {
        SqlEntryPackage entryPackage = new SqlEntryPackage();
        if (includeId) {
            entryPackage.put(EventEntryContract.COL_ID, event.getId());
        }
        entryPackage.put(EventEntryContract.COL_CONTENT_THING_ID, event.getContentThingId());
        entryPackage.put(EventEntryContract.COL_START_DATE, event.getStartDatetime());
        entryPackage.put(EventEntryContract.COL_END_DATE, event.getEndDatetime());
        entryPackage.put(EventEntryContract.COL_REPETITION, event.getRepetition());
        entryPackage.put(EventEntryContract.COL_NOTE, event.getNote());

        return entryPackage;
    }

    /**
     * When add/remove any columns, consider editing this method
     */
    private boolean eventIsValid(@NonNull Event event, boolean checkId) {
        return ((!checkId ||
                (event.getId() != DbContract.NULL_ID)) &&
                event.getTableOfEmbodier().equals(EventEntryContract.TABLE_NAME) &&
                event.getContentThingId() != DbContract.NULL_ID &&
                event.getStartDatetime() < event.getEndDatetime() &&
                event.getRepetition() != Event.DEFAULT_REPETITION);
    }
}
