import org.database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main {
    public static void main(String[] args) {
      mealsAppGui start = new mealsAppGui();
      mealClient MC = new mealClient();
       start.JFrameMain();



        //Database db = new Database();
        //db.malakies();

        //mealAppApi mealAppApi = new mealAppApi();
        //mealAppApi.getCategoryMeal();
        //mealAppApi.searchByName("Chicken");
        //mealAppApi.getMealsByCategory("Seafood");
        
    }
}
