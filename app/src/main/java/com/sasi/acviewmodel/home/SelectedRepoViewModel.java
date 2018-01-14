package com.sasi.acviewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.util.Log;

import com.sasi.acviewmodel.model.Repo;
import com.sasi.acviewmodel.networking.RepoService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sasikumar on 06/01/2018.
 */

public class SelectedRepoViewModel extends ViewModel {

    private static final String TAG = "SelectedRepoViewModel";
    private final MutableLiveData<Repo> selectedRepo = new MutableLiveData<>();
    private final String REPO_DETAILS = "repo_details";
    private Call<Repo> repoCall;
    private RepoService repoService;

    @Inject
    public SelectedRepoViewModel(RepoService repoService) {
        this.repoService = repoService;
    }

    public LiveData<Repo> getSelectedRepo() {
        return selectedRepo;
    }

    void setSelectedRepo(Repo repo) {
        selectedRepo.setValue(repo);
    }

    public void restoreFromBundle(Bundle savedInstanceState) {

        // Try to retrieve from savedInstance only if its not null AND the selectedRepo.getValue is null.
        // We only care about restoring if we don't have a selected repo set already. i.e. if the app is kicked out of memory,
        // - then we will get a fresh ViewModel.
        if (selectedRepo.getValue() == null) {
            if (savedInstanceState != null && savedInstanceState.containsKey(REPO_DETAILS)) {

                // Get the owner and name saved into savedInstance and use it to query the api to get a Repo object.
                String[] repDetailsArray = savedInstanceState.getStringArray(REPO_DETAILS);

                if (repDetailsArray != null && repDetailsArray.length > 0) {
                    loadRepo(repDetailsArray[0], repDetailsArray[1]);
                }
            }
        }
    }

    // Ideally this can be retrieving data from a database or making a network call.
    private void loadRepo(String repoOwner, String repoName) {

        repoCall = repoService.getRepo(repoOwner, repoName);

        repoCall.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                setSelectedRepo(response.body());
                repoCall = null;
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {

                // Ideally the view should be updated here.
                Log.e(TAG, "Error retrieving Repo for " + repoOwner + "/" + repoName);
                repoCall = null;
            }
        });
    }

    public void saveToBundle(Bundle outState) {

        if (selectedRepo.getValue() != null) {
            outState.putStringArray(REPO_DETAILS,
                    new String[]{selectedRepo.getValue().owner.login, selectedRepo.getValue().name});
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }
}
