package com.zlate87.fyberapp.feature.offers.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zlate87.fyberapp.R;
import com.zlate87.fyberapp.feature.offers.model.Offer;

import java.util.List;

/**
 * An adapter responsible for handling the offers.
 */
public class OffersAdapter extends RecyclerView.Adapter<OfferViewHolder> {

	private final List<Offer> offers;
	private final Context context;

	/**
	 * Constructor.
	 *
	 * @param offers list of offers
	 */
	public OffersAdapter(List<Offer> offers, Context context) {
		this.offers = offers;
		this.context = context;
	}

	@Override
	public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		View offersListElement = layoutInflater.inflate(R.layout.offers_list_element, parent, false);
		return new OfferViewHolder(offersListElement, context);
	}

	@Override
	public void onBindViewHolder(OfferViewHolder holder, int position) {
		holder.reUseView(offers.get(position));
	}

	@Override
	public int getItemCount() {
		return offers.size();
	}
}
