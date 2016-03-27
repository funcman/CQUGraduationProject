package cn.edu.cqu.W14217100;

import java.lang.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class DataManager {
    String databaseDirPath;

    public DataManager() {
        databaseDirPath = this.getClass().getResource("/").getPath() + "../../../DATABASE/";
    }

    private Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");        
            c = DriverManager.getConnection("jdbc:sqlite:"+databaseDirPath+"/database.sqlite");
        }catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
        return c;
    }

    private Statement getStatement(Connection c) {
        Statement st = null;
        try {
            st = c.createStatement();
        }catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
        return st;        
    }

    public void databaseInitialize() {
        File initFile   = new File(databaseDirPath+"INIT");
        if (!initFile.exists()) {
            return;
        }

        Connection c = null;
        try {
            c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    st.setQueryTimeout(30);
                    st.executeUpdate("drop table if exists user");
                    st.executeUpdate("create table user (id integer primary key autoincrement, name string not null, pwd string not null, rank string not null)");
                    st.executeUpdate("insert into user values(1, 'admin', 'admin777', 'sa')");
                    ResultSet rs = st.executeQuery("select * from user");
                    while (rs.next()) {
                        System.out.println("id = "+rs.getInt("id"));
                        System.out.println("name = "+rs.getString("name"));
                        System.out.println("pwd = "+rs.getString("pwd"));
                        System.out.println("rank = "+rs.getString("rank"));
                    }
                    initFile.delete();
                    rs.close();
                    st.close();
                }
                c.close();
            }
        }catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isValidUser(String name, String pwd) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from user where name='"+name+"'");
                    if (rs.next()) {
                        ret = rs.getString("pwd").equals(pwd);
                    }
                    rs.close();
                    st.close();
                }
                c.close();
            }
        }catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    public boolean isAdmin(String name) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from user where name='"+name+"'");
                    if (rs.next()) {
                        String rank = rs.getString("rank");
                        ret = rank.equals("admin") || rank.equals("sa");
                    }
                    rs.close();
                    st.close();
                }
                c.close();
            }
        }catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    public boolean addUser(String name, String pwd) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from user where name='"+name+"'");
                    if (!rs.next()) {
                        if (st.executeUpdate("insert into user(name, pwd, rank) values('"+name+"', '"+pwd+"', 'user')") > 0) {
                            ret = true;
                        }
                    }
                    rs.close();
                    st.close();
                }
                c.close();
            }
        }catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    public boolean deleteUser(String name) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from user where name='"+name+"'");
                    if (rs.next()) {
                        if (!rs.getString("name").equals("admin")) {
                            if (st.executeUpdate("delete from user where name='"+name+"'") > 0) {
                                ret = true;
                            }
                        }
                    }
                    rs.close();
                    st.close();
                }
                c.close();
            }
        }catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    public boolean setUserPwd(String name, String pwd) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("update user set pwd='"+pwd+"' where name='"+name+"'") > 0) {
                        ret = true;
                    }
                    st.close();
                }
                c.close();
            }
        }catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    public boolean incUserRank(String name) {
        return this.setUserRank(name, "admin");
    }

    public boolean decUserRank(String name) {
        return this.setUserRank(name, "user");
    }

    public boolean setUserRank(String name, String rank) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("update user set rank='"+rank+"' where name='"+name+"'") > 0) {
                        ret = true;
                    }
                    st.close();
                }
                c.close();
            }
        }catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    public String[] allAdmin() {
        ArrayList<String> users = new ArrayList<String>();
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from user where rank!='user'");
                    while (rs.next()) {
                        users.add(rs.getString("name"));
                    }
                    rs.close();
                    st.close();
                }
                c.close();
            }
        }catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
        String usersArray[] = new String[users.size()];
        usersArray = users.toArray(usersArray);
        return usersArray;
    }
}

