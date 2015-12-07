package com.zlate87.fyberapp.base;

import com.zlate87.fyberapp.feature.offers.service.OffersModule;

import javax.inject.Singleton;

/**
 * Dagger Component interface that should be used in the app.
 */
@Singleton
@dagger.Component(modules = OffersModule.class)
public interface FyberComponent extends Component {
}
