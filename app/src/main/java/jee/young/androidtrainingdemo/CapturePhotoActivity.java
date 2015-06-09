package jee.young.androidtrainingdemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CapturePhotoActivity extends ActionBarActivity {

    private ImageView mImageView;
    private Bitmap mImageBitmap;
    private VideoView mVideoView;
    private Uri mVideoUri;

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    private String mCurrentPhotoPath;

    private static final int ACTION_TAKE_PHOTO_B = 1;
    private static final int ACTION_TAKE_PHOTO_S = 2;
    private static final int ACTION_TAKE_VIDEO = 3;
    private String JPEG_FILE_PREFIX="IMG_";
    private String JPEG_FILE_SUFFIX=".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_photo);

        mImageView = (ImageView) findViewById(R.id.imageView1);
        mVideoView = (VideoView) findViewById(R.id.videoView1);
        mImageBitmap = null;
        mVideoUri = null;

        Button picBtn = (Button) findViewById(R.id.btnintend);
        setBtnListenerOrDisable(picBtn, mTakePicOnClickListener, MediaStore.ACTION_IMAGE_CAPTURE);

        Button picSBtn = (Button) findViewById(R.id.btnintendS);
        setBtnListenerOrDisable(picSBtn,mTakePicSOnClickListener,MediaStore.ACTION_IMAGE_CAPTURE);

        Button vidBtn = (Button) findViewById(R.id.btnintendV);
        setBtnListenerOrDisable(vidBtn,mTakeVidOnClickListener,MediaStore.ACTION_VIDEO_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }

    Button.OnClickListener mTakePicOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
        }
    };

    Button.OnClickListener mTakePicSOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);
        }
    };

    Button.OnClickListener mTakeVidOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            dispatchTakeVideoIntent();
        }
    };

    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        switch (actionCode){
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
//                  mCurrentPhotoPath =/storage/emulated/0/Pictures/CameraSample/IMG_20150609_160931_917948611.jpg
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    f = null;
                    mCurrentPhotoPath = null;
                }

                break;

            default:
                break;
        }

        startActivityForResult(takePictureIntent,actionCode);
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }



    private File getAlbumDir() {
        File storageDir = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null){
                if (!storageDir.mkdirs()){
                    if (!storageDir.exists()) {
                        return null;
                    }
                }
            }
        } else {
            Log.d(this.getClass().getSimpleName(), "External stroage is not mounted READ/WRITE");
        }
        return storageDir;
    }

    private String getAlbumName() {
        return getString(R.string.album_name);
    }


    private void dispatchTakeVideoIntent() {
        Intent VideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(VideoIntent,ACTION_TAKE_VIDEO);
    }

    private void setBtnListenerOrDisable(Button btn, Button.OnClickListener onClickListener, String intentName){
        if ( isIntentAvailable(this,intentName) ){
            btn.setOnClickListener(onClickListener);
        } else {
            btn.setText(getText(R.string.cannot).toString()+" "+btn.getText());
            btn.setClickable(false);
        }
    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ACTION_TAKE_PHOTO_B:{
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            }

            case ACTION_TAKE_PHOTO_S:{
                if (resultCode == RESULT_OK) {
                    handleSmallCameraPhoto(data);
                }
                break;
            }

            case ACTION_TAKE_VIDEO:{
                if (resultCode == RESULT_OK) {
                    handleCameraVideo(data);
                }
                break;
            }
        }
    }

    private void handleCameraVideo(Intent data) {
    }

    private void handleSmallCameraPhoto(Intent intent) {

//        Uri currImageURI = intent.getData();
//        Log.d(this.getClass().getSimpleName(), "[" + currImageURI.toString() + "]");
//        06-09 17:41:45.118: D/CapturePhotoActivity(28019): [>>>>>>>>content://media/external/images/media/23645]


        Log.d(this.getClass().getSimpleName(), "[>>>>>>>>" + intent.getData() + "]");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), intent.getData());
            bitmap = Bitmap.createScaledBitmap(bitmap, 200, 400, true);
            mImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            Log.d(this.getClass().getSimpleName(), "errorMessage:["+e.getMessage()+"]");
            e.printStackTrace();
        }



//        mImageView.setVisibility(View.VISIBLE);
//        mVideoView.setVisibility(View.INVISIBLE);
        /*mImageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(mImageBitmap);
        mVideoUri = null;


*/
    }

    private void handleBigCameraPhoto() {
        if(mCurrentPhotoPath != null){
//            setPic();
//            galleryAddPic();
            mCurrentPhotoPath = null;
        }
    }

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private static final String VIDEO_STORAGE_KEY = "viewvideo";
    private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY,mImageBitmap);
        outState.putParcelable(VIDEO_STORAGE_KEY,mVideoUri);
        outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null));
        outState.putBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY,(mVideoUri != null));

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
        mVideoUri = savedInstanceState.getParcelable(VIDEO_STORAGE_KEY);

        mImageView.setImageBitmap(mImageBitmap);
        mImageView.setVisibility(
                savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? ImageView.VISIBLE : ImageView.INVISIBLE
        );

        mVideoView.setVideoURI(mVideoUri);
        mVideoView.setVisibility(
                savedInstanceState.getBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY)?ImageView.VISIBLE:ImageView.INVISIBLE
        );
    }
}
