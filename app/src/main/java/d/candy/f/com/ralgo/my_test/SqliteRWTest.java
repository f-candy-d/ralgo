package d.candy.f.com.ralgo.my_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;

import d.candy.f.com.ralgo.data_store.sql_database.DbOpenHelper;
import d.candy.f.com.ralgo.data_store.sql_database.EventEntryContract;
import d.candy.f.com.ralgo.data_store.sql_database.LocationEntryContract;
import d.candy.f.com.ralgo.data_store.sql_database.PeopleEntryContract;
import d.candy.f.com.ralgo.data_store.sql_database.PlanEntryContract;
import d.candy.f.com.ralgo.data_store.sql_database.ThingEntryContract;
import d.candy.f.com.ralgo.domain.service.EventEntryRWService;
import d.candy.f.com.ralgo.domain.service.LocationEntryRWService;
import d.candy.f.com.ralgo.domain.service.PeopleEntryRWService;
import d.candy.f.com.ralgo.domain.service.PlanEntryRWService;
import d.candy.f.com.ralgo.domain.service.ThingEntryRWService;
import d.candy.f.com.ralgo.domain.structure.Event;
import d.candy.f.com.ralgo.domain.structure.Location;
import d.candy.f.com.ralgo.domain.structure.People;
import d.candy.f.com.ralgo.domain.structure.Plan;
import d.candy.f.com.ralgo.domain.structure.Thing;
import d.candy.f.com.ralgo.infra.Repository;
import d.candy.f.com.ralgo.infra.SqliteAndSharedPrefRepository;
import d.candy.f.com.ralgo.infra.sqlite.SqliteDatabaseOpenHelper;
import d.candy.f.com.ralgo.infra.sqlite.SqliteTableUtils;

/**
 * Created by daichi on 8/18/17.
 */

public class SqliteRWTest {
    
    private static final String TAG = "SqliteRWTest";

    public static void test(Context context) {

        SqliteDatabaseOpenHelper helper = new DbOpenHelper(context);
        SQLiteDatabase database = helper.openWritableDatabase();
        SqliteTableUtils.resetTable(database,
                EventEntryContract.getTableCreatorSourse(),
                LocationEntryContract.getTableCreatorSourse(),
                ThingEntryContract.getTableCreatorSourse(),
                PeopleEntryContract.getTableCreatorSourse(),
                PlanEntryContract.getTableCreatorSourse());
        database.close();

        Repository repository = new SqliteAndSharedPrefRepository(context, helper);
        EventEntryRWService eventEntryRWService = new EventEntryRWService();
        LocationEntryRWService locationEntryRWService = new LocationEntryRWService();
        ThingEntryRWService thingEntryRWService = new ThingEntryRWService();
        PeopleEntryRWService peopleEntryRWService = new PeopleEntryRWService();
        PlanEntryRWService planEntryRWService = new PlanEntryRWService();
        eventEntryRWService.setRepository(repository);
        locationEntryRWService.setRepository(repository);
        thingEntryRWService.setRepository(repository);
        peopleEntryRWService.setRepository(repository);
        planEntryRWService.setRepository(repository);

        // Save event
        Calendar calendar = Calendar.getInstance();
        final long now = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR_OF_DAY, 3);
        final long after3hours = calendar.getTimeInMillis();
        Event event = new Event();
        event.setStartDatetime(now);
        event.setEndDatetime(after3hours);
        event.setRepetition(Event.Repetition.ONE_DAY);
        event.setNote("Mr.Children Thanksgiving 25!");
        event.setContentThingId(1);
        event.setId(eventEntryRWService.writeEvent(event));
        event.setThingId(thingEntryRWService.writeThing(event));

        // Save location
        Location location = new Location();
        location.setName("Yahuoku Dome");
        location.setNote("on 2017/7/16 Sun");
        location.setId(locationEntryRWService.writeLocation(location));
        location.setThingId(thingEntryRWService.writeThing(location));

        // Save people
        People people = new People();
        people.setName("JEN");
        people.setNote("I am Drums");
        people.setId(peopleEntryRWService.writePeople(people));
        people.setThingId(thingEntryRWService.writeThing(people));

        // Save plan
        Plan plan = new Plan();
        plan.setName("Mr.CHildren himawari, now on SALE!");
        plan.setNote("Release -> 2017/7/26");
        plan.setId(planEntryRWService.writePlan(plan));
        plan.setThingId(thingEntryRWService.writeThing(plan));

        Log.d(TAG, event.toString("########### SAVED EVENT ##########"));
        Log.d(TAG, location.toString("########### SAVED LOCATION ##########"));
        Log.d(TAG, people.toString("########### SAVED PEOPLE ##########"));
        Log.d(TAG, plan.toString("########### SAVED PLAN ##########"));

        Thing thing;

        // Load event
        thing = thingEntryRWService.readThingForEmbodierIdAndTable(event.getThingEmbodierId(), event.getTableOfEmbodier());
        event = eventEntryRWService.readEventForId(event.getId());
        if (thing != null && event != null) {
            event.setThingData(thing);
            Log.d(TAG, event.toString("########### LOADED EVENT ##########"));
        } else {
            Log.d(TAG, "loading event or thing data failed");
        }

        // Load location
        thing = thingEntryRWService.readThingForEmbodierIdAndTable(location.getThingEmbodierId(), location.getTableOfEmbodier());
        location = locationEntryRWService.readLocationForId(location.getId());
        if (thing != null && location != null) {
            location.setThingData(thing);
            Log.d(TAG, location.toString("########### LOADED LOCATION ##########"));
        } else {
            Log.d(TAG, "loading location or thing data failed");
        }

        // Load people
        thing = thingEntryRWService.readThingForEmbodierIdAndTable(people.getThingEmbodierId(), people.getTableOfEmbodier());
        people = peopleEntryRWService.readPeopleForId(people.getId());
        if (thing != null && people != null) {
            people.setThingData(thing);
            Log.d(TAG, people.toString("########### LOADED PEOPLE ##########"));
        } else {
            Log.d(TAG, "loading people or thing data failed");
        }

        // Load plan
        thing = thingEntryRWService.readThingForEmbodierIdAndTable(plan.getThingEmbodierId(), plan.getTableOfEmbodier());
        plan = planEntryRWService.readPlanForId(plan.getId());
        if (thing != null && plan != null) {
            plan.setThingData(thing);
            Log.d(TAG, plan.toString("########### LOADED PLAN ##########"));
        } else {
            Log.d(TAG, "loading plan or thing data failed");
        }
    }
}
