package d.candy.f.com.ralgo.domain.structure;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;

/**
 * Created by daichi on 17/08/16.
 */

public class Thing {

    private long mThingId;
    private long mEmbodierId;
    private String mTableOfEmbodier;

    public Thing(long thingId, long embodierId, String tableOfEmbodier) {
        mThingId = thingId;
        mEmbodierId = embodierId;
        mTableOfEmbodier = tableOfEmbodier;
    }

    public Thing() {
        this(DbContract.NULL_ID, DbContract.NULL_ID, null);
    }

    public long getThingId() {
        return mThingId;
    }

    public void setThingId(long thingId) {
        mThingId = thingId;
    }

    public long getEmbodierId() {
        return mEmbodierId;
    }

    public void setEmbodierId(long embodierId) {
        mEmbodierId = embodierId;
    }

    public String getTableOfEmbodier() {
        return mTableOfEmbodier;
    }

    public void setTableOfEmbodier(String tableOfEmbodier) {
        mTableOfEmbodier = tableOfEmbodier;
    }
}
