import org.database.Database;
public class Main {
    public static void main(String[] args) {
        mealsAppGui start = new mealsAppGui();
        mealAppApi mealAppApi = new mealAppApi();
        start.JFrameMain();
        Database db = new Database();
        db.malakies();
        
        //mealAppApi.getCategoryMeal();
        //mealAppApi.searchByName("Chicken");
        //mealAppApi.getMealsByCategory("Seafood");
        
    }
}
