package com.shaghaf.thechef.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shaghaf.thechef.MainActivity;
import com.shaghaf.thechef.R;
import com.shaghaf.thechef.StepDetailsActivity;
import com.shaghaf.thechef.model.Recipe;
import com.shaghaf.thechef.model.RecipeIngredients;
import com.shaghaf.thechef.model.RecipeSteps;

import java.util.ArrayList;
import java.util.List;



public class RecipeDetailsFragment extends Fragment implements RecipeDetailsAdapter.RecipeDetailsAdapterOnClick {

    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();

    private static final String RECIPE_BUNDLE_KEY = "recipe-bundle";
    private static final String RECIPE_STEPS_BUNDLE_KEY = "recipe-steps-bundle";

    private OnFragmentItemClickListener mListener;
    private Recipe recipe;
    private RecipeDetailsAdapter detailsAdapter;
    private List<RecipeSteps> steps;
    public RecipeDetailsFragment() {
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_details,container,false);

        if(savedInstanceState!=null)
        {
            recipe=savedInstanceState.getParcelable(RECIPE_BUNDLE_KEY);
            steps=savedInstanceState.getParcelableArrayList(RECIPE_STEPS_BUNDLE_KEY);
        }else
        {
            Bundle bundle = getArguments();
            if(bundle!=null)
            {
                recipe  = bundle.getParcelable(MainActivity.BUNDLE_EXTRA_KEY);
                steps = recipe.getRecipeSteps();

            }

        }
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_recipe_step_description);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        detailsAdapter = new RecipeDetailsAdapter(steps,this);
        recyclerView.setAdapter(detailsAdapter);



        return view;
    }

    @Override
    public void onRecipeDetailsItemOnclick(int position) {
          mListener.onItemSelected(steps.get(position),position);
    }


//
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      try {
          mListener = (OnFragmentItemClickListener) context;
      }catch (ClassCastException e)
      {
          throw new ClassCastException(context.toString()
                  +"must implement OnFragmentItemClickListener");
      }
    }



    public interface OnFragmentItemClickListener {
        void onItemSelected(RecipeSteps step,int position);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_BUNDLE_KEY,recipe);
        outState.putParcelableArrayList(RECIPE_STEPS_BUNDLE_KEY, (ArrayList<? extends Parcelable>) steps);
    }
}
