package com.ezz.bakeappudacity.ingredient.ui.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.base.baseListners.RecyclerViewClickListener;
import com.ezz.bakeappudacity.recipe.model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EzzWalid on 10/20/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    public IngredientAdapter(boolean tablet, ArrayList<Ingredient> ingredients) {
        this.tablet = tablet;
        this.ingredients = ingredients;
    }

    private boolean tablet;

    private ArrayList<Ingredient> ingredients;

    private RecyclerViewClickListener clickListener;

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, null));
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.bindData(ingredients.get(position), position);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_item_card)
        CardView cardView;
        @BindView(R.id.ingredient_title)
        TextView title;
        IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        void bindData(final Ingredient ingredient, final int position){
            if (tablet){
                if (ingredient.isSelected()){
                    cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
                }
                else {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.cardview_light_background));
                }
            }
            title.setText(ingredient.getIngredient());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tablet){
                        releaseAllSelectedItems();
                        ingredient.setSelected(true);
                        cardView.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
                    }
                    if (clickListener != null){
                        clickListener.onRecyclerItemClicked(ingredient, position);
                    }
                }
            });
        }
    }

    public RecyclerViewClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(RecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void updateData(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    private void releaseAllSelectedItems(){
        for (Ingredient ingredient : ingredients){
            ingredient.setSelected(false);
        }
    }
}
