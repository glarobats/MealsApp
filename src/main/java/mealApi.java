import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;




public class mealApi {


    private static final Gson gson = new Gson();
    private static final OkHttpClient client = new OkHttpClient();

    public static Meal searchByName(String name) {
        try {
            // Create a request object
            Call call = mealClient.getSearch(name);

            // Execute the request using the OkHttp
            Response response = call.execute();

            // Get the response body as a string
            String responseString = response.body().string();

            // Use Gson to deserialize
            MealSearchResult result = gson.fromJson(responseString, MealSearchResult.class);

            // Check if the result contains any meals
            if (result.getMeals() != null && result.getMeals().size() > 0) {
                // Get the first meal from the result
                MealData mealData = result.getMeals().get(0);
                // Return a new Meal object created using the data from the first meal
                return new Meal(mealData.getStrMeal(), mealData.getStrCategory(), mealData.getStrArea(), mealData.getStrInstructions());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


class Meal {
    private String name;
    private String category;
    private String area;
    private String instructions;

    public Meal(String name, String category, String area, String instructions) {
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
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
        return "Meal [name=" + name + ", category=" + category + ", area=" + area + ", instructions=" + instructions + "]";
    }
}

class MealData {
    private String strMeal;
    private String strCategory;
    private String strArea;
    private String strInstructions;

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