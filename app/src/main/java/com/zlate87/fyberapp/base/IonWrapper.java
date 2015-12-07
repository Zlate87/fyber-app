package com.zlate87.fyberapp.base;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.zlate87.fyberapp.R;

/**
 * Class responsible for HTTP communication. This class is basically a wrapper for the ion library.
 */
public class IonWrapper {

	private static final String LOG_TAG = IonWrapper.class.getSimpleName();

	// TODO add java doc
	public void getStringObjectWithResponse(String url, FutureCallback<Response<String>> callback, Context context) {
		Log.d(LOG_TAG, String.format("getStringObjectWithResponse called with URL [%s]", url));
		Ion.with(context).load(url).asString().withResponse().setCallback(callback);
	}

	// TODO add java doc
	public void loadImageFromUrlIntoImageView(ImageView imageView, String url, int placeholderRebid, int errorResId) {
		Ion.with(imageView).placeholder(placeholderRebid).error(errorResId).load(url);
	}
}
