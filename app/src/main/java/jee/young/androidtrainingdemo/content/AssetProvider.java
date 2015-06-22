package jee.young.androidtrainingdemo.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AssetProvider extends ContentProvider {

    public static String CONTENT_URI = "jee.young.androidtrainingdemo";

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(getClass().getSimpleName(), "query method!!!!!!!!!!!!!!");
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String mode) throws FileNotFoundException {
        final String assetName = uri.getLastPathSegment();

        if (TextUtils.isEmpty(assetName)) {
            throw new FileNotFoundException();
        }

        try {
            AssetManager am = getContext().getAssets();
            return am.openFd(assetName);
        } catch (IOException e){
            e.printStackTrace();
            return super.openAssetFile(uri, mode);
        }
    }
}
