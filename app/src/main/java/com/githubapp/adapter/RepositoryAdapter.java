package com.githubapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.githubapp.R;
import com.githubapp.model.Repository;

import java.util.ArrayList;

/**
 * Created by Ravi on 20/03/2016.
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    public interface IClickListener {
        void OnClick(int position, View view);
    }

    public ArrayList<Repository> repositories;
    public static IClickListener clickListener;


    public RepositoryAdapter(ArrayList<Repository> repositories, IClickListener clickListener) {
        this.repositories = repositories;
        this.clickListener = clickListener;
    }


    @Override
    public RepositoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View repositoryView = inflater.inflate(R.layout.repository_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(repositoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RepositoryAdapter.ViewHolder holder, int position) {

        Repository repository = repositories.get(position);

        TextView repositoryTextView = holder.repositoryTextView;
        TextView languageTextView = holder.languageTextView;
        TextView viewTextView = holder.viewTextView;

        repositoryTextView.setText(repository.name + " " + repository.full_name);
        languageTextView.setText(repository.language);
        viewTextView.setText(repository.watchers);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView repositoryTextView, languageTextView, viewTextView;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);

            repositoryTextView = (TextView) itemView.findViewById(R.id.repositoryTextView);
            languageTextView = (TextView) itemView.findViewById(R.id.languageTextView);
            viewTextView = (TextView) itemView.findViewById(R.id.viewTextView);
            context = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null)
                        clickListener.OnClick(getLayoutPosition(), v);
                }
            });
        }

    }

}
