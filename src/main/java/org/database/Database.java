package org.database;

import java.sql.*;
public class Database {
    public void maladies() {
        //createTable();
        //insMeal(1,"fakes","ospria","athina","maladies");
        //insMeal(2,"fakes","ospria","athina","malakies");
        //insMeal(3,"fakes","ospria","athina","malakies");
        selectAll();
    }

    private static Connection connenct(){
        String connectionString = "jdbc:derby:mealsdb;create=true";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return connection;
    }
    private static void createTable(){
        try{
            Connection connection = connenct();
            Statement statement = connection.createStatement();
            String createSQL = "CREATE TABLE CENTRAL" +
                    "(ID INTEGER NOT NULL PRIMARY KEY," +
                    "STRNAME VARCHAR(20)," +
                    "STRCATEGORY VARCHAR(20)," +
                    "STRAREA VARCHAR(20)," +
                    "STRINSTRUCTION VARCHAR(250))";
            statement.executeUpdate(createSQL);
            statement.close();
            connection.close();
        }catch (SQLException throwables){
            System.out.println(throwables.getLocalizedMessage());
        }
    }
    private static void selectAll(){
        try{
            Connection connection = connenct();
            Statement statement = connenct().createStatement();
            String selectSQL = "Select * from CENTRAL";
            ResultSet rs = statement.executeQuery(selectSQL);
            while(rs.next()){
                System.out.println(rs.getInt("ID")+","+rs.getString("STRNAME")+","+rs.getString("STRCATEGORY")+","+rs.getString("STRAREA")+","+rs.getString("STRINSTRUCTION"));
            }
            statement.close();
            connection.close();
            System.out.println("Done");
        }catch (SQLException throwables){
            System.out.println(throwables.getLocalizedMessage());
        }
    }

    private static void insMeal(int id,String name, String category,String area, String instruction){
        try{
            Connection connection = connenct();
            String insSQL = "Insert into CENTRAL values(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,category);
            preparedStatement.setString(4,area);
            preparedStatement.setString(5, instruction);
            int count = preparedStatement.executeUpdate();
            if (count>0){
                System.out.println(count + "Εγγραφή ενημερώθηκε");
            }else{
                System.out.println("Ανεπιτυχείς ενημέρωση δεδομένων");
            }
            preparedStatement.close();
            connection.close();
        }catch (SQLException throwables){
            System.out.println(throwables.getLocalizedMessage());
        }
    }
}