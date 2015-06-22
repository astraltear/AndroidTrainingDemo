package jee.young.androidtrainingdemo.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class ContentItem {
    public static final int CONTENT_TYPE_IMAGE = 0;
    public static final int CONTENT_TYPE_TEXT = 1;

    public final int contentType;
    public final int contentResourceId;
    public final String contentAssertFilePath;

    public ContentItem(int type, int resourceId) {
        this.contentType = type;
        this.contentResourceId = resourceId;
        contentAssertFilePath = null;
    }

    public ContentItem(int type, String assertFilePath) {
        this.contentType = type;
        this.contentResourceId = 0;
        this.contentAssertFilePath = assertFilePath;
    }

    public Uri getContentUri(){
        if (!TextUtils.isEmpty(contentAssertFilePath)) {
//            06-22 15:38:23.398: D/ContentItem(13132): content://jee.young.androidtrainingdemo/03.png

            Log.d(getClass().getSimpleName(), Uri.parse("content://" + AssetProvider.CONTENT_URI + "/" + contentAssertFilePath).toString());
            return Uri.parse("content://" + AssetProvider.CONTENT_URI + "/" + contentAssertFilePath);
        } else {
            return null;
        }
    }

    public Intent getShareIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        switch (contentType){
            case CONTENT_TYPE_IMAGE:
                intent.setType("image/jpg");
                intent.putExtra(Intent.EXTRA_STREAM, getContentUri());
                break;

            case CONTENT_TYPE_TEXT:
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, context.getString(contentResourceId));
                break;
        }

        return intent;
    }
}
