package jee.young.androidtrainingdemo;

import android.os.Environment;

import java.io.File;

public class BaseAlbumDirFactory extends AlbumStorageDirFactory {

    private static final String CAMERA_DIR = "/dcim/";

    @Override
    public File getAlbumStorageDir(String albumName) {
        return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR + albumName);
    }
}
