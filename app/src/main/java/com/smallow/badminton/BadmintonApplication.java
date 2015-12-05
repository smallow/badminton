package com.smallow.badminton;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import smallow.core.AppAction;
import smallow.core.AppActionImpl;

/**
 * Created by smallow on 2015/10/28.
 */
public class BadmintonApplication extends Application {

    private AppAction appAction;

    @Override
    public void onCreate() {
        super.onCreate();
        appAction = new AppActionImpl(this);
        initImageLoader(getApplicationContext());
    }

    public AppAction getAppAction() {
        return appAction;
    }

    private void initImageLoader(Context context) {
        try {
            File cacheDir = StorageUtils.getCacheDirectory(context);
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    context).threadPoolSize(3)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .diskCache(new UnlimitedDiskCache(cacheDir)).writeDebugLogs()
                    .build();
            ImageLoader.getInstance().init(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
