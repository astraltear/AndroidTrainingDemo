package jee.young.androidtrainingdemo;

import java.io.File;

abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
