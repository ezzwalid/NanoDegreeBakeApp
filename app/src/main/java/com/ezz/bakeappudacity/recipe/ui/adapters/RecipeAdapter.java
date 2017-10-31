package com.ezz.bakeappudacity.recipe.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.base.baseListners.RecyclerViewClickListener;
import com.ezz.bakeappudacity.recipe.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EzzWalid on 10/20/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    RecyclerViewClickListener clickListener;

    ArrayList<Recipe> recipes;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, null));
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bindData(recipes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_title)
        TextView recipeTitle;
        @BindView(R.id.recipeImage)
        ImageView recipeImage;
        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bindData(final Recipe recipe, final int positon){
            recipeTitle.setText(recipe.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null){
                        clickListener.onRecyclerItemClicked(recipe, positon);
                    }
                }
            });
            if (recipe.getImage() != null && !recipe.getImage().isEmpty()){
                Glide.with(itemView.getContext()).load(recipe.getImage()).into(recipeImage);
            }
        }
    }

    public RecyclerViewClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(RecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void updateData(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
