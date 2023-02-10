package org.database;

import java.sql.*;

public class Database {

    public void startDB() {
        createTables();
    }

    private static Database instance;
    private Database() {}
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }



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

    private static void createTables(){
        try{
            Connection connection = connect();
            Statement statement = connection.createStatement();
            //Δημιουργία κεντρικού πίνακα
            String createSQL = "CREATE TABLE CENTRAL(ID INT NOT NULL, Όνομα VARCHAR(200),Κατηγορία VARCHAR(200),Περιοχή VARCHAR(200),Οδηγίες VARCHAR(10000), PRIMARY KEY(ID))";
            statement.executeUpdate(createSQL);

            //Δημιουργία του πίνακα εμφανίσεις
            String createViewsSQL = "CREATE TABLE VIEWS(ID INT NOT NULL, Εμφανίσεις INT NOT NULL, PRIMARY KEY (ID),FOREIGN KEY (ID) REFERENCES CENTRAL (ID))";
            statement.executeUpdate(createViewsSQL);
            statement.close();
            connection.close();
        }catch (SQLException throwable){
            System.out.println(throwable.getLocalizedMessage());
        }
    }//end createTables

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
                System.out.println("Data inserted into both CENTRAL and VIEWS tables successfully");
            } else if (countCentral > 0) {
                System.out.println("Data inserted into CENTRAL table successfully");
            } else if (countViews > 0) {
                System.out.println("Data inserted into VIEWS table successfully");
            } else {
                System.out.println("Failed to insert data into either CENTRAL or VIEWS table");
            }



            connection.commit();
            preparedStatementCentral.close();
            preparedStatementViews.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }//end insMeal

    public static void incrementViews(int id) {
        try {
            Connection connection = connect();
            String updateSQL = "UPDATE VIEWS SET Εμφανίσεις = Εμφανίσεις + 1 WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("Column Εμφανίσεις has been incremented successfully");
            } else {
                System.out.println("Failed to increment column Εμφανίσεις");
            }
            connection.commit();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }




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

    public void dropDatabase() {

        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String deleteSQL = "DROP TABLE  CENTRAL";
            String deleteSQL2 = "DROP TABLE VIEWS";
           // String constr = "ALTER TABLE VIEWS DROP FOREIGN KEY ID";

          //  statement.executeUpdate(constr);
            statement.executeUpdate(deleteSQL2);
            statement.executeUpdate(deleteSQL);
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }
    }
}//end databaseNew


