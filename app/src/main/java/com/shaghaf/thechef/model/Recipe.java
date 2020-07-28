package com.shaghaf.thechef.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements Parcelable {
    @SerializedName("name")
private String name;
    @SerializedName("ingredients")
private List<RecipeIngredients> recipeIngredients;
    @SerializedName("steps")
private List<RecipeSteps> recipeSteps;

    public Recipe(String name, List<RecipeIngredients> recipeIngredients, List<RecipeSteps> recipeSteps) {
        this.name = name;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        recipeIngredients = in.createTypedArrayList(RecipeIngredients.CREATOR);
        recipeSteps = in.createTypedArrayList(RecipeSteps.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(recipeIngredients);
        dest.writeTypedList(recipeSteps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getName() {
        return name;
    }

    public List<RecipeIngredients> getRecipeIngredients() {
        return recipeIngredients;
    }

    public List<RecipeSteps> getRecipeSteps() {
        return recipeSteps;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipeIngredients(List<RecipeIngredients> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public void setRecipeSteps(List<RecipeSteps> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }
}
