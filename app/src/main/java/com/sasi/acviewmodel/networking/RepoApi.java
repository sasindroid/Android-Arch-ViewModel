package com.sasi.acviewmodel.networking;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by sasikumar on 04/01/2018.
 */

public class RepoApi {

    private static final String BASE_URL = "https://api.github.com";

    private static Retrofit retrofit;
    private static RepoService repoService;

    public static RepoService getInstance() {

        if (repoService != null) {
            return repoService;
        }

        if (retrofit == null) {
            initializeRetrofit();
        }

        repoService = retrofit.create(RepoService.class);
        return repoService;
    }

    private static void initializeRetrofit() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build();
    }

    private RepoApi() {

    }
}
