import org.database.Database;
public class Main {
    public static void main(String[] args) {
        mealsAppGui start = new mealsAppGui();
        start.JFrameMain();
        Database db = new Database();
        db.maladies();
    }
}