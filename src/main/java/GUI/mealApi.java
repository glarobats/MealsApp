package GUI;

import okhttp3.Call;
import okhttp3.Response;
import com.google.gson.Gson;
import java.util.List;
import okhttp3.OkHttpClient;


public class mealApi {
    private static final Gson gson = new Gson();
    private static final OkHttpClient client = new OkHttpClient();

    public static Meal searchByName(String name) {
        try {
            // Φτιάξε ένα ανικείμενο request
            Call call = mealClient.getSearch(name);

            // Εκτέλεσε το request χρησιμοποιώντας OkHttp
            Response response = call.execute();

            // Αποθήκευσε σε String
            String responseString = response.body().string();
            MealSearchResult result = gson.fromJson(responseString, MealSearchResult.class);

            // Έλεγχος εάν είναι κενό
            if (result.getMeals() != null && result.getMeals().size() > 0) {
                MealData mealData = result.getMeals().get(0);
                return new Meal(mealData.getIdMeal(),mealData.getStrMeal(), mealData.getStrCategory(), mealData.getStrArea(), mealData.getStrInstructions());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


class Meal {
    private final String id, name, category, area, instructions;

    public Meal(String id,String name, String category, String area, String instructions) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return "Meal [id="+ id +", name=" + name + ", category=" + category + ", area=" + area + ", instructions=" + instructions + "]";
    }
}


class MealData {
    private String idMeal, strMeal, strCategory, strArea, strInstructions;

    public String getIdMeal() { return idMeal; }

    public String getStrId() {
        return idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }
}


class MealSearchResult {
    private List<MealData> meals;

    public List<MealData> getMeals() {
        return meals;
    }
}