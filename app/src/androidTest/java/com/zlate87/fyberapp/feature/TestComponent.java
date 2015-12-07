package com.zlate87.fyberapp.feature;

import com.zlate87.fyberapp.feature.offers.controller.OffersActivityTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Zlatko on 12/6/2015.
 */
@Singleton
@Component(modules = MockOffersModule.class)
public interface TestComponent extends com.zlate87.fyberapp.base.Component {
	void inject(OffersActivityTest offersActivityTest);
}
