package d.candy.f.com.ralgo.domain.structure;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;

/**
 * Created by daichi on 17/08/16.
 */

public class People extends Thing {

    public static final String DEFAULT_NAME = null;
    public static final String DEFAULT_NOTE = null;
    public static final String DEFAULT_MAIL = null;
    public static final String DEFAULT_TEL = null;

    private long mId;
    private String mName;
    private String mNote;
    private String mMail;
    private String mTel;

    public People(long id, String name, String mail, String tel, String note) {
        mId = id;
        mName = name;
        mMail = mail;
        mTel = tel;
        mNote = note;
    }

    public People() {
        this(DbContract.NULL_ID, DEFAULT_NAME, DEFAULT_MAIL, DEFAULT_TEL, DEFAULT_NOTE);
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
