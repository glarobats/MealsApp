

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class mealClient extends OkHttpClient {
    // Define BASE_URL and CATEGORIES_URL
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    private static final String CATEGORIES_URL = "https://www.themealdb.com/api/json/v1/1/list.php?c=list";

    private static final String CATEGORIESCHOOSE_URL = "https:www.themealdb.com/api/json/v1/1/filter.php?c=";

    private static mealClient singletonInstance;

    private static mealClient getInstance() {
        // Create a singleton instance
        // that extends the OkHttpClient
        if (singletonInstance == null) {
            singletonInstance = new mealClient();
        }
        return singletonInstance;
    }

    /**
     * Perform a GET request to a specific path
     *
     * @param path The path of the endpoint we're calling
     * @return
     */
    public static Call get(String path) {
        Request request = new Request.Builder().url(BASE_URL + path).get().build();
        return getInstance().newCall(request);
    }

    /**
     * Perform a GET request to the categories endpoint
     *
     * @return
     */
    public static Call getCategories() {
        Request request = new Request.Builder().url(CATEGORIES_URL).get().build();
        return getInstance().newCall(request);
    }
    /**
     * Perform a GET request to the categories_choose endpoint
     *
     * @return
     */
    public static Call getCategoriesChoose(String path) {
        Request request = new Request.Builder().url(CATEGORIESCHOOSE_URL+path).get().build();
        return getInstance().newCall(request);
    }
}
