package com.wencharm.fun.data.datasource;

import android.util.LruCache;

/**
 * Created by Wencharm on 10/11/2016.
 */

public class Cache extends LruCache {

	/**
	 * @param maxSize for caches that do not override {@link #sizeOf}, this is
	 *                the maximum number of entries in the cache. For all other caches,
	 *                this is the maximum sum of the sizes of the entries in this cache.
	 */
	public Cache(int maxSize) {
		super(maxSize);
	}
}
