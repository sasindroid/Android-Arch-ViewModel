package com.sasi.acviewmodel.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.sasi.acviewmodel.home.ListViewModel;
import com.sasi.acviewmodel.home.SelectedRepoViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by sasikumar on 14/01/2018.
 */

// This module provides all of the ViewModel map values.
// Key: ViewModelKey eg: ListViewModel.class
// Value: Arguments eg: ListViewModel listViewModel

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectedRepoViewModel.class)
    abstract ViewModel bindSelectedRepoViewModel(SelectedRepoViewModel selectedRepoViewModel);
}
