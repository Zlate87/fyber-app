package com.zlate87.fyberapp.base;

import android.content.Context;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

/**
 * Class responsible for HTTP communication. This class is basically a wrapper for the ion library.
 */
public class HttpStack {

	private final Context context;

	public HttpStack(Context context) {
		this.context = context;
	}

	public void getStringObjectWithResponse(String url, FutureCallback<Response<String>> callback) {
		// TODO enable keep-alive and gzip
		Ion.with(context).load(url).asString().withResponse().setCallback(callback);
	}
}
