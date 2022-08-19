package com.farasatnovruzov.newsappjava.ui;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.farasatnovruzov.newsappjava.R;
import com.farasatnovruzov.newsappjava.databinding.ItemFavBinding;
import com.farasatnovruzov.newsappjava.model.Articles;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.parceler.Parcels;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavListAdapter extends ListAdapter<Articles,BaseViewHolder> {

    SharedPreferences sharedPreferences;
    ArrayList<Articles> favList = new ArrayList<>();
//    boolean removedItem = false;

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }


    public FavListAdapter(@NonNull DiffUtil.ItemCallback<Articles> diffCallback,Context context) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavBinding itemFavBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_fav,parent,false);
        return new ItemFavViewHolder(itemFavBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
//            position = holder.getAdapterPosition();
            holder.bindData(getItem(position));

//        notifyDataSetChanged();
//        article = Parcels.unwrap(getArguments().getParcelable("article"));
          holder.itemView.findViewById(R.id.ivFavArticleImage).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    if (isLongClick == false) { // this checks to see if it was long clicked
//                        // Perform your action here

                    getFavDetails(view, position);
                }
//                    isLongClick = false;
//                }
            });

        holder.itemView.findViewById(R.id.tvFavTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (isLongClick == false) { // this checks to see if it was long clicked
//                    // Perform your action here
                getFavDetails(view, position);
            }
//                isLongClick = false;
//            }
        });


        holder.itemView.findViewById(R.id.tvFavDescription).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (isLongClick == false) { // this checks to see if it was long clicked
//                    // Perform your action here
                getFavDetails(view, position);

            }
//                isLongClick = false;
//            }
        });














        holder.itemView.findViewById(R.id.ivFavArticleImage).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                isLongClick = true;
//                Toast.makeText(view.getContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
//                if (removedItem==false) {
                    showAlertDialog(holder,position);
//                    removeFavItem(holder,position);
//                }
//                removedItem = true;
                return true;
            }
        });


        holder.itemView.findViewById(R.id.tvFavTitle).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                isLongClick = true;
//                Toast.makeText(view.getContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
//                if (removedItem==false) {
                    showAlertDialog(holder,position);
//                    removeFavItem(holder,position);
//                }
//                removedItem = true;
                return true;
            }
        });


        holder.itemView.findViewById(R.id.tvFavDescription).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                isLongClick = true;
//                Toast.makeText(view.getContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
//                if (removedItem == false) {
                    showAlertDialog(holder,position);
//                    removeFavItem(holder,position);
//                }
//                removedItem = true;
                return true;
            }
        });

        holder.itemView.findViewById(R.id.tvFavAuthor).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                isLongClick = true;
//                Toast.makeText(view.getContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
//                if (removedItem == false) {
                    showAlertDialog(holder,position);
//                    removeFavItem(holder,position);
//                }
//                removedItem = true;
                return true;
            }
        });

        holder.itemView.findViewById(R.id.tvFavSource).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                isLongClick = true;
//                Toast.makeText(view.getContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
//                if (removedItem==false) {
                    showAlertDialog(holder,position);
//                    removeFavItem(holder,position);
//                }
//                removedItem = true;
                return true;
            }
        });
        holder.itemView.findViewById(R.id.tvFavSource).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                isLongClick = true;
//                Toast.makeText(view.getContext(), "Long Clicked", Toast.LENGTH_SHORT).show();

//                if (removedItem==false) {
                    showAlertDialog(holder,position);
//                    removeFavItem(holder,position);
//                }
//                removedItem = true;
                return true;
            }
        });


    }

    private void getFavDetails(View view, int position) {
        try{
            Bundle bundle = new Bundle();
            bundle.putBoolean("from_saved", true);
            bundle.putParcelable("article_from_fav", Parcels.wrap(getItem(position)));
            bundle.putInt("position", position);
            bundle.putString("from_fav_url", getItem(position).url);
            bundle.putString("title", getItem(position).title);
            bundle.putString("description", getItem(position).description);
            bundle.putString("publishedat", getItem(position).publishedAt);
            bundle.putString("url", getItem(position).urlToImage);
            System.out.println("clicked and sent article!");
            if (getItem(position).url != null) {
                Navigation.findNavController(view).navigate(R.id.action_favourite_to_details, bundle);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void showAlertDialog(RecyclerView.ViewHolder holder,int position){
        try{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(holder.itemView.getContext());
            builder1.setMessage("Are you sure to delete this item?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            removeFavItem(holder,position);
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    private void removeFavItem(RecyclerView.ViewHolder holder,int position) {
        try{
            Context context = holder.itemView.getContext();
            sharedPreferences = context.getApplicationContext().getSharedPreferences("favs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.remove("favs").apply();
            Gson gson = new Gson();
            String json = sharedPreferences.getString("favs", "");
            Type type = new TypeToken<ArrayList<Articles>>() {
            }.getType();
            favList = gson.fromJson(json, type);
            favList.remove(position);
            editor.clear().apply();
            String jsonAfterRemove = gson.toJson(favList);
            editor.putString("favs", jsonAfterRemove);
            editor.apply();
//        notifyItemRemoved(position);
//        notifyItemRangeRemoved(position, favList.size());
//        notifyItemRangeChanged(position, favList.size());
            notifyDataSetChanged();
            holder.itemView.setVisibility(View.GONE);
//        NewsFavFragment favFragment = new NewsFavFragment();
//        if (favFragment.getFragmentManager().beginTransaction()!=null){
//            favFragment.getFragmentManager().beginTransaction().detach(favFragment).commitNow();
//            favFragment.getFragmentManager().beginTransaction().attach(favFragment).commitNow();
//        }

//        favFragment.getActivity().recreate();
//        MainActivity activity = new MainActivity();
//        activity.recreate();
//        ((FavListAdapter)context).refreshActivtiy();
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra("for_back",true);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            Toast.makeText(holder.itemView.getContext(), "Article removed from favourite list", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
