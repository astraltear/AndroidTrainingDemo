package jee.young.androidtrainingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.IOException;


public class GetGalleryImageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_gallery_image);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = new Intent();
        Uri uri = Uri.parse("content://media/external/images/media/23645");

        intent.setData(uri);

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), intent.getData());

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 400, true);
            imageView.setImageBitmap(resized);


           /* BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            bitmap = BitmapFactory.decodeFile("/sdcard/image.jpg", options);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 400, true);
            imageView.setImageBitmap(resized);*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
