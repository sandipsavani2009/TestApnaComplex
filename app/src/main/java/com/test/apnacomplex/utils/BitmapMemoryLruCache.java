package com.test.apnacomplex.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by sc on 8/4/18.
 */

public class BitmapMemoryLruCache extends LruCache<String, Bitmap> {

    public BitmapMemoryLruCache() {
        this((int) (Runtime.getRuntime().maxMemory() / 1024) * 1024);  // 1 mb
    }

    public BitmapMemoryLruCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return bitmap.getByteCount() / 1024;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Bitmap getBitmap(String key) {
        return get(key);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

}
