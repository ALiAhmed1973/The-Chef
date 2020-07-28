package com.shaghaf.thechef.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaghaf.thechef.R;
import com.shaghaf.thechef.model.RecipeIngredients;

import java.util.ArrayList;
import java.util.List;


public class RecipeIngredientsFragment extends Fragment {
    private static final String INGREDIENTS_lIST_KEY ="ingredients-list";
    List<RecipeIngredients> ingredients;
    public RecipeIngredientsFragment() {

    }
    public RecipeIngredientsFragment(List<RecipeIngredients> recipeIngredientsList) {
         ingredients=recipeIngredientsList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null)
        {
            ingredients = savedInstanceState.getParcelableArrayList(INGREDIENTS_lIST_KEY);
        }

        View view =inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_ingredients);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
       recyclerView.setLayoutManager(linearLayoutManager);
        RecipeIngredientsAdapter adapter = new RecipeIngredientsAdapter(ingredients);
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INGREDIENTS_lIST_KEY, (ArrayList<? extends Parcelable>) ingredients);
    }
}
