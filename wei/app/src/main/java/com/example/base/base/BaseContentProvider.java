package com.example.base.base;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2016/1/2.
 */
public abstract class BaseContentProvider extends ContentProvider {

//    BaseSQLiteOpenHelper dBlite;
//    SQLiteDatabase db;
//
//    public  void setData(LigthSQLiteOpenHelper dBlite){
//
//        this.dBlite = dBlite;
//    };
//
//    private static final UriMatcher sMatcher;
//    static {
//        sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        sMatcher.addURI(LigthMetaData.AUTOHORITY,LigthMetaData.TABLE_NAME, LigthMetaData.ITEM);
//        sMatcher.addURI(LigthMetaData.AUTOHORITY, LigthMetaData.TABLE_NAME+"/#", LigthMetaData.ITEM_ID);
//    }
//
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        // TODO Auto-generated method stub
//        db = dBlite.getWritableDatabase();
//        int count = 0;
//        switch (sMatcher.match(uri)) {
//            case LigthMetaData.ITEM:
//                count = db.delete(LigthMetaData.TABLE_NAME,selection, selectionArgs);
//                break;
//            case LigthMetaData.ITEM_ID:
//                String id = uri.getPathSegments().get(1);
//                count = db.delete(BaseMetaData.JID, BaseMetaData.JID+"="+id+(!TextUtils.isEmpty(BaseMetaData.JID="?")?"AND("+selection+')':""), selectionArgs);
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI"+uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
//        return count;
//    }
//
//    @Override
//    public String getType(Uri uri) {
//        // TODO Auto-generated method stub
//        switch (sMatcher.match(uri)) {
//            case LigthMetaData.ITEM:
//                return LigthMetaData.CONTENT_TYPE;
//            case LigthMetaData.ITEM_ID:
//                return LigthMetaData.CONTENT_ITEM_TYPE;
//            default:
//                throw new IllegalArgumentException("Unknown URI"+uri);
//        }
//    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        // TODO Auto-generated method stub
//
//        db = dBlite.getWritableDatabase();
//        long rowId;
//        if(sMatcher.match(uri)!=LigthMetaData.ITEM){
//            throw new IllegalArgumentException("Unknown URI"+uri);
//        }
//        rowId = db.insert(LigthMetaData.TABLE_NAME,BaseMetaData._ID,values);
//        if(rowId>0){
//            Uri noteUri=ContentUris.withAppendedId(LigthMetaData.CONTENT_URI, rowId);
//            getContext().getContentResolver().notifyChange(noteUri, null);
//            return noteUri;
//        }
//        throw new IllegalArgumentException("Unknown URI"+uri);
//    }
//
//    @Override
//    public boolean onCreate() {
//        // TODO Auto-generated method stub
////        this.dBlite = new LigthSQLiteOpenHelper(this.getContext());
////                db = dBlite.getWritableDatabase();
////                return (db == null)?false:true;
//        return true;
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection,
//                        String[] selectionArgs, String sortOrder) {
//        // TODO Auto-generated method stub
//        db = dBlite.getWritableDatabase();
//        Cursor c;
//        Log.d("-------", String.valueOf(sMatcher.match(uri)));
//        switch (sMatcher.match(uri)) {
//            case LigthMetaData.ITEM:
//                c = db.query(LigthMetaData.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
//
//                break;
//            case LigthMetaData.ITEM_ID:
//                String id = uri.getPathSegments().get(1);
//                c = db.query(LigthMetaData.TABLE_NAME, projection, BaseMetaData._ID+"="+id+(!TextUtils.isEmpty(selection)?"AND("+selection+')':""),selectionArgs, null, null, sortOrder);
//                break;
//            default:
//                Log.d("!!!!!!", "Unknown URI"+uri);
//                throw new IllegalArgumentException("Unknown URI"+uri);
//        }
//        c.setNotificationUri(getContext().getContentResolver(), uri);
//        return c;
//    }
//    @Override
//    public int update(Uri uri, ContentValues values, String selection,
//                      String[] selectionArgs) {
//        // TODO Auto-generated method stub
//        return 0;
//    }


