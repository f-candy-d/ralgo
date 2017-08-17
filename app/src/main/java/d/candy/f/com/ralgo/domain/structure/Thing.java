package d.candy.f.com.ralgo.domain.structure;

import android.support.annotation.NonNull;

import java.lang.reflect.Field;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;

/**
 * Created by daichi on 17/08/16.
 */

public class Thing {

    public static final String DEFAULT_TABLE_OF_EMBODIER = null;

    private long mThingId;
    private long mThingEmbodierId;
    private String mTableOfEmbodier;

    public Thing(long thingId, long thingEmbodierId, String tableOfEmbodier) {
        mThingId = thingId;
        mThingEmbodierId = thingEmbodierId;
        mTableOfEmbodier = tableOfEmbodier;
    }

    public Thing() {
        this(DbContract.NULL_ID, DbContract.NULL_ID, null);
    }

    public void setThingData(@NonNull Thing thing) {
        setThingId(thing.getThingId());
        setThingEmbodierId(thing.getThingEmbodierId());
        setTableOfEmbodier(thing.getTableOfEmbodier());
    }

    public String toString(String header) {
        StringBuilder sb = new StringBuilder();
        if (header != null) {
            sb.append(header + "\n");
        }
        sb.append("Class: " + this.getClass().getCanonicalName() + "\n");
        sb.append("Settings:\n");
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                sb.append(field.getName() + " = " + field.get(this) + "\n");
            } catch (IllegalAccessException e) {
                sb.append(field.getName() + " = " + "access denied\n");
            }
        }
        sb.append(this.toString());

        return sb.toString();
    }

    @Override
    public String toString() {

        String string = "Thing class fields:\n";
        string = string.concat("mThingId=" + mThingId + "\n");
        string = string.concat("mThingEmbodierId=" + mThingEmbodierId + "\n");
        string = string.concat("mTableOfEmbodier=" + mTableOfEmbodier + "\n");

        return string;
    }

    public long getThingId() {
        return mThingId;
    }

    public void setThingId(long thingId) {
        mThingId = thingId;
    }

    public long getThingEmbodierId() {
        return mThingEmbodierId;
    }

    public void setThingEmbodierId(long thingEmbodierId) {
        mThingEmbodierId = thingEmbodierId;
    }

    public String getTableOfEmbodier() {
        return mTableOfEmbodier;
    }

    public void setTableOfEmbodier(String tableOfEmbodier) {
        mTableOfEmbodier = tableOfEmbodier;
    }

    /**
     * TODO; Override this method in a child class to synchronize its ID & and mThingEmbodierId
     */
    public long getId() { return DbContract.NULL_ID; }

    /**
     * TODO; Override this method in a child class & call super.setId(long) in it to synchronize its ID & and mThingEmbodierId
     */
    public void setId(long id) {
        mThingEmbodierId = id;
    }
}
