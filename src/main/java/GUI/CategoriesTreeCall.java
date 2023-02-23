package GUI;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CategoriesTreeCall extends JFrame {
    private static CategoriesTreeCall instance;

    private CategoriesTreeCall() throws InterruptedException {
        final String API_URL = "https://www.themealdb.com/api/json/v1/1/search.php?f=";
        final Gson GSON = new Gson();

        OkHttpClient client = new OkHttpClient();
        //Χρήση Map για την εισαγωγή γεύματος σε κατηγορίες
        Map<String, List<getMealsFromApi>> categories = new HashMap<>();

        //Δημιουργία thread pool
        ExecutorService executor = Executors.newFixedThreadPool(26);

        //Κλήση του API με threads γιατί με σκέτο for loop καθυστερούσε, βελτίωση χρόνου Χ4 τουλάχιστον.
        //Γίνονται ταυτόχρονα 26 κλήσεις οπότε:
        //Μην κάνετε κατάχρηση του API call μην περάσει ο server του MealsDB την κλήση για DDOS Attack.
        for (char letter = 'a'; letter <= 'z'; letter++) {
            char finalLetter = letter;
            executor.submit(() -> {
                Request request = new Request.Builder()
                        .url(API_URL + finalLetter)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    assert response.body() != null;
                    MealResponse mealResponse = GSON.fromJson(response.body().string(), MealResponse.class);
                    //Αποθήκευση των γευμάτων ανα κατηγορία
                    if (mealResponse.getMeals() != null && !mealResponse.getMeals().isEmpty()) {

                        for (getMealsFromApi meal : mealResponse.getMeals()) {
                            String category = meal.getStrCategory();
                            // Χρήση της synchronize για εισαγωγή σε map με κλειδί την κατηγορία γεύματος και πεδίο το γεύμα
                            synchronized (categories) {
                                List<getMealsFromApi> meals = categories.getOrDefault(category, new ArrayList<>());
                                meals.add(meal);
                                categories.put(category, meals);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error making API request: " + e.getMessage());
                }
            });
        }
        //Κλείσιμο του executor αφού έχουν ολοκληρωθεί όλες οι παραπάνω εργασίες
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);


        mealsAppGui gui = mealsAppGui.getInstance();
        // Δημιουργία JTree με τις κατηγορίες και τα γεύματα
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");
        for (Map.Entry<String, List<getMealsFromApi>> entry : categories.entrySet()) {
            DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(entry.getKey());
            root.add(categoryNode);
            for (getMealsFromApi meal : entry.getValue()) {
                DefaultMutableTreeNode mealNode = new DefaultMutableTreeNode(meal.getStrMeal());
                categoryNode.add(mealNode);
            }
        }
        gui.getCategoriesTree().setModel(new DefaultTreeModel(root));

        //Δημιουργία JTree
        DefaultTreeModel model = new DefaultTreeModel(root);
        gui.getRightSidePanel().removeAll();
        gui.getCategoriesTree().setModel(model);
        gui.getRightSidePanel().setVisible(true);
        gui.getCategoriesPanel().setVisible(true);
        gui.getCategoriesTree().setVisible(true);
        JScrollPane scrollPane = new JScrollPane(gui.getCategoriesTree());
        gui.getRightSidePanel().add(scrollPane);
    }

    public static CategoriesTreeCall getInstance() throws InterruptedException {
        if (instance == null) {
            instance = new CategoriesTreeCall();
        }
        return instance;
    }
}


class MealResponse {
    private List<getMealsFromApi> meals;

    public List<getMealsFromApi> getMeals() {
        return meals;
    }
}

class getMealsFromApi {

    private String strMeal;
    private String strCategory;

    public String getStrMeal() {
        return strMeal;
    }
    public String getStrCategory() {
        return strCategory;
    }

}