    public static final boolean DEBUG = false;
    private static final String TAG = "BaseContentProvider";

    private static final String PARAMETER_NOTIFY = "notify";
    private static final String PARAMETER_SYNC = "sync";

    private SQLiteOpenHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        mOpenHelper = onCreateSQLiteOpenHelper();
        return true;
    }

    public String getAuthority(){
        try {
            ProviderInfo providerInfo = getContext().getPackageManager().getProviderInfo(new ComponentName(getContext(), getClass()), PackageManager.GET_META_DATA);
            return providerInfo.authority;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ContentResolver getContentResolver(){
        return getContext().getContentResolver();
    }

    public Uri getAuthorityUri(){
        return Uri.parse("content://" + getAuthority());
    }

    protected abstract SQLiteOpenHelper onCreateSQLiteOpenHelper();

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        if (DEBUG) {
            Log.d(TAG, "query:" + uri.toString());
        }
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(args.table);

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor result = qb.query(db, projection, args.where, args.args, null,
                null, sortOrder);
        // result.setNotificationUri(getContext().getContentResolver(), uri);

        return result;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        SqlArguments args = new SqlArguments(uri, null, null);
        if (TextUtils.isEmpty(args.where)) {
            return "vnd.android.cursor.dir/" + args.table;
        } else {
            return "vnd.android.cursor.item/" + args.table;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        if (DEBUG) {
            Log.d(TAG, "insert:" + uri.toString());
        }
        SqlArguments args = new SqlArguments(uri);

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final long rowId = db.insert(args.table, null, values);
        if (rowId <= 0)
            return null;

        uri = ContentUris.withAppendedId(uri, rowId);
        sendNotify(uri);

        return uri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) {
            Log.d(TAG, "bulkInsert:" + uri.toString());
        }
        SqlArguments args = new SqlArguments(uri);

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            int numValues = values.length;
            for (int i = 0; i < numValues; i++) {
                if (db.insert(args.table, null, values[i]) < 0) {
                    return 0;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        sendNotify(uri);
        return values.length;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        if (DEBUG) {
            Log.d(TAG, "delete:" + uri.toString());
        }
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = db.delete(args.table, args.where, args.args);
        if (count > 0)
            sendNotify(uri);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (DEBUG) {
            Log.d(TAG, "update:" + uri.toString());
        }
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = db.update(args.table, values, args.where, args.args);
        if (count > 0)
            sendNotify(uri);

        return count;
    }

    private void sendNotify(Uri uri) {
        if (DEBUG) {
            Log.d(TAG, "sendNotify:" + uri.toString());
        }
        String notify = uri.getQueryParameter(PARAMETER_NOTIFY);
        if (notify == null || "true".equals(notify)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }

    private static class SqlArguments {
        public final String table;
        public final String where;
        public final String[] args;

        SqlArguments(Uri url, String where, String[] args) {
            if (url.getPathSegments().size() == 1) {
                this.table = url.getPathSegments().get(0);
                this.where = where;
                this.args = args;
            } else if (url.getPathSegments().size() != 2) {
                throw new IllegalArgumentException("Invalid URI: " + url);
            } else if (!TextUtils.isEmpty(where)) {
                throw new UnsupportedOperationException(
                        "WHERE clause not supported: " + url);
            } else {
                this.table = url.getPathSegments().get(0);

                String str = url.getLastPathSegment();
                if (str.contains("?")) {
                    this.where = "_id=" + str.substring(0, str.indexOf("?"));
                } else {
                    this.where = "_id=" + ContentUris.parseId(url);
                }
                this.args = null;
            }
        }

        SqlArguments(Uri url) {
            if (url.getPathSegments().size() == 1) {
                table = url.getPathSegments().get(0);
                where = null;
                args = null;
            } else {
                throw new IllegalArgumentException("Invalid URI: " + url);
            }
        }
    }

}
