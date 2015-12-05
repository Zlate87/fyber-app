package com.zlate87.fyberapp.feature.offers.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zlate87.fyberapp.R;
import com.zlate87.fyberapp.feature.offers.model.Offer;

/**
 * View holder class responsible for an offer.
 */
public class OfferViewHolder extends RecyclerView.ViewHolder {

	private TextView title;

	public OfferViewHolder(View itemView) {
		super(itemView);
		setupView(itemView);
	}

	private void setupView(View itemView) {
		title = (TextView) itemView.findViewById(R.id.title);
	}

	public void reUseView(Offer offer) {
		title.setText(offer.getTitle());
	}
}
