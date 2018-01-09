package com.sasi.acviewmodel.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sasi.acviewmodel.R;
import com.sasi.acviewmodel.details.DetailsFragment;
import com.sasi.acviewmodel.model.Repo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sasikumar on 04/01/2018.
 */

public class ListFragment extends Fragment implements RepoSelectedListener {

    @BindView(R.id.recycler_view)
    RecyclerView listview;

    @BindView(R.id.tv_error)
    TextView tv_error;

    @BindView(R.id.loading_view)
    ProgressBar loading_view;

    private Unbinder unbinder;
    private ListViewModel viewModel;

    private final static String TAG = "ListFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "In onCreateView - Fragment - Recycler view");

        View view = inflater.inflate(R.layout.screen_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    // Called immediately after onCreateView
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "In onViewCreated");
        setupData();
    }

    private void setupData() {

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        // Add some dividers in the list.
        listview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Set adapter.
        listview.setAdapter(new RepoListAdapter(viewModel, this, this));
        listview.setLayoutManager(new LinearLayoutManager(getContext()));

        // This method will listen/observe changes coming from LiveData.
        observeViewModel();
    }

    @Override
    public void onRepoSelected(Repo repo) {

        // Get the ViewModel to the Activity scope.
        SelectedRepoViewModel selectedRepoViewModel = ViewModelProviders.of(getActivity()).get(SelectedRepoViewModel.class);
        selectedRepoViewModel.setSelectedRepo(repo);

        // Go to DetailsFragment
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.screen_container, new DetailsFragment())
                // Add the Listfragment to the backstack.
                .addToBackStack(null)
                .commit();
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
                Log.d(TAG, "repos size: " + repos.size());
                listview.setVisibility(View.VISIBLE);
                // Setting the data is done in the Adapter.
            }
        });

        viewModel.getError().observe(this, isError -> {

            Log.d(TAG, "Error: " + isError);

            if(isError) {
                tv_error.setVisibility(View.VISIBLE);
                tv_error.setText(R.string.api_error_repos);
                listview.setVisibility(View.GONE);
            } else {
                tv_error.setVisibility(View.GONE);
                tv_error.setText(null);
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            if(isLoading) {
                loading_view.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                tv_error.setVisibility(View.GONE);
            } else {
                loading_view.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
