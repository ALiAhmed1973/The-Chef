package com.shaghaf.thechef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shaghaf.thechef.fragment.RecipeIngredientsFragment;
import com.shaghaf.thechef.fragment.StepDetailsFragment;
import com.shaghaf.thechef.model.Recipe;
import com.shaghaf.thechef.model.RecipeIngredients;
import com.shaghaf.thechef.model.RecipeSteps;

import java.util.ArrayList;
import java.util.List;

public class StepDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = StepDetailsActivity.class.getSimpleName();
    private static final String RECIPE_STEPS_EXTRA_KEY = "com.shaghaf.thechef.recipe.steps";
    private static final String NUMBER_OF_INDEX_EXTRA_KEY = "com.shaghaf.thechef.number_of_index";
    FragmentManager fragmentManager;
    Button buttonPrev,buttonNext;
    List<RecipeSteps> recipeSteps;
    int numberOfIndex;
    public  static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        buttonNext = findViewById(R.id.bt_next);
        buttonPrev = findViewById(R.id.bt_prev);
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        setTitle(name);
        if(savedInstanceState==null) {
            Intent intent = getIntent();
            if (intent.hasExtra(RecipeDetailsActivity.STEPS_INTENT_BUNDLE_KEY)) {
                buttonNext.setVisibility(View.VISIBLE);
                buttonPrev.setVisibility(View.VISIBLE);
                Bundle bundle = intent.getBundleExtra(RecipeDetailsActivity.STEPS_INTENT_BUNDLE_KEY);
                numberOfIndex = bundle.getInt(RecipeDetailsActivity.STEP_POSITION_BUNDLE_KEY);
                recipeSteps = bundle.getParcelableArrayList(RecipeDetailsActivity.STEPS_LIST_BUNDLE_KEY);
                RecipeSteps step = recipeSteps.get(numberOfIndex);
                changeSteps(step);
            } else if (intent.hasExtra(RecipeDetailsActivity.INGREDIENTS_INTENT_EXTRA_KEY)) {
                buttonNext.setVisibility(View.GONE);
                buttonPrev.setVisibility(View.GONE);
                Recipe recipe = intent.getParcelableExtra(RecipeDetailsActivity.INGREDIENTS_INTENT_EXTRA_KEY);
                List<RecipeIngredients> ingredients = recipe.getRecipeIngredients();
                RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment(ingredients);
                fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.step_details_container, recipeIngredientsFragment)
                        .commit();
            }
        }else
        {
            recipeSteps = savedInstanceState.getParcelableArrayList(RECIPE_STEPS_EXTRA_KEY);
            numberOfIndex = savedInstanceState.getInt(NUMBER_OF_INDEX_EXTRA_KEY);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.bt_prev)
        {
            if(numberOfIndex>0)
            {
                numberOfIndex--;
                RecipeSteps step = recipeSteps.get(numberOfIndex);
                changeSteps(step );
            }
        }else if(id==R.id.bt_next)
        {
            if(numberOfIndex<recipeSteps.size()-1)
            {
                numberOfIndex++;
                RecipeSteps step = recipeSteps.get(numberOfIndex);
                changeSteps(step);
            }
        }
    }

    private void changeSteps(RecipeSteps step) {
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

        String videoUrl = null;
        if (step.getVideoUrl() != null && !step.getVideoUrl().isEmpty()) {
            videoUrl = step.getVideoUrl();
            stepDetailsFragment.setVideoOrNot(true);
        } else if (step.getThumbnailUrl() != null && !step.getThumbnailUrl().isEmpty()) {
            videoUrl = step.getThumbnailUrl();
            stepDetailsFragment.setVideoOrNot(false);
        }
        String description = step.getDescription();

        stepDetailsFragment.setVideoString(videoUrl);
        stepDetailsFragment.setDescriptionString(description);

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, stepDetailsFragment)
                .commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NUMBER_OF_INDEX_EXTRA_KEY,numberOfIndex);
        outState.putParcelableArrayList(RECIPE_STEPS_EXTRA_KEY, (ArrayList<? extends Parcelable>) recipeSteps);
    }
}
