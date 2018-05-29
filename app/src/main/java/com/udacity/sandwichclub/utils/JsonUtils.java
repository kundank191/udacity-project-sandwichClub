package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_OBJECT = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String OTHER_NAME_ARRAY = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE_URL = "image";
    private static final String INGREDIENTS_ARRAY = "ingredients";

    /**
     *
     * @param json Contains all the data related to sandwich object
     * @return A sandwich object will all the data
     */
    public static Sandwich parseSandwichJson(String json) {
        Sandwich theSandwich = new Sandwich();
        try {
            JSONObject sandwichJSON = new JSONObject(json);

            //The main name and alsoKnownAs names are extracted here
            JSONObject nameObject = sandwichJSON.getJSONObject(NAME_OBJECT);
            String mainName = nameObject.getString(MAIN_NAME);
            JSONArray alsoKnownAs = nameObject.getJSONArray(OTHER_NAME_ARRAY);
            //All the alsoKnownAs names will be extracted from JSON array and then added to a List of String
            List<String> alsoKnownAsList = getStringListFromJSONArray(alsoKnownAs);
            //All the ingredients are extracted here
            JSONArray ingredients = sandwichJSON.getJSONArray(INGREDIENTS_ARRAY);
            List<String> ingredientsList = getStringListFromJSONArray(ingredients);
            String placeOfOrigin = sandwichJSON.getString(PLACE_OF_ORIGIN);
            String description = sandwichJSON.getString(DESCRIPTION);
            String imageUrl = sandwichJSON.getString(IMAGE_URL);
            //Adding all the extracted data into the sandwich object
            theSandwich.setAlsoKnownAs(alsoKnownAsList);
            theSandwich.setDescription(description);
            theSandwich.setImage(imageUrl);
            theSandwich.setMainName(mainName);
            theSandwich.setIngredients(ingredientsList);
            theSandwich.setPlaceOfOrigin(placeOfOrigin);
        } catch (JSONException e) {
            e.printStackTrace();
            theSandwich = null;
        }
        return theSandwich;
    }

    /**
     * Takes in a JSON array and returns a list of String
     * @param array the json array which is to be converted into a list of strings
     * @return the List<String> from the respective array
     * @throws JSONException
     */
    private static List<String > getStringListFromJSONArray(JSONArray array) throws JSONException {
        List<String> list = new ArrayList<>();
        //check if the array is empty or not
        if (array.length()  > 0) {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
        } else {
            //add a hyphen when nothing is present
            list.add("-");
        }
        return list;
    }
}
