package d.candy.f.com.ralgo.data_store.sql_database;

/**
 * Created by daichi on 8/17/17.
 */

public class EntryContractHelper {

    private EntryContractHelper() {}

    public static String[] getThingEntryColumns() {
        return new String[] {
                ThingEntryContract.COL_ID,
                ThingEntryContract.COL_EMBODIER_ID,
                ThingEntryContract.COL_TABLE_OF_EMBODIER
        };
    }

    public static String[] getEventEntryColumns() {
        return new String[] {
                EventEntryContract.COL_ID,
                EventEntryContract.COL_CONTENT_THING_ID,
                EventEntryContract.COL_START_DATE,
                EventEntryContract.COL_END_DATE,
                EventEntryContract.COL_REPETITION,
                EventEntryContract.COL_NOTE
        };
    }
}
