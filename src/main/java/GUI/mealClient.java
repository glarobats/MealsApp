package GUI;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class mealClient extends OkHttpClient {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";

    private static mealClient singletonInstance;

    //Χρήση singleton
    private static mealClient getInstance() {
        //Χρήση singleton που επεκτείνει τον OkHttpClient
        if (singletonInstance == null) {
            singletonInstance = new mealClient();
        }
        return singletonInstance;
    }

    //Δημιουργία GET request
    public static Call getSearch(String path) {
        Request request = new Request.Builder().url(BASE_URL + path).get().build();
        return getInstance().newCall(request);
    }

}
