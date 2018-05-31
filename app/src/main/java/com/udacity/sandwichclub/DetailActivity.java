package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAsTV, placeOfOriginTV, descriptionTV, ingredientsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.app_bar_detailed);
        setSupportActionBar(toolbar);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTV = findViewById(R.id.also_known_tv);
        placeOfOriginTV = findViewById(R.id.origin_tv);
        descriptionTV = findViewById(R.id.description_tv);
        ingredientsTV = findViewById(R.id.ingredients_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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

    /**
     * Populated all the textViews with relevant data
     * @param sandwich which has the data to be populated in the text views
     */
    private void populateUI(Sandwich sandwich) {
        alsoKnownAsTV.setText(getFormattedList(sandwich.getAlsoKnownAs()));
        placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
        descriptionTV.setText(sandwich.getDescription());
        ingredientsTV.setText(getFormattedList(sandwich.getIngredients()));
    }

    /**
     *
     * @param list Takes in a list of string and returns a formatted form of that list removing brackets
     * @return a string
     */
    private String getFormattedList(List<String> list){
        String string = list.toString();
        return string.substring(1,string.length() - 1);
    }
}
