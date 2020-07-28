package com.shaghaf.thechef;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaghaf.thechef.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {
    private List<Recipe> recipes;
    private RecipeAdapterOnClick recipeAdapterOnClick;

   public RecipeAdapter(RecipeAdapterOnClick recipeOnClick)
   {
      recipeAdapterOnClick=recipeOnClick;
   }
   public interface RecipeAdapterOnClick
    {
      void onRecipeItemClick(Recipe recipe);
    }
    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId,parent,false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.recipeNameTextView.setText(recipe.getName());

    }

    @Override
    public int getItemCount() {
        if(recipes==null)
        {return 0;}
        return recipes.size();
    }


    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView recipeNameTextView ;
        public RecipeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = recipes.get(getAdapterPosition());
            recipeAdapterOnClick.onRecipeItemClick(recipe);
        }
    }

    public void setRecipes(List<Recipe> recipeList)
    {

            recipes = recipeList;
            notifyDataSetChanged();

    }
}
