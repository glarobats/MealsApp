import org.database.Database;

public class Main {
    public static void main(String[] args) {

        mealsAppGui start = new mealsAppGui();
        mealClient MC = new mealClient();
        Database db = Database.getInstance();
        db.startDB();
        start.JFrameMain();
    }
}