package com.sasi.acviewmodel.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sasi.acviewmodel.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sasikumar on 06/01/2018.
 */

public class DetailsFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.tv_repo_name) TextView tv_repo_name;
    @BindView(R.id.tv_repo_description) TextView tv_repo_description;
    @BindView(R.id.tv_forks) TextView tv_forks;
    @BindView(R.id.tv_stars) TextView tv_stars;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.screen_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        displayRepo();
    }

    private void displayRepo() {

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
