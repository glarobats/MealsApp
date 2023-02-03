import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.*;
public class popUpListTree extends JFrame {
    public popUpListTree() {
        final String API_URL = "https://www.themealdb.com/api/json/v1/1/search.php?f=";
        final Gson GSON = new Gson();

        OkHttpClient client = new OkHttpClient();

        Map<String, List<getMealsFromApi>> categories = new HashMap<>();

        // make requests for each letter of the alphabet
        for (char letter = 'a'; letter <= 'z'; letter++) {
            Request request = new Request.Builder()
                    .url(API_URL + letter)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                MealResponse mealResponse = GSON.fromJson(response.body().string(), MealResponse.class);
                // store the meals by category
                if (mealResponse.getMeals() != null && !mealResponse.getMeals().isEmpty()) {

                    for (getMealsFromApi meal : mealResponse.getMeals()) {
                        String category = meal.getStrCategory();
                        List<getMealsFromApi> meals = categories.getOrDefault(category, new ArrayList<>());
                        meals.add(meal);
                        categories.put(category, meals);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error making API request: " + e.getMessage());
            }
        }

        // create a JTree with the categories and meals
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");
        for (Map.Entry<String, List<getMealsFromApi>> entry : categories.entrySet()) {
            DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(entry.getKey());
            root.add(categoryNode);
            for (getMealsFromApi meal : entry.getValue()) {
                DefaultMutableTreeNode mealNode = new DefaultMutableTreeNode(meal.getStrMeal());
                categoryNode.add(mealNode);
            }
        }
            JTree tree = new JTree(root);

            // create a popup window to display the JTree
            JFrame frame = new JFrame("Meals");
            frame.add(new JScrollPane(tree));
            frame.setSize(250, 600);
            frame.setLocation(1155,108);
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);

            //έλεγχος εαν το popUpWindow είναι ανοιχτό
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    closePopUpWindow();
                }
            });


    }

    //singleton pattern for popUp window
    private static popUpListTree instance;
    private static boolean isOpen = false;

    public static popUpListTree getInstance() {
        if (instance == null || !isOpen) {
            instance = new popUpListTree();
            isOpen = true;
        }
        return instance;
    }

    public void popUpWindow() {
        isOpen = true;
    }

    public void closePopUpWindow() {
        isOpen = false;
    }


    //-------------------------//

  //  public void popUpWindow() {}
}

class MealResponse {
    private List<getMealsFromApi> meals;

    public List<getMealsFromApi> getMeals() {
        return meals;
    }

    public void setMeals(List<getMealsFromApi> meals) {
        this.meals = meals;
    }
}

class getMealsFromApi {

    private String strMeal;
    private String strCategory;

    public String getStrMeal() {
        return strMeal;
    }
    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }
}
