package com.cameron.verticalphoto.modules.providers;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * <p>This class is a Roboguice provider for an Otto bus.</p>
 *
 * @author Pablo Sánchez Alonso
 * @version 1.0
 */
@Singleton
public class BusProvider implements Provider<Bus> {
    private final Bus bus = new Bus(ThreadEnforcer.ANY);
    @Override
    public Bus get() {
        return bus;
    }
}
