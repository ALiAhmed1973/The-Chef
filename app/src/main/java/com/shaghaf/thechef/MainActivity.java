package com.shaghaf.thechef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shaghaf.thechef.IdlingResource.SimpleIdlingResource;
import com.shaghaf.thechef.model.Recipe;
import com.shaghaf.thechef.utils.JsonApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>>,
        RecipeAdapter.RecipeAdapterOnClick {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BUNDLE_EXTRA_KEY = "recipe-list";
    private static final int LOADER_ID = 10;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ProgressBar progressBar;
    private TextView errorText;



    @Nullable
    private SimpleIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progress_loading);
        errorText=findViewById(R.id.tv_error_message);

        if (findViewById(R.id.recyclerview_main) != null) {
            recyclerView = findViewById(R.id.recyclerview_main);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

        } else {
            recyclerView = findViewById(R.id.recyclerview_main_land);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);

        }
        recyclerView.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(recipeAdapter);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);


    }

    private void showData() {
        errorText.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showError() {
        errorText.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(this) {
            List<Recipe> recipesList;


            @Override
            protected void onStartLoading() {
                if (recipesList != null && !recipesList.isEmpty()) {
                    deliverResult(recipesList);

                } else {
                    if(idlingResource!=null)
                    {
                        idlingResource.setIdleState(false);
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(@Nullable List<Recipe> data) {
                recipesList = data;
                super.deliverResult(data);
            }

            @Nullable
            @Override
            public List<Recipe> loadInBackground() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonApi jsonApi = retrofit.create(JsonApi.class);
                Call<List<Recipe>> call = jsonApi.getRecipes();
                try {
                    Response<List<Recipe>> response = call.execute();
                    if (response.isSuccessful()) {
                        List<Recipe> recipes = response.body();

                        return recipes;

                    } else {

                        return null;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };


    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        progressBar.setVisibility(View.INVISIBLE);
        if (data != null && !data.isEmpty()) {
            if(idlingResource!=null)
            {
                idlingResource.setIdleState(true);
            }

            showData();
            recipeAdapter.setRecipes(data);
        } else {
            showError();
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {
        recipeAdapter.setRecipes(null);
    }

    @Override
    public void onRecipeItemClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(BUNDLE_EXTRA_KEY, recipe);
        startActivity(intent);

    }

    @VisibleForTesting
    @NonNull
    public SimpleIdlingResource getIdlingResource() {
        if(idlingResource==null)
        {
            idlingResource= new SimpleIdlingResource();
        }
        return idlingResource;
    }
}
