package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        JsonUtils jsonUtils = new JsonUtils();
        Sandwich sandwich = jsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView originTv = findViewById(R.id.origin_tv);
        TextView descTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView aliasTv = findViewById(R.id.also_known_tv);

        originTv.setText(sandwich.getPlaceOfOrigin());
        descTv.setText(sandwich.getDescription());

        //Ingredients
        if(sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            ingredientsTv.setText(cleanList(sandwich.getIngredients()));
        }

        //Also known as
        if(sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            aliasTv.setText(cleanList(sandwich.getAlsoKnownAs()));
        }
    }

    /**
     * Clean the list, add commas so it looks good.
     * @param values array list of strnigs
     * @return string of comma seperated values.
     */
    private String cleanList(List<String> values){
        StringBuilder valuesStringBuilder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            valuesStringBuilder.append(values.get(i));
            //Don't add
            if(i != values.size() - 1){
                valuesStringBuilder.append(", ");
            }
        }

        return valuesStringBuilder.toString();
    }
}
