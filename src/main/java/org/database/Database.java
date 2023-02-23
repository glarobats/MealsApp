package org.database;

import java.sql.*;

public class Database {

    public static void startDB() {
        createTables();
    }
    //Singleton για να υπάρχει μόνο ένα στιγμιότυπο της ΒΔ
    private static Database instance;
    private Database() {}
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    //σύνδεση με API
    public static Connection connect(){
        String connectionString = "jdbc:derby:mealsdb;create=true";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return connection;
    }//end Connection

    //δημιουργία πινάκων
   private static void createTables(){
        try{
                                        /*--------------ΠΑΡΑΔΟΧΗ----------------*/
            /*
            Κατασκευάστηκαν 3 πίνακες. Ο πίνακας CENTRAL και ο πίνακας SAVED ναι μεν είναι ίδιοι αλλά επειδή ο πίνακας SAVED
            μπορεί να δεχτεί τροποποιήσεις απο τον χρήστη καλό είναι να κρατάμε και τις αρχικές τιμές που ανακτήθηκαν απο
            το API σε περίπτωση που θα τις χρειαστούμε επίσης ο πίνακας CENTRAL χρειάζεται για τα στατιστικά καθώς αυτά δημιουργούνται
            απο τα γεύματα που έχουν προβληθεί, ένα γεύμα που έχει τροποποιηθεί ή σωθεί, έχει προβληθεί αλλά ένα γεύμα που έχει προβληθεί
            δεν είναι δεδομένο ότι έχει τροποποιηθεί ή σωθεί.
             */

            Connection connection = connect();
            Statement statement = connection.createStatement();
            //Δημιουργία πίνακα που αποθηκεύει ποια γεύματα έχουν προβληθεί
            String createSQL = "CREATE TABLE CENTRAL(ID INT NOT NULL, Όνομα VARCHAR(200),Κατηγορία VARCHAR(200),Περιοχή VARCHAR(200),Οδηγίες VARCHAR(10000), PRIMARY KEY(ID))";
            statement.executeUpdate(createSQL);

            //Δημιουργία του πίνακα εμφανίσεις ο οποίος μετράει τις προβολές του κάθε φαγητού
            String createViewsSQL = "CREATE TABLE VIEWS(ID INT NOT NULL, Εμφανίσεις INT NOT NULL, PRIMARY KEY (ID),FOREIGN KEY (ID) REFERENCES CENTRAL (ID))";
            statement.executeUpdate(createViewsSQL);

            //Δημιουργία πίνακα που αποθηκεύει ποια γεύματα έχουν αποθηκευτεί και υπάρχει περίπτωση να δεχτούν τροποποίηση
            String createSavedMealsSQL = "CREATE TABLE SAVED(ID INT NOT NULL, Όνομα VARCHAR(200),Κατηγορία VARCHAR(200),Περιοχή VARCHAR(200),Οδηγίες VARCHAR(10000), PRIMARY KEY(ID), FOREIGN KEY (ID) REFERENCES CENTRAL (ID))";
            statement.executeUpdate(createSavedMealsSQL);

            statement.close();
            connection.close();
        }catch (SQLException throwable){
            System.out.println(throwable.getLocalizedMessage());
        }
    }//end createTables

