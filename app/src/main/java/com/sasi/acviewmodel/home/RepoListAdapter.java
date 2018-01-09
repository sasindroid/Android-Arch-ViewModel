package com.sasi.acviewmodel.home;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sasi.acviewmodel.R;
import com.sasi.acviewmodel.model.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sasikumar on 05/01/2018.
 */

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder> {

    private static final String TAG = "RepoListAdapter";
    private final RepoSelectedListener repoSelectedListener;
    private List<Repo> data = new ArrayList<>();

    // We can use listViewModel to observe the LiveData.
    // We can use the lifecycleOwner to unbind from the observer when the owner is destroyed (fragment).
    public RepoListAdapter(ListViewModel listViewModel, LifecycleOwner lifecycleOwner, RepoSelectedListener repoSelectedListener) {
        this.repoSelectedListener = repoSelectedListener;

        listViewModel.getRepos().observe(lifecycleOwner, repos -> {

            data.clear();

            if (repos != null) {
                // Recycler view visibility is set in the fragment.

                data.addAll(repos);
            }

            notifyDataSetChanged();
        });

        setHasStableIds(true);
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repo_list_item, parent, false);
        return new RepoViewHolder(view, repoSelectedListener);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }

    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repo_name)
        TextView tv_repo_name;

        @BindView(R.id.tv_repo_description)
        TextView tv_repo_description;

        @BindView(R.id.tv_forks)
        TextView tv_forks;

        @BindView(R.id.tv_stars)
        TextView tv_stars;

        Repo repo;

        RepoViewHolder(View itemView, RepoSelectedListener repoSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                if (repo != null) {
                    repoSelectedListener.onRepoSelected(repo);
                }
            });
        }

        void bind(Repo repo) {
            this.repo = repo;
            tv_repo_name.setText(repo.name);
            tv_repo_description.setText(repo.description);
            tv_forks.setText(String.valueOf(repo.forks));
            tv_stars.setText(String.valueOf(repo.stars));
        }
    }
}
