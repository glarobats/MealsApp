package org.example;

import com.github.tsohr.JSONArray;
import com.github.tsohr.JSONException;
import com.github.tsohr.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


import java.io.IOException;

public class mealAppApi {

    public void searchByName(String name) {
        Call call = mealClient.get(name);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                if (responseString == null || responseString.equals("{\"meals\":null}")) {
                    System.out.println("Meal not available\n");
                } else {
                    try {
                        JSONObject json = new JSONObject(responseString);
                        JSONArray mealsArray = json.getJSONArray("meals");
                        JSONObject meal = mealsArray.getJSONObject(0);
                        String name = meal.getString("strMeal");
                        String category = meal.getString("strCategory");
                        String area = meal.getString("strArea");
                        String instructions = meal.getString("strInstructions");

                        System.out.println("Name: " + name + "\nCategory: " + category + "\nArea: " + area + "\nInstructions: " + instructions);
                        System.out.println("\n");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getCategoryMeal() {
        Call call = mealClient.getCategories();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        JSONObject json = new JSONObject(responseString);
                        JSONArray mealsArray = json.getJSONArray("meals");

                        System.out.println("Categories:");
                        for (int i = 0; i < mealsArray.length(); i++) {
                            JSONObject meal = mealsArray.getJSONObject(i);
                            String strCategory = meal.getString("strCategory");
                            System.out.println("- " + strCategory);

                        }
                        System.out.println("\n");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
        void getMealsByCategory(String category) {
            Call call = mealClient.getCategoriesChoose(category);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            String responseString = response.body().string();
                            JSONObject json = new JSONObject(responseString);
                            JSONArray mealsArray = json.getJSONArray("meals");
                            System.out.println("Meals:");
                            for (int i = 0; i < mealsArray.length(); i++) {
                                JSONObject meal = mealsArray.getJSONObject(i);
                                String strMeal = meal.getString("strMeal");
                                System.out.println("- " + strMeal);
                            }
                            System.out.println("\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
}

