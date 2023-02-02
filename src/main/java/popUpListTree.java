import com.github.tsohr.JSONArray;
import com.github.tsohr.JSONException;
import com.github.tsohr.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.derby.impl.store.raw.data.EncryptOrDecryptData;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.IOException;

public class popUpListTree extends JFrame {

    //singleton pattern for popUp window
        private static popUpListTree instance;
        public static popUpListTree getInstance() {
            if (instance == null) {
                instance = new popUpListTree();
            }
            return instance;
        }



    public popUpListTree() {
        /* DefaultMutableTreeNode root = new DefaultMutableTreeNode("Meals");
        DefaultMutableTreeNode breakfast = new DefaultMutableTreeNode("Breakfast");
        DefaultMutableTreeNode lunch = new DefaultMutableTreeNode("Lunch");
        DefaultMutableTreeNode dinner = new DefaultMutableTreeNode("Dinner");
        root.add(breakfast);
        root.add(lunch);
        root.add(dinner);
        breakfast.add(new DefaultMutableTreeNode("Pancakes"));
        breakfast.add(new DefaultMutableTreeNode("Eggs Benedict"));
        breakfast.add(new DefaultMutableTreeNode("Bagel and Cream Cheese"));
        lunch.add(new DefaultMutableTreeNode("BLT Sandwich"));
        lunch.add(new DefaultMutableTreeNode("Grilled Cheese Sandwich"));
        lunch.add(new DefaultMutableTreeNode("Tuna Salad Sandwich"));
        dinner.add(new DefaultMutableTreeNode("Grilled Salmon"));
        dinner.add(new DefaultMutableTreeNode("Roasted Chicken"));
        dinner.add(new DefaultMutableTreeNode("Beef Stir Fry"));



        JTree tree = new JTree(root);
        add(tree);
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        */
    }


    public void popUpWindow() {
        Call call = mealClient.getCategories();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        JSONObject json = new JSONObject(responseString);
                        JSONArray mealsArray = json.getJSONArray("meals");

                        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");
                        for (int i = 0; i < mealsArray.length(); i++) {
                            JSONObject mealCat = mealsArray.getJSONObject(i);
                            DefaultMutableTreeNode node = new DefaultMutableTreeNode(mealCat.getString("strCategory"));
                            root.add(node);
                        }



                        JTree tree = new JTree(root);
                        add(tree);
                        setSize(250, 600);
                        setLocation(1155,108);
                        setVisible(true);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}

