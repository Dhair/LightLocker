package com.dhair.light.locker.component.cp;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.android.common.db.UPreferenceManager;
import com.dhair.light.locker.application.LightLockerApplication;

import java.util.Map;


public class UContentProvider extends ContentProvider {

    private static final String AUTHORITY = ".component.cp.UContentProvider";
    private static Uri mUri;

    public static class UContentProviderIntent {
        public static final String TYPE = "type";
        public static final String KEY = "key";
        public static final String VALUE = "value";
        public static final String PREFERENCE = "SharedPreferences";
    }

    private static final int MATCH_SHARE_PREFERENCES_DATA = 0x00001;

    private static final int MATCH_USER_CODE = 0x00002;
    public static final String MATCH_USER = "User";

    private static UriMatcher mUriMatcher;
    private UPreferenceManager mUPreferenceManager;

    public static Uri getUri() {
        return mUri;
    }

    private static String getAuthority(Context context) {
        return context.getPackageName() + AUTHORITY;
    }

    public static void init(Context context) {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(getAuthority(context), UContentProviderIntent.PREFERENCE + "/*/*", MATCH_SHARE_PREFERENCES_DATA);

        mUriMatcher.addURI(getAuthority(context), MATCH_USER, MATCH_USER_CODE);
        mUri = Uri.parse("content://" + getAuthority(context));
    }

    private UPreferenceManager getPreferenceManager() {
        if (mUPreferenceManager == null) {
            mUPreferenceManager = new UPreferenceManager(getApplicationContext());
        }
        return mUPreferenceManager;
    }

    @Override
    public boolean onCreate() {
        if (mUriMatcher == null) {
            init(getApplicationContext());
        }
        return true;
    }

    protected Context getApplicationContext() {
        Context context = getContext();
        if (context != null) {
            context = context.getApplicationContext();
        } else {
            context = LightLockerApplication.getContext();
        }
        return context;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor;
        switch (mUriMatcher.match(uri)) {
            case MATCH_SHARE_PREFERENCES_DATA:
                final String key = uri.getPathSegments().get(1);
                final String type = uri.getPathSegments().get(2);
                cursor = new MatrixCursor(new String[]{key});
                if (!getPreferenceManager().contains(key)) {
                    return cursor;
                }
                MatrixCursor.RowBuilder rowBuilder = cursor.newRow();
                rowBuilder.add(getValue(uri, key, type));
                break;
            default:
                cursor = new MatrixCursor(new String[]{UContentProviderIntent.VALUE}, 1);
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + getAuthority(getApplicationContext()) + ".item";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (mUriMatcher.match(uri)) {
            case MATCH_SHARE_PREFERENCES_DATA:
                for (Map.Entry<String, Object> entry : values.valueSet()) {
                    final String key = entry.getKey();
                    final Object value = entry.getValue();
                    putValue(uri, key, value);
                }
                break;
            default:
                break;
        }

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (mUriMatcher.match(uri)) {
            case MATCH_SHARE_PREFERENCES_DATA:
                final String key = uri.getPathSegments().get(1);
                getPreferenceManager().remove(key);
                break;
            default:
                break;
        }

        return 1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private Object getValue(Uri uri, String key, String type) {
        if (PreferenceType.STRING_TYPE.equals(type)) {
            return getPreferenceManager().getString(key);
        } else if (PreferenceType.BOOLEAN_TYPE.equals(type)) {
            return getPreferenceManager().getBoolean(key) ? 1 : 0;
        } else if (PreferenceType.LONG_TYPE.equals(type)) {
            return getPreferenceManager().getLong(key);
        } else if (PreferenceType.INT_TYPE.equals(type)) {
            return getPreferenceManager().getInt(key);
        } else if (PreferenceType.FLOAT_TYPE.equals(type)) {
            return getPreferenceManager().getFloat(key);
        } else {
            throw new IllegalArgumentException("Unsupported type " + uri);
        }
    }

    private void putValue(Uri uri, String key, Object value) {
        if (value == null) {
            getPreferenceManager().remove(key);
        } else if (value instanceof String) {
            getPreferenceManager().putString(key, (String) value);
        } else if (value instanceof Boolean) {
            getPreferenceManager().putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            getPreferenceManager().putLong(key, (Long) value);
        } else if (value instanceof Integer) {
            getPreferenceManager().putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            getPreferenceManager().putFloat(key, (Float) value);
        } else {
            throw new IllegalArgumentException("Unsupported type " + uri);
        }
    }
}
