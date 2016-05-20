package main.java.dao;

import java.sql.*;

public class GeneralDAO {

    public static Connection connection = null;
    static Statement stmt = null;
    PreparedStatement prepSt;
    static final String url = "jdbc:postgresql://localhost:5432/students";
    static final String JDBCdriver = "org.postgresql.Driver";
    static final String name = "postgres";
    static final String password = "123456";

    public GeneralDAO() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }

    public static void createConnection() throws SQLException {
        try {
            Class.forName(JDBCdriver);
            connection = DriverManager.getConnection(url, name, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createDatabase(String nameDB) throws SQLException {
        System.out.println("Створення бази даних...");
        stmt = connection.createStatement();
        String sql = "CREATE DATABASE " + nameDB;
        stmt.executeUpdate(sql);
        System.out.println("Базу даних успішно створено!");
    }

    public static void createTable(String tableName, String columnName, String dataType) throws SQLException {
        getConnection();
        stmt = connection.createStatement();
        String sql = "CREATE TABLE " + tableName + " (" + columnName + " " + dataType +  " " + "PRIMARY KEY)";
        stmt.executeUpdate(sql);
    }

    public static void addColumnToTable(String tableName, String columnName, String dataType) throws SQLException {
        getConnection();
        stmt = connection.createStatement();
        String sql = "ALTER TABLE " + tableName + " ADD " + columnName + " " + dataType;
        stmt.executeUpdate(sql);
    }

    public static void addForeignKey(String tableName, String columnName, String refTableName) throws SQLException {
        getConnection();
        stmt = connection.createStatement();
        String sql = "ALTER TABLE " + tableName + " ADD FOREIGN KEY (" + columnName + ") REFERENCES " + refTableName + "(" + columnName + ")";
        stmt.executeUpdate(sql);
    }

    public static void dropTable(String tableName) throws SQLException {
        getConnection();
        stmt = connection.createStatement();
        String sql = "DROP TABLE " + tableName;
        stmt.executeUpdate(sql);
    }

    public static void closeConnection() throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


}
