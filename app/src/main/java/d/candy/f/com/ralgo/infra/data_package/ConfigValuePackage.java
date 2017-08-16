package d.candy.f.com.ralgo.infra.data_package;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import d.candy.f.com.ralgo.infra.RepositoryDefaults;

/**
 * Created by daichi on 8/14/17.
 */

public class ConfigValuePackage {

    /**
     * Value maps
     */
    private Map<String, Integer> mIntMap = null;
    private Map<String, Long> mLongMap = null;
    private Map<String, Float> mFloatMap = null;
    private Map<String, String> mStringMap = null;
    private Map<String, Boolean> mBooleanMap = null;

    /**
     * Default values
     */
    private boolean mDefaultBoolean = RepositoryDefaults.DEFAULT_BOOLEAN;
    private float mDefaultFloat = RepositoryDefaults.DEFAULT_FLOAT;
    private long mDefaultLong = RepositoryDefaults.DEFAULT_LONG;
    private int mDefaultInt = RepositoryDefaults.DEFAULT_INT;
    private String mDefaultString = RepositoryDefaults.DEFAULT_STRING;

    public ConfigValuePackage() {}

    /**
     * region; put
     */

    public ConfigValuePackage put(@NonNull String key, boolean value) {
        if (mBooleanMap == null) {
            mBooleanMap = new HashMap<>();
        }
        mBooleanMap.put(key, value);
        return this;
    }

    public ConfigValuePackage put(@NonNull String key, float value) {
        if (mFloatMap == null) {
            mFloatMap = new HashMap<>();
        }
        mFloatMap.put(key, value);
        return this;
    }

    public ConfigValuePackage put(@NonNull String key, long value) {
        if (mLongMap == null) {
            mLongMap = new HashMap<>();
        }
        mLongMap.put(key, value);
        return this;
    }

    public ConfigValuePackage put(@NonNull String key, int value) {
        if (mIntMap == null) {
            mIntMap = new HashMap<>();
        }
        mIntMap.put(key, value);
        return this;
    }

    public ConfigValuePackage put(@NonNull String key, String value) {
        if (mStringMap == null) {
            mStringMap = new HashMap<>();
        }
        mStringMap.put(key, value);
        return this;
    }

    /**
     * region; get
     */

     public String getString(@NonNull String key) {
        return getStringOrDefault(key, mDefaultString);
    }

    public boolean getBoolean(@NonNull String key) {
        return getBooleanOrDefault(key, mDefaultBoolean);
    }

    public float getFloat(@NonNull String key) {
        return getFloatOrDefault(key, mDefaultFloat);
    }

    public long getLong(@NonNull String key) {
        return getLongOrDefault(key, mDefaultLong);
    }

    public int getInt(@NonNull String key) {
        return getIntOrDefault(key, mDefaultInt);
    }

    public String getStringOrDefault(@NonNull String key, String def) {
        if (mStringMap != null && mStringMap.containsKey(key)) {
            return mStringMap.get(key);
        }
        return def;
    }

    public boolean getBooleanOrDefault(@NonNull String key, boolean def) {
        if (mBooleanMap != null && mBooleanMap.containsKey(key)) {
            return mBooleanMap.get(key);
        }
        return def;
    }

    public float getFloatOrDefault(@NonNull String key, Float def) {
        if (mFloatMap != null && mFloatMap.containsKey(key)) {
            return mFloatMap.get(key);
        }
        return def;
    }

    public long getLongOrDefault(@NonNull String key, long def) {
        if (mLongMap != null && mLongMap.containsKey(key)) {
            return mLongMap.get(key);
        }
        return def;
    }

    public int getIntOrDefault(@NonNull String key, int def) {
        if (mIntMap != null && mIntMap.containsKey(key)) {
            return mIntMap.get(key);
        }
        return def;
    }

    /**
     * region; get a key set
     */

    public Set<String> keySetForBooleanValues() {
        if (mBooleanMap != null) {
            return mBooleanMap.keySet();
        }
        return null;
    }

    public Set<String> keySetForLongValues() {
        if (mLongMap != null) {
            return mLongMap.keySet();
        }
        return null;
    }

    public Set<String> keySetForFloatValues() {
        if (mFloatMap != null) {
            return mFloatMap.keySet();
        }
        return null;
    }

    public Set<String> keySetForIntValues() {
        if (mIntMap != null) {
            return mIntMap.keySet();
        }
        return null;
    }

    public Set<String> keySetForStringValues() {
        if (mStringMap != null) {
            return mStringMap.keySet();
        }
        return null;
    }

    /**
     * region; get & set a default value
     */

    public boolean getDefaultBoolean() {
        return mDefaultBoolean;
    }

    public void setDefaultBoolean(boolean defaultBoolean) {
        mDefaultBoolean = defaultBoolean;
    }

    public float getDefaultFloat() {
        return mDefaultFloat;
    }

    public void setDefaultFloat(float defaultFloat) {
        mDefaultFloat = defaultFloat;
    }

    public long getDefaultLong() {
        return mDefaultLong;
    }

    public void setDefaultLong(long defaultLong) {
        mDefaultLong = defaultLong;
    }

    public int getDefaultInt() {
        return mDefaultInt;
    }

    public void setDefaultInt(int defaultInt) {
        mDefaultInt = defaultInt;
    }

    public String getDefaultString() {
        return mDefaultString;
    }

    public void setDefaultString(String defaultString) {
        mDefaultString = defaultString;
    }

    /**
     * region; preset a key
     */

    public ConfigValuePackage presetKeyForLongValue(@NonNull String key) {
        if (mLongMap == null) {
            mLongMap = new HashMap<>();
        }
        mLongMap.put(key, mDefaultLong);
        return this;
    }

    public ConfigValuePackage presetKeyForIntValue(@NonNull String key) {
        if (mIntMap == null) {
            mIntMap = new HashMap<>();
        }
        mIntMap.put(key, mDefaultInt);
        return this;
    }

    public ConfigValuePackage presetKeyForFloatValue(@NonNull String key) {
        if (mFloatMap == null) {
            mFloatMap = new HashMap<>();
        }
        mFloatMap.put(key, mDefaultFloat);
        return this;
    }

    public ConfigValuePackage presetKeyForBooleanValue(@NonNull String key) {
        if (mBooleanMap == null) {
            mBooleanMap = new HashMap<>();
        }
        mBooleanMap.put(key, mDefaultBoolean);
        return this;
    }

    public ConfigValuePackage presetKeyForStringValue(@NonNull String key) {
        if (mStringMap == null) {
            mStringMap = new HashMap<>();
        }
        mStringMap.put(key, mDefaultString);
        return this;
    }
}
