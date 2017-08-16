package d.candy.f.com.ralgo.domain.structure;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;

/**
 * Created by daichi on 17/08/16.
 */

public class People extends Thing {

    private long mId;
    private String mName;
    private String mNote;
    private String mMail;
    private String mTel;

    public People(long id, String name, String mail, String tel) {
        mId = id;
        mName = name;
        mMail = mail;
        mTel = tel;
    }

    public People() {
        this(DbContract.NULL_ID, null, null, null);
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public String getMail() {
        return mMail;
    }

    public void setMail(String mail) {
        mMail = mail;
    }

    public String getTel() {
        return mTel;
    }

    public void setTel(String tel) {
        mTel = tel;
    }
}
