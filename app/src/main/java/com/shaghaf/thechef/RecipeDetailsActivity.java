package com.shaghaf.thechef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shaghaf.thechef.fragment.RecipeDetailsAdapter;
import com.shaghaf.thechef.fragment.RecipeDetailsFragment;
import com.shaghaf.thechef.fragment.RecipeIngredientsFragment;
import com.shaghaf.thechef.fragment.StepDetailsFragment;
import com.shaghaf.thechef.model.Recipe;
import com.shaghaf.thechef.model.RecipeIngredients;
import com.shaghaf.thechef.model.RecipeSteps;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnFragmentItemClickListener,View.OnClickListener {
    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    public static final String INGREDIENTS_INTENT_EXTRA_KEY = "recipe-ingredients";
    public static final String STEPS_INTENT_BUNDLE_KEY = "recipe-steps";
    public static final String STEP_POSITION_BUNDLE_KEY = "step-position";
    public static final String STEPS_LIST_BUNDLE_KEY = "steps-list";
    private Recipe recipe;
    Button button;

    private boolean isTablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        button = findViewById(R.id.bt_recipe_ingredients);
        button.setOnClickListener(this);
        Intent intent =getIntent();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (intent.hasExtra(MainActivity.BUNDLE_EXTRA_KEY)) {

            recipe = intent.getParcelableExtra(MainActivity.BUNDLE_EXTRA_KEY);
            ChefService.startActionUpdateWidgets(this,recipe);
            setTitle(recipe.getName());

        }
        if(savedInstanceState==null) {

                Bundle bundle = new Bundle();
                bundle.putParcelable(MainActivity.BUNDLE_EXTRA_KEY, recipe);
                RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
                detailsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.recipe_details_container, detailsFragment).commit();


        }

        if(findViewById(R.id.step_details_container)!=null)
        {
            isTablet =true;

        }
        else {
            isTablet=false;
        }



    }



    @Override
    public void onItemSelected(RecipeSteps step, int position) {

        if(isTablet)
        {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            String videoUrl =null;
            if(step.getVideoUrl()!=null&&!step.getVideoUrl().isEmpty()) {
                videoUrl = step.getVideoUrl();
                stepDetailsFragment.setVideoOrNot(true);
            }else if(step.getThumbnailUrl()!=null&&!step.getThumbnailUrl().isEmpty())
            {
                videoUrl = step.getThumbnailUrl();
                stepDetailsFragment.setVideoOrNot(false);
            }
            String description = step.getDescription();
            stepDetailsFragment.setVideoString(videoUrl);
            stepDetailsFragment.setDescriptionString(description);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }else {
            StepDetailsActivity.name = recipe.getName();
            Bundle bundle = new Bundle();
            bundle.putInt(STEP_POSITION_BUNDLE_KEY,position);
            bundle.putParcelableArrayList(STEPS_LIST_BUNDLE_KEY, (ArrayList<? extends Parcelable>) recipe.getRecipeSteps());
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra(STEPS_INTENT_BUNDLE_KEY,bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if(isTablet)
        {
            List<RecipeIngredients> ingredients = recipe.getRecipeIngredients();
            RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment(ingredients);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_details_container, recipeIngredientsFragment)
                    .commit();
        }else {
            StepDetailsActivity.name = recipe.getName();
            int viewId = view.getId();
            if (viewId == R.id.bt_recipe_ingredients) {
                Intent intent = new Intent(this, StepDetailsActivity.class);
                intent.putExtra(INGREDIENTS_INTENT_EXTRA_KEY, recipe);
                startActivity(intent);
            }
        }
    }


}
