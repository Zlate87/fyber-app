package com.zlate87.fyberapp.feature.offers.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.zlate87.fyberapp.R;
import com.zlate87.fyberapp.feature.offers.model.Offer;

/**
 * View holder class responsible for an offer.
 */
public class OfferViewHolder extends RecyclerView.ViewHolder {

	private final Context context;

	private ImageView thumbnail;
	private TextView title;
	private TextView teaser;
	private TextView payout;

	public OfferViewHolder(View itemView, Context context) {
		super(itemView);
		this.context = context;
		setupView(itemView);
	}

	private void setupView(View itemView) {
		thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
		title = (TextView) itemView.findViewById(R.id.title);
		teaser = (TextView) itemView.findViewById(R.id.teaser);
		payout = (TextView) itemView.findViewById(R.id.payout);
	}

	public void reUseView(Offer offer) {
		String thumbnailUrl = offer.getThumbnail();
		Ion.with(thumbnail).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).load(thumbnailUrl);
		title.setText(offer.getTitle().trim());
		teaser.setText(offer.getTeaser().trim());
		payout.setText(String.format(context.getString(R.string.payout), offer.getPayout()));
	}
}
