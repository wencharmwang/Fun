package com.wencharm.fun.app;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by Wencharm on 19/10/2016.
 */

public class Image {

	public Image(Context context, Network network) {
		ImagePipelineConfig imagePipelineConfig = OkHttpImagePipelineConfigFactory.newBuilder(context, network.httpClient).build();
		Fresco.initialize(context, imagePipelineConfig);
	}

	public void load(SimpleDraweeView imageView, Uri uri) {
		imageView.setImageURI(uri);
	}

	public void load(SimpleDraweeView imageView, String url) {
		imageView.setImageURI(url);
	}
}
