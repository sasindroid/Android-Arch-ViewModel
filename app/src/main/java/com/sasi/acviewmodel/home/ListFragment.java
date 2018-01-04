package com.sasi.acviewmodel.home;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.screen_list, container, false);
        unbinder = ButterKnife.bind(view);
        return super.onCreateView(inflater, container, savedInstanceState);
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
