package com.sasi.acviewmodel.details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sasi.acviewmodel.R;
import com.sasi.acviewmodel.base.MyApplication;
import com.sasi.acviewmodel.home.SelectedRepoViewModel;
import com.sasi.acviewmodel.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sasikumar on 06/01/2018.
 */

public class DetailsFragment extends Fragment {

    private Unbinder unbinder;

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.tv_repo_name)
    TextView tv_repo_name;
    @BindView(R.id.tv_repo_description)
    TextView tv_repo_description;
    @BindView(R.id.tv_forks)
    TextView tv_forks;
    @BindView(R.id.tv_stars)
    TextView tv_stars;

    SelectedRepoViewModel selectedRepoViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        MyApplication.getApplicationComponent(context).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.screen_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        selectedRepoViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(SelectedRepoViewModel.class);

        // Initially try to get from SavedInstance if savedInstance is not null.
        selectedRepoViewModel.restoreFromBundle(savedInstanceState);

        displayRepo();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        selectedRepoViewModel.saveToBundle(outState);
    }

    private void displayRepo() {

        selectedRepoViewModel.getSelectedRepo().observe(this, repo -> {
            if (repo != null) {
                tv_repo_name.setText(repo.name);
                tv_repo_description.setText(repo.description);
                tv_forks.setText(String.valueOf(repo.forks));
                tv_stars.setText(String.valueOf(repo.stars));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
