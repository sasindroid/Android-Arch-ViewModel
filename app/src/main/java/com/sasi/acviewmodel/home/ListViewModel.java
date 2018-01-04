package com.sasi.acviewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.sasi.acviewmodel.model.Repo;
import com.sasi.acviewmodel.networking.RepoApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sasikumar on 04/01/2018.
 */

public class ListViewModel extends ViewModel {

    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private Call<List<Repo>> repoCall

    public ListViewModel() {
        fetchRepos();
    }

    LiveData<List<Repo>> getRepos() {
        return repos;
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    private void fetchRepos() {

        // Set an initial value.
        loading.setValue(true);

        // Get the Retrofit to return a callback.
        repoCall = RepoApi.getInstance().getRepositories();

        // Use the callback and make a network request.
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                repoLoadError.setValue(false);
                repos.setValue(response.body());
                loading.setValue(false);
                repoCall = null;
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "Error loading repos", t);
                repoLoadError.setValue(true);
                loading.setValue(false);
                repoCall = null;
            }
        });
    }

    // This method will be called when this ViewModel is not anymore in scope.
    @Override
    protected void onCleared() {
        super.onCleared();

        if(repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }
}