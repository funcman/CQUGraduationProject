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
                    // user
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
                    // article
                    st.executeUpdate("drop table if exists article");
                    st.executeUpdate("create table article (id integer primary key autoincrement, name string not null, content text not null)");
                    // list
                    st.executeUpdate("drop table if exists list");
                    st.executeUpdate("create table list (id integer primary key autoincrement, name string not null, parent integer)");
                    // drug
                    st.executeUpdate("drop table if exists drug");
                    st.executeUpdate("create table drug (id integer primary key autoincrement, name string not null, list integer not null, introduction text not null)");
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

    public Map getUser(String id) {
        HashMap<String,String> user = null;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from user where id="+id);
                    if (rs.next()) {
                        user = new HashMap<String,String>();
                        //user.put("id",              rs.getString("id"));
                        user.put("name",            rs.getString("name"));
                        user.put("pwd",             rs.getString("pwd"));
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
        return user;
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

    public boolean modifyUser(String id, String name, String pwd) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("update user set name='"+name+"', pwd='"+pwd+"' where id="+id) > 0) {
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

    public boolean deleteUser(String id) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from user where id="+id);
                    if (rs.next()) {
                        if (!rs.getString("name").equals("sa")) {
                            if (st.executeUpdate("delete from user where id="+id) > 0) {
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

    public boolean incUserRank(String id) {
        return this.setUserRank(id, "admin");
    }

    public boolean decUserRank(String id) {
        return this.setUserRank(id, "user");
    }

    private boolean setUserRank(String id, String rank) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("update user set rank='"+rank+"' where id="+id) > 0) {
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

    public Map[] allUsers() {
        ArrayList<Map> users = new ArrayList<Map>();
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from user");
                    while (rs.next()) {
                        HashMap<String,String> user = new HashMap<String,String>();
                        user.put("id",      rs.getString("id"));
                        user.put("name",    rs.getString("name"));
                        //user.put("pwd",     rs.getString("pwd"));
                        user.put("rank",    rs.getString("rank"));
                        users.add(user);                        
                    }
                    rs.close();
                    st.close();
                }
                c.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        Map usersArray[] = new Map[users.size()];
        usersArray = users.toArray(usersArray);
        return usersArray;
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

    public Map getArticle(String id) {
        HashMap<String,String> article = null;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from article where id="+id);
                    if (rs.next()) {
                        article = new HashMap<String,String>();
                        //article.put("id",       rs.getString("id"));
                        article.put("name",     rs.getString("name"));
                        article.put("content",  rs.getString("content"));
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
        return article;
    }

    public boolean addArticle(String name, String content) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("insert into article(name, content) values('"+name+"', '"+content+"')") > 0) {
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

    public boolean modifyArticle(String id, String name, String content) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("update article set name='"+name+"', content='"+content+"' where id="+id) > 0) {
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

    public boolean deleteArticle(String id) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("delete from article where id="+id) > 0) {
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

    public Map[] allArticles() {
        ArrayList<Map> articles = new ArrayList<Map>();
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from article");
                    while (rs.next()) {
                        HashMap<String,String> article = new HashMap<String,String>();
                        article.put("id",       rs.getString("id"));
                        article.put("name",     rs.getString("name"));
                        //article.put("content",  rs.getString("content"));
                        articles.add(article);
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
        Map articlesArray[] = new Map[articles.size()];
        articlesArray = articles.toArray(articlesArray);
        return articlesArray;
    }

    public Map getList(String id) {
        HashMap<String,String> list = null;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from list where id="+id);
                    if (rs.next()) {
                        list = new HashMap<String,String>();
                        //list.put("id",              rs.getString("id"));
                        list.put("name",            rs.getString("name"));
                        list.put("parent",          rs.getString("parent"));
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
        return list;
    }

    public boolean addList(String name, String parent) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("insert into list(name, parent) values('"+name+"', "+parent+")") > 0) {
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

    public boolean modifyList(String id, String name, String parent) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("update list set name='"+name+"', parent="+parent) > 0) {
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

    public boolean deleteList(String id) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("delete from list where id="+id) > 0) {
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

    public Map[] allLists() {
        ArrayList<Map> lists = new ArrayList<Map>();
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from list");
                    while (rs.next()) {
                        HashMap<String,String> list = new HashMap<String,String>();
                        list.put("id",              rs.getString("id"));
                        list.put("name",            rs.getString("name"));
                        //list.put("list",            rs.getString("list"));
                        //list.put("introduction",    rs.getString("introduction"));
                        lists.add(list);
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
        Map listsArray[] = new Map[lists.size()];
        listsArray = lists.toArray(listsArray);
        return listsArray;
    }

    public Map getDrug(String id) {
        HashMap<String,String> drug = null;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from drug where id="+id);
                    if (rs.next()) {
                        drug = new HashMap<String,String>();
                        //drug.put("id",              rs.getString("id"));
                        drug.put("name",            rs.getString("name"));
                        drug.put("list",            rs.getString("list"));
                        drug.put("introduction",    rs.getString("introduction"));
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
        return drug;
    }

    public boolean addDrug(String name, String list, String introduction) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("insert into drug(name, list, introduction) values('"+name+"', "+list+", '"+introduction+"')") > 0) {
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

    public boolean modifyDrug(String id, String name, String list, String introduction) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("update drug set name='"+name+"', list="+list+", introduction='"+introduction+"' where id="+id) > 0) {
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

    public boolean deleteDrug(String id) {
        boolean ret = false;
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    if (st.executeUpdate("delete from drug where id="+id) > 0) {
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

    public Map[] allDrugs() {
        ArrayList<Map> drugs = new ArrayList<Map>();
        try {
            Connection c = this.getConnection();
            if (c != null) {
                Statement st = this.getStatement(c);
                if (st != null) {
                    ResultSet rs = st.executeQuery("select * from drug");
                    while (rs.next()) {
                        HashMap<String,String> drug = new HashMap<String,String>();
                        drug.put("id",              rs.getString("id"));
                        drug.put("name",            rs.getString("name"));
                        //drug.put("list",            rs.getString("list"));
                        //drug.put("introduction",    rs.getString("introduction"));
                        drugs.add(drug);
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
        Map drugsArray[] = new Map[drugs.size()];
        drugsArray = drugs.toArray(drugsArray);
        return drugsArray;
    }
}

