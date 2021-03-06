package com.wencharm.fun.presentation.main;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wencharm.fun.app.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Wencharm on 10/05/2017.
 */

public class ImageSyncService extends Service {

	public static final String TAG = "ImageSyncService";
	public static final String ALBUM_NAME = "Wencharm";
	public static final String TAG_ON_IMAGE = "TAG_ON_IMAGE";
	public static final String TEXT = "Wencharm家的璐璐怎么辣么美~";
	public static final String SYNCED_UNTIL_DATE = "synced_until_date";
	public static int MAX_SYNC = 100;

	private Handler mHandle;

	public ImageSyncService() {
		super();
	}

	@Override
	public void onCreate() {
		mHandle = new Handler(Looper.getMainLooper());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		register();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void register() {
		getContentResolver().registerContentObserver(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true,
				new ContentObserver(mHandle) {
					@Override
					public void onChange(boolean selfChange) {
						Log.d(TAG, "External Media has been changed");
						super.onChange(selfChange);
						Single.create(s -> {
							ArrayList<String> images;
							images = readLastDateFromMediaStore(ImageSyncService.this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							addTag(images);
						}).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe();
					}
				}
		);
	}

	public static ArrayList<String> readLastDateFromMediaStore(Context context, Uri uri) {
		Cursor cursor = context.getContentResolver().query(uri, null, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
		long untilDate = App.sp.getLong(SYNCED_UNTIL_DATE, 0);
		ArrayList<String> images = new ArrayList<>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN));
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
				if (date > untilDate && images.size() <= MAX_SYNC && path.toLowerCase().contains("camera")) {
					images.add(path);
				}
				if (cursor.isFirst() && date > untilDate) {
					App.sp.edit().putLong(SYNCED_UNTIL_DATE, date).apply();
				}
			}
			cursor.close();
		}
		return images;
	}

	public static void addTag(ArrayList<String> images) {
		String text = App.sp.getString(TAG_ON_IMAGE, TEXT);
		if (images != null && images.size() > 0) {
			for (int i = 0; i < images.size(); i++) {
				try {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inMutable = true;
					Bitmap bitmap = BitmapFactory.decodeFile(images.get(i), options);
					ExifInterface exif = new ExifInterface(images.get(i));
					Canvas canvas = new Canvas(bitmap);
					Paint paint = new Paint();
					int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
					float rotate = 0;
					if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
						rotate = -90;
					} else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
						rotate = -180;
					} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
						rotate = -270;
					}
					canvas.rotate(rotate, canvas.getWidth() / 2, canvas.getHeight() / 2);
					paint.setColor(Color.WHITE);
					paint.setTextSize(30 * App.app.getResources().getDisplayMetrics().density);
					paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
					// draw text to the Canvas center
					Rect bounds = new Rect();
					paint.getTextBounds(text, 0, text.length(), bounds);
					int x = (bitmap.getWidth() - bounds.width()) / 2;
					int y = bounds.height();
					canvas.drawText(text, x, y, paint);
					// save image;
					String state = Environment.getExternalStorageState();
					if (Environment.MEDIA_MOUNTED.equals(state)) {
						// Get the directory for the user's public pictures directory.
						File file = new File(Environment.getExternalStoragePublicDirectory(
								Environment.DIRECTORY_PICTURES), ALBUM_NAME);
						File imageFile = null;
						if (!file.exists()) {
							file.mkdirs();
						}
						imageFile = new File(file, SystemClock.elapsedRealtime() + ".jpg");
						OutputStream os = new FileOutputStream(imageFile);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
						os.flush();
						os.close();

						// copy exif to new image;
						ExifInterface newExif = new ExifInterface(imageFile.getPath());
						newExif.setAttribute(ExifInterface.TAG_ORIENTATION, exif.getAttribute(ExifInterface.TAG_ORIENTATION));
						newExif.saveAttributes();
						// save image to Media Provider
						Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
						mediaScanIntent.setData(Uri.fromFile(imageFile));
						App.app.sendBroadcast(mediaScanIntent);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
