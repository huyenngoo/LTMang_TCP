/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
/**
 *
 * @author admin
 */
public class Dao {
    private Connection conn;
    private Statement stm;

    public Dao() {
        String dbUrl="jdbc:mysql://localhost:3306/exam";
        String dbClass="com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            conn= DriverManager.getConnection(dbUrl, "root", "050599");
            stm=conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 // tim kiem nguoi thoa man theo ten
    public List<User> search(String name){
        List<User> list= new ArrayList<>();
        String sql="SELECT*FROM user WHERE name LIKE '%"+name+"%'";
        System.out.println(sql);
        try {
            ResultSet rs= stm.executeQuery(sql);
            while (rs.next()) {                
                User u= new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // them User
    public boolean addUser(User u){
        String sql="INSERT INTO user(name, birth, address) VALUES ('"+u.getName()+"', '"
                +u.getBirth()+"', '"+u.getAddress()+"')";
        System.out.println(sql);
        try {
            if(stm.executeUpdate(sql)!=-1){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
//    // xoa
//    public boolean deleteU(int id){
//        String sql="DELETE*FROM user WHERE id="+id;
//        System.out.println(sql);
//        try {
//            if(stm.executeUpdate(sql)!=-1){
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
