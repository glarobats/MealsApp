import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class popUpListTree extends JFrame {
    public popUpListTree() throws InterruptedException {
        final String API_URL = "https://www.themealdb.com/api/json/v1/1/search.php?f=";
        final Gson GSON = new Gson();

        OkHttpClient client = new OkHttpClient();
        //Χρήση Map για την εισαγωγή γεύματος σε κατηγορίες
        Map<String, List<getMealsFromApi>> categories = new HashMap<>();

        //Δημιουργία thread pool
        ExecutorService executor = Executors.newFixedThreadPool(26);

        //Κλήση του API με threads γιατί με σκέτο for loop καθυστερούσε, βελτίωση χρόνου Χ4 τουλάχιστον.
        //Γίνονται ταυτόχρονα 26 κλήσεις οπότε:
        //Μην κάνετε κατάχρηση του API call μην περάσει ο server του MealsDB την κλήση για DDOS Attack!!!!!
        for (char letter = 'a'; letter <= 'z'; letter++) {
            char finalLetter = letter;
            executor.submit(() -> {
                Request request = new Request.Builder()
                        .url(API_URL + finalLetter)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
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
            //Δημιουργία JTree
            JTree tree = new JTree(root);
            //Αλλαγή μεγέθους γραμμτοσειράς
            Font font = tree.getFont();
            font = font.deriveFont(16f); // change the font size to 16
            tree.setFont(font);
            // Δημιούργησε popup ώστε να εμφανιστεί στο JTree
            JFrame frame = new JFrame("Meals");
            ImageIcon image = new ImageIcon("logo.png");
            frame.setIconImage(image.getImage());
            frame.add(new JScrollPane(tree));
            frame.setSize(370, 600);
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

    //singleton pattern for popUp window μαζί με exception για την κλήση με τα threads
    private static popUpListTree instance;
    private static boolean isOpen = false;

    public static popUpListTree getInstance() throws InterruptedException {
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
    //Τέλος, singleton μαζί με έλεγχο
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