    //εισαγωγή του φαγητού στη ΒΔ
    public static void insMeal(int id, String name, String category, String area, String instruction) {
        try {
            Connection connection = connect();
            String insrtCntrlSQL = "INSERT INTO CENTRAL (ID, Όνομα, Κατηγορία, Περιοχή, Οδηγίες) VALUES (?,?,?,?,?)";
            String insrtVwsSQL = "INSERT INTO VIEWS (ID, Εμφανίσεις) VALUES (?,?)";

            PreparedStatement preparedStatementCentral = connection.prepareStatement(insrtCntrlSQL);
            PreparedStatement preparedStatementViews = connection.prepareStatement(insrtVwsSQL);

            preparedStatementCentral.setInt(1, id);
            preparedStatementCentral.setString(2, name);
            preparedStatementCentral.setString(3, category);
            preparedStatementCentral.setString(4, area);
            preparedStatementCentral.setString(5, instruction);

            preparedStatementViews.setInt(1, id);
            preparedStatementViews.setInt(2, 1);

            int countCentral = preparedStatementCentral.executeUpdate();
            int countViews = preparedStatementViews.executeUpdate();
            if (countCentral > 0 && countViews > 0) {
                System.out.println("Επιτυχής εγγραφή δεδομένων και στους δύο πίνακες");
            } else if (countCentral > 0) {
                System.out.println("Επιτυχής εγγραφή δεδομένων στον πίνακα CENTRAL");
            } else if (countViews > 0) {
                System.out.println("Επιτυχής εγγραφή δεδομένων στον πίνακα VIEWS");
            } else {
                System.out.println("ΑΠΟΤΥΧΙΑ ΕΓΓΡΑΦΗΣ ΣΤΟΥΣ ΠΙΝΑΚΕΣ");
            }
            connection.commit();
            preparedStatementCentral.close();
            preparedStatementViews.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }//end insMeal

    //αύξηση του κελιού "Εμφανίσεις" στον πίνακα VIEWS κάθε φορά που καλείται η μέθοδος για το συγκεκριμένο ID
    public static void incrementViews(int id) {
        try {
            Connection connection = connect();
            String updateSQL = "UPDATE VIEWS SET Εμφανίσεις = Εμφανίσεις + 1 WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, id);


            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("Επιτυχής ενημέρωση κελιού");
            } else {
                System.out.println("Μη επιτυχής ενημέρωση κελιού!!!!!!!!");
            }
            connection.commit();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    //αναζήτηση του γεύματος στη ΒΔ
    public static boolean idSearch(int id){
        try{
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String searchSQL = "SELECT * FROM CENTRAL WHERE ID = " + id;
            ResultSet resultSet = statement.executeQuery(searchSQL);
            boolean exist = resultSet.next();
            resultSet.close();
            statement.close();
            connection.close();
            return exist;
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
            return false;
        }
    }//end idSearch

    //εκκαθάριση της ΒΔ κατά το κλείσιμο
    public void deleteDB() {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM VIEWS");
            statement.executeUpdate("DELETE FROM SAVED");
            statement.executeUpdate("DELETE FROM CENTRAL");

            statement.executeUpdate("DROP TABLE VIEWS");
            statement.executeUpdate("DROP TABLE SAVED");
            statement.executeUpdate("DROP TABLE CENTRAL");
            connection.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }//end deleteDB

    //αντιγραφή του πίνακα CENTRAL στον πίνακα SAVED
    public static void saveToNewTable(int id) {
        try {
            ResultSet res = null;
            Connection connection = connect();
            PreparedStatement preparedStatement;
            String selectSql = "SELECT * FROM CENTRAL WHERE id = ?";
            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
                if (res.next()) {
                    String insertSql = "INSERT INTO SAVED (ID, Όνομα, Κατηγορία, Περιοχή, Οδηγίες) VALUES (?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertSql);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, res.getString("Όνομα"));
                    preparedStatement.setString(3,res.getString("Κατηγορία"));
                    preparedStatement.setString(4,res.getString("Περιοχή"));
                    preparedStatement.setString(5,res.getString("Οδηγίες"));
                    preparedStatement.executeUpdate();
                    connection.close();
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//end saveToNewTable

    //διαγραφή του πίνακα που έχει σωθεί
    public static void deleteSavedTable(int id) {
        try {
            Connection connection = connect();
            String deleteTable = "DELETE FROM SAVED WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteTable);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }//end deleteSavedTable

    //αναζήτηση στον πίνακα SAVED εάν υπάρχει το ID που αναζητούμε
    public static boolean idSearchInSAVED(int id){
        try{
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String searchSQL = "SELECT * FROM SAVED WHERE ID = " + id;
            ResultSet resultSet = statement.executeQuery(searchSQL);
            boolean exist = resultSet.next();
            resultSet.close();
            statement.close();
            connection.close();
            return exist;
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
            return false;
        }
    }//end idSearch

    //Ταξινόμηση πίνακα VIEWS απο τις περισσότερες εμφανίσεις στις λιγότερες
    public void orderBy() {
        Connection connection = connect();
        String orderDesc = "SELECT * FROM VIEWS ORDER BY Εμφανίσεις DESC";
        String print = "SELECT * FROM VIEWS";
        try {
            PreparedStatement statement = connection.prepareStatement(orderDesc);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("ID") + "\t" + resultSet.getString("Εμφανίσεις"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }//end orderBy
}//end databaseNew


