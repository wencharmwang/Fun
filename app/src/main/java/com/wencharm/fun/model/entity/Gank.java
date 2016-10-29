package com.wencharm.fun.model.entity;

import java.util.List;

/**
 * Created by Wencharm on 18/10/2016.
 */

public class Gank {

	public boolean error;

	public List<GankInfo> results;

	public static class GankInfo {

		public String _id;

		public String createdAt;

		public String desc;

		public String publishedAt;

		public String source;

		public String type;

		public String url;

		public boolean used;

		public String who;
	}
}
