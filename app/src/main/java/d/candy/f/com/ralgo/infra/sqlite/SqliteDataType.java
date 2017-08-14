package d.candy.f.com.ralgo.infra.sqlite;

/**
 * Created by daichi on 8/15/17.
 */

public enum SqliteDataType {
    INTEGER("INTEGER"),
    INTEGER_PK("INTEGER PRIMARY KEY"),
    TEXT("TEXT"),
    REAL("REAL");

    final private String mString;

    SqliteDataType(String string) {
        mString = string;
    }

    @Override
    public String toString() {
        return mString;
    }
}
