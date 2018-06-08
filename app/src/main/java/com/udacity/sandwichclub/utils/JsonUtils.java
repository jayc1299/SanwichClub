package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String NAME_KEY = "name";
    private static final String MAIN_NAME_KEY = "mainName";
    private static final String ORIGIN_KEY = "placeOfOrigin";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "image";
    private static final String ALIAS_KEY = "alsoKnownAs";
    private static final String INGREDIENTS_KEY = "ingredients";

    public Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            //Get main json objects
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject(NAME_KEY);
            //get easy strings
            String mainName = name.getString(MAIN_NAME_KEY);
            String origin = jsonObject.getString(ORIGIN_KEY);
            String description = jsonObject.getString(DESCRIPTION_KEY);
            String image = jsonObject.getString(IMAGE_KEY);
            //now get the arrays
            JSONArray aliases = name.getJSONArray(ALIAS_KEY);
            JSONArray ingredients = jsonObject.getJSONArray(INGREDIENTS_KEY);

            sandwich = new Sandwich(mainName, convertJsonStringArrayToList(aliases), origin, description, image, convertJsonStringArrayToList(ingredients));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private ArrayList<String> convertJsonStringArrayToList(JSONArray array){
        ArrayList<String> convertedList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                convertedList.add(array.getString(i));
            } catch (JSONException e) {
                //Don't worry, we'll return an empty list
                Log.e(TAG, "convertJsonStringArrayToList: ", e);
            }
        }

        return convertedList;
    }
}