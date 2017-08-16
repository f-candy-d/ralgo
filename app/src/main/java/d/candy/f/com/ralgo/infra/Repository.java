package d.candy.f.com.ralgo.infra;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import d.candy.f.com.ralgo.infra.data_package.ConfigValuePackage;
import d.candy.f.com.ralgo.infra.data_package.SqlEntryPackage;
import d.candy.f.com.ralgo.infra.sqlite.SqliteQuery;
import d.candy.f.com.ralgo.infra.sqlite.SqliteWhere;

/**
 * Created by daichi on 17/08/14.
 */

public interface Repository {

    long SQL_ENTRY_NULL_ID = -1;

    /**
     * region; Save config data
     */
    void saveConfigValue(@NonNull String key, int value);
    void saveConfigValue(@NonNull String key, long value);
    void saveConfigValue(@NonNull String key, float value);
    void saveConfigValue(@NonNull String key, String value);
    void saveConfigValue(@NonNull String key, boolean value);
    void saveConfigValues(@NonNull ConfigValuePackage entryPackage);

    /**
     * region; Load config data
     */
    int loadConfigValueAsInt(@NonNull String key);
    int loadConfigValueAsIntOrDefault(@NonNull String key, int def);
    long loadConfigValueAsLong(@NonNull String key);
    long loadConfigValueAsLongOrDefault(@NonNull String key, long def);
    float loadConfigValueAsFloat(@NonNull String key);
    float loadConfigValueAsFloatOrDefault(@NonNull String key, float def);
    String loadConfigValueAsString(@NonNull String key);
    String loadConfigValueAsStringOrDefault(@NonNull String key, String def);
    boolean loadConfigValueAsBoolean(@NonNull String key);
    boolean loadConfigValueAsBooleanOrDefault(@NonNull String key, boolean def);
    void loadConfigValuesForPresetKeys(@NonNull ConfigValuePackage presetPackage);

    /**
     * region; Save sql entry
     */
    long saveSqlEntry(@NonNull String tableName, @NonNull SqlEntryPackage entryPackage);
    long[] saveSqlEntries(@NonNull String tableName, @NonNull Collection<SqlEntryPackage> entryPackages);

    /**
     * region; Load sql entry
     */
    @NonNull
    ArrayList<SqlEntryPackage> loadSqlEntries(@NonNull SqliteQuery sqliteQuery);

    /**
     * region; Update sql entry
     */
    boolean updateSqlEntry(@NonNull String tableName, @NonNull SqlEntryPackage entryPackage, @NonNull String idColumnName);
    int updateSqlEntriesIfMatch(@NonNull String tableName, @NonNull SqlEntryPackage entryPackage, @NonNull SqliteWhere.Expr condition);

    /**
     * region; Delete sql entry
     */
    boolean deleteSqlEntry(@NonNull String tableName, @NonNull String idColumnName, long id);
    int deleteSqlEntriesIfMatch(@NonNull String tableName, @NonNull SqliteWhere.Expr condition);
}
