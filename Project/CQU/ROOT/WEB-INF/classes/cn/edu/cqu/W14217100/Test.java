package cn.edu.cqu.W14217100;

import java.lang.*;
import java.io.*;
import java.sql.*;

public class Test {
    int num;

    public Test() {
        num = 777;
    }

    public int theNumber() {
        System.out.println("The number is "+num+".");
        return num;
    }

    public void sqliteTest() {
        String path     = this.getClass().getResource("/").getPath() + "../../../DATABASE/";
        File initFile   = new File(path+"INIT");
        if (!initFile.exists()) {
            return;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c    = DriverManager.getConnection("jdbc:sqlite:"+path+"/test.sqlite");
            System.out.println("Opened database successfully!");
            Statement stmt  = c.createStatement();
            String sql = "CREATE TABLE COMPANY "                +
                         "(ID INT PRIMARY KEY     NOT NULL,"    +
                         " NAME           TEXT    NOT NULL, "   + 
                         " AGE            INT     NOT NULL, "   + 
                         " ADDRESS        CHAR(50)"             +
                         ")";
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully!");
            stmt.close();
            c.close();
            initFile.delete();
        }catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }
}

