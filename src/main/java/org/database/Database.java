package org.database;

import java.sql.*;
public class Database {

    public void malakies() {
        createTables();
        //insMeal(1,"fakes","ospria","athina","maladies");
        //insMeal(2,"fakes","ospria","athina","malakies");
        //insMeal(3,"fakes","ospria","athina","malakies");
        //selectAll();
        //deleteTables();
        //deleteRow(1);
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
    }
    private static void createTables(){
        try{
            Connection connection = connect();
            Statement statement = connection.createStatement();
            //Δημιουργία κεντρικού πίνακα
            String createSQL = "CREATE TABLE CENTRAL" +
                    "(ID INTEGER NOT NULL PRIMARY KEY," +
                    "Όνομα VARCHAR(20)," +
                    "Κατηγορία VARCHAR(20)," +
                    "Περιοχή VARCHAR(20)," +
                    "Οδηγίες VARCHAR(10000))";
            statement.executeUpdate(createSQL);

            /*Δημιουργία του πίνακα εμφανίσεις
            String createViewsSQL = "CREATE TABLE VIES" +
                    "(ID INTEGER NOT NULL PRIMARY KEY," +
                    "Όνομα VARCHAR(20)," +
                    "Εμφανίσεις INT," +
                    "FOREGN KEY (ID) REFERENCES CENTRAL(ID))";
            statement.executeUpdate(createViewsSQL);*/
            statement.close();
            connection.close();
        }catch (SQLException throwables){
            System.out.println(throwables.getLocalizedMessage());
        }
    }

    private static void deleteTables(){
        try{
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String deleteSQL = "DROP TABLE CENTRAL" ;
            //String deleteCategorySQL = "DROP TABLE CATEGORY";
            statement.executeUpdate(deleteSQL);
            //statement.executeUpdate(deleteCategorySQL);
            System.out.println("Οι πίνακες διαγράφηκαν με επιτυχία!");
            statement.close();
            connection.close();
        } catch (SQLException throwables){
            System.out.println(throwables.getLocalizedMessage());
        }
    }

    public static void selectAll(){
        try{
            Connection connection = connect();
            Statement statement = connect().createStatement();
            String selectSQL = "Select * from CENTRAL";
            ResultSet rs = statement.executeQuery(selectSQL);
            while(rs.next()){
                System.out.println(rs.getInt("ID")+","+rs.getString("Όνομα")+","+rs.getString("Κατηγορία")+","+rs.getString("Περιοχή")+","+rs.getString("Οδηγίες"));
            }
            statement.close();
            connection.close();
            System.out.println("Done");
        }catch (SQLException throwables){
            System.out.println(throwables.getLocalizedMessage());
        }
    }

    public static void selectByName(String name){
        try{
            Connection connection = connect();
            Statement statement = connect().createStatement();
            String selectSQL = "Select * from CENTRAL WHERE Όνομα='" + name + "'";
            ResultSet rs = statement.executeQuery(selectSQL);
            while(rs.next()){
                System.out.println(rs.getInt("ID")+","+rs.getString("Όνομα")+","+rs.getString("Κατηγορία")+","+rs.getString("Περιοχή")+","+rs.getString("Οδηγίες"));
            }
            statement.close();
            connection.close();
            System.out.println("Done");
        }catch (SQLException throwables){
            System.out.println(throwables.getLocalizedMessage());
        }
    }

    public static void insMeal(int id,String name, String category,String area, String instruction){
        try{
            Connection connection = connect();
            String insSQL = "Insert into CENTRAL (ID, Όνομα, Κατηγορία, Περιοχή, Οδηγίες) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,category);
            preparedStatement.setString(4,area);
            preparedStatement.setString(5, instruction);
            int count = preparedStatement.executeUpdate();
            if (count>0){
                System.out.println(count + " Εγγραφή ενημερώθηκε");
            }else{
                System.out.println("Ανεπιτυχείς ενημέρωση δεδομένων");
            }
            preparedStatement.close();
            connection.close();
        }catch (SQLException throwables){
            System.out.println(throwables.getLocalizedMessage());
        }
    }

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
        }
}