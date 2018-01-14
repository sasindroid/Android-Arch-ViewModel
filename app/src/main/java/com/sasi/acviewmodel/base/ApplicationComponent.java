package com.sasi.acviewmodel.base;

import com.sasi.acviewmodel.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sasikumar on 11/01/2018.
 */

@Singleton
@Component(modules = {
        NetworkModule.class,
})
public interface ApplicationComponent {

}
