package com.shaghaf.thechef.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaghaf.thechef.R;
import com.shaghaf.thechef.model.RecipeIngredients;

import java.util.List;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientsViewHolder> {
    private List<RecipeIngredients> recipeIngredients;
public RecipeIngredientsAdapter(List<RecipeIngredients> ingredients)
{
    recipeIngredients= ingredients;
}

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.recipe_ingredients_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        RecipeIngredients recipeIngredient = recipeIngredients.get(position);
        String quantity = String.valueOf(recipeIngredient.getQuantity());
        String measure= recipeIngredient.getMeasure();
        String ingredient= recipeIngredient.getIngredient();
        String allText =quantity+" "+measure+" of "+ingredient;
        holder.textView.setText(allText);

    }

    @Override
    public int getItemCount() {
        return recipeIngredients.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        TextView textView ;
        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_ingredient_text);
        }
    }

}
