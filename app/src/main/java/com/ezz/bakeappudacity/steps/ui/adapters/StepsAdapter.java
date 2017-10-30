package com.ezz.bakeappudacity.steps.ui.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.base.baseListners.RecyclerViewClickListener;
import com.ezz.bakeappudacity.recipe.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EzzWalid on 10/20/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.IngredientViewHolder>{

    public StepsAdapter(boolean tablet, ArrayList<Step> steps) {
        this.tablet = tablet;
        this.steps = steps;
    }

    private boolean tablet;

    private ArrayList<Step> steps;

    private RecyclerViewClickListener clickListener;

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, null));
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.bindData(steps.get(position), position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
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
        void bindData(final Step step, final int position){
            if (tablet){
                if (step.isSelected()){
                    cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
                }
                else {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.cardview_light_background));
                }
            }
            title.setText(step.getShortDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tablet){
                        releaseAllSelectedItems();
                        step.setSelected(true);
                        cardView.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
                    }
                    if (clickListener != null){
                        clickListener.onRecyclerItemClicked(step, position);
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

    public void updateData(ArrayList<Step> ingredients){
        this.steps = ingredients;
        notifyDataSetChanged();
    }

    private void releaseAllSelectedItems(){
        for (Step step : steps){
            step.setSelected(false);
        }
        notifyDataSetChanged();
    }
}
