package com.sasi.acviewmodel.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sasi.acviewmodel.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sasikumar on 04/01/2018.
 */

public class ListFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tv_error)
    TextView tv_error;

    @BindView(R.id.loading_view)
    ProgressBar loading_view;

    private Unbinder unbinder;
    private ListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.screen_list, container, false);
        unbinder = ButterKnife.bind(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // Called immediately after onCreateView
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        // This method will listen/observe changes coming from LiveData.
        observeViewModel();
    }

    private void observeViewModel() {

        // LifeCycleOwner is fragment here. This means if the fragment is destroyed the ViewModel will be cleared to avoid leaks.

        // Before Lambda (< java 8)
//        viewModel.getRepos().observe(this, new Observer<List<Repo>>() {
//            @Override
//            public void onChanged(@Nullable List<Repo> repos) {
//
//            }
//        });

        // With Lambda (>= java 8)
        viewModel.getRepos().observe(this, repos -> {
            if(repos != null) {
                recycler_view.setVisibility(View.VISIBLE);

            }
        });

        viewModel.getError().observe(this, isError -> {
            if(isError) {
                tv_error.setVisibility(View.VISIBLE);
                tv_error.setText(R.string.api_error_repos);
                recycler_view.setVisibility(View.GONE);
            } else {
                tv_error.setVisibility(View.GONE);
                tv_error.setText(null);
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            if(isLoading) {
                loading_view.setVisibility(View.VISIBLE);
                recycler_view.setVisibility(View.GONE);
            } else {
                loading_view.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
