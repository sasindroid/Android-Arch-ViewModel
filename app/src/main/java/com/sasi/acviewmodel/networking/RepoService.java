package com.sasi.acviewmodel.networking;

import com.sasi.acviewmodel.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sasikumar on 04/01/2018.
 */

public interface RepoService {

    @GET("orgs/Google/repos")
    Call<List<Repo>> getRepositories();
}
