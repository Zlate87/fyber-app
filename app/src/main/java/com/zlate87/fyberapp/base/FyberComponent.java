package com.zlate87.fyberapp.base;

import com.zlate87.fyberapp.feature.offers.service.OffersModule;

import javax.inject.Singleton;

/**
 * Created by Zlatko on 12/6/2015.
 */
@Singleton
@dagger.Component(modules = OffersModule.class)
public interface FyberComponent extends Component {
}
