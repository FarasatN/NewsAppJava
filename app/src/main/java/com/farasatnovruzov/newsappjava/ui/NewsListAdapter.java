package com.farasatnovruzov.newsappjava.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.databinding.ItemNewsBinding;
import com.farasatnovruzov.newsappjava.model.Articles;

import org.parceler.Parcels;

public class NewsListAdapter extends ListAdapter<Articles,BaseViewHolder> {


//    Glide.with(this).load(article.urlToImage).into(ivArticleImage)
//    tvSource.text = article.source.name
//    tvTitle.text = article.title
//    tvDescription.text = article.description
//    tvPublishedAt.text = article.publishedAt



    public NewsListAdapter(@NonNull DiffUtil.ItemCallback<Articles> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // based on the item viewtype we create viewholder
        // we have two viewholder for now : text and image
        // and don't forget that we are going to use data Binding feature to bind our data in the viewholder class
        // we need to configure our viewholders so they can accept a binding layout like so ...
        // new we can instantiate our viewholder safely


        ItemNewsBinding itemNewsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_news,parent,false);
        return new ItemViewHolder(itemNewsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bindData(getItem(position));
        holder.itemView.findViewById(R.id.ivArticleImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewsDetails(view, position, "position");
            }
        });

        holder.itemView.findViewById(R.id.tvTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewsDetails(view, position, "position");
            }
        });

        holder.itemView.findViewById(R.id.tvDescription).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewsDetails(view, position, "article");
            }
        });


        holder.itemView.findViewById(R.id.tvSource).setOnClickListener(new View.OnClickListener() {
            TextView urlText = holder.itemView.findViewById(R.id.tvSource);
//            Context context;
            @Override
            public void onClick(View view) {
                Intent openUrlIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlText.getText().toString()));
                openUrlIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(openUrlIntent);
            }
        });
    }

    private void getNewsDetails(View view, int position, String position2) {
        //                    Intent intent = new Intent(holder.itemView.getContext(),
//                            NewsDetailsFragment.class);
//                    intent.putExtra("message", getItem(position).url);
//                    holder.    startActivity(intent);
        Bundle bundle = new Bundle();
        bundle.putString("url", getItem(position).url);
        System.out.println("clicked!");
        bundle.putInt(position2, position);
        System.out.println("clicked!");
//                    bundle.putSerializable("article",getItem(position));
        bundle.putParcelable("article", Parcels.wrap(getItem(position)));
        System.out.println("clicked!");
//                    Actio action = FeedFragmentDirections.actionFeedFragmentToDetailsFragment()
//                    Navigation.findNavController(v).navigate(action)
        if (getItem(position).url != null) {
            Navigation.findNavController(view).navigate(R.id.action_home_to_details, bundle);
        }
    }
}
