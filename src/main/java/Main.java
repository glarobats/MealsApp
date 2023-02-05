import org.database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main {
    public static void main(String[] args) {
     // mealsAppGui start = new mealsAppGui();
      mealClient MC = new mealClient();

      // start.JFrameMain();

        Meal meal = mealApi.searchByName("chicken");

        if (meal != null) {
            System.out.println("id: " + meal.getId());
            System.out.println("name: " + meal.getName());
            System.out.println("category: " + meal.getCategory());
            System.out.println("instructions: " + meal.getInstructions());
        } else {
            System.out.println("No meal found.");
        }

        if (meal != null) {
            System.out.println(meal);
        } else {
            System.out.println("No meal found");
        }
    }

    }


        //Database db = new Database();
        //db.malakies();

        //mealAppApi mealAppApi = new mealAppApi();
        //mealAppApi.getCategoryMeal();
        //mealAppApi.searchByName("Chicken");
        //mealAppApi.getMealsByCategory("Seafood");
        


