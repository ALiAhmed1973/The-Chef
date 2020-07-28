package com.shaghaf.thechef.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaghaf.thechef.R;
import com.shaghaf.thechef.model.RecipeSteps;

import java.util.List;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailsAdapterViewHolder> {
    private List<RecipeSteps> recipeSteps;
    private RecipeDetailsAdapterOnClick detailsAdapterOnClick;

    public RecipeDetailsAdapter(List<RecipeSteps> steps,RecipeDetailsAdapterOnClick onClick) {
        recipeSteps = steps;
        detailsAdapterOnClick =onClick;
    }
    public interface RecipeDetailsAdapterOnClick {
        void onRecipeDetailsItemOnclick(int position);
    }
    @NonNull
    @Override
    public RecipeDetailsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutId = R.layout.recipe_details_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);
        return new RecipeDetailsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailsAdapterViewHolder holder, int position) {
        RecipeSteps step = recipeSteps.get(position);

        holder.textView.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return recipeSteps.size();
    }



    public class RecipeDetailsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;

        public RecipeDetailsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_recipe_step_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            detailsAdapterOnClick.onRecipeDetailsItemOnclick(getAdapterPosition());
        }
    }


}
