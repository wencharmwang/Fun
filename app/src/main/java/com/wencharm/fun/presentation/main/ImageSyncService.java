package com.wencharm.fun.presentation.main;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wencharm.fun.app.App;

import java.util.ArrayList;

/**
 * Created by Wencharm on 10/05/2017.
 */

public class ImageSyncService extends IntentService {

	public static final String TAG = "ImageSyncService";

	private Handler mHandle;

	public ImageSyncService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		mHandle = new Handler(Looper.getMainLooper());
		register();
	}

	private void register() {
		getContentResolver().registerContentObserver(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true,
				new ContentObserver(mHandle) {
					@Override
					public void onChange(boolean selfChange) {
						Log.d(TAG, "External Media has been changed");
						super.onChange(selfChange);
						ArrayList<String> images;
						images = readLastDateFromMediaStore(ImageSyncService.this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						addTag(images);
					}
				}
		);
	}

	public static ArrayList<String> readLastDateFromMediaStore(Context context, Uri uri) {
		Cursor cursor = context.getContentResolver().query(uri, null, null, null, "date_added DESC");
		Long dateAdded = -1L;
		ArrayList<String> images = new ArrayList<>();
		if (cursor != null) {
			if (cursor.moveToNext()) {
				dateAdded = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED));
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
				images.add(path);
			}
			cursor.close();
		}
		App.sp.edit().putLong("latest_date", dateAdded).apply();
		return images;
	}

	private void addTag(ArrayList<String> images) {

	}
}
