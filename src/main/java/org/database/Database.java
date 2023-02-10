package org.database;

import java.sql.*;

public class Database {

    public void startDB() {
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
    private static Connection connect(){
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
            Connection connection = connect();
            Statement statement = connection.createStatement();
            //Δημιουργία κεντρικού πίνακα
            String createSQL = "CREATE TABLE CENTRAL(ID INT NOT NULL, Όνομα VARCHAR(200),Κατηγορία VARCHAR(200),Περιοχή VARCHAR(200),Οδηγίες VARCHAR(10000), PRIMARY KEY(ID))";
            statement.executeUpdate(createSQL);

            //Δημιουργία του πίνακα εμφανίσεις ο οποίος μετράει τις προβολές του κάθε φαγητού
            String createViewsSQL = "CREATE TABLE VIEWS(ID INT NOT NULL, Εμφανίσεις INT NOT NULL, PRIMARY KEY (ID),FOREIGN KEY (ID) REFERENCES CENTRAL (ID))";
            statement.executeUpdate(createViewsSQL);
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

    //αύξηση του κελιού "Εμφανίσεις" στον πίνακα VIEWS
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
    public boolean idSearch(int id){
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

    public static void deleteData() {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String deleteSQL = "TRUNCATE TABLE CENTRAL";
            statement.executeUpdate(deleteSQL);
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }//end deleteData

    //εκκαθάριση της ΒΔ κατά το κλείσιμο
    public void dropDatabase() {

        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String deleteSQL = "DROP TABLE  CENTRAL";
            String deleteSQL2 = "DROP TABLE VIEWS";

            statement.executeUpdate(deleteSQL2);
            statement.executeUpdate(deleteSQL);
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }//end dropDatabase
}//end databaseNew


