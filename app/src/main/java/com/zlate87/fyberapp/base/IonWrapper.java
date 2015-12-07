package com.zlate87.fyberapp.base;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

/**
 * Class responsible for HTTP communication. This class is basically a wrapper for the ion library.
 */
public class IonWrapper {

	private static final String LOG_TAG = IonWrapper.class.getSimpleName();

	/**
	 * Loads URL and returns a string response.
	 *
	 * @param url      the URL to load
	 * @param callback the callback to be called when the loading is done
	 * @param context  the context
	 */
	public void loadUrlWithStringResponse(String url, FutureCallback<Response<String>> callback, Context context) {
		Log.d(LOG_TAG, String.format("loadUrlWithStringResponse called with URL [%s]", url));
		Ion.with(context).load(url).asString().withResponse().setCallback(callback);
	}

	/**
	 * Loads an image from and URL and adds it ot a image view.
	 *
	 * @param imageView        the image view
	 * @param url              the URL
	 * @param placeholderResId placeholder resource for while the image is loading
	 * @param errorResId       resource to be used in case of an error
	 */
	public void loadImageFromUrlIntoImageView(ImageView imageView, String url, int placeholderResId, int errorResId) {
		Log.d(LOG_TAG, String.format("loadImageFromUrlIntoImageView called with URL [%s]", url));
		Ion.with(imageView).placeholder(placeholderResId).error(errorResId).load(url);
	}
}
