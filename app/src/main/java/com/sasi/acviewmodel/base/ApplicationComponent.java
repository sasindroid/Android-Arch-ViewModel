package com.sasi.acviewmodel.base;

import com.sasi.acviewmodel.details.DetailsFragment;
import com.sasi.acviewmodel.home.ListFragment;
import com.sasi.acviewmodel.networking.NetworkModule;
import com.sasi.acviewmodel.viewmodel.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sasikumar on 11/01/2018.
 */

@Singleton
@Component(modules = {
        NetworkModule.class,
        ViewModelModule.class,
})
public interface ApplicationComponent {

    void inject(ListFragment listFragment);

    void inject(DetailsFragment detailsFragment);
}